<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<jsp:include page="/front_end/prod/head.jsp">
<jsp:param name="title" value="Home page"/>
</jsp:include>
<script src="<%=request.getContextPath() %>/js/prod/index.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>


<%@ include file="/front_end/prod/header.jsp" %>
<%@ include file="/front_end/prod/header_bottom.jsp"%>
<%@ include file="/front_end/prod/error_modal.jsp"%>
<jsp:useBean id="memSvc" class="com.membre.model.MembreService"/>
<div style="height:20%"></div>
<div class="container">
<div class="row">
	<c:if test="${membrevo.status != 1 }">
		<div class="col-md-3">
			<a style="border:3px solid gray" href="<%=request.getContextPath() %>/membre/membre.do?action=openShop&shopper=${membrevo.membre_id}" class="card card-product-grid">
			<div class="img-wrap"> <img src="<%=request.getContextPath()%>/membre/membre.do?action=getphoto&membre_id=${membrevo.membre_id}"> </div>
			<figcaption class="info-wrap">
				<p class="title text-center">創建我的賣場</p>
			</figcaption>
		</a> <!-- card // -->
	</div> <!-- col.// -->
	</c:if>
		<c:if test="${membrevo.status == 1 }">
		<div class="col-md-3" >
			<a style="border:3px solid gray" href="<%=request.getContextPath() %>/front_end/prod/myShop.jsp?shopper=${membrevo.membre_id}" class="card card-product-grid">
			<div class="img-wrap"> <img src="<%=request.getContextPath()%>/membre/membre.do?action=getphoto&membre_id=${membrevo.membre_id}"> </div>
			<figcaption class="info-wrap">
				<p class="title text-center">管理我的賣場</p>
			</figcaption>
		</a> <!-- card // -->
	</div> <!-- col.// -->
	</c:if>


<c:forEach var="shopper" items="${memSvc.getAllWithShop()}">
<c:if test="${shopper.membre_id != membrevo.membre_id }">
	<div class="col-md-3">
		<a href="<%=request.getContextPath() %>/front_end/prod/index.jsp?shopper=${shopper.membre_id}" class="card card-product-grid">
			<div class="img-wrap"> <img src="<%=request.getContextPath()%>/membre/membre.do?action=getphoto&membre_id=${shopper.membre_id}"> </div>
			<figcaption class="info-wrap">
				<p class="title text-center">${shopper.mem_name }的賣場</p>
			</figcaption>
		</a> <!-- card // -->
	</div> <!-- col.// -->
</c:if>
	</c:forEach>


</div>
</div>
</body>
</html>