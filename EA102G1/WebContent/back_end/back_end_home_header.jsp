<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="left-side-menu">

	<!-- LOGO -->
	<a href="<%=request.getContextPath()%>/front_end/home/home.jsp"
		class="logo text-center "> <span class=""> <img
			style="width: 75%; height: 50px;"
			src="<%=request.getContextPath()%>/img/home.png" alt="" height="16">

	</span>
	</a>

	<div class="h-100" id="left-side-menu-container" data-simplebar>

		<ul class="metismenu side-nav">
			<li class="side-nav-title side-nav-item"></li>
			<li class="side-nav-item"><a href="javascript: void(0);"
				class="side-nav-link"> <span> <font size="3">管理員</font></span>
			</a>
				<ul class="side-nav-second-level" aria-expanded="false">
					<li><a
						href="<%=request.getContextPath()%>/back_end/adm/list_all_adm.jsp"><font
							size="2">個人資料</font></a></li>
					<li><a
						href="<%=request.getContextPath()%>/back_end/adm/list_all_adm.jsp"><font
							size="2">管理員總覽</font></a></li>
		</ul>
		</li>
		</ul>

		<ul class="metismenu side-nav">
			<li class="side-nav-title side-nav-item"></li>
			<li class="side-nav-item"><a href="javascript: void(0);"
				class="side-nav-link"> <span> <font size="3">廠商管理</font></span>
			</a>
				<ul class="side-nav-second-level" aria-expanded="false">
					<li><a
						href="<%=request.getContextPath()%>/back_end/vender/list_all_vender.jsp"><font
							size="2">廠商總覽</font></a></li>
					<li>
					<li><a
						href="<%=request.getContextPath()%>/back_end/vender/list_all_verification_vender.jsp"><font
							size="2">未驗證廠商</font></a></li>
					<li><a
						href="<%=request.getContextPath()%>/back_end/vender/list_all_blockade_vender.jsp"><font
							size="2">已封鎖廠商</font></a></li></li>
		</ul>
		</li>
		</ul>
		
		<ul class="metismenu side-nav">

			<li class="side-nav-title side-nav-item"></li>

			<li class="side-nav-item"><a href="javascript: void(0);"
				class="side-nav-link"> <span> <font size="3">會員管理</font></span>
			</a>
				<ul class="side-nav-second-level" aria-expanded="false">
					<li><a
						href="<%=request.getContextPath()%>/back_end/membre/list_all_membre.jsp"><font
							size="2">會員總覽</font></a></li>		
		</ul>
		</li>
		</ul>
		
		<ul class="metismenu side-nav">
			<li class="side-nav-title side-nav-item"></li>
			<li class="side-nav-item"><a href="javascript: void(0);"
				class="side-nav-link"> <span> <font size="3">檢舉管理</font></span>
			</a>
				<ul class="side-nav-second-level" aria-expanded="false">

					<li><a
						href="<%=request.getContextPath()%>"><font
							size="2">婚攝訂單</font></a></li>
					<li><a
						href="<%=request.getContextPath()%>/back_end/dressorder/list_dress_order.jsp"><font
							size="2">婚紗訂單</font></a></li></li>
		</ul>
		</li>
		</ul>
		
				<ul class="metismenu side-nav">
					<li class="side-nav-title side-nav-item"></li>
					<li class="side-nav-item"><a href="javascript: void(0);"
						class="side-nav-link"> <span> <font size="3">廣告管理</font></span>
					</a>
						<ul class="side-nav-second-level" aria-expanded="false">
							<li><a
								href="<%=request.getContextPath()%>/back_end/ad/listAllAd.jsp"><font
									size="2">廣告總覽</font></a></li>
							<li><a
								href="<%=request.getContextPath()%>/back_end/ad/addAd.jsp"><font
									size="2">新增廣告</font></a></li>

							<li><a href="<%=request.getContextPath()%>/back_end/ad/listAllAd.jsp">
							<font size="2">首頁輪播大圖上傳/修改</font></a></li>							
				</ul>
				</li>
				</ul>

				<ul class="metismenu side-nav">
					<li class="side-nav-title side-nav-item"></li>
					<li class="side-nav-item"><a href="javascript: void(0);"
						class="side-nav-link"> <span> <font size="3">置定公告管理</font></span>
					</a>
						<ul class="side-nav-second-level" aria-expanded="false">
							<li><a
								href="<%=request.getContextPath()%>/back_end/ptp/listAllPtp.jsp"><font
									size="2">置定公告總覽</font></a></li>
							<li><a
								href="<%=request.getContextPath()%>/back_end/ptp/addPtp.jsp"><font
									size="2">新增置定公告</font></a></li>		
				</ul>
				</li>
				</ul>
	</div>
</div>