<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- Topbar Start -->
<div class="navbar-custom">
	<ul class="list-unstyled topbar-right-menu float-right mb-0">

		<a class="nav-link  nav-user  mr-0">

			<FORM class="float-right" METHOD="post"
				ACTION="<%=request.getContextPath()%>/vender/vender.do">
				<input type="hidden" name="action" value="session_off">
				<button type="submit"
					href="<%=request.getContextPath()%>/front_end/home/home.jsp"
					class="float-right btn btn-dark">登出</button>
			</FORM> <span style="font-size: 14px; color: #000000"
			class="float-left mr-2"> <span>您好~${admvo.adm_name}</span> <span
				style="font-size: 14px; color: #000000" class="account-position">管理編號:${admvo.adm_id}</span>
		</span>
		</a>

	</ul>
	<button class="button-menu-mobile open-left disable-btn">
		<i class="mdi mdi-menu"></i>
	</button>

	<div class="app-search dropdown d-none d-lg-block">
		<FORM METHOD="post"
			ACTION="<%=request.getContextPath()%>/vender/vender.do">
			<input type="text" name="vender_id" placeholder="請輸廠商編號如V001">
			<input type="hidden" name="action" value="getOne_For_Display">
			<input type="submit" value="查詢廠商">
		</FORM>
	</div>
</div>
<!-- end Topbar -->