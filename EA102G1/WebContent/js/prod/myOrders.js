function init() {
	  var card_headers = document.getElementsByClassName("card-header");
	  for (let i = 0; i < card_headers.length; i++) {

		  card_headers[i].addEventListener("click", function(e) {
			  var up = this.closest(".card-header").children[0];
			  var down = this.closest(".card-header").children[1];
			 if(up.getAttribute("style") == "display:inline"){
				 up.setAttribute("style", "display:none");
				 down.setAttribute("style", "display:inline");
			 } else {
				 up.setAttribute("style", "display:inline");
				 down.setAttribute("style", "display:none");
			 }
		  })
	  }
	  
	  let confirm = document.getElementById("confirm");
	  let myBtn = document.getElementsByClassName("myBtn");
	  let modalTitle = document.getElementsByClassName("modal-title")[0];
	  var order_no="";
	  var myTarget="";
	  var order_status="";
	 
	  var dontSend = false;
	  var innerStatus="";
	  function getInnerStatus(order_no) {
		  var xhr = new XMLHttpRequest();
		  xhr.onload  = function(e) {
			  if (xhr.status == "200"){
				  innerStatus = xhr.responseText;
			  }
		  }
		  var data = "action=getInnerStatus&order_no="+order_no;
		  var ajaxUrl = path+"/order/order.do";
		  xhr.open("post", ajaxUrl, false);
		  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		  xhr.send(data);
	  }
	  
	  
	  
	  
	  for (let i = 0; i < myBtn.length; i++) {
		  myBtn[i].addEventListener("click", function(e) {
			  $('#orderModal').modal('show');
			  e.stopPropagation();
			   modalTitle.innerText = e.srcElement.innerText + "?";
			   order_no = e.srcElement.getAttribute("data-order_no");
			   myTarget = e.srcElement.getAttribute("data-myTarget");
			   var status = e.srcElement.textContent;
			   var url="";
			  getInnerStatus(order_no);
			  
				  if (status==="確認取貨") {
					  order_status="3";
					  url=path+"/order/order.do?action=getDifferentStatusOrders_buyer&order_status=3";
				  }
				  if (status==="取消訂單") {
					  console.log(innerStatus);
					  order_status="0";
					  url=path+"/order/order.do?action=getDifferentStatusOrders_buyer&order_status=0";
					  if (innerStatus == "2"){
						  url=path+"/order/order.do?action=getDifferentStatusOrders_buyer&order_status=2"
						  dontSend = true;
					  }
				  }
				  console.log(url);
				  confirm.setAttribute("href", url);

		  }) 
	  }
	  
		  confirm.addEventListener("click", function(e){
			  if (!dontSend) {
				  sendNotice(myTarget, order_no, order_status);
			  }
			  ajaxChangeStatus(order_status, order_no);
		  })
	   function sendNotice(target, order_no, order_status) {
		   var jsonObj = {
				   "type":"notice",
				   "order_no":order_no,
				   "receiver":target,
				   "order_status":order_status
		   }
			   this.send(JSON.stringify(jsonObj));
	   }
		  
		  function ajaxChangeStatus(order_status, order_no){
			  var xhr = new XMLHttpRequest();
			  var url = path+"/order/order.do";
			  var data="action=changeStatus&order_no="+order_no+"&order_status="+order_status;
			  xhr.open("post", url, true);
			  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			  xhr.send(data);
		  }

		  var navBar = document.getElementById("myNav");
		  var navs = navBar.children;
		  var hr = document.createElement("hr");
		  hr.setAttribute("color", "blue");
		  var order_status = document.getElementById("order_status").textContent;
		  var index=0;
		  switch (order_status) {
				  case"All":
					  index=0;
				  	  break;
				  case"1":
					  index=1;
				      break;
				  case"2":
					  index=2;
				      break;
				  case"3":
					  index=3;
				      break;
				  case"0":
					  index=4;
				      break;
		  }
		  navs[index].children[0].appendChild(hr);
	      navs[index].children[0].classList.add("text-primary");
}

window.addEventListener("load", init);