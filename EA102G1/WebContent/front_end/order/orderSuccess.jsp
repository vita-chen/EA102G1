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
<meta charset="BIG5">
<jsp:include page="/front_end/prod/head.jsp">
<jsp:param name="title" value="Order success"/>
</jsp:include>
<style>

#order_master {
box-sizing: border-box;
box-shadow: 1px 1px 1px 2px #aaaaaa;
border-radius:5px;
}
#order_detail, #total{
	box-sizing: border-box;
	border-radius:5px;
}
.table th, .table td {
 vertical-align:middle;
}
</style>
</head>
<jsp:useBean id = "orderSvc" class="com.prod_order.model.OrderService"/>
<jsp:useBean id="prodSvc" class="com.prod.model.ProdService"/>
<jsp:useBean id="membreSvc" class="com.membre.model.MembreService"/>
<%
MembreVO membrevo = (MembreVO) session.getAttribute("membrevo");
String membre_id = membrevo.getMembre_id();
OrderVO ordervo = orderSvc.getLatestOrder(membre_id); 
pageContext.setAttribute("ordervo", ordervo);
String order_no = ordervo.getOrder_no();

List<ProdVO> prodList = prodSvc.getAllByOrder_no(order_no);
pageContext.setAttribute("prodList", prodList);
%>
<body>
<%@ include file="/front_end/prod/header.jsp" %>
<%@ include file="/front_end/prod/header_bottom.jsp"%>
<div style="height:10%"></div>
<div class="container accordion mx-auto"  id="myContainer">
<h4>親愛的<span class="text-primary">${membrevo.mem_name }</span> 您好，請確認下列購物資訊是否正確</h4>
<div style="height:2%"></div>
<div class="w-100" id="order_master"><span class="pl-2">訂單編號：<span class="text-info">${ordervo.order_no }</span></span></div>
<div style="height:2%"><p style="display:none" class="target">${membreSvc.getSeller(ordervo.order_no).getMembre_id()}</p>
</div>
<div class="bg-info w-100 text-white" id="order_detail"><h3 class="py-2 ml-3">購物明細</h3></div>


<table class="table table-borderless table-shopping-cart">
<thead class="text-muted">

<tr class="text-uppercase">
  <th scope="col">商品名稱</th>
  <th scope="col" width="120">數量</th>
  <th scope="col" width="120">價格</th>
  <th scope="col" width="120">小計</th>
</tr>
</thead>
<tbody>
	<c:forEach var="prodvo" items="${prodList }">
<tr>
	<td>
		<figure class="itemside">
			<div class="aside"><img src="<%= request.getContextPath() %>/prod/prod.do?action=getOne&prod_no=${prodvo.prod_no}&number=one" class="img-sm"></div>
			<figcaption class="info  d-flex flex-column ">
				<div class="title text-dark my-auto">${prodvo.prod_name }</div>
			</figcaption>
		</figure>
	</td>
	<td> <var class="my-auto qty" >${orderSvc.getOneDetail(prodvo.prod_no, ordervo.order_no).order_qty }</var></td>
	<td> 
		<div class="price-wrap "> 
			<var class ="price">${prodvo.price }</var> 
		</div> <!-- price-wrap .// -->
	</td>
		<td> 
		<div class="price-wrap "> 
			<var class = "amount"></var> 
		</div> <!-- price-wrap .// -->
	</td>
</tr>
</c:forEach>
</tbody>
</table>
<div class="bg-dark w-100 text-white d-flex justify-content-end" id="total"><h4 class="py-2 ml-3">總金額 : NT$	</h4><h4 class="py-2">${ordervo.total }</h4><h4 class="py-2 pr-3">元</h4></div>
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
	var prices = document.getElementsByClassName("price");
	var qtys = document.getElementsByClassName("qty");
	var amounts = document.getElementsByClassName("amount");

	for (let i = 0; i < prices.length; i++) {
		var price = Number(prices[i].innerText);
		var qty = Number(qtys[i].innerText);
		console.log(price);
		console.log(qty);
		amounts[i].innerText = price *qty;
	}
}
window.addEventListener("load", init);
</script>
</html>