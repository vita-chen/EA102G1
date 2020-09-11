<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri ="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html lang="en">
<head>
<jsp:include page="/front_end/prod/head.jsp">
<jsp:param name="title" value="Product Detail"/>
</jsp:include>
<script src="<%=request.getContextPath() %>/js/prod/index.js" type="text/javascript" charset="utf-8"> 
</script>
</head>
<body>
<%@ include file="/front_end/prod/header.jsp" %>
<%@ include file="/front_end/prod/header_bottom.jsp"%>

<section class="section-content padding-y">
<div class="container">
<!-- ============================ COMPONENT 2 ================================= -->
<div class="card mt-5 pt-5">
	<div class="row no-gutters">
		<aside class="col-sm-6 border-right">
<c:if test="${not empty piclist }">

<article class="gallery-wrap pics"> 
<c:forEach var="picvo" items="${piclist}" varStatus="index">
<c:choose>
	<c:when test="${index.count==1 }">
	<div class="img-big-wrap">
	   <a href="#"><img src="<%=request.getContextPath()%>/prod/prod.do?action=getOne&number=many&pic_no=${picvo.pic_no}"></a>
	</div> <!-- img-big-wrap.// -->
	<div class="thumbs-wrap">
	</c:when>
	<c:otherwise>
	  <a href="#" class="item-thumb"><img src="<%=request.getContextPath()%>/prod/prod.do?action=getOne&number=many&prod_no=${picvo.prod_no}&pic_no=${picvo.pic_no}"></a>
	  </c:otherwise>
	  </c:choose>
	</c:forEach>
</div> <!-- thumbs-wrap.// -->
</article> <!-- gallery-wrap .end// -->
</c:if>
		</aside>
		<main class="col-sm-6">
<article class="content-body">
	<h2 class="title prod_name">${prodvo.prod_name }</h2>
<p>Virgil Abloh’s Off-White is a streetwear-inspired collection that continues to 
break away from the conventions of mainstream fashion. Made in Italy, these black and brown Odsy-1000 low-top sneakers.</p>
<dl class="row">
  <dt class="col-sm-3">商品編號:</dt>
  <dd class="col-sm-9 prod_no">${prodvo.prod_no }</dd>

  <dt class="col-sm-3">賣家:</dt>
  <dd class="col-sm-9 membre_id">${prodvo.membre_id }</dd>

  <dt class="col-sm-3">剩餘數量:</dt>
  <dd class="col-sm-9 prod_qty">${prodvo.prod_qty }</dd>
</dl>

<div class="h5 mb-4"> 價格: 
	<var class="h4">$<span class="price">${prodvo.price }</span></var> 
</div> <!-- price-wrap.// -->
<c:if test="${prodvo.membre_id != sessionScope.membrevo.membre_id }">
<div class="form-row myParent">
	<p style="display:none">${prodvo.prod_no }</p>
	<button type="button" class="btn btn-primary btn-block cartBut w-25" data-prod_no="${prodvo.prod_no }">Add to cart</button>
	<br>
	<div class="col">
		<button type="button"class="btn btn-block btn-light addList w-25" data-prod_no="${prodvo.prod_no }">
		<svg class="emptyHeart" width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-heart" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
						  <path fill-rule="evenodd" d="M8 2.748l-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z"/>
						</svg>
						<svg class="fullHeart" display="none" width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-suit-heart-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
						  <path d="M4 1c2.21 0 4 1.755 4 3.92C8 2.755 9.79 1 12 1s4 1.755 4 3.92c0 3.263-3.234 4.414-7.608 9.608a.513.513 0 0 1-.784 0C3.234 9.334 0 8.183 0 4.92 0 2.755 1.79 1 4 1z"/>
						</svg>
		</button>
	</div>
</div> 
</c:if>
</article> <!-- product-info-aside .// -->
		</main> <!-- col.// -->
	</div> <!-- row.// -->
</div> <!-- card.// -->

<!-- ============================ COMPONENT 2 END .// ================================= -->

<!-- ========================= FOOTER ========================= -->

<!-- ========================= FOOTER END // ========================= -->
<script>
var pics = document.getElementsByClassName('pics');
for (var i = 0; i < pics.length; i++) {
	pics[i].addEventListener('click', function(e) {
		var targetPic = e.target.closest('img');
		var targetParent = targetPic.parentNode;
		var replacePic =e.target.closest('.pics').children[0].children[0].children[0];
		var replaceParent = replacePic.parentNode;
	
		targetPic.remove();
		replacePic.remove();
		targetParent.appendChild(replacePic);
		replaceParent.appendChild(targetPic);
	})
}
</script>

</body>
</html>