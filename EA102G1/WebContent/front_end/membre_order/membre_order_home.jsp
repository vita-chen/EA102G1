<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.vender.model.*"%>
<%@ page import="com.membre.model.*"%>

<%
Object account = session.getAttribute("membrevo");                  // 從 session內取出 (key) account的值
if (account == null) {                                             // 如為 null, 代表此user未登入過 , 才做以下工作
  response.sendRedirect(request.getContextPath()+"/front_end/membre/login.jsp");   //*工作2 : 請該user去登入網頁(login.html) , 進行登入
  return;
}
%>

<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/front_end/membre_order/membre_head.jsp" %>
</head>
<body>
	<div class="wrapper">
	<!-- topbar -->
	
<%@ include file="/front_end/membre_order/membre_topbar.jsp" %>
	<!-- header -->
<%@ include file="/front_end/membre_order/membre_header.jsp" %>
<div class="main-panel"style="overflow-y:auto;">
<div class="content">
<div class="page-inner">
					<div class="page-header">
					
						<h4 class="page-title">會員</h4>
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
								<a href="#">會員首頁</a>
							</li>
						</ul>	
</div>
<!-- 塞資料的地方 -->
<!-- 塞資料的地方 -->
<div class="page-category">
				

</div>
<!-- 塞資料的地方 -->
<!-- 塞資料的地方 -->
</div>
</div>

</div>
</div>
<%@ include file="/front_end/membre_order/membre_js.jsp" %>	


</body>
</html>