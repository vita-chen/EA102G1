<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"
	language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.car.model.*"%>
<%@ page import="com.carPic.model.*"%>
<%@ page import="com.carExt.model.*"%>
<%@ page import="com.membre.model.*"%>

<%
Object account = session.getAttribute("membrevo");                  // 從 session內取出 (key) account的值
if (account == null) {                                             // 如為 null, 代表此user未登入過 , 才做以下工作
  response.sendRedirect(request.getContextPath()+"/front_end/membre/login.jsp");   //*工作2 : 請該user去登入網頁(login.html) , 進行登入
  return;
}
MembreVO membreVO = (MembreVO) session.getAttribute("membreVO");

pageContext.setAttribute("membreVO", membreVO);

	String cid = request.getParameter("cid");
	/*out.print(cid); /*確認目前抓到的Parameter*/
	CarVO carVO = new CarVO();
	carVO.setCid(cid);
	CarService carSvc = new CarService();
	carSvc.getOneCar(carVO);
	pageContext.setAttribute("carVO", carVO);
	pageContext.setAttribute("contextPath", request.getContextPath()); // 把request.getContextPath()塞到pageContext，並取一個變數叫做contextPath去存。為了取得專案路徑而寫 

	CarPicVO carPicVO = new CarPicVO();
	carPicVO.setCid(cid);
	List<CarPicVO> carPicVOList = carSvc.getAllCarPicByCID(carPicVO);
	pageContext.setAttribute("carPicVOList", carPicVOList);

	CarExtVO carExtVO = new CarExtVO();
	carExtVO.setVender_id(carVO.getVender_id());
	CarExtService carExtSvc = new CarExtService();
	List<CarExtVO> carExtVOList = carExtSvc.getAllByVenderId(carExtVO);
	pageContext.setAttribute("carExtVOList", carExtVOList);
%>


<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<!-- Google Fonts -->
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+TC:wght@300;500&display=swap"
	rel="stylesheet">

<!-- Custom CSS -->
<link rel="stylesheet"
	href="${contextPath}/front_end/carOrder/css/styleForCarReservation.css">
	


<title>禮車預約頁 | 會員</title>
</head>
<body>


	<jsp:include page="../home/home_header.jsp" />

	<div class="container">
		<div class="row mt-5">
			<form method="post" action="${contextPath}/carOrder/carorder.do">
				<input type="hidden" name="action" value="checkout"> <input
					type="hidden" name="membre_id" value="${membrevo.getMembre_id()}">
				<input type="hidden" name="vender_id"
					value="${carVO.getVender_id()}"> <input type="hidden"
					name="cid" value="${carVO.getCid()}">


				<div class="container ">
					<div class="row mt-5">
						<div class="col-md-12">
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb" style="margin-top: 5%;"> <%-- 為了不讓header遮蓋掉麵包屑效果 --%>
									<li class="breadcrumb-item"><a href="#">Home</a></li>
									<li class="breadcrumb-item"><a href="#">禮車租借</a></li>
									<li class="breadcrumb-item active" aria-current="page">幸福禮車出租–${carVO.getCbrand()}-${carVO.getCmodel()}</li>
								</ol>
							</nav>
						</div>
					</div>
				</div>
				<div class="container">
					<div class="row">

						<%-- ********************輪播禮車圖片******************* --%>
						<div class="col-md-6">
							<div id="carouselExampleControls" class="carousel slide"
								data-ride="carousel">
								<div class="carousel-inner">
									<c:forEach var="carPicVO" items="${carPicVOList}"
										varStatus="stat">

										<%-- 將 .active 樣式添加到第一張圖片，否則輪播效果無法正常運行 --%>
										<div
											class="carousel-item
								<c:if test="${stat.first}">
									active
								</c:if>
								">
											<img class="d-block w-100"
												src="${contextPath}/car/car.do?action=getCarPic&cpic_id=${carPicVO.getCpic_id()}"
												style="width: 500px;" />
										</div>
									</c:forEach>
								</div>
								<a class="carousel-control-prev" href="#carouselExampleControls"
									role="button" data-slide="prev"> <span
									class="carousel-control-prev-icon" aria-hidden="true"></span> <span
									class="sr-only">Previous</span>
								</a> <a class="carousel-control-next"
									href="#carouselExampleControls" role="button" data-slide="next">
									<span class="carousel-control-next-icon" aria-hidden="true"></span>
									<span class="sr-only">Next</span>
								</a>
							</div>
						</div>

						<%-- ********************for 顧客輸入預訂禮車的相關資訊******************* --%>
						<div class="col-md-6">
							<div class="row">
								<h1 class="title">${carVO.getCbrand()}-${carVO.getCmodel()}</h1>
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

								</div>
							</div>
							<div class="row mt-3 ">
								<h3>金額：${carVO.getCprice()}</h3>
							</div>
							<div class="row mt-3">
								<h3>介紹：${carVO.getCintro()}</h3>
							</div>
							<div class="row mt-3">
								<h3>租車日:&nbsp;</h3>
								<input name="cneed_date" id="cneed_date" type="text">
							</div>
							<div class="row mt-3">
								<h3>還車日:&nbsp;</h3>
								<input name="creturn_date" id="creturn_date" type="text">
							</div>
							<div class="row mt-3">
								<h3>起點:&nbsp;</h3>
								<input name="cstart" type="text">
							</div>
							<div class="row mt-3">
								<h3>終點:&nbsp;</h3>
								<input name="cdest" type="text">
							</div>
						</div>
					</div>
				</div>

				<%-- ********************for 顧客選禮車的加購商品******************* --%>
				<div class="container">
					<div class="row mt-5">
						<h2>加購商品</h2>
					</div>
					<div class="row mt-5">
						<c:forEach var="carExtVO" items="${carExtVOList}">
							<c:if test="${carExtVO.getCext_status() == 0}">
								<div class="col-md-3">
									<div class="card">
										<img class="card-img-top img-fluid"
											src="${contextPath}/carExt/carext.do?action=getCarExtPic&cext_id=${carExtVO.getCext_id()}"
											width="100px" />
										<div class="card-title">
											<h4>${carExtVO.getCext_name()}</h4>
											<h4>${carExtVO.getCext_price()}</h4>
											<input type="checkbox" name="cext_id"
												value="${carExtVO.getCext_id()}">
										</div>

									</div>
								</div>
							</c:if>
						</c:forEach>
					</div>
					<p>
						<input type="submit" value="訂購" class="btn btn-primary" />
					</p>
				</div>

			</form>
		</div>
	</div>
	<jsp:include page="../home/home_footer.jsp" />

	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>


</body>
<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<!-- 參考網站: https://xdsoft.net/jqplugins/datetimepicker/ -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}
</style>

<script>
	$.datetimepicker.setLocale('zh'); // kr ko ja en
	$(function() {
		$('#cneed_date').datetimepicker(
				{
					format : 'Y-m-d',
					onShow : function() {
						this.setOptions({
							maxDate : $('#creturn_date').val() ? $(
									'#creturn_date').val() : false
						})
					},
					timepicker : false
				});

		$('#creturn_date').datetimepicker(
				{
					format : 'Y-m-d',
					onShow : function() {
						this.setOptions({
							minDate : $('#cneed_date').val() ? $('#cneed_date')
									.val() : false
						})
					},
					timepicker : false
				});
	});
</script>





</html>