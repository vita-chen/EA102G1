<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.prod.model.*, java.util.*, com.membre.model.MembreVO" %>


<head>
<jsp:include page="/front_end/prod/head.jsp">
<jsp:param name="title" value="Home page"/>
</jsp:include>
<script src="<%=request.getContextPath() %>/js/prod/index.js" type="text/javascript" charset="utf-8"></script>
<style>
.card-product-list {
margin-bottom: 0px;
}
</style>
</head>
<body>
<%@ include file="/front_end/prod/header.jsp" %>
<%@ include file="/front_end/prod/header_bottom.jsp"%>
<!-- ========================= SECTION PAGETOP ========================= -->
<jsp:useBean id="prodSvc" class="com.prod.model.ProdService"/>


<%		MembreVO membrevo = (MembreVO) session.getAttribute("membrevo");
			String membre_id = membrevo.getMembre_id();
			pageContext.setAttribute("shopper", membre_id);
			List<ProdVO> prodList =null;
 %>
<c:if test="${prodList == null }">
<%
		prodList = prodSvc.getAllByMembre_id(membre_id);
		if (prodList.size() > 0) {
		request.setAttribute("prodList", prodList);
		}
%>
</c:if>
<c:if test="${prodList != null }">
<%
		prodList = (ArrayList<ProdVO>) request.getAttribute("prodList");
		if (prodList != null) {
		pageContext.setAttribute("prodList", prodList);
		}
%>

</c:if>
<section class="section-pagetop" style="padding-bottom:0">
<jsp:useBean id="membreSvc" class="com.membre.model.MembreService"/>
<div class="container">
	<h2 class="title-page">我的賣場</h2>
<!-- 	<nav> -->
<!-- 	<ol class="breadcrumb text-white"> -->
<!-- 	    <li class="breadcrumb-item"><a href="#">Home</a></li> -->
<!-- 	    <li class="breadcrumb-item"><a href="#">Best category</a></li> -->
<!-- 	    <li class="breadcrumb-item active" aria-current="page">Great articles</li> -->
<!-- 	</ol>   -->
<!-- 	</nav> -->
</div> <!-- container //  -->
</section>
<!-- ========================= SECTION INTRO END// ========================= -->

<!-- ========================= SECTION CONTENT ========================= -->
<section class="section-content padding-y">
<div class="container">

<div class="row">
	<aside class="col-md-3">
		
<div class="card">
	<article class="filter-group">
		<header class="card-header">
				<h4 class="title">商品種類</h4>
		</header>
		<div class="filter-content collapse show" >
			<div class="card-body">
				<form class="pb-3" action="<%=request.getContextPath()%>/prod/prod.do" method="post">
				<div class="input-group">
				  <input type="text" class="form-control" name="query"placeholder="Search">
				  <input type="hidden" name="action" value="query">
				  <input type="hidden" name="shopper" value="${shopper }">
				  <div class="input-group-append">
				    <button class="btn btn-light" type="button"><i class="fa fa-search"></i></button>
				  </div>
				</div>
				</form>
				
				<ul class="list-menu">
				
				<li><a href="<%=request.getContextPath() %>/front_end/prod/index.jsp?shopper=${shopper}">全部</a></li>
				<li><a href="<%=request.getContextPath() %>/prod/prod.do?action=filter&shopper=${shopper }&type=T001">手提包</a></li>
				<li><a href="<%=request.getContextPath() %>/prod/prod.do?action=filter&shopper=${shopper }&type=T002">服飾</a></li>
				<li><a href="<%=request.getContextPath() %>/prod/prod.do?action=filter&shopper=${shopper }&type=T003">書籍</a></li>
				<li><a href="<%=request.getContextPath() %>/prod/prod.do?action=filter&shopper=${shopper }&type=T004">3C</a></li>
				<li><a href="<%=request.getContextPath() %>/prod/prod.do?action=filter&shopper=${shopper }&type=T005">鞋類</a></li>
				<li><a href="<%=request.getContextPath() %>/prod/prod.do?action=filter&shopper=${shopper }&type=T0010">其它</a></li>
				</ul>

			</div> <!-- card-body.// -->
		</div>
	</article> <!-- filter-group  .// -->
	
		<article class="filter-group">
		<header class="card-header">
				<h4 class="title">依價格排序</h4>
		</header>
		<div class="filter-content collapse show" >
			<div class="card-body">
				<ul class="list-menu">
				<li><a href="<%=request.getContextPath() %>/prod/prod.do?action=sorting&shopper=${shopper }&type=${param.type}&order=desc">$(高→低)</a></li>
				<li><a href="<%=request.getContextPath() %>/prod/prod.do?action=sorting&shopper=${shopper }&type=${param.type}&order=asc">$(低→高)</a></li>
				</ul>
			</div> <!-- card-body.// -->
		</div>
	</article> <!-- filter-group  .// -->

 </div> 

	</aside> <!-- col.// -->
	<main class="col-md-9">
	
<c:if test="${prodList == null}">
<div class="container w-100 h-75 d-flex justify-content-center align-items-center">
<div><svg width="5em" height="5em" viewBox="0 0 16 16" class="bi bi-cart-x" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
  <path fill-rule="evenodd" d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm7 0a1 1 0 1 0 0 2 1 1 0 0 0 0-2z"/>
  <path fill-rule="evenodd" d="M6.646 5.646a.5.5 0 0 1 .708 0L8.5 6.793l1.146-1.147a.5.5 0 0 1 .708.708L9.207 7.5l1.147 1.146a.5.5 0 0 1-.708.708L8.5 8.207 7.354 9.354a.5.5 0 1 1-.708-.708L7.793 7.5 6.646 6.354a.5.5 0 0 1 0-.708z"/>
</svg></div>
<div>尚未新增任何商品</div>
</div>
<div class="h-25 d-flex justify-content-center align-items-center" ><a href="<%=request.getContextPath() %>/front_end/prod/addProd.jsp" class="btn btn-primary" style="color:white;">新增商品</a></div>
</c:if>

<%@ include file="page1.file" %>  
 <c:forEach var="prodvo" items="${prodList}"  begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
 
<article class="card card-product-list">
	<div class="row no-gutters">
		<aside class="col-md-3">
			<a href="<%=request.getContextPath() %>/prod/prod.do?action=getOne&prod_no=${prodvo.prod_no}" class="img-wrap">
				<c:if test="${System.currentTimeMillis() - prodvo.sale_time.getTime() < (1000*60*60*24) }">
				<span class="badge badge-danger"> NEW </span>
				</c:if>
				<img src="<%= request.getContextPath() %>/prod/prod.do?action=getOne&prod_no=${prodvo.prod_no}&number=one">
			</a>
		</aside> <!-- col.// -->
		<div class="col-md-6">
			<div class="info-main h-100 d-flex align-items-start flex-column bd-highlight mb-3">
				<a href="" class="h5 title prod_name mb-auto p-2 bd-highlight"> ${prodvo.prod_name }  </a>
				 
                <p style="display:none" class="membre_id">${prodvo.membre_id }</p>
				
				<div class="price-wrap p-2 bd-highlight">剩餘數量 : <span >${prodvo.prod_qty}</span></div>
				<div class="price-wrap p-2 bd-highlight">上架時間 : <span ><fmt:formatDate value="${prodvo.sale_time}" pattern="yyyy-MM-dd HH:mm:ss"/></span></div>
			</div> <!-- info-main.// -->
		</div> <!-- col.// -->
		<aside class="col-sm-3">
			<div class="info-aside">
			<br>
				<div class="price-wrap">售價 : $
					<span class="price h5">${prodvo.price }</span>	
				</div> <!-- info-price-detail // -->
				<br>
		
			</div> <!-- info-aside.// -->
		</aside> <!-- col.// -->
	</div> <!-- row.// -->
</article> <!-- card-product .// -->
 </c:forEach>
 <c:if test="${prodList != null }">
 <div class="d-flex justify-content-center align-items-center" ><a href="<%=request.getContextPath() %>/front_end/prod/addProd.jsp" class="btn btn-primary" style="color:white ;margin:5px;">新增商品</a></div>
 </c:if>
<%@ include file="page2.file" %> 


<!-- <div class="d-flex justify-content-center mt-2"> -->
<!-- <nav aria-label="Page navigation example" id="pagination"> -->
<!--   <ul class="pagination" style="color:#0991a0"> -->
<!--     <li class="Previous page-item"><a class="page-link" >First</a></li> -->
<!--     <li class="Next page-item"><a class="page-link" >Last</a></li> -->
<!--   </ul> -->
<!-- </nav> -->
<!-- </div> -->




	</main> <!-- col.// -->

</div>

</div> <!-- container .//  -->
</section>
<%-- <c:if test="${not empty prodList }"> --%>

<%-- </c:if> --%>
<!-- ========================= SECTION CONTENT END// ========================= -->

<!-- ========================= FOOTER ========================= -->
<footer class="section-footer border-top padding-y">
	<div class="container">
		<p class="float-md-right"> 
			&copy Copyright 2019 All rights reserved
		</p>
		<p>
			<a href="#">Terms and conditions</a>
		</p>
	</div><!-- //container -->
</footer>
<!-- ========================= FOOTER END // ========================= -->


</body>
</html>