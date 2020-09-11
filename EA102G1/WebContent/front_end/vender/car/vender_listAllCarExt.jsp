<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.carExt.model.*"%>
<%@ page import="com.vender.model.*"%>
<%@ page import="com.car.config.*"%>

<%
    Object account = session.getAttribute("vendervo");                  // 從 session內取出 (key) account的值
    if (account == null) {                                             // 如為 null, 代表此user未登入過 , 才做以下工作
      response.sendRedirect(request.getContextPath()+"/front_end/vender/vender_login.jsp");   //*工作2 : 請該user去登入網頁(login.html) , 進行登入
      return;
    }
    
	VenderVO vendervo = (VenderVO) session.getAttribute("vendervo");

	CarExtVO carExtVO = new CarExtVO();
	carExtVO.setVender_id(vendervo.getVender_id());
	CarExtService carExtSvc = new CarExtService();
	List<CarExtVO> carExtVOList = carExtSvc.getAllByVenderId(carExtVO);
	pageContext.setAttribute("carExtVOList", carExtVOList);

	pageContext.setAttribute("contextPath", request.getContextPath()); // 把request.getContextPath()塞到pageContext，並取一個變數叫做contextPath去存。為了取得專案路徑而寫

	// 載入設定檔
	Config cfg = new Config(getServletContext());
	Map<String, String> cstatusMap = cfg.get("car", "cstatusMap");
	pageContext.setAttribute("cstatusMap", cstatusMap);
	
	Map<String, String> cextStatusMap = cfg.get("carExt", "cextStatusMap");
	pageContext.setAttribute("cextStatusMap", cextStatusMap);
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
								<a href="#">加購品列表</a>
							</li>
						</ul>	
</div>
<!-- 塞資料的地方 -->
<!-- 塞資料的地方 -->
<div class="page-category">
					
<h3 class="bg-primary rounded text-white text-center"
		style="padding: 10px;">加購商品清單頁 |廠商</h3>


	<table class="table table-striped table-bordered">
		<thead class="thead-dark">
		<tr>
		<th class="text-center" scope="col">No.</th>
			<th class="text-center" scope="col">禮車加購商品編號</th>
			<th class="text-center" scope="col">廠商編號</th>
			<th class="text-center" scope="col">禮車加購商品類別</th>
			<th class="text-center" scope="col">加購品名稱</th>
			<th class="text-center" scope="col">加購品價錢</th>
			<th class="text-center" scope="col">加購品照片</th>
			<th class="text-center" scope="col">上架時間</th>
			<th class="text-center" scope="col">狀態</th>
			<th class="text-center" scope="col">修改</th>
			<th class="text-center" scope="col">刪除</th>
		</tr>
		</thead>
				<tbody>
			<%
				int i = 1;
			%>
		<c:forEach var="carExtVO" items="${carExtVOList}">

						<tr>
					<th class="text-center align-middle" scope="row"><%=i%></th>
					<%
						i++;
					%>
				<td class="text-center align-middle">${carExtVO.cext_id}</td>
				<td class="text-center align-middle">${carExtVO.vender_id}</td>
				<td class="text-center align-middle">${cextStatusMap.get(carExtVO.getCext_cat_id().toString())}</td>
				<td class="text-center align-middle">${carExtVO.cext_name}</td>
				<td class="text-center align-middle">${carExtVO.cext_price}</td>
				<td class="text-center align-middle"><img src="${contextPath}/carExt/carext.do?action=getCarExtPic&cext_id=${carExtVO.cext_id}" width="100px" /></td>
				<td class="text-center align-middle">${carExtVO.cext_addtime}</td>
				<td class="text-center align-middle">${cstatusMap.get(carExtVO.getCext_status().toString())}</td>

				<td class="text-center align-middle">
					<button type="button" class="btn btn-primary" data-toggle="modal"
						data-target="#updateModal_${carExtVO.cext_id}">修改</button>
					<div class="modal fade" id="updateModal_${carExtVO.cext_id}"
						tabindex="-1" role="dialog" aria-labelledby="modalLabel"
						aria-hidden="true">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="modalLabel">修改</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<form
									action="<%=request.getContextPath() + "/carExt/carext.do"%>"
									method="post">
									<div class="modal-body">
										<div class="form-group">
											<input type="hidden" name="action" value="update"
												class="form-control"> <input type="hidden"
												name="cext_id" value="${carExtVO.cext_id}"
												class="form-control">
										</div>

										<div class="form-group">
											<label>禮車加購商品類別</label><br /> <input type="text"
												name="cext_cat_id" value="${carExtVO.cext_cat_id}" class="form-control"
												readonly>
										</div>
										<div class="form-group">
											<label>加購品名稱</label> <input type="text" name="cext_name"
												value="${carExtVO.cext_name}" class="form-control">
										</div>
										<div class="form-group">
											<label>加購品價錢</label> <input type="text" name="cext_price"
												value="${carExtVO.cext_price}" class="form-control">
										</div>
										<div class="form-group">
											<div class="row">
												<div class="col">
													<label>狀態</label>
												</div>
											</div>
											<div class="row">
												<div class="col">
													<label>下架</label> <input type="radio" name="cext_status"
														value="1"
														<c:if test="${carExtVO.cext_status == 1}">
																checked="checked"
															</c:if> />
													<label>上架</label> <input type="radio" name="cext_status"
														value="0"
														<c:if test="${carExtVO.cext_status == 0}">
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
					<form action="<%=request.getContextPath() + "/carExt/carext.do"%>"
						method="post">
						<input type="hidden" name="action" value="delete"> <input
							type="hidden" name="cext_id" value="${carExtVO.cext_id}">
						<input type="submit" value="刪除" class="btn btn-danger">
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