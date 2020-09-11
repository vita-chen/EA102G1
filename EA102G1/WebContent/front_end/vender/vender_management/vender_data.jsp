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
								<a href="#">廠商資料查看</a>
							</li>
						</ul>	
</div>
<!-- 塞資料的地方 -->
<!-- 塞資料的地方 -->
<div class="page-category">
					
<table class="table" style="width: 40%;">
  <thead>
    <tr>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>廠商編號</td>
      <td>${vendervo.vender_id}</td>
    </tr>

    <tr>
      <td>廠商名稱</td>
      <td>${vendervo.ven_name}</td>
    </tr>

    <tr>
      <td>住址</td>
      <td>${vendervo.ven_addr}</td>

    <tr>
      <td>電話</td>
      <td>${vendervo.ven_phone}</td>

    <tr>
      <td>Email</td>
      <td>${vendervo.ven_mail}</td>

    <tr>
      <td>註冊時間</td>
      <td>${vendervo.ven_regis_time}</td>

    <tr>

      <td>名片</td>
      <td>
								<button type="button" class="btn btn-primary"
									data-toggle="modal" data-target="#exampleModalCenter">
									廠商名片</button>
								<div class="modal fade" id="exampleModalCenter" tabindex="-1"
									role="dialog" aria-labelledby="exampleModalCenterTitle"
									aria-hidden="true">
									<div class="modal-dialog modal-dialog-centered" role="document">
										<img style="width: 75%; height: 75%;"
											src="<%=request.getContextPath() %>/vender/vender.do?action=getphot&vender_id=${vendervo.vender_id}">
									</div>
								</div>      
      </td>
    </tr>
  </tbody>
</table>		
					
</div>
<!-- 塞資料的地方 -->
<!-- 塞資料的地方 -->
</div>
</div>

</div>
</div>
<!-- js -->
<%@ include file="/front_end/vender/vender_home_js.jsp" %>	
</body>
</html>