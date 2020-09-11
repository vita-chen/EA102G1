function init() {
//	var path = document.getElementById("path").innerText;
//	console.log(path);
	var prod_nos = document.getElementsByClassName("prod_no");
	var emptyHearts = document.getElementsByClassName('emptyHeart');
	var fullHearts = document.getElementsByClassName('fullHeart');
	var membre_id = document.getElementById('currentUser').innerText;
	
	var ajaxGetFollowList = function () {
		if (membre_id === undefined){
			return;
		}
		var xhr = new XMLHttpRequest();
		xhr.onload =function() {
			if (xhr.status == 200) {
				changeHeart(xhr.responseText);
			}
		}
		var url = path+"/prod/list.do?action=getFollowList&membre_id="+membre_id;
		xhr.open("GET", url, true);
		xhr.send(null);
	}();

	function changeHeart(list) {
		for (var i = 0; i < prod_nos.length; i++) {
			if(list.indexOf(prod_nos[i].innerText) != -1) {
				emptyHearts[i].setAttribute('display', 'none');
				fullHearts[i].setAttribute('display', 'inline');
			}
		}
	}
	
	var addListButs = document.getElementsByClassName('addList');
	for (let i = 0; i < addListButs.length; i++) {
		addListButs[i].addEventListener('click', function(e){
			this.children[0].setAttribute('display', 'none');
			this.children[1].setAttribute('display', 'inline');
			var prod_no = prod_nos[i].innerText;
			console.log(prod_no);
			ajaxAddList(prod_no);
		})
	}
	function ajaxAddList(prod_no) {
		var xhr = new XMLHttpRequest();
		var url = path + "/prod/list.do";
		xhr.open("post", url , true);
		xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded");
		let data = "action=add&prod_no="+prod_no;
		xhr.send(data);
	}	
	var cartButs = document.getElementsByClassName("cartBut");

	for (let i = 0 ; i < cartButs.length; i++) {
		cartButs[i].addEventListener("click", function(e){
			ajaxAddCart(e.srcElement.getAttribute("data-prod_no"));
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
window.addEventListener('load', init);