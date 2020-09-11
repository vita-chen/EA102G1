<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<jsp:include page="/front_end/prod/head.jsp">
<jsp:param name="title" value="Wish list"/>
</jsp:include>
<script src="<%=request.getContextPath() %>/js/prod/wishList.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<%@ include file="/front_end/prod/header.jsp" %>
<%@ include file="/front_end/prod/header_bottom.jsp"%>
	<jsp:useBean id="listSvc" class="com.buy_list.model.ListService"/>
	<c:if test="${listSvc.getAll(membrevo.membre_id).size() == 0}">
	<div class="container w-100 h-75 d-flex justify-content-center align-items-center">
<div><svg width="5em" height="5em" viewBox="0 0 16 16" class="bi bi-cart-x" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
  <path fill-rule="evenodd" d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm7 0a1 1 0 1 0 0 2 1 1 0 0 0 0-2z"/>
  <path fill-rule="evenodd" d="M6.646 5.646a.5.5 0 0 1 .708 0L8.5 6.793l1.146-1.147a.5.5 0 0 1 .708.708L9.207 7.5l1.147 1.146a.5.5 0 0 1-.708.708L8.5 8.207 7.354 9.354a.5.5 0 1 1-.708-.708L7.793 7.5 6.646 6.354a.5.5 0 0 1 0-.708z"/>
</svg></div>
<div>尚未收藏任何商品!</div>
</div>
	</c:if>
		<c:if test="${listSvc.getAll(membrevo.membre_id).size() != 0}">
	<div style="height:10%"></div>
<section class="section-content padding-y">
<div class="container">
<h2>追蹤清單</h2>
</div>
<div class="container mt-5">
	<c:forEach var="prodvo" items="${listSvc.getAll(membrevo.membre_id) }">
<div class="myrow">
<div class="card w-75">
<!-- <div class="row no-gutters"> -->
			<article class="card-body border-bottom">
					<div class="row">
						<div class="col-md-7">
							<figure class="media">
								<div class="img-wrap mr-3"><img src="<%= request.getContextPath() %>/prod/prod.do?action=getOne&prod_no=${prodvo.prod_no}&number=one" class="border img-sm"></div>
								<figcaption class="media-body">
									<a href="#" class="title h6 prod_name">${prodvo.prod_name }</a>
									
									<div class="price-wrap">$<span class="price">${prodvo.price }</span></div>
									<small class="text-muted mb-5">剩餘數量 :<span class="prod_qty ml-1">${prodvo.prod_qty}</span></small>
								</figcaption>
							</figure> 
						</div> <!-- col.// -->
						<div class="col-md-5 text-md-right text-right"> 
							<div class="mt-3 pt-1 myParent">
							<span class= "prod_no" style="display:none">${prodvo.prod_no}</span>
							<button class="btn btn-light cartBut"> <i class="fa fa-shopping-cart "></i> </button>
							<button class="btn btn-light removeList"> <i class="fa fa-trash"></i> </button>
							</div> 
						</div>
					</div> <!-- row.// -->
			</article> <!-- card-group.// -->
<!-- </div> row.// -->
</div> <!-- card.// -->
</div> <!-- row.// -->
</c:forEach>

</div> <!-- container .//  -->
</section>
	</c:if>
<!-- ========================= SECTION CONTENT END// ========================= -->

<!-- ========================= FOOTER ========================= -->

<!-- ========================= FOOTER END // ========================= -->


</body>
</html>