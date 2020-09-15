<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "com.dressorder.model.DressOrderVO,com.membre.model.MembreVO" %>
<%@ page import = "com.dresscase.model.DressCaseVO,com.dressorderdetail.model.DressOrderDetailVO" %>
<%@ page import = "com.dressaddorder.model.DressAddOrderVO,com.dressaddon.model.DressAddOnVO" %>
<%@ page import = "java.util.*" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>OrderSuccess_membre檢查訂單.jsp</title>
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
.pp{
	color: deeppink;
}
.bgpink{
	background-color:pink;
}

#myContainer{
	position:relative;
	padding-bottom:30px;
	clear:both;
}
#vidDiv{
	position:absolute;
	left:50%;
	right:50%;
	transform:translateX(-50%);
	height:300px;
}

#footerDiv{
	margin-top:200px;
	padding-top:100px;
}

#head{
		background-color: 	#E6E6FA;
	}
</style>
</head>
<jsp:useBean id = "orderSvc" class="com.dressorder.model.DressOrderService"/>
<jsp:useBean id="dcSvc" class="com.dresscase.model.DressCaseService"/>
<jsp:useBean id="membreSvc" class="com.membre.model.MembreService"/>
<jsp:useBean id="detailSvc" class="com.dressorderdetail.model.DressOrderDetailService"/>
<jsp:useBean id="addSvc" class="com.dressaddorder.model.DressAddOrderService"/>
<jsp:useBean id="addOnSvc" class="com.dressaddon.model.DressAddOnService"/>

<%
MembreVO membrevo = (MembreVO) session.getAttribute("membrevo");
String membre_id = membrevo.getMembre_id();
DressOrderVO doVO = orderSvc.findLatestOrder(membre_id); 
pageContext.setAttribute("doVO", doVO);
String drord_id = doVO.getDrord_id();

List<DressOrderDetailVO> detailList = detailSvc.findByOrder(drord_id);
pageContext.setAttribute("detailList", detailList);
%>
<body>

<%@ include file="/front_end/home/Header_Cart.jsp"%>
<div style="height:10%"></div>


<div class="container accordion mx-auto"  id="myContainer">
<h5><span class="pp">${membrevo.mem_name }</span> 您好，以下是您下訂的訂單</h5>
<br>
<table class="table table-responsive table-hover">
<tbody>
<!-- 一筆訂單可能包含多筆訂單明細 -->
<tr>
	<c:forEach var="detailVO" items="${detailList}">
		<tr id="head">
		  <th scope="col" width="400">訂單明細編號</th>
		  <th scope="col" width="400">方案名稱</th>
		  <th scope="col" width="400">方案價格</th>
		</tr>
	<td>
				<div class="title text-dark my-auto">${detailVO.drde_id}</div>
	</td>
				<!-- 一筆訂單明細包括一個方案 -->
				<c:forEach var="dcVO" items="${dcSvc.all}">
				<c:if test="${dcVO.drcase_id eq detailVO.drcase_id}">
					<td>${dcVO.drcase_na}</td>
					<td>${dcVO.drcase_pr}</td>
				</c:if>
				</c:forEach>
				
				<!-- 一筆訂單明細包括0~多個加購項目 -->
				<c:forEach var="addOdVO" items="${addSvc.all}">
					<c:if test="${addOdVO.drde_id eq detailVO.drde_id}">
					<tr>
					<th width="400"></th>
					<th width="400">加購項目名稱</th>
					<th width="400">加購項目價格</th></tr>
					
					<tr>
					<c:forEach var="addOnVO" items="${addOnSvc.all}"> 
						<c:if test="${addOnVO.dradd_id eq addOdVO.dradd_id }"> 
							<td></td>
							<td>${addOnVO.dradd_na}</td> 
							<td>${addOnVO.dradd_pr}</td> 
						</c:if>
					</c:forEach>  
					
					</tr>
					</c:if>
					</c:forEach>
					<!-- end of 一筆訂單明細 -->
					
				</c:forEach>
				
</tr>
</tbody>
</table>
<br>
<div><h5>訂單總金額 : NT$  <font color="deeppink">${doVO.drord_pr }</font>元</h5></div>

<div id="vidDiv">
<video autoplay muted loop width="480" height="320">  
    <source src="../video/Thankyou.mp4" type="video/mp4">
    Your browser does not support the video tag.  
</video>
</div>
</div>



<div id="footerDiv">
<%@ include file="/front_end/home/home_footer.jsp" %>
</div>
</body>
</html>