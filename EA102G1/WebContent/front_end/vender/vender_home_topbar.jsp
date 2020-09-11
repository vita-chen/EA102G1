<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.vender.model.*"%>

		<div class="main-header" style="height: 70px;">
			<!-- Logo Header background-color:#FF88C2;-->
			<div class="logo-header" data-background-color="green" style="height: 70px;">
				
	<a href="<%=request.getContextPath()%>/front_end/home/home.jsp"
		class="logo text-center "> <span class=""> <img
			style="width: 80%; height: 45px;"
			src="<%=request.getContextPath()%>/img/logo.png" alt="" height="16">

	</span>
	</a>	
				<button class="navbar-toggler sidenav-toggler ml-auto" type="button" data-toggle="collapse" data-target="collapse" aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon">
						<i class="icon-menu"></i>
					</span>
				</button>
				<button class="topbar-toggler more"><i class="icon-options-vertical"></i></button>
				<div class="nav-toggle">
					<button class="btn btn-toggle toggle-sidebar">
						<i class="icon-menu"></i>
					</button>
				</div>
			</div>
			<!-- End Logo Header -->

			<!-- Navbar Header -->
	<ul class="list-unstyled topbar-right-menu float-right mb-0">

		<a class="nav-link  nav-user  mr-0">
			<span class="account-user-avatar"> 
							<img src="<%=request.getContextPath() %>/vender/vender.do?action=getphot&vender_id=${vendervo.vender_id}"
								 alt="user-image" class="rounded-circle">
							</span>
							<span class="float-right">
			<FORM class="float-right" METHOD="post"
				ACTION="<%=request.getContextPath()%>/vender/vender.do">
				<input type="hidden" name="action" value="session_off">
				<button type="submit"
					href="<%=request.getContextPath()%>/front_end/home/home.jsp"
					class="float-right btn btn-dark">登出</button>
			</FORM> <span style="font-size: 14px; color: #000000"
			class="float-left mr-2"> <span>您好~${vendervo.ven_contact}</span> <span
				style="font-size: 14px; color: #000000" class="account-position">廠商編號:${vendervo.vender_id}</span>
		</span>
		</a>
	</ul>
		</div>