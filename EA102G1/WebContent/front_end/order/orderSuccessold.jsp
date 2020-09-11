<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import = "com.prod_order.model.OrderVO" %>
  <%@ page import = "com.membre.model.MembreVO" %>
    <%@ page import = "com.prod.model.ProdVO" %>
     <%@ page import = "java.util.*" %>
   <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<jsp:include page="/front_end/prod/head.jsp">
<jsp:param name="title" value="Order success"/>
</jsp:include>
<style>
.myBg {
background-color:#cce6ff;
}
.btn {
	padding-top:0;
	padding-bottom:0;
}
.myFlex  div {
 width:80px;
}
form {
margin-block-end:0;
}
</style>
</head>
<body>
<%@ include file="/front_end/prod/header.jsp" %>
<%@ include file="/front_end/prod/header_bottom.jsp"%>
<jsp:useBean id = "orderSvc" class="com.prod_order.model.OrderService"/>
<jsp:useBean id="prodSvc" class="com.prod.model.ProdService"/>
<%
MembreVO membrevo = (MembreVO) session.getAttribute("membrevo");
String membre_id = membrevo.getMembre_id();
OrderVO ordervo = orderSvc.getLatestOrder(membre_id); 
pageContext.setAttribute("ordervo", ordervo);
String order_no = ordervo.getOrder_no();

List<ProdVO> prodList = prodSvc.getAllByOrder_no(order_no);
pageContext.setAttribute("prodList", prodList);
%>
<div class="accordion container mx-auto mt-5 pt-5" id="accordionExample" >
<h2 class="ml-5">下訂成功!</h2>
<div class="col-md-12">
  <jsp:useBean id="membreSvc" class="com.membre.model.MembreService"/>
<div class="accordion container mx-auto mt-2" id="accordionExample" >
<div class="col-md-12">
  <div class="card myCard">
  <p style="display:none">${ordervo.order_no }</p>
    <div class="card-header d-flex justify-content-start myParent myBg align-items-center" id="headingOne" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
    <p style="display:none" class="target">${membreSvc.getSeller(ordervo.order_no).getMembre_id()}</p>
    	<span style="display:inline" class="ml-3">
	    	<svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-caret-up-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
			  <path d="M7.247 4.86l-4.796 5.481c-.566.647-.106 1.659.753 1.659h9.592a1 1 0 0 0 .753-1.659l-4.796-5.48a1 1 0 0 0-1.506 0z"/>
			</svg>
		</span>
 		<span style="display:none" class="ml-3">
 			<svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-caret-down-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
			  <path d="M7.247 11.14L2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z"/>
			</svg>
 		</span>
          <span class="col-3 text-center">訂單編號:${ordervo.order_no}</span><span class="col-2">總金額:${ordervo.total}</span><span >下訂時間:<fmt:formatDate value="${ordervo.order_date}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
          <span class="col-3 text-center">賣家:${membreSvc.getSeller(ordervo.order_no).getMem_name()}</span>
    </div>
    <div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordionExample">
	
    <c:forEach var="prodvo" items="${prodList}">
    <div class="w-100 d-flex justify-content-start align-items-center myFlex">
    
								<div class="img-wrap mx-3"><img src="<%= request.getContextPath() %>/prod/prod.do?action=getOne&prod_no=${prodvo.prod_no}&number=one" class="border img-sm"></div>
								<div class="prod_name align-middle text-center" style="width:15%">
									${prodvo.prod_name }
								</div>
								<div class="align-middle">
									<div class="price-wrap">$<span class="price px-1">${prodvo.price }</span></div>
								</div>
								
								<div class="w-25 ml-5">
									訂購數量 :<span class="price px-1">${orderSvc.getOneDetail(prodvo.prod_no, ordervo.order_no).order_qty }</span>
								</div>
								</div>
      </c:forEach>
    </div>
    </div>
  </div>
</div>
	

    </div>
  </div>
</body>
<script>
function init() {
	var order_no = "${ordervo.order_no}";
	var receiver = "${membreSvc.getSeller(ordervo.order_no).getMembre_id()}";
	var sendNotice = function (){

		var jsonObj = {
				"type":"new",
				"order_no": order_no,
				"receiver": receiver,
				"order_status":"1"
		}
		this.send(JSON.stringify(jsonObj));
	}();
	
	  var card_headers = document.getElementsByClassName("card-header");
	  for (let i = 0; i < card_headers.length; i++) {

		  card_headers[i].addEventListener("click", function(e) {
			  var up = this.closest(".card-header").children[1];
			  var down = this.closest(".card-header").children[2];
			 if(up.getAttribute("style") == "display:inline"){
				 up.setAttribute("style", "display:none");
				 down.setAttribute("style", "display:inline");
			 } else {
				 up.setAttribute("style", "display:inline");
				 down.setAttribute("style", "display:none");
			 }
		  })
	  }
}
window.addEventListener("load", init);
</script>
</html>