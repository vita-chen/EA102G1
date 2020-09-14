<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.prod.model.ProdVO, java.util.*" %>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/front_end/prod/head.jsp">
<jsp:param name="title" value="Registre page"/>
</jsp:include>
</head>
<body>
<%@ include file="/front_end/prod/header.jsp" %>
<%@ include file="/front_end/prod/header_bottom.jsp"%>
<%@ include file="/front_end/prod/error_modal.jsp"%>
<!-- ========================= SECTION CONTENT ========================= -->
<section class="section-content padding-y">

<!-- ============================ COMPONENT REGISTER   ================================= -->
	<div class="card mx-auto" style="max-width:520px; margin-top:40px;">
      <article class="card-body">
		<header class="mb-4"><h4 class="card-title">註冊</h4></header>
		<form action="<%=request.getContextPath()%>/membre/membre.do?action=regis" method="post" enctype="multipart/form-data">
				<div class="form-row">
					<div class="col form-group">
						<label>姓名</label>
					  	<input type="text" class="form-control" name="mem_name" value="${errorMembrevo.mem_name }" maxlength="10">
					</div> <!-- form-group end.// -->
					<div class="col form-group">
						<label>電話</label>
					  	<input type="text" class="form-control" name="phone" value="${errorMembrevo.phone }" maxlength="10">
					</div> <!-- form-group end.// -->
				</div> <!-- form-row end.// -->
				<div class="form-group">
					<label>Email</label>
					<input type="email" class="form-control" placeholder="email" name="email" value="${errorMembrevo.email }" maxlength="20">
				</div> <!-- form-group end.// -->
				<div class="form-group">
					<label class="custom-control custom-radio custom-control-inline">
					  <input class="custom-control-input" checked="" type="radio" name="sex" value="男">
					  <span class="custom-control-label"> 男</span>
					</label>
					<label class="custom-control custom-radio custom-control-inline">
					  <input class="custom-control-input" type="radio" name="sex" value="女">
					  <span class="custom-control-label"> 女 </span>
					</label>
				</div> <!-- form-group end.// -->
<!-- 				<div class="form-row"> -->
<!-- 					<div class="form-group col-md-12"> -->
<!-- 					  <label>地址</label> -->
<%-- 					  <input type="text" class="form-control" name="address" value="${errorMembrevo.address }"> --%>
<!-- 					</div> form-group end.// -->
<!-- 				</div> form-row.// -->
				<div class="form-row">
					<div class="form-group col-md-6">
						<label>密碼</label>
					    <input class="form-control" type="password" name="passe" id="passe" value="${errorMembrevo.passe }" maxlength="20">
					</div> <!-- form-group end.// --> 
					<div class="form-group col-md-6">
						<label class="btn btn-info form-control" style="margin-top:34px">
					    <input style="display:none" type="file" name="photo">
						<i class="fa fa-photo"></i> 大頭貼
					    </label>
					</div> <!-- form-group end.// -->  
				</div>
			    <div class="form-group">
			        <button type="submit" class="btn btn-primary btn-block"> 註冊</button>
			    </div> <!-- form-group// -->      
			    <div class="form-group"> 
<!-- 		            <label class="custom-control custom-checkbox"> <input type="checkbox" class="custom-control-input" checked=""> <div class="custom-control-label"> I am agree with <a href="#">terms and contitions</a>  </div> </label> -->
		        </div> <!-- form-group end.// -->           
			</form>
		</article><!-- card-body.// -->
    </div> <!-- card .// -->
    <p class="text-center mt-4">已經有帳號了嗎?<a href="login.jsp">登入</a></p>
    <br><br>
<!-- ============================ COMPONENT REGISTER  END.// ================================= -->


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