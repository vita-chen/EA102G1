<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.car.model.*"%>
<%@ page import="com.carPic.model.*"%>
<%@ page import="com.vender.model.*"%>
<%@ page import="com.car.config.*"%>

<%
    Object account = session.getAttribute("vendervo");                  // 從 session內取出 (key) account的值
    if (account == null) {                                             // 如為 null, 代表此user未登入過 , 才做以下工作
      response.sendRedirect(request.getContextPath()+"/front_end/vender/vender_login.jsp");   //*工作2 : 請該user去登入網頁(login.html) , 進行登入
      return;
    }
    
	VenderVO vendervo = (VenderVO) session.getAttribute("vendervo");
    
	CarVO carVO = new CarVO();
	carVO.setVender_id(vendervo.getVender_id());
	CarService carSvc = new CarService();
	List<CarVO> carVOList = carSvc.getAllByVenderId(carVO);
	pageContext.setAttribute("carVOList", carVOList);

	List<CarPicVO> carPicVOList = carSvc.getAllCarPic();
	pageContext.setAttribute("carPicVOList", carPicVOList);

	pageContext.setAttribute("contextPath", request.getContextPath()); // 把request.getContextPath()塞到pageContext，並取一個變數叫做contextPath去存。為了取得專案路徑而寫

	// 載入設定檔
	Config cfg = new Config(getServletContext());
	Map<String, String> cstatusMap = cfg.get("car", "cstatusMap");
	pageContext.setAttribute("cstatusMap", cstatusMap);
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
								<a href="#">禮車管理/上架</a>
							</li>
							<li class="separator">
								<i class="flaticon-right-arrow"></i>
							</li>
							<li class="nav-item">
								<a href="#">禮車列表</a>
							</li>
						</ul>	
</div>
<!-- 塞資料的地方 -->
<!-- 塞資料的地方 -->
<div class="page-category">
					
<h3 class="bg-primary rounded text-white text-center"
		style="padding: 10px;">禮車列表頁 |廠商</h3>

	<table class="table table-striped table-bordered">
	<thead class="thead-dark">
		<tr>
		<th class="text-center" scope="col">No.</th>
			<th class="text-center" scope="col">禮車編號</th>
			<th class="text-center" scope="col">廠商編號</th>
			<th class="text-center" scope="col">車子品牌</th>
			<th class="text-center" scope="col">車型</th>
			<th class="text-center" scope="col">車子介紹</th>
			<th class="text-center" scope="col">車子價格</th>
			<th class="text-center" scope="col">上架時間</th>
			<th class="text-center" scope="col">狀態</th>
			<th class="text-center" scope="col">照片</th>
			<th class="text-center" scope="col">修改</th>
			<th class="text-center" scope="col">刪除</th>
		</tr>
		</thead>
		<tbody>
			<%
				int i = 1;
			%>
		<c:forEach var="carVO" items="${carVOList}">

		<tr>
					<th class="text-center align-middle" scope="row"><%=i%></th>
					<%
						i++;
					%>
				<td class="text-center align-middle">${carVO.cid}</td>
				<td class="text-center align-middle">${carVO.vender_id}</td>
				<td class="text-center align-middle">${carVO.cbrand}</td>
				<td class="text-center align-middle">${carVO.cmodel}</td>
				<td class="align-middle">${carVO.cintro}</td>
				<td class="text-center align-middle">${carVO.cprice}</td>
				<td class="text-center align-middle">${carVO.caddtime}</td>
				<td class="text-center align-middle">${cstatusMap.get(carVO.getCstatus().toString())}</td>
				

				<td class="text-center align-middle"><c:forEach var="carPicVO" items="${carPicVOList}"> <!-- 先把所有的圖片都取出，有一百張取一百張 -->
						<c:if test="${carVO.cid == carPicVO.cid}"> <!-- 取出的所有照片都去核對該照片所對應的禮車編號，如果符合的話就代表該照片是該禮車的照片，就可以湊出路徑了(每台禮車都要核對100次) -->
							<div class="row">
								<div class="col">
									<!--<a>${carVO.cid}</a><br/>-->  <!-- 測試carVO.cid跟carPicVO.cid是否一致，藉此確認禮車編號有抓對 -->
									<!--<a>${carPicVO.cid}</a><br/>-->
									<img
										src="${contextPath}/car/car.do?action=getCarPic&cpic_id=${carPicVO.cpic_id}"
										width="100px" />
								</div>
							</div>
						</c:if>
					</c:forEach></td>

				<td class="text-center align-middle">
					<button type="button" class="btn btn-primary" data-toggle="modal"
						data-target="#updateModal_${carVO.cid}">修改</button>
					<div class="modal fade" id="updateModal_${carVO.cid}" tabindex="-1"
						role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="modalLabel">修改</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<form action="<%=request.getContextPath() + "/car/car.do"%>"
									method="post"> <!-- 在CarServlet.java是放在 doPost中，所以method也要是"post" -->
									<div class="modal-body">
									
										<div class="form-group">
											<input type="hidden" name="action" value="update"
												class="form-control"> <input type="hidden"
												name="cid" value="${carVO.cid}" class="form-control">
										</div>

										<div class="form-group">
											<label>車子品牌</label><br /> <input type="text" name="cbrand"
												value="${carVO.cbrand}" class="form-control" readonly>
										</div>
										<div class="form-group">
											<label>車子型號</label><br /> <input type="text" name="cmodel"
												value="${carVO.cmodel}" class="form-control" readonly>
										</div>
										<div class="form-group">
											<label>車子介紹</label> <input required  type="text" name="cintro"
												value="${carVO.cintro}" class="form-control">
										</div>
										<div class="form-group">
											<label>租一天的租金</label> <input required  type="text" name="cprice"
												value="${carVO.cprice}" class="form-control">
										</div>

										<div class="form-group">
											<div class="row">
												<div class="col">
													<label>狀態</label>
												</div>
											</div>
											<div class="row">
												<div class="col">
													<label>下架</label> <input type="radio" name="cstatus"
														value="1"
														<c:if test="${carVO.cstatus == 1}">
																checked="checked"
															</c:if> />
													<label>上架</label> <input type="radio" name="cstatus"
														value="0"
														<c:if test="${carVO.cstatus == 0}">
																checked="checked"
															</c:if> />
												</div>
											</div>
										</div>

									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary"
											data-dismiss="modal">關閉</button>
										<button type="submit" class="btn btn-primary">送出</button>
									</div>
								</form>
							</div>
						</div>
					</div>

				</td>

				<td class="text-center align-middle">
					<form action="<%=request.getContextPath() + "/car/car.do"%>"
						method="post"> <!-- 在CarServlet.java是放在 doPost中，所以method也要是"post" -->
						<input type="hidden" name="action" value="delete"> <input
							type="hidden" name="cid" value="${carVO.cid}"> <input
							type="submit" value="刪除" class="btn btn-danger">
					</form>
				</td>
			</tr>
		</c:forEach>
		
		</tbody>
		
		
	</table>		
					
</div>
<!-- 塞資料的地方 -->
<!-- 塞資料的地方 -->
</div>
</div>

</div>
</div>
<%@ include file="/front_end/vender/vender_home_js.jsp" %>	
</body>
</html>