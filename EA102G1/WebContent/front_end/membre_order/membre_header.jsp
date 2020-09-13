<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

		<div class="sidebar sidebar-style-2">			
			<div class="sidebar-wrapper scrollbar scrollbar-inner">
				<div class="sidebar-content">
									<!-- 顏色 -->
					<ul class="nav nav-info">
						
						<li class="nav-section">
							<span class="sidebar-mini-icon">
								<i class="fa fa-ellipsis-h"></i>
							</span>
							<h1 class="text-section" >會員專區</h1>
						</li>
						<li class="nav-item active">
							<a data-toggle="collapse" href="#base">
								<i class="fas fa-layer-group"></i>
								<p>帳戶管理</p>
								<span class="caret"></span>
							</a>
							
							<div class="collapse" id="base">
								<ul class="nav nav-collapse">
									<li>
										<a href="components/avatars.html">
											<span class="sub-item">會員資料</span>
										</a>
									</li>
									<li>
										<a href="components/buttons.html">
											<span class="sub-item">會員資料修改</span>
										</a>
									</li>
									
								</ul>
							</div>
						</li>
						
						<li class="nav-item">
							<a data-toggle="collapse" href="#sidebarLayouts">
								<i class="fas fa-th-list"></i>
								<p>訂單管理</p>
								<span class="caret"></span>
							</a>
							<div class="collapse" id="sidebarLayouts">
								<ul class="nav nav-collapse">
									<li>
										<a href="<%=request.getContextPath()%>/front_end/membre_order/membre_order_car.jsp">
											<span class="sub-item">禮車訂單</span>
										</a>
									</li>
									<li>
										<a href="<%=request.getContextPath()%>/front_end/membre_order/membre_order_dress.jsp">
											<span class="sub-item">婚紗訂單</span>
										</a>
									</li>
									 <li>
										<a href="<%=request.getContextPath()%>/front_end/membre_order/membre_order_wp.jsp">
											<span class="sub-item">婚攝訂單</span>
										</a>
									</li>
									 <li>
										<a href="<%=request.getContextPath()%>/order/order.do?action=getDifferentStatusOrders_seller&order_status=All">
											<span class="sub-item">商城賣家訂單管理</span>
										</a>
									</li>
									
								</ul>
							</div>
						</li>
						
						<li class="nav-item">
							<a data-toggle="collapse" href="#tables">
								<i class="fas fa-table"></i>
								<p>收藏管理</p>
								<span class="caret"></span>
							</a>
							<div class="collapse" id="tables">
								<ul class="nav nav-collapse">

									<li>
										<a href="<%=request.getContextPath()%>/front_end/membre_order/membre_dress_listall_track.jsp">
											<span class="sub-item">婚紗收藏清單</span>
										</a>
									</li>
									<li>
										<a href="<%=request.getContextPath()%>/front_end/membre_order/membre_wp_listall_track.jsp">
											<span class="sub-item">婚攝收藏清單</span>
										</a>
									</li>

								</ul>
							</div>
						</li>

						
					</ul>
				</div>
			</div>
		</div>