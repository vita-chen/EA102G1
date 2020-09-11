	var tbody = document.querySelector('#tableSort'); //抓到大肚子 整個 tbody 包含tr td
	var thead = document.querySelectorAll('#tableSort thead tr th'); //找出三個 標題
	var tr = document.querySelectorAll('#tableSort tbody tr');//三行內容
	
	/*
	    flag 旗幟 本身為 0 或 1 的數值 
	   	 在這邊用 1 跟 -1 代表正向排序(1) 與逆向排序(-1)
	*/
	
	for (var i = 0; i < thead.length; i++) {
	    thead[i].flag = 1;
	    thead[i].onclick = function() {
	        sortA(this.dataset.type, this.flag, this.cellIndex);
	        this.flag = -this.flag; //每按一次就切換一次        
	        return;
	    };
	};
	
	function sortA(str, flag, n) {   
	    var newArray = [];
	    for (var i = 0; i < tr.length; i++) { //每一行都放進陣列
	        newArray.push(tr[i]);
	    }    
	    newArray.sort(function(a, b) { // 得到新的排序完的資料
	        return method(a.cells[n].innerText, b.cells[n].innerText, str) * flag;
	    });
	
	    for (var i = 0; i < newArray.length; i++) {
	        tbody.appendChild(newArray[i]); //大肚子重新裝 排序完的資料
	    };
	    return;
	};
	
	function method(a, b, str) { //參數順序重要
	    switch (str) {
	        case 'num':
	            return a - b;
	        case 'string':
	            return a.localeCompare(b, "zh-Hant");
	        default:
	            return new Date(a.split('-').join('/')).getTime() - new Date(b.split('-').join('/')).getTime();
	    };
	}
	
	/*	
	 	表格排序 參考網址 : https://codertw.com/%E5%89%8D%E7%AB%AF%E9%96%8B%E7%99%BC/247444/
		練習,修改時需注意 是否產生無窮迴圈 電腦變熱 變吵
		參數位置需注意 順序錯誤會跑不出結果 排序函式 a,b一定要在最前面
		表格id="tableSort" ; 欲排序的欄位標題加上對應屬性
		data-type="num"
		data-type="string"
		data-type="date"
	*/
	
	var tablerow = document.getElementsByClassName("tablerow");

    var tr = document.querySelectorAll("tbody tr"); //抓取所有內容行 
    var num = tr.length; //表格所有行數 
    var ary = [];
    for (var i = 0; i < num.length; i++) {
        ary.push(num[i]);
    }

    /*--------設定每頁顯示行數------*/
    var pageSize = 5;         //*
    /*-------------------------*/

    var totalPage = Math.ceil(num / pageSize); //算出總共有幾頁
    var str = '';
    for (var i = 0; i < totalPage; i++) {       //產生對應按鈕數
        str += `<li class="page-item" value="${i+1}"><a class="page-link">${i+1}</a></li>`;
    }
    $(".Next").before(str);

    var btn = document.querySelectorAll('.page-item'); //每個按鈕註冊除了第一個跟最後一個
    for (var i = 1; i < btn.length - 1; i++) {
        btn[i].addEventListener('click', goPage.bind(this, i));
    }
    var previous = document.querySelector('.Previous'); //第一頁按鈕 各別註冊
    previous.addEventListener('click', goPage.bind(this, 1));
    var next = document.querySelector('.Next'); 		//最末頁按鈕 各別註冊
    next.addEventListener('click', goPage.bind(this, totalPage));


    function goPage(page) { 			//切換頁數函式 page > 當前頁數

        var startRow = (page - 1) * pageSize + 1; //開始顯示的行
        var endRow = page * pageSize; 		//結束顯示的行
        endRow = (endRow > num) ? num : endRow; //num > 表格所有行數

        //遍歷顯示資料實現分頁
        for (var i = 1; i < (num + 1); i++) {
            var trrow = tr[i - 1];
            if (i >= startRow && i <= endRow) {
                trrow.style.display = "table-row";
            } else {
                trrow.style.display = "none";
            }
        }
    }
    
    goPage(1);
    

    /*
            
            按鈕註冊 第一Previous 最末Next

    <div class="content_2">
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <li class="page-item Previous"><a class="page-link" tabindex="-1">Previous</a></li>
                <li class="page-item Next"><a class="page-link">Next</a></li>
            </ul>
        </nav>
    </div>


    */
