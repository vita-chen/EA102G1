<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.vender.model.*"%>


		<div class="sidebar sidebar-style-2">			
			<div class="sidebar-wrapper scrollbar scrollbar-inner">
				<div class="sidebar-content">
									<!-- 顏色 -->
					<ul class="nav nav-success">
					
						<li class="nav-section">
							<span class="sidebar-mini-icon">
								<i class="fa fa-ellipsis-h"></i>
							</span>
							<h1 class="text-section" >廠商專區</h1>
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
										<a href="<%=request.getContextPath()%>/front_end/vender/vender_management/vender_data.jsp">
											<span class="sub-item">廠商資料查看</span>
										</a>
									</li>
									<li>
										<a href="<%=request.getContextPath()%>/front_end/vender/vender_management/vender_modify.jsp">
											<span class="sub-item">編輯廠商資料</span>
										</a>
									</li>
								</ul>
							</div>
						</li>
						<li class="nav-item">
							<a data-toggle="collapse" href="#sidebarLayouts">
								<i class="fas fa-th-list"></i>
								<p>禮車管理/上架</p>
								<span class="caret"></span>
							</a>
							<div class="collapse" id="sidebarLayouts">
								<ul class="nav nav-collapse">
									<li>
										<a href="<%=request.getContextPath()%>/front_end/vender/car/vender_listAllCar.jsp">
											<span class="sub-item">禮車列表</span>
										</a>
									</li>
									<li>
										<a href="<%=request.getContextPath()%>/front_end/vender/car/addCar.jsp">
											<span class="sub-item">上架禮車</span>
										</a>
									</li>
									<li>
										<a href="<%=request.getContextPath()%>/front_end/vender/car/vender_listAllCarExt.jsp">
											<span class="sub-item">加購品列表</span>
										</a>
									</li>
									<li>
										<a href="<%=request.getContextPath()%>/front_end/vender/car/addCarExt.jsp">
											<span class="sub-item">上架加購品</span>
										</a>
									</li>																		
								</ul>
							</div>
						</li>
						<li class="nav-item">
							<a href="<%=request.getContextPath()%>/front_end/vender/car/vender_listAllCarOrderVen.jsp">
								<i class="fas fa-pen-square"></i>
								<p>禮車訂單管理</p>
								
							</a>
						<!-- <li class="nav-item">
							<a data-toggle="collapse" href="#forms">
								<i class="fas fa-pen-square"></i>
								<p>Forms</p>
								<span class="caret"></span>
							</a>
							<div class="collapse" id="forms">
								<ul class="nav nav-collapse">
									<li>
										<a href="forms/forms.html">
											<span class="sub-item">Basic Form</span>
										</a>
									</li>
								</ul>
							</div> -->
						</li>
						<li class="nav-item">
							<a data-toggle="collapse" href="#tables">
								<i class="fas fa-table"></i>
								<p>婚紗管理/上架</p>
								<span class="caret"></span>
							</a>
							<div class="collapse" id="tables">
								<ul class="nav nav-collapse">
									<li>
										<a href="<%=request.getContextPath()%>/front_end/vender/dress/vender_ListAllDC.jsp">
											<span class="sub-item">婚紗列表/上架</span>
										</a>
									</li>
																		<li>
										<a href="<%=request.getContextPath()%>/front_end/vender/dress/vender_ListAddDressOn.jsp">
											<span class="sub-item">婚紗加購列表/上架</span>
										</a>
									</li>
								</ul>
							</div>
						</li>
						<li class="nav-item">
							<!-- <a data-toggle="collapse" href="#maps">
								<i class="fas fa-map-marker-alt"></i>
								<p>婚紗訂單管理</p>
								<span class="caret"></span>
							</a> -->

						
								<a href="<%=request.getContextPath()%>/front_end/vender/dress/vender_listDressOrder.jsp">
									<i class="fas fa-pen-square"></i>
									<p>婚紗訂單管理</p>
									
								</a>
							<div class="collapse" id="maps">
								<ul class="nav nav-collapse">
									<li>
										<a href="maps/jqvmap.html">
											<span class="sub-item">JQVMap</span>
										</a>
									</li>
								</ul>
							</div>
						</li>
						<li class="nav-item">
							<a data-toggle="collapse" href="#charts">
								<i class="fas fa-th-list"></i>
								<p>婚攝方案管理/上架</p>
								<span class="caret"></span>
							</a>
							<div class="collapse" id="charts">
								<ul class="nav nav-collapse">
									<li>
										<a href="<%=request.getContextPath()%>/front_end/vender/wp/vender_listAllWPCase.jsp">
											<span class="sub-item">婚攝方案列表</span>
										</a>
									</li>
									<li>
										<a href="<%=request.getContextPath()%>/front_end/vender/wp/vender_addWPcase.jsp">
											<span class="sub-item">上架婚攝方案</span>
										</a>
									</li>
								</ul>
							</div>
						</li>
						<!-- <li class="nav-item">
							<a href="widgets.html">
								<i class="fas fa-desktop"></i>
								<p>Widgets</p>
								<span class="badge badge-success">4</span>
							</a>
						</li> -->
						<li class="nav-item">
							<!-- <a data-toggle="collapse" href="#submenu">
								<i class="fas fa-bars"></i>
								<p>婚攝訂單管理</p>
								<span class="caret"></span>
							</a> -->
							<a href="<%=request.getContextPath()%>/front_end/vender/wp/vender_listAllWPOrder.jsp">
								<i class="fas fa-pen-square"></i>
								<p>婚攝訂單管理</p>
								
							</a>
							<!-- <div class="collapse" id="submenu">
								<ul class="nav nav-collapse">
									<li>
										<a data-toggle="collapse" href="#subnav1">
											<span class="sub-item">Level 1</span>
											<span class="caret"></span>
										</a>
										<div class="collapse" id="subnav1">
											<ul class="nav nav-collapse subnav">
												<li>
													<a href="#">
														<span class="sub-item">Level 2</span>
													</a>
												</li>
												<li>
													<a href="#">
														<span class="sub-item">Level 2</span>
													</a>
												</li>
											</ul>
										</div>
									</li>
									<li>
										<a data-toggle="collapse" href="#subnav2">
											<span class="sub-item">Level 1</span>
											<span class="caret"></span>
										</a>
										<div class="collapse" id="subnav2">
											<ul class="nav nav-collapse subnav">
												<li>
													<a href="#">
														<span class="sub-item">Level 2</span>
													</a>
												</li>
											</ul>
										</div>
									</li>
									<li>
										<a href="#">
											<span class="sub-item">Level 1</span>
										</a>
									</li>
								</ul>
							</div> -->
						</li>
						<!-- <li class="mx-4 mt-2">
							<a href="http://themekita.com/atlantis-bootstrap-dashboard.html" class="btn btn-primary btn-block"><span class="btn-label mr-2"> <i class="fa fa-heart"></i> </span>Buy Pro</a> 
						</li> -->
					</ul>
				</div>
			</div>
		</div>