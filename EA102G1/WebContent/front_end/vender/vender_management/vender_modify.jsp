<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.vender.model.*"%>

<%
    Object account = session.getAttribute("vendervo");                  // 從 session內取出 (key) account的值
    if (account == null) {                                             // 如為 null, 代表此user未登入過 , 才做以下工作
      response.sendRedirect(request.getContextPath()+"/front_end/vender/vender_login.jsp");   //*工作2 : 請該user去登入網頁(login.html) , 進行登入
      return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/front_end/vender/vender_home_head.jsp" %>
</head>
<body>
	<div class="wrapper">
	<!-- topbar -->
<%@ include file="/front_end/vender/vender_home_topbar.jsp" %>
	<!-- header -->
<%@ include file="/front_end/vender/vender_home_header.jsp" %>
<div class="main-panel"style="overflow-y:auto;">
<div class="content">
<div class="page-inner">
					<div class="page-header">
					
						<h4 class="page-title">廠商</h4>
						<ul class="breadcrumbs">
							<li class="nav-home">
								<a href="#">
									<i class="flaticon-home"></i>
								</a>
							</li>
							<li class="separator">
								<i class="flaticon-right-arrow"></i>
							</li>
							<li class="nav-item">
								<a href="#">帳戶管理</a>
							</li>
							<li class="separator">
								<i class="flaticon-right-arrow"></i>
							</li>
							<li class="nav-item">
								<a href="#">編輯廠商資料</a>
							</li>
						</ul>	
</div>
<!-- 塞資料的地方 -->
<!-- 塞資料的地方 -->
				<!-- end Topbar 名稱,住址,電話,負責人,EMAIL,廠商圖片,密碼-->
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>
<form  method="POST" action="<%=request.getContextPath()%>/vender/vender.do"
	   name="form1" enctype="multipart/form-data">
  <div class="form-group row">
    <label for="staticEmail" class="col-sm-1 col-form-label">廠商編號</label>
    <div class="col-sm-3">
      <input type="text" readonly class="form-control-plaintext" name="vender_id" value="${vendervo.vender_id}">
    </div>
  </div>
  <div class="form-group row">
    <label for="inputPassword" class="col-sm-1 colcol-form-label">密碼</label>
    <div class="col-sm-3">
      <input type="password" class="form-control" name="ven_pwd" value="${vendervo.ven_pwd}">
    </div>
  </div>
  
    <div class="form-group row">
    <label for="inputPassword" class="col-sm-1 col-form-label">廠商名稱</label>
    <div class="col-sm-3">
      <input type="text" class="form-control" name="ven_name" value="${vendervo.ven_name}">
    </div>
  </div>
    <div class="form-group row">
    <label for="inputPassword" class="col-sm-1 col-form-label">住址</label>
    <div class="col-sm-3">
<select id="縣市1"name="addr1"></select>
<select id="鄉鎮市區1"name="addr2"></select>
<input class="form-control" type="TEXT" name="addr3" size="45" value="${vendervo.ven_addr}" />
    </div>
  </div>
    <div class="form-group row">
    <label for="inputPassword" class="col-sm-1 col-form-label">電話</label>
    <div class="col-sm-3">
      <input type="text" class="form-control" name="ven_phone" value="${vendervo.ven_phone}">
    </div>
  </div>
    <div class="form-group row">
    <label for="inputPassword" class="col-sm-1 col-form-label">負責人</label>
    <div class="col-sm-3">
      <input type="text" class="form-control" name="ven_contact" value="${vendervo.ven_contact}">
    </div>
  </div>
  <div class="form-group row">
    <label for="inputPassword" class="col-sm-1 col-form-label">EMAIL</label>
    <div class="col-sm-3">
      <input type="text" class="form-control" name="ven_mail" value="${vendervo.ven_mail}">
    </div>
  </div>
  <div class="form-group row">
    	<label for="exampleFormControlFile1"class="col-sm-1 col-form-label">名片</label>
    	<div class="col-sm-2">
			<input type="file" class="" name="ven_evidence_pic">
		</div>
  </div>
  <div class="form-group row">
  <img id="imgg" style="height: 30%;width: 30%;border:0;"
	src="<%=request.getContextPath() %>/vender/vender.do?action=getphot&vender_id=${vendervo.vender_id}">
  </div>

<button type="submit" class="btn btn-primary col-sm-4" name="action" value="update">修改</button>

</form>
<br>
<br>					
</div>
<!-- 塞資料的地方 -->
<!-- 塞資料的地方 -->
</div>
</div>

</div>
</div>
<!-- js -->
<script src="<%=request.getContextPath()%>/js/vender/regis.js"></script>
<%@ include file="/front_end/vender/vender_home_js.jsp" %>	
  <script>
    $('input').on('change', function(e){      
      const file = this.files[0];
      
      const fr = new FileReader();
      fr.onload = function (e) {
        $('#imgg').attr('src', e.target.result);
      };
      
      // 使用 readAsDataURL 將圖片轉成 Base64
      fr.readAsDataURL(file);
    });
  </script>
</body>
</html>