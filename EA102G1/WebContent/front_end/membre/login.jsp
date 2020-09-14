<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<head>
<jsp:include page="/front_end/prod/head.jsp">
<jsp:param name="title" value="Login page"/>
</jsp:include>
</head>
<body>
<%@ include file="/front_end/prod/header.jsp" %>
<%@ include file="/front_end/prod/header_bottom.jsp"%>
<%@ include file="/front_end/prod/error_modal.jsp"%>

<!-- ========================= SECTION CONTENT ========================= -->
<section class="section-conten padding-y mt-5 pt-5" style="min-height:84vh">

<!-- ============================ COMPONENT LOGIN   ================================= -->
	<div class="card mx-auto" style="max-width: 380px; margin-top:100px;">
      <div class="card-body">
      <h4 class="card-title mb-4">登入</h4>
      <form action="<%= request.getContextPath()%>/membre/membre.do" method="post">

<!--       	  <a href="#" class="btn btn-facebook btn-block mb-2"> <i class="fab fa-facebook-f"></i> &nbsp  Sign in with Facebook</a> -->
<!--       	  <a href="#" class="btn btn-google btn-block mb-4"> <i class="fab fa-google"></i> &nbsp  Sign in with Google</a> -->
          <div class="form-group">
			 <input name="compte" class="form-control" placeholder="Email" type="text" maxlength="20">
          </div> <!-- form-group// -->
          <div class="form-group">
			<input name="passe" class="form-control" placeholder="Password" type="password" maxlength="20">
          </div> <!-- form-group// -->
          
          <div class="form-group">
          	<a href="#" class="float-right">忘記密碼?</a> 
            <label class="float-left custom-control custom-checkbox"> <input type="checkbox" class="custom-control-input" checked=""></label>
          </div> <!-- form-group form-check .// -->
          <div class="form-group">
         	 <input type = "hidden" name = "action" value="login">
         	 <input type ="hidden" name="location" value="${param.location }">
              <button type="submit" class="btn btn-primary btn-block">登入</button>
          </div> <!-- form-group// -->    
      </form>
      </div> <!-- card-body.// -->
    </div> <!-- card .// -->

     <p class="text-center mt-4">還沒有帳號? <a href="<%=request.getContextPath() %>/front_end/membre/regis.jsp" >註冊</a></p>
     <br><br>
<!-- ============================ COMPONENT LOGIN  END.// ================================= -->


</section>
<!-- ========================= SECTION CONTENT END// ========================= -->


<!-- ========================= FOOTER ========================= -->
<footer class="section-footer border-top padding-y">
	<div class="container">
		<p class="float-md-right"> 
			&copy Copyright 2020 All rights reserved
		</p>
		<p>
			<a href="#">Terms and conditions</a>
		</p>
	</div><!-- //container -->
</footer>
<!-- ========================= FOOTER END // ========================= -->



</body>
</html>
