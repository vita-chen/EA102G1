<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.carOrder.model.*"%>
<%@ page import="com.car.model.*"%>
<%@ page import="com.carExt.model.*"%>
<%@ page import="com.carPic.model.*"%>
<%@ page import="com.vender.model.*"%>

<%
	CarService carSvc = new CarService();
	List<CarVO> carVOList = carSvc.getAllCar();
	pageContext.setAttribute("carVOList", carVOList);

	// 用vender_id來換vender_name
	VenderService venderSvc = new VenderService();
	Map<String, VenderVO> allVenderMap = venderSvc.getAllVenderMap();
	pageContext.setAttribute("allVenderMap", allVenderMap);

	Map<String, CarPicVO> allCarPicMap = carSvc.getAllCarPicMap();
	pageContext.setAttribute("allCarPicMap", allCarPicMap);

	// 取得平均星數
	CarOrderService carOrderSvc = new CarOrderService();
	Map<String, CarOrderVO> carAvgStarMap = carOrderSvc.getCarAvgStarMap();
	pageContext.setAttribute("carAvgStarMap", carAvgStarMap);

	pageContext.setAttribute("contextPath", request.getContextPath()); // 把request.getContextPath()塞到pageContext，並取一個變數叫做contextPath去存。為了取得專案路徑而寫
%>

<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

<!-- FontAwesome -->
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.5.0/css/all.css">

<!-- custom style -->
<link href="css/ui.css" rel="stylesheet" type="text/css" />
<link href="css/responsive.css" rel="stylesheet"
	media="only screen and (max-width: 1200px)" />
</head>
<body>
	<jsp:include page="../home/Header_Cart.jsp" />


	<!-- ========================= SECTION CONTENT ========================= -->
	<section class="section-content padding-y">
		<div class="container">

			<div class="row">
				<aside class="col-md-3" style="margin-top: 10%;">

					<div class="card">
						<article class="filter-group">
							<header class="card-header">
								<a href="#" data-toggle="collapse" data-target="#collapse_1"
									aria-expanded="true" class=""> <i
									class="icon-control fa fa-chevron-down"></i>
									<h6 class="title">關鍵字</h6>
								</a>
							</header>
							<div class="filter-content collapse show" id="collapse_1"
								style="">
								<div class="card-body">
									<form class="pb-3">
										<div class="input-group">
											<input type="text" name="keyword" class="form-control"
												placeholder="搜尋">
											<div class="input-group-append">
												<button class="btn btn-light apply" type="button">
													<i class="fa fa-search"></i>
												</button>
											</div>
										</div>
									</form>

								</div>
								<!-- card-body.// -->
							</div>
						</article>
						<!-- filter-group  .// -->
						<article class="filter-group">
							<header class="card-header">
								<a href="#" data-toggle="collapse" data-target="#collapse_3"
									aria-expanded="true" class=""> <i
									class="icon-control fa fa-chevron-down"></i>
									<h6 class="title">價格範圍</h6>
								</a>
							</header>
							<div class="filter-content collapse show" id="collapse_3"
								style="">
								<div class="card-body">
									<div class="form-row">
										<div class="form-group col-md-6">
											<label>最小值</label> <input class="form-control"
												name="minPirce" placeholder="$0" type="number">
										</div>
										<div class="form-group text-right col-md-6">
											<label>最大值</label> <input class="form-control"
												name="maxPirce" placeholder="$1,0000" type="number">
										</div>
									</div>
									<!-- form-row.// -->
									<button class="btn btn-block btn-primary apply">套用</button>
								</div>
								<!-- card-body.// -->
							</div>
						</article>
						<!-- filter-group .// -->
						<article class="filter-group">
							<header class="card-header">
								<a href="#" data-toggle="collapse" data-target="#collapse_4"
									aria-expanded="true" class=""> <i
									class="icon-control fa fa-chevron-down"></i>
									<h6 class="title">評價</h6>
								</a>
							</header>
							<div class="filter-content collapse show" id="collapse_4"
								style="">
								<div class="card-body">

									<label class="custom-control custom-checkbox"> <input
										type="checkbox" id="star5" checked=""
										class="custom-control-input">
										<div class="custom-control-label text-warning">
											<i class="fa fa-star"></i> <i class="fa fa-star"></i> <i
												class="fa fa-star"></i> <i class="fa fa-star"></i> <i
												class="fa fa-star"></i>
										</div>
									</label> <label class="custom-control custom-checkbox"> <input
										type="checkbox" id="star4" checked=""
										class="custom-control-input">
										<div class="custom-control-label text-warning">
											<i class="fa fa-star"></i> <i class="fa fa-star"></i> <i
												class="fa fa-star"></i> <i class="fa fa-star"></i>
										</div>
									</label> <label class="custom-control custom-checkbox"> <input
										type="checkbox" id="star3" checked=""
										class="custom-control-input">
										<div class="custom-control-label text-warning">
											<i class="fa fa-star"></i> <i class="fa fa-star"></i> <i
												class="fa fa-star"></i>
										</div>
									</label> <label class="custom-control custom-checkbox"> <input
										type="checkbox" id="star2" checked=""
										class="custom-control-input">
										<div class="custom-control-label text-warning">
											<i class="fa fa-star"></i> <i class="fa fa-star"></i>
										</div>
									</label> <label class="custom-control custom-checkbox"> <input
										type="checkbox" id="star1" checked=""
										class="custom-control-input">
										<div class="custom-control-label text-warning">
											<i class="fa fa-star"></i>
										</div>
									</label>
									<label class="custom-control custom-checkbox"> <input
										type="checkbox" id="star0" checked=""
										class="custom-control-input">
										<div class="custom-control-label text-warning">
											
										</div>
									</label>
									<button class="btn btn-block btn-primary apply">套用</button>
								</div>
								<!-- card-body.// -->
							</div>
						</article>
						<!-- filter-group .// -->
					</div>
					<!-- card.// -->

				</aside>
				<!-- col.// -->
				<main class="col-md-9" style="margin-top: 10%">

					<header class="border-bottom mb-4 pb-3">
						<div class="form-inline">
							<label>排序：</label> <select class="mr-2 form-control"
								id="sortByPrice">
								<option value="asc">價格：低到高</option>
								<option value="desc">價格：高到低</option>
							</select>
						</div>
					</header>
					<!-- sect-heading -->

					<div class="row" id="items"></div>
					<!-- row end.// -->



				</main>
				<!-- col.// -->

			</div>

		</div>
		<!-- container .//  -->
	</section>
	<!-- ========================= SECTION CONTENT END// ========================= -->

	<template id='template'>
	<div class="col-md-4" style="margin-top: 5%;">
		<figure class="card card-product-grid">
			<div class="img-wrap">
				<img src="">
			</div>
			<figcaption class="info-wrap">
				<div class="fix-height">
					<a href="#" class="title"></a>
					<div class="price-wrap mt-2">
						<span class="price"></span>
					</div>
					<!-- price-wrap.// -->
				</div>
				<a href="" class="btn btn-block btn-primary">選擇</a>
			</figcaption>
		</figure>
	</div>
	</template>

	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->

	<script type="text/javascript">
		// 將動態資料載入
		class Car {
			constructor(cid, ven_name, cbrand, cmodel, cprice, cpic_id, crev_star) {
				this.cid = cid;
				this.ven_name = ven_name;
				this.cbrand = cbrand;
				this.cmodel = cmodel;
				this.cprice = cprice;
				this.cpic_id = cpic_id;
				this.crev_star = crev_star;
			}
		}
		var carList = [];
		<c:forEach var="carVO" items="${carVOList}">
			<c:choose>
				<c:when test="${carAvgStarMap.get(carVO.getCid()).getCrev_star() == null}">
				carList.push(new Car('${carVO.getCid()}', '${allVenderMap.get(carVO.getVender_id()).getVen_name()}', '${carVO.getCbrand()}', '${carVO.getCmodel()}', '${carVO.getCprice()}', '${allCarPicMap.get(carVO.getCid()).getCpic_id()}', '0'));
			</c:when>
			<c:otherwise>
				carList.push(new Car('${carVO.getCid()}', '${allVenderMap.get(carVO.getVender_id()).getVen_name()}', '${carVO.getCbrand()}', '${carVO.getCmodel()}', '${carVO.getCprice()}', '${allCarPicMap.get(carVO.getCid()).getCpic_id()}', '${carAvgStarMap.get(carVO.getCid()).getCrev_star()}'));
			</c:otherwise>
			</c:choose>
		</c:forEach>
		
		function updateSectionContent() {
			var newCarList = carList.slice();
			
			// 篩選資料1–以關鍵字篩選
			var keyword = $('input[name="keyword"]').val().toLowerCase();
			if (keyword != ''){
				var tempList = [];
				newCarList.forEach(car => { if ((car.ven_name + '' + car.cbrand + '' + car.cmodel).toLowerCase().indexOf(keyword) > 0) tempList.push(car) });
				newCarList = tempList.slice();
			}
			
			// 篩選資料2–以minPirce篩選
			var minPirce = $('input[name="minPirce"]').val();
			if (minPirce != '') {
				var tempList = [];
				newCarList.forEach(car => { if (parseInt(car.cprice) >= parseInt(minPirce)) tempList.push(car)});
				newCarList = tempList.slice();
			}
			// 篩選資料3–以maxPirce篩選
			var maxPirce = $('input[name="maxPirce"]').val();
			if (maxPirce != '') {
				var tempList = [];
				newCarList.forEach(car => {if (parseInt(car.cprice) <= parseInt(maxPirce)) tempList.push(car)});
				newCarList = tempList.slice();
			}
			
			// 篩選資料4–以平均星數篩選
			var tempList2 = [];
			for(var i =0; i<=5; i++){
			 	if($('#star'+i).prop('checked')){
				 	var tempList = [];
					newCarList.forEach(car => { if (parseInt(car.crev_star) == i) tempList.push(car) });
					tempList2 = tempList2.concat(tempList.slice());
			 	}
			}
			newCarList = tempList2.slice();
			
// 			var newAllCarList=[[], [], [], [], []];
			
// 			for(var i =1; i<=5; i++){
// 			 if($('#star'+i).prop('checked')){
// 				 var tempList = [];
// 					newCarList.forEach(car => { if (parseInt(car.crev_star) == i) tempList.push(car) });
// 					newAllCarList[i] = tempList.slice();
// 			 }
// 			}
			
// 			var tempList2 = [];
// 			for(var i =1; i<=5; i++){
// 				tempList2 = tempList2.concat(newAllCarList[i]);
// 			}
			
// 			newCarList = tempList2.slice();
			
			// 排序資料
			if ($('#sortByPrice').val() == 'asc') {
				newCarList = newCarList.sort(function (a, b) {
					return a.cprice > b.cprice ? 1 : -1;
				});
			}
			else {
				newCarList = newCarList.sort(function (a, b) {
					return a.cprice < b.cprice ? 1 : -1;
				});
			}

			// 更新 Section Content
			$('#items').html('');
			$.each(newCarList, function(key, value) {
				var template = $($('#template').html()).clone();
				template.find('.title').html(value.ven_name + '-' + value.cbrand + ' ' + value.cmodel);
				template.find('.price').html('$' + value.cprice);
				template.find('img').attr('src', '${contextPath}/car/car.do?action=getCarPic&cpic_id=' + value.cpic_id);
				template.find('.btn').attr('href', '${contextPath}/front_end/carOrder/carReservation.jsp?cid=' + value.cid);
				$('#items').append(template);
			});
		}
		
		// document 載入後才更新 Section Content
		$(document).ready(function() {
			updateSectionContent();
		});

		// 註冊排序事件		
		$('#sortByPrice').change(function() {
			updateSectionContent();
		});

		// 註冊套用按鈕事件
		$('.apply').click(function() {
			updateSectionContent();
		});
		
	</script>
	<jsp:include page="../home/home_footer.jsp" />
</body>
</html>