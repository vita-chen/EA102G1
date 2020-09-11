function init() {
	var path = document.getElementById("path").innerText;
	console.log(path);
	//抓取結帳要用的資訊
	var prices = document.getElementsByClassName("price");
	var total = document.getElementById("total");
	var orderButton = document.getElementById("order");
	orderButton.addEventListener("click", sendOrder);
	var quantities = document.getElementsByClassName("quantity");
	
	function sendOrder() {
		var prod_nos = document.getElementsByClassName("prod_no");
		var jsonObj = {};
		var jsonArr = [];
		for (let i = 0; i < quantities.length; i++) {
				var prodvo = {
				prod_no : prod_nos[i].innerText,
				quantity : quantities[i].value	
			}
			jsonArr.push(JSON.stringify(prodvo));
		}
		jsonObj.prodvos = jsonArr;
		jsonObj.total = total.innerText;
		ajaxOrder(JSON.stringify(jsonObj));
		location.href=path+"/front_end/order/wait_order.jsp";
		
	}
	
	function ajaxOrder(data) {
		var xhr = new XMLHttpRequest();
		var url = path + "/order/order.do";
		xhr.open("post", url, true);
		xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded");
		
		var data = "action=order&json="+data;
		xhr.send(data);
	}

	//刪除按鈕
	var deleteButton = document.getElementsByClassName("deleteButton");
	for (let i = 0; i < deleteButton.length; i++) {
		deleteButton[i].addEventListener("click", function(e) {
			var prod_no = this.closest(".myParent").children[0].textContent;
			//移除tr
			e.target.closest(".productItems").remove();
	 		ajaxDeleteCart(prod_no);
	 		//計算總額
	 		getPrice();
	 		getCartNum();
		});
	}

	var amounts = document.getElementsByClassName("amount");
	//改變數量就更新總額
	for (let i = 0; i < quantities.length; i++) {
		quantities[i].addEventListener("change", getPrice);
	}

	function getPrice() {
		let totalPrice = 0;
		for (let i = 0; i <quantities.length; i++) {
			var qty = Number(quantities[i].value);
			var price = Number(prices[i].innerText);
			amounts[i].innerText = qty * price;
			totalPrice += (qty*price);
		}
		total.innerText = totalPrice;
	};
	getPrice();


	//加入下次再買
	var addListButton = document.getElementsByClassName("addList");
	for (let i = 0; i < addListButton.length; i++) {
		addListButton[i].addEventListener("click", function(e) {
			var prod_no = this.closest(".myParent").children[0].textContent;
			//加入下次再買
			ajaxAddList(prod_no);
			//從購物車中移除
			ajaxDeleteCart(prod_no);
			//移除tr
			e.target.closest(".productItems").remove();
			//計算總額
			getPrice();
			getCartNum();
		});
	}

	function ajaxAddList(prod_no) {
		var xhr = new XMLHttpRequest();
		var url = path + "/prod/list.do";
		xhr.open("post", url, true);
		xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded");
		
		let data = "action=add&prod_no="+prod_no;
		console.log(data);
		xhr.send(data);
	}

	function ajaxDeleteCart(prod_no) {
		var xhr = new XMLHttpRequest();
		var url = path + "/order/shopping.do";
		xhr.open("post", url, true);
		xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded");
		
		let data="action=delete&prod_no="+prod_no;
		xhr.send(data);
	}
	var cartNum = document.getElementById("shopping_cart_num");
	var getCartNum = function() {
		var xhr = new XMLHttpRequest();
		xhr.onload = function() {
			if (xhr.status == 200) {
				cartNum.innerText = xhr.responseText;
			}
		}
		var url = path + "/order/shopping.do?action=getCartNum";
		xhr.open("get", url, true);
		xhr.send(null);
	};
	getCartNum();
} 
window.addEventListener('load', init);