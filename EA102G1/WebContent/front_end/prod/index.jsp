<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.prod.model.*, java.util.*, com.prod_order.model.OrderVO" %>
<head>
<jsp:include page="/front_end/prod/head.jsp">
<jsp:param name="title" value="Home page"/>
</jsp:include>
<script src="<%=request.getContextPath() %>/js/prod/index.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<%@ include file="/front_end/prod/header.jsp" %>
<%@ include file="/front_end/prod/header_bottom.jsp"%>
<!-- ========================= SECTION PAGETOP ========================= -->
<jsp:useBean id="prodSvc" class="com.prod.model.ProdService"/>
<%		String membre_id = (String) request.getParameter("shopper");
			pageContext.setAttribute("shopper", membre_id);
			List shoppingCart = (LinkedList<ProdVO>)session.getAttribute("shoppingCart");
			if (shoppingCart != null && shoppingCart.size() > 0) {
				ProdVO prodvo =(ProdVO) shoppingCart.get(0);
				if (!prodvo.getMembre_id().equals(membre_id)){
					session.removeAttribute("shoppingCart");
				}
			}
			List<ProdVO> prodList =null;
 %>
<c:if test="${prodList == null }">
<%
		prodList = prodSvc.getAllByMembre_id(membre_id);
		request.setAttribute("prodList", prodList);
		
%>
</c:if>
<c:if test="${prodList != null }">
<%
		prodList = (ArrayList<ProdVO>) request.getAttribute("prodList");
		pageContext.setAttribute("prodList", prodList);
%>

</c:if>
<section class="section-pagetop" style="padding-bottom:0">
<jsp:useBean id="membreSvc" class="com.membre.model.MembreService"/>
<div class="container">
	<h2 class="title-page">${membreSvc.getOneById(shopper).getMem_name() }的賣場</h2>
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
<%-- 				<form class="pb-3" action="<%=request.getContextPath()%>/prod/prod.do" method="post"> --%>
				<div class="input-group">
				  <input type="text" class="form-control" name="query"placeholder="Search" id="search">
				  <input type="hidden" name="action" value="query">
				  <input type="hidden" name="shopper" value="${shopper }">
				  <div class="input-group-append">
				    <button class="btn btn-light" type="button"><i class="fa fa-search"></i></button>
				  </div>
				</div>
<!-- 				</form> -->
				
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

<!-- <header class="border-bottom mb-4 pb-3"> -->
<!-- 		<div class="form-inline"> -->
<!-- 			<span class="mr-md-auto">32 Items found </span> -->
<!-- 			<select class="mr-2 form-control"> -->
<!-- 				<option>Latest items</option> -->
<!-- 				<option>Trending</option> -->
<!-- 				<option>Most Popular</option> -->
<!-- 				<option>Cheapest</option> -->
<!-- 			</select> -->
<!-- 			<div class="btn-group"> -->
<!-- 				<a href="#" class="btn btn-outline-secondary active" data-toggle="tooltip" title="List view">  -->
<!-- 					<i class="fa fa-bars"></i></a> -->
<!-- 				<a href="#" class="btn  btn-outline-secondary" data-toggle="tooltip" title="Grid view">  -->
<!-- 					<i class="fa fa-th"></i></a> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- </header>sect-heading -->

<%-- <%@ include file="page1.file" %>   --%>
 <c:forEach var="prodvo" items="${prodList}">
<%--  <c:forEach var="prodvo" items="${prodList}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>"> --%>
 <div style="display:none" class="prod_no">${prodvo.prod_no }</div>
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
				
				<div class="price-wrap p-2 bd-highlight">剩餘數量 : <span>${prodvo.prod_qty}</span></div>
				<div class="price-wrap p-2 bd-highlight">上架時間 : <span ><fmt:formatDate value="${prodvo.sale_time}" pattern="yyyy-MM-dd HH:mm:ss"/></span></div>
			</div> <!-- info-main.// -->
		</div> <!-- col.// -->
		<aside class="col-sm-3">
			<div class="info-aside">
			<br>
				<div class="price-wrap">售價:$
					<span class="price h5">${prodvo.price }</span>	
				</div> <!-- info-price-detail // -->
				<br>
				<div class="form-inline myParent">
					<button type="button" class="btn btn-primary btn-block cartBut" data-prod_no="${prodvo.prod_no }">Add to cart</button>
					
					<button type="button"class="btn btn-block btn-light addList" data-prod_no="${prodvo.prod_no }">
						<svg class="emptyHeart" width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-heart" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
						  <path fill-rule="evenodd" d="M8 2.748l-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z"/>
						</svg>
						<svg class="fullHeart" display="none" width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-suit-heart-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
						  <path d="M4 1c2.21 0 4 1.755 4 3.92C8 2.755 9.79 1 12 1s4 1.755 4 3.92c0 3.263-3.234 4.414-7.608 9.608a.513.513 0 0 1-.784 0C3.234 9.334 0 8.183 0 4.92 0 2.755 1.79 1 4 1z"/>
						</svg>
						<span class="text">Add to wishlist</span>
					</button>
				</div>
			</div> <!-- info-aside.// -->
		</aside> <!-- col.// -->
	</div> <!-- row.// -->
</article> <!-- card-product .// -->
 </c:forEach>
<%-- <%@ include file="page2.file" %>  --%>
<c:if test="${not empty prodList }">
<div class="d-flex justify-content-center mt-2">
<nav aria-label="Page navigation example" id="pagination">
  <ul class="pagination" style="color:#0991a0">
    <li class="Previous page-item"><a class="page-link" >First</a></li>
    <li class="Next page-item"><a class="page-link" >Last</a></li>
  </ul>
</nav>
</div>
</c:if>



	</main> <!-- col.// -->

</div>

</div> <!-- container .//  -->
</section>
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