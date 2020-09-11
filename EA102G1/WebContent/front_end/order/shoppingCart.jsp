<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<html lang="en">
<head>
<jsp:include page="/front_end/prod/head.jsp">
<jsp:param name="title" value="Shopping Cart"/>
</jsp:include>
<script src="<%=request.getContextPath() %>/js/prod/shoppingCart.js" type="text/javascript" charset="utf-8"></script>
<style>
.price::before{
	content:"$ ";
}
.price::after{
	content:" each";
}
body {
	height:800px;
}

</style>
</head>
<body>

<%@ include file="/front_end/prod/header.jsp" %>
<%@ include file="/front_end/prod/header_bottom.jsp"%>


<c:if test="${empty shoppingCart}">
<div class="container w-100 h-75 d-flex justify-content-center align-items-center">
<div><svg width="5em" height="5em" viewBox="0 0 16 16" class="bi bi-cart-x" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
  <path fill-rule="evenodd" d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm7 0a1 1 0 1 0 0 2 1 1 0 0 0 0-2z"/>
  <path fill-rule="evenodd" d="M6.646 5.646a.5.5 0 0 1 .708 0L8.5 6.793l1.146-1.147a.5.5 0 0 1 .708.708L9.207 7.5l1.147 1.146a.5.5 0 0 1-.708.708L8.5 8.207 7.354 9.354a.5.5 0 1 1-.708-.708L7.793 7.5 6.646 6.354a.5.5 0 0 1 0-.708z"/>
</svg></div>
<div>你的購物車是空的!</div>
</div>
</c:if>


<c:if test="${not empty shoppingCart}">
<div style="height:10%"></div>
<section class="section-content padding-y">
<div class="container">
<h2>購物車</h2>
</div>
<div class="container pt-5">
<!-- ============================ COMPONENT 1 ================================= -->
<div class="row">
	<aside class="col-lg-9">
<div class="card">
<table class="table table-borderless table-shopping-cart" >
<thead class="text-muted">
<tr class="small text-uppercase">
  <th scope="col">Product</th>
  <th scope="col" width="120">Quantity</th>
  <th scope="col" width="120" class="text-center">Price</th>
  <th scope="col" class="text-right" width="200"> </th>
</tr>
</thead>
<tbody>
	<c:forEach var="prodvo" items="${shoppingCart}" varStatus="index">
<tr class="productItems">
	<td class="w-25">
		<figure class="itemside align-items-center">
			<div class="aside"><img src="<%= request.getContextPath() %>/prod/prod.do?action=getOne&prod_no=${prodvo.prod_no}&number=one" class="img-sm"></div>
			<figcaption class="info">
				<a href="#" class="title text-dark">${prodvo.prod_name }</a>
<!-- 				<p class="text-muted small">Matrix: 25 Mpx <br> Brand: Canon</p> -->
			</figcaption>
		</figure>
	</td>
	<td class="my-auto"> 
			<select class="quantity form-control mt-3">
			<c:forEach begin="1" end="${prodvo.prod_qty }" var = "i">
			<option value="${i }">${i }
			</c:forEach>
			</select>
	</td>
	<td class="w-50"> 
		<div class="price-wrap w-100 mt-3 text-center"> 
			<var class="amount">$1156.00</var> 
			<small class="text-muted price w-100 text-center">${prodvo.price }</small> 
		</div> <!-- price-wrap .// -->
	
	</td>
	<td class="text-right myParent w-25"> 
	<p class="prod_no" style="display:none">${prodvo.prod_no }</p>
	<button class="btn btn-light addList"><i class="fa fa-heart"></i></button>
<!-- 	<a data-original-title="Save to Wishlist" title="" href="#" class="btn btn-light addList" data-toggle="tooltip"> <i class="fa fa-heart"></i></a>  -->
	<a href="#" class="btn btn-light deleteButton">Remove</a>
	</td>
</tr>
</c:forEach>
</tbody>
</table>
</div> <!-- card.// -->
	</aside> <!-- col.// -->
	<aside class="col-md-3">
		<div class="card-body">
			<dl class="dlist-align">
			  <dt>Total price:</dt>
			  <dd class="text-right" >$<span id="total"></span></dd>
			</dl>
			<hr>
			<a href="#" class="btn btn-primary btn-block" data-toggle="modal" data-target="#orderModal"> Make Purchase </a>
			<a href="<%=request.getContextPath() %>/front_end/prod/index.jsp?shopper=${shoppingCart['0'].membre_id }" class="btn btn-light btn-block">Continue Shopping</a>
		</div> <!-- card-body.// -->
	</aside> <!-- col.// -->
</div> <!-- row.// -->

<!-- ============================ COMPONENT 1 END .// ================================= -->
<div class="modal fade" id="orderModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">確認結帳?</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body d-flex justify-content-center">
        <button type="button" class="btn btn-primary mr-5" id="order">確定</button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
      </div>
      <div class="modal-footer">
      </div>
    </div>
  </div>
</div>

</div> <!-- container .//  -->
</section>
</c:if>
<!-- ========================= SECTION CONTENT END// ========================= -->

<!-- ========================= FOOTER ========================= -->
<!-- <footer class="section-footer border-top padding-y"> -->
<!-- 	<div class="container"> -->

<!-- 	</div>//container -->
<!-- </footer> -->
<!-- ========================= FOOTER END // ========================= -->


</body>
</html>

