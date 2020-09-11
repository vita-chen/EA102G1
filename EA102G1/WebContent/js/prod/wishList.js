function init() {
	var cartButs = document.getElementsByClassName("cartBut");
	var removeListButs = document.getElementsByClassName("removeList");
	var path = document.getElementById("path").innerText;
	
	for (let i = removeListButs.length-1; i >= 0; i--) {
		removeListButs[i].addEventListener("click", function(e){
			console.log(this.closest(".myParent").children[0].textContent);
	 		ajaxRemoveList(this.closest(".myParent").children[0].textContent);
			e.target.closest(".myrow").remove();
		});
	}

	function ajaxRemoveList (prod_no) {
		var xhr = new XMLHttpRequest();
		var url = path+"/prod/list.do"
		xhr.open("post", url, true);
		xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded");
		let data="action=delete&prod_no="+prod_no;
		xhr.send(data);
	}


	for (let i = 0 ; i < cartButs.length; i++) {
		cartButs[i].addEventListener("click", function(e){
			var prod_no = this.closest(".myParent").children[0].textContent;
			ajaxAddCart(prod_no);
			ajaxRemoveList(prod_no);
			e.target.closest(".myrow").remove();
		});
	}
	var cartNum = document.getElementById("shopping_cart_num");
	function ajaxAddCart (prod_no) {
		var xhr = new XMLHttpRequest();
		xhr.onload =function() {
			if (xhr.status == 200) {
				cartNum.innerText = xhr.responseText;
			}
		}
		var url = path + "/order/shopping.do";
		xhr.open("post", url, true);
		xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded");
		let data = "action=addToCart&prod_no="+prod_no;
		xhr.send(data);
	}
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
	}();
}

window.addEventListener("load", init);