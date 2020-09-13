<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="head.jsp">
<jsp:param name="title" value="My products"/>
</jsp:include>
<script src="<%=request.getContextPath() %>/js/prod/myProds.js" type="text/javascript" charset="utf-8"></script>
<style>
.table th, .table td {
 vertical-align:middle;
}
tr:nth-child(even) {background: #e6f2ff}
tr:nth-child(odd) {background: #bbe6ff}
#myTr {
	background:#04233e;
	color:white;
}
</style>
</head>
<body>
<%@ include file="/front_end/prod/header.jsp" %>
<%@ include file="/front_end/prod/header_bottom.jsp"%>
<section class="section-content padding-y">
	<jsp:useBean id="prodSvc" class="com.prod.model.ProdService"/>

<div class="container">
<table class="table table-borderless table-shopping-cart">
<thead class="text-muted">

<tr class="small text-uppercase" id="myTr">
  <th scope="col">商品</th>
  <th scope="col" width="120">數量</th>
  <th scope="col" width="120">價格</th>
  <th scope="col" width="120">出售中?</th>
  <th scope="col" class="text-center" width="200">上/下架</th>
</tr>
</thead>
<tbody>
	<c:forEach var="prodvo" items="${prodSvc.getAllMyProds(membrevo.membre_id) }">
<tr>
	<td>
		<figure class="itemside">
			<a href="<%=request.getContextPath() %>/prod/prod.do?action=getOne&prod_no=${prodvo.prod_no}" class="img-wrap">
			<div class="aside"><img src="<%= request.getContextPath() %>/prod/prod.do?action=getOne&prod_no=${prodvo.prod_no}&number=one" class="img-sm">
			</div>
			</a>
			<figcaption class="info  d-flex flex-column ">
				<div href="#" class="title text-dark my-auto">${prodvo.prod_name }</div>
			</figcaption>
		</figure>
	</td>
	<td> <var class="price my-auto">${prodvo.prod_qty }</var></td>
	<td> 
		<div class="price-wrap "> 
			<var class="price">${prodvo.price }</var> 
		</div> <!-- price-wrap .// -->
	</td>
    <td>
	<svg display="inline" width="2em" height="2em" viewBox="0 0 16 16" class="bi bi-check2 check" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
	  <path fill-rule="evenodd" d="M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0z"/>
	</svg>
	<svg display="none" width="2em" height="2em" viewBox="0 0 16 16" class="bi bi-x cross" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
	  <path fill-rule="evenodd" d="M11.854 4.146a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708-.708l7-7a.5.5 0 0 1 .708 0z"/>
	  <path fill-rule="evenodd" d="M4.146 4.146a.5.5 0 0 0 0 .708l7 7a.5.5 0 0 0 .708-.708l-7-7a.5.5 0 0 0-.708 0z"/>
	</svg>
    </td>
	<td class="text-right myParent"> 
	<p style="display:none">${prodvo.prod_no }</p>
	<p style="display:none" class="myStatus">${prodvo.prod_status }</p>
	<button class="btn btn-light upDown" data-toggle="tooltip"> 
	<svg display = "inline" width="1.5em" height="1.5em" viewBox="0 0 16 16" class="bi bi-arrow-down down" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
  <path fill-rule="evenodd" d="M4.646 9.646a.5.5 0 0 1 .708 0L8 12.293l2.646-2.647a.5.5 0 0 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 0-.708z"/>
  <path fill-rule="evenodd" d="M8 2.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V3a.5.5 0 0 1 .5-.5z"/>
</svg>
<svg display = "none" width="1.5em" height="1.5em" viewBox="0 0 16 16" class="bi bi-arrow-up up" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
  <path fill-rule="evenodd" d="M8 3.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V4a.5.5 0 0 1 .5-.5z"/>
  <path fill-rule="evenodd" d="M7.646 2.646a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L8 3.707 5.354 6.354a.5.5 0 1 1-.708-.708l3-3z"/>
</svg>
	</button> 
	<a href="<%=request.getContextPath() %>/prod/prod.do?action=getOneForUpdate&prod_no=${prodvo.prod_no}" class="btn btn-light">修改</a>
	</td>
</tr>
</c:forEach>
</tbody>
</table>

  </div> <!-- container .//  -->
</section>  
</body>
</html>