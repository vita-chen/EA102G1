
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.forum.model.*"%>


<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8" />
<title>討論區</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<!-- App favicon -->
<link rel="shortcut icon" href="assets/images/favicon.ico">

<!-- third party css -->
<link
	href="<%=request.getContextPath()%>/css/jquery-jvectormap-1.2.2.css"
	rel="stylesheet" type="text/css" />
<!-- third party css end -->

<!-- App css -->
<link href="<%=request.getContextPath()%>/css/icons.min.css"
	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/app.min.css"
	rel="stylesheet" type="text/css" id="light-style" />
<link href="<%=request.getContextPath()%>/css/app-dark.min.css"
	rel="stylesheet" type="text/css" id="dark-style" />
<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
</style>

<style>
table {
	width: 100%;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 5px;
	text-align: center;
}
</style>

</head>

<body class="loading"
	data-layout-config='{"leftSideBarTheme":"dark","layoutBoxed":false, "leftSidebarCondensed":false, "leftSidebarScrollable":false,"darkMode":false, "showRightSidebarOnStart": true}'>
	<!-- Begin page -->
	<div class="wrapper">
		<!-- ========== Left Sidebar Start ========== -->
            <div class="left-side-menu">
    
                <!-- LOGO -->
<!--                 <img style="width:100px;height:50px;" src="images/logo.png" alt="" height="16">
 -->                <a href="<%=request.getContextPath()%>/front_end/home/home.jsp" class="logo text-center ">
                    <span class="">     
                    <img style="width:100%;height:75px; " src="<%=request.getContextPath()%>/img/home.png" alt="" height="16">                

                    </span>
                </a>
                       
			</div>
    </div>
            
            <!-- ========== Left Sidebar end ========== -->

		<div class="content-page">
			<div class="content">
			
				<!-- Topbar Start -->
				<div class="navbar-custom">
					<ul class="list-unstyled topbar-right-menu float-right mb-0">

						<li class="dropdown notification-list"><a
							class="nav-link dropdown-toggle nav-user arrow-none mr-0"
							data-toggle="dropdown" href="#" role="button"
							aria-haspopup="false" aria-expanded="false"> <span
								class="account-user-avatar"> <img
									src="<%=request.getContextPath()%>/img/home.png"
									alt="user-image" class="rounded-circle">
							</span> <span class="float-right">
									<FORM class="float-right" METHOD="" ACTION="">
										<input type="hidden" name="" value="">
										<button type="submit" class="btn btn-dark">登出</button>
									</FORM>
							</span> 
							</span>
						</a></li>

					</ul>
					<button class="button-menu-mobile open-left disable-btn">
						<i class="mdi mdi-menu"></i>
					</button>

					<div class="app-search dropdown d-none d-lg-block">
						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/front_end/forum/forum.do">
							<input type="text" name="forum_id" placeholder="請輸文章編號如F001">
							<input type="hidden" name="action" value="getOne_For_Display">
							<input type="submit" value="查詢文章">
						</FORM>
					</div>
				</div>
				<!-- end Topbar -->


				<div>
					<%-- 錯誤表列 --%>
					<c:if test="${not empty errorMsgs}">
						<font style="color: red">請修正以下錯誤:</font>
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li style="color: red">${message}</li>
							</c:forEach>
						</ul>
					</c:if>



		<ul>
		  <li><a href='listAllForum.jsp'>List</a> all Forums.  <br><br></li>
		  
		  
		  <li>
		    <FORM METHOD="post" ACTION="forum.do" >
		        <b>輸入文章編號 (如F001):</b>
		        <input type="text" name="forum_id">
		        <input type="hidden" name="action" value="getOne_For_Display">
		        <input type="submit" value="送出">
		    </FORM>
		  </li>
		
		  <jsp:useBean id="frSvc" scope="page" class="com.forum.model.ForumService" />
		
		  
		  <li>
		     <FORM METHOD="post" ACTION="forum.do" >
		       <b>選擇文章編號:</b>
		       <select size="1" name="forum_id">
		         <c:forEach var="forumVO" items="${frSvc.all}" > 
		          <option value="${forumVO.forum_id}">${forumVO.forum_id}
		         </c:forEach>   
		       </select>
		       <input type="hidden" name="action" value="getOne_For_Display">
		       <input type="submit" value="送出">
		    </FORM>
		  </li>
		  
		  <li>
		     <FORM METHOD="post" ACTION="forum.do" >
		       <b>選擇文章標題:</b>
		       <select size="1" name="forum_id">
		         <c:forEach var="forumVO" items="${frSvc.all}" > 
		          <option value="${forumVO.forum_id}">${forumVO.forum_title}
		         </c:forEach>   
		       </select>
		       <input type="hidden" name="action" value="getOne_For_Display">
		       <input type="submit" value="送出">
		     </FORM>
		  </li>
		</ul>
		
		
		<h3>文章管理</h3>
		
		<ul>
		  <li><a href='addForum.jsp'>Add</a> a new Forum.</li>
		</ul>					


					

				</div>
				<!-- Footer Start -->
				<footer class="footer">
					<div class="container-fluid">
						<div class="row">
							<div class="col-md-6"></div>
							<div class="col-md-6">
								<div class="text-md-right footer-links d-none d-md-block">
									<a>●聯絡我們客服專線: 0800-000-482</a> <a>●服務時間週一至週六 09:00-18:00
										(週日與國定假日除外)</a>
								</div>
							</div>
						</div>
					</div>
				</footer>
				<!-- end Footer -->
			</div>

		</div>
		<script src="<%=request.getContextPath()%>/js/vendor.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/app.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/apexcharts.min.js"></script>
		<script
			src="<%=request.getContextPath()%>/js/jquery-jvectormap-1.2.2.min.js"></script>
		<script
			src="<%=request.getContextPath()%>/js/jquery-jvectormap-world-mill-en.js"></script>
		<script src="<%=request.getContextPath()%>/js/demo.dashboard.js"></script>
</body>
</html>