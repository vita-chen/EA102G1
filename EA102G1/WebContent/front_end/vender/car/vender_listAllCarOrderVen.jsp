<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.vender.model.*"%>
<%@ page import="com.carOrder.model.*"%>
<%@ page import="com.car.model.*"%>
<%@ page import="com.carExt.model.*"%>
<%@ page import="com.carPic.model.*"%>
<%@ page import="com.car.config.*"%>

<%
    Object account = session.getAttribute("vendervo");                  // 從 session內取出 (key) account的值
    if (account == null) {                                             // 如為 null, 代表此user未登入過 , 才做以下工作
      response.sendRedirect(request.getContextPath()+"/front_end/vender/vender_login.jsp");   //*工作2 : 請該user去登入網頁(login.html) , 進行登入
      return;
    }
    
	VenderVO vendervo = (VenderVO) session.getAttribute("vendervo");
    
	CarOrderVO carOrderVO = new CarOrderVO();
	carOrderVO.setVender_id(vendervo.getVender_id());
	CarOrderService carOrderSvc = new CarOrderService();
	List<CarOrderVO> carOrderVOList = carOrderSvc.getAllByVenderId(carOrderVO);
	pageContext.setAttribute("carOrderVOList", carOrderVOList);

	Map<String, List<CarExtVO>> allCarExtMap = carOrderSvc.getAllCarExtMap();
	pageContext.setAttribute("allCarExtMap", allCarExtMap);

	CarService carSvc = new CarService();
	Map<String, CarVO> allCarMap = carSvc.getAllCarMap();
	pageContext.setAttribute("allCarMap", allCarMap);

	Map<String, CarPicVO> allCarPicMap = carSvc.getAllCarPicMap();
	pageContext.setAttribute("allCarPicMap", allCarPicMap);

	pageContext.setAttribute("contextPath", request.getContextPath()); // 把request.getContextPath()塞到pageContext，並取一個變數叫做contextPath去存。為了取得專案路徑而寫
	
	// 載入設定檔
	Config cfg = new Config(getServletContext());
	Map<String, String> codStatusMap = cfg.get("carOrder", "codStatusMap");
	pageContext.setAttribute("codStatusMap", codStatusMap);
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
								<a href="#">禮車訂單管理</a>
							</li>
						</ul>	
</div>
<!-- 塞資料的地方 -->
<!-- 塞資料的地方 -->
<div class="page-category">
					
<h3 class="bg-primary rounded text-white text-center"
		style="padding: 10px;">禮車訂單列表頁 | 廠商</h3>

	<table class="table table-striped table-bordered">
		<thead class="thead-dark">
			<tr>
				<th class="text-center" scope="col">No.</th>
				<th class="text-center" scope="col">訂單成立時間</th>
				<th class="text-center" scope="col">訂單編號</th>
				<th class="text-center" scope="col">廠商編號</th>
				<th class="text-center" scope="col">會員編號</th>
				<th class="text-center" scope="col">訂單細節</th>
				<th class="text-center" scope="col">最終價格</th>
				<th class="text-center" scope="col">禮車訂單狀態</th>
				<th class="text-center" scope="col">確認訂單已完成</th>
				<th class="text-center" scope="col">取消訂單</th>
			</tr>
		</thead>
		<tbody>
			<%
				int i = 1;
			%>
			<c:forEach var="carOrderVO" items="${carOrderVOList}">
				<tr>
					<th class="text-center align-middle" scope="row"><%=i%></th>
					<%
						i++;
					%>
					<td class="text-center align-middle">${carOrderVO.getCod_time()}</td>
					<td class="text-center align-middle">${carOrderVO.cod_id}</td>
					<td class="text-center align-middle">
						<button type="button" class="btn btn-link">${carOrderVO.getVender_id()}</button>
					</td>
					<td class="text-center align-middle"><button type="button"
							class="btn btn-link">${carOrderVO.getMembre_id()}</button></td>
					<td class="text-center align-middle">


						<button type="button" class="btn btn-warning btn-sm"
							data-toggle="modal"
							data-target="#updateModal_${carOrderVO.getCod_id()}">查看訂單細節</button>
						<div class="modal fade" id="updateModal_${carOrderVO.getCod_id()}"
							tabindex="-1" role="dialog" aria-labelledby="modalLabel"
							aria-hidden="true">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title" id="modalLabel">${carOrderVO.getCod_id()}—訂單細節</h5>
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>

									<div class="modal-body">
										<div class="card-header">禮車名稱</div>
										<div class="card mb-3" style="max-width: 540px;">
											<div class="row no-gutters">
												<div class="col-md-4">
													<img
														src="${contextPath}/car/car.do?action=getCarPic&cpic_id=${allCarPicMap.get(carOrderVO.getCid()).getCpic_id()}"
														class="border card-img" alt="...">
												</div>
												<div class="col-md-8">
													<div class="card-body">
														<h5 class="card-title">${allCarMap.get(carOrderVO.getCid()).getCbrand()}-${allCarMap.get(carOrderVO.getCid()).getCmodel()}</h5>
														<h4 class="price">$${allCarMap.get(carOrderVO.getCid()).getCprice()}</h4>

													</div>
												</div>
											</div>
										</div>
										<div class="card-header">加購項目</div>
										<div class="card mb-3" style="max-width: 540px;">
											<c:forEach var="carExtVO"
												items="${allCarExtMap.get(carOrderVO.getCod_id())}">
												<div class="row no-gutters">
													<div class="col-md-4">
														<img
															src="${contextPath}/carExt/carext.do?action=getCarExtPic&cext_id=${carExtVO.cext_id}"
															class="border card-img" alt="...">
													</div>
													<div class="col-md-8">
														<div class="card-body">
															<h5 class="card-title">${carExtVO.cext_name}</h5>
															<h4 class="price">$${carExtVO.cext_price}</h4>

														</div>
													</div>
												</div>
											</c:forEach>
										</div>

										<ul>
											<li class="text-left">訂金：$${carOrderVO.getCdeposit()}</li>
											<li class="text-left">押金：$${carOrderVO.getCreturn_pay()}</li>
											<li class="text-left">租車日：${carOrderVO.getCneed_date()}</li>
											<li class="text-left">還車日：${carOrderVO.getCreturn_date()}</li>
											<li class="text-left">起點：${carOrderVO.getCstart()}</li>
											<li class="text-left">終點：${carOrderVO.getCdest()}</li>
										</ul>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary"
											data-dismiss="modal">關閉</button>

									</div>

								</div>
							</div>
						</div>

					</td>
					<td class="text-center align-middle">${carOrderVO.getCfinal_price()}</td>
					<td class="text-center align-middle">${codStatusMap.get(carOrderVO.getCod_status().toString())}</td>
					<%-- ************一旦會員or廠商按下「取消訂單」、「確認訂單已完成」，則lock住「確認訂單已完成」按鈕************ --%>
					<td class="text-center align-middle">
						<form method="post" action="${contextPath}/carOrder/carorder.do">
							<c:choose>
								<c:when test="${carOrderVO.getCod_status() >=2}">
									<input type="hidden" name="action" value="updateCarOrderStatus">
									<input type="hidden" name="cod_id" value="${carOrderVO.cod_id}">
									<input type="hidden" name="cod_status" value="2">
									<input type="submit" value="確認訂單已完成" disabled
										class="btn btn-info btn-sm">
								</c:when>
								<c:otherwise>
									<input type="hidden" name="action" value="updateCarOrderStatus">
									<input type="hidden" name="cod_id" value="${carOrderVO.cod_id}">
									<input type="hidden" name="cod_status" value="2">
									<input type="submit" value="確認訂單已完成"
										class="btn btn-info btn-sm">
								</c:otherwise>
							</c:choose>
						</form>
					</td>
					<%-- ************一旦廠商按下「確認訂單已完成」，則lock住「取消訂單」按紐************ --%>
					<td class="text-center align-middle">
						<form method="post" action="${contextPath}/carOrder/carorder.do">
							<c:choose>
								<c:when test="${carOrderVO.getCod_status() >1}">
									<input type="hidden" name="action" value="updateCarOrderStatus">
									<input type="hidden" name="cod_id" value="${carOrderVO.cod_id}">
									<input type="submit" value="取消訂單" disabled
										class="btn btn-danger">
								</c:when>
								<c:otherwise>
									<input type="hidden" name="action" value="updateCarOrderStatus">
									<input type="hidden" name="cod_id" value="${carOrderVO.cod_id}">
									<input type="hidden" name="cod_status" value="4">
									<input type="submit" value="取消訂單" class="btn btn-danger btn-sm">
								</c:otherwise>
							</c:choose>
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