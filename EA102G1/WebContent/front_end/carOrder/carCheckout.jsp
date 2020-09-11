<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.car.model.*"%>
<%@ page import="com.carPic.model.*"%>
<%@ page import="com.carExt.model.*"%>
<%@ page import="com.membre.model.*"%>

<%

MembreVO membreVO = (MembreVO) session.getAttribute("membrevo");

pageContext.setAttribute("membrevo", membreVO);


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

	Map<String, CarPicVO> allCarPicMap = carSvc.getAllCarPicMap();
	pageContext.setAttribute("allCarPicMap", allCarPicMap);
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

<!-- Google Fonts -->
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+TC:wght@300;500&display=swap"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Montserrat"
	rel="stylesheet">

<!-- Custom CSS -->
<link rel="stylesheet"
	href="${contextPath}/front_end/carOrder/css/shopping-cart.css">

<!-- SweetAlert -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.css" />


<style>
body {
	font-family: 'Noto Sans TC', sans-serif;
	color: #000;
	overflow-x: hidden;
	height: 100%;
	background-color: #f6f6f6;
	background-repeat: no-repeat
}

.plus-minus {
	position: relative
}

.plus {
	position: absolute;
	top: -4px;
	left: 2px;
	cursor: pointer
}

.minus {
	position: absolute;
	top: 8px;
	left: 5px;
	cursor: pointer
}

.vsm-text:hover {
	color: #FF5252
}

.border-top {
	border-top: 1px solid #EEEEEE !important;
	margin-top: 20px;
	padding-top: 15px
}

.card {
	margin: 40px 0px;
	padding: 40px 50px;
	/*     border-radius: 20px; */
	border: none;
	box-shadow: 1px 5px 10px 1px rgba(0, 0, 0, 0.2)
}

input, textarea {
	background-color: #F3E5F5;
	padding: 8px 15px 8px 15px;
	width: 100%;
	border-radius: 5px !important;
	box-sizing: border-box;
	border: 1px solid #F3E5F5;
	font-size: 15px !important;
	color: #000 !important;
	font-weight: 300
}

input:focus, textarea:focus {
	-moz-box-shadow: none !important;
	-webkit-box-shadow: none !important;
	box-shadow: none !important;
	border: 1px solid #9FA8DA;
	outline-width: 0;
	font-weight: 400
}

button:focus {
	-moz-box-shadow: none !important;
	-webkit-box-shadow: none !important;
	box-shadow: none !important;
	outline-width: 0
}

.pay {
	width: 80px;
	height: 40px;
	border-radius: 5px;
	border: 1px solid #673AB7;
	margin: 10px 20px 10px 0px;
	cursor: pointer;
	box-shadow: 1px 5px 10px 1px rgba(0, 0, 0, 0.2)
}

.gray {
	-webkit-filter: grayscale(100%);
	-moz-filter: grayscale(100%);
	-o-filter: grayscale(100%);
	-ms-filter: grayscale(100%);
	filter: grayscale(100%);
	color: #E0E0E0
}

.gray .pay {
	box-shadow: none
}

#tax {
	border-top: 1px lightgray solid;
	margin-top: 10px;
	padding-top: 10px
}

.btn-blue {
	border: none;
	border-radius: 10px;
	background-color: #673AB7;
	color: #fff;
	padding: 8px 15px;
	margin: 20px 0px;
	cursor: pointer
}

.btn-blue:hover {
	background-color: #311B92;
	color: #fff
}

#checkout {
	float: left
}

#check-amt {
	float: right
}

@media screen and (max-width: 768px) {
	.book, .book-img {
		width: 100px;
		height: 150px
	}
	.card {
		padding-left: 15px;
		padding-right: 15px
	}
	.mob-text {
		font-size: 13px
	}
	.pad-left {
		padding-left: 20px
	}
}
</style>

<title>預約確認頁 | 會員</title>
</head>
<body>
<body>
	<form method="post" action="${contextPath}/carOrder/carorder.do"
		id="submitCheckout">
		<input type="hidden" name="action" value="insert"> <input
			type="hidden" name="membre_id" value="${membrevo.getMembre_id()}">
		<input type="hidden" name="vender_id" value="${carVO.getVender_id()}">
		<input type="hidden" name="cid" value="${carVO.getCid()}"> <input
			type="hidden" name="cneed_date" value="${carOrderVO.getCneed_date()}">
		<input type="hidden" name="creturn_date"
			value="${carOrderVO.getCreturn_date()}"> <input type="hidden"
			name="cstart" value="${carOrderVO.getCstart()}"> <input
			type="hidden" name="cdest" value="${carOrderVO.getCdest()}">
		<c:forEach var="carExtVO" items="${carExtVOList}">
			<input type="hidden" name="cext_id" value="${carExtVO.getCext_id()}">
		</c:forEach>

		<main class="page">
			<section class="shopping-cart dark">
				<div class="container">
					<div class="block-heading">

						<h2>最後確認</h2>
						<h3 style="color: #6c757d;">本次預約明細如下：</h3>

					</div>
					<div class="content">
						<div class="row">
							<div class="col-md-12 col-lg-8">
								<div class="items">
									<div class="product">
										<div class="row">
											<div class="col-md-3">
												<img class="img-fluid mx-auto d-block image"
													src="${contextPath}/car/car.do?action=getCarPic&cpic_id=${allCarPicMap.get(carOrderVO.getCid()).getCpic_id()}">
											</div>
											<div class="col-md-8">
												<div class="info">
													<div class="row">
														<div class="col-md-5 product-name">
															<div class="product-name">
																<a href="#">禮車</a>
																<div class="product-info">
																	<div>
																		品牌: <span class="value">${carVO.getCbrand()}</span>
																	</div>
																	<div>
																		型號: <span class="value">${carVO.getCmodel()}</span>
																	</div>

																</div>
															</div>
														</div>
														<div class="col-md-4 quantity"></div>
														<div class="col-md-3 price">
															<span>$${carVO.getCprice()}</span>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="product">
										<div class="row">
											<c:forEach var="carExtVO" items="${carExtVOList}">
												<c:if test="${carExtVOList.size() > 0}">
													<div class="col-md-3">
														<img class="img-fluid mx-auto d-block image"
															src="${contextPath}/carExt/carext.do?action=getCarExtPic&cext_id=${carExtVO.getCext_id()}"
															width="100px">
													</div>
													<div class="col-md-8">
														<div class="info">
															<div class="row">
																<div class="col-md-5 product-name">
																	<div class="product-name">
																		<a href="#">加購商品</a>
																		<div class="product-info">
																			<div>
																				<span class="value">${carExtVO.getCext_name()}</span>
																			</div>

																		</div>
																	</div>
																</div>
																<div class="col-md-4 quantity"></div>
																<div class="col-md-3 price">
																	<span>$${carExtVO.getCext_price()}</span>
																</div>
															</div>
														</div>
													</div>
												</c:if>
											</c:forEach>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-12 col-lg-4">
								<div class="summary">
									<h3>預約明細</h3>

									<div class="summary-item">
										<span class="text">起點</span> <span class="cstart">${carOrderVO.getCstart()}</span>
									</div>
									<div class="summary-item">
										<span class="text">目的地</span><span class="cdesc">${carOrderVO.getCdest()}</span>
									</div>
									<div class="summary-item">
										<span class="text">租車日</span><span class="cneed_date">${carOrderVO.getCneed_date()}</span>
									</div>
									<div class="summary-item">
										<span class="text">還車日</span><span class="creturn_date">${carOrderVO.getCreturn_date()}</span>
									</div>
									<div class="summary-item">
										<span class="text">總金額</span><span class="cfinal_price">${carOrderVO.getCfinal_price()}</span>
									</div>
									<div class="summary-item">
										<span class="text">訂金</span><span class="cdeposit">${carOrderVO.getCdeposit()}</span>
									</div>
									<div class="summary-item">
										<span class="text">押金</span><span class="creturn_pay">${carOrderVO.getCreturn_pay()}</span>
									</div>
									<div class="summary-item">
										<span class="text">總預付金(訂金+押金)</span><span
											class="total_prepaid">${carOrderVO.getCdeposit()+carOrderVO.getCreturn_pay()}</span>
									</div>
									<div class="summary-item">
										<span class="text"></span><span class="price"></span>
									</div>
									<button type="button" class="btn btn-primary btn-lg btn-block" onclick="history.back()">回上頁修改</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
		</main>
		<div class="container px-4 py-5 mx-auto">

			<div class="row justify-content-center">
				<div class="block-heading" style="text-align: center">
					<h2 style="color: #673AB7;'">信用卡付訂</h2>

				</div>
				<div class="col-lg-12">
					<div class="card">
						<div class="row">
							<div class="col-lg-3 radio-group">
								<div class="row d-flex px-3 radio">
									<img class="pay" src="https://i.imgur.com/WIAP9Ku.jpg">
									<p class="my-auto">Master Card</p>
								</div>
								<div class="row d-flex px-3 radio gray">
									<img class="pay" src="https://i.imgur.com/OdxcctP.jpg">
									<p class="my-auto">VISA</p>
								</div>
								<div class="row d-flex px-3 radio gray mb-3">
									<img class="pay" src="https://i.imgur.com/cMk1MtK.jpg">
									<p class="my-auto">PayPal</p>
								</div>
							</div>
							<div class="col-lg-5">
								<div class="row px-2">
									<div class="form-group col-md-6">
										<label class="form-control-label">持卡人姓名</label> <input
											type="text" id="cname" placeholder="Johnny Doe">
									</div>
									<div class="form-group col-md-6">
										<label class="form-control-label">信用卡卡號</label> <input
											type="text" id="cnum" placeholder="1111 2222 3333 4444">
									</div>
								</div>
								<div class="row px-2">
									<div class="form-group col-md-6">
										<label class="form-control-label">到期年月</label> <input
											type="text" id="exp" placeholder="MM/YYYY">
									</div>
									<div class="form-group col-md-6">
										<label class="form-control-label">卡片背面末三碼</label> <input
											type="text" id="cvv" placeholder="***">
									</div>
								</div>
							</div>
							<div class="col-lg-4 mt-2">
								<div class="row d-flex justify-content-between px-4">

									<h5 class="mb-1">請按下確認付訂，以完成交易 ↓</h5>

								</div>
								<div class="row d-flex justify-content-between px-4" id="tax">
									<p class="mb-1 text-left"></p>
									<h6 class="mb-1 text-right"></h6>
								</div>

								<button  type="button" class="btn-block btn-blue btn-lg" id="btnCheckout">
									<span id="checkout">確認付訂</span>
									<span id="check-amt">$${carOrderVO.getCreturn_pay()+carOrderVO.getCreturn_pay()}</span>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</form>
</body>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.js"
	type="text/javascript"></script>

<script>
	$(document).ready(function() {

		$('.radio-group .radio').click(function() {
			$('.radio').addClass('gray');
			$(this).removeClass('gray');
		});

		$('.plus-minus .plus').click(function() {
			var count = $(this).parent().prev().text();
			$(this).parent().prev().html(Number(count) + 1);
		});

		$('.plus-minus .minus').click(function() {
			var count = $(this).parent().prev().text();
			$(this).parent().prev().html(Number(count) - 1);
		});

	});
</script>

<script type="text/javascript">
$('#btnCheckout').click(function() {
	swal({
		title: "感謝您的預約",
		text: "您可以在會員專區查看這筆訂單！",
		type: "success",
		timer: 3000
	}).then((value) => {
		 $('#submitCheckout').submit();
	});
});

</script>




</body>

</html>