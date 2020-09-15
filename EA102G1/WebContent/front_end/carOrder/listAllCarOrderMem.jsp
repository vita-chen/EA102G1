<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.vender.model.*"%>
<%@ page import="com.carOrder.model.*"%>
<%@ page import="com.car.model.*"%>
<%@ page import="com.carExt.model.*"%>
<%@ page import="com.carPic.model.*"%>
<%@ page import="com.membre.model.*"%>
<%@ page import="com.car.config.*"%>

<%
	// 日後對接完之後可以拿掉	
	MembreVO memTemp = new MembreVO();
	memTemp.setMembre_id("M014");
	session.setAttribute("membreVO", (Object) memTemp);
	MembreVO membreVO = (MembreVO) session.getAttribute("membreVO");
	pageContext.setAttribute("membreVO", membreVO);

	CarOrderVO carOrderVO = new CarOrderVO();
	carOrderVO.setMembre_id(membreVO.getMembre_id());
	CarOrderService carOrderSvc = new CarOrderService();

	List<CarOrderVO> carOrderVOList = carOrderSvc.getAllByMemberId(carOrderVO);
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
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+TC:wght@300;500&display=swap">

<style>
body {
	font-family: 'Noto Sans TC', sans-serif;
}

.rating-loading {
	width: 25px;
	height: 25px;
	font-size: 0;
	color: #fff;
}

.rating-container .rating-stars {
	position: relative;
	cursor: pointer;
	vertical-align: middle;
	display: inline-block;
	overflow: hidden;
	white-space: nowrap;
	line-height: 1.5em !important;
}

.rating-container .rating-input {
	position: absolute;
	cursor: pointer;
	width: 100%;
	height: 1px;
	bottom: 0;
	left: 0;
	font-size: 1px;
	border: none;
	background: 0 0;
	padding: 0;
	margin: 0
}

.rating-disabled .rating-input, .rating-disabled .rating-stars {
	cursor: default;
}

.rating-container .star {
	display: inline-block;
	margin: 0 3px;
	text-align: center
}

.rating-container .star .far {
	line-height: 1.5;
}

.rating-container .star .fas {
	line-height: 1.5;
}

.rating-container .empty-stars {
	color: #aaa
}

.rating-container .filled-stars {
	position: absolute;
	left: 0;
	top: 0;
	margin: auto;
	color: #fde16d;
	white-space: nowrap;
	overflow: hidden;
	-webkit-text-stroke: 1px #777;
	text-shadow: 1px 1px #999
}

.rating-rtl {
	float: right
}

.rating-animate .filled-stars {
	transition: width .25s ease;
	-o-transition: width .25s ease;
	-moz-transition: width .25s ease;
	-webkit-transition: width .25s ease
}

.rating-rtl .filled-stars {
	left: auto;
	right: 0;
	-moz-transform: matrix(-1, 0, 0, 1, 0, 0) translate3d(0, 0, 0);
	-webkit-transform: matrix(-1, 0, 0, 1, 0, 0) translate3d(0, 0, 0);
	-o-transform: matrix(-1, 0, 0, 1, 0, 0) translate3d(0, 0, 0);
	transform: matrix(-1, 0, 0, 1, 0, 0) translate3d(0, 0, 0)
}

.rating-rtl.is-star
             .filled-stars {
	right: .06em
}

.rating-rtl.is-heart .empty-stars {
	margin-right: .07em
}

.rating-xl {
	font-size: 2.6em
}

.rating-lg {
	font-size: 2.0em
}

.rating-md {
	font-size: 1.5em
}

.rating-sm {
	font-size: 1em
}

.rating-xs {
	font-size: 0.7em
}

.rating-container .clear-rating {
	display: none;
	color: #aaa;
	cursor: default;
	vertical-align: middle;
	font-size: 60%;
	padding-right: 5px
}

.clear-rating-active {
	cursor: pointer !important;
	display: inline-block !important;
}

.clear-rating-active:hover {
	color: #843534
}

.rating-container .caption {
	color: #999;
	display: inline-block;
	vertical-align: middle;
	font-size: 60%;
	margin-top: -.6em;
	margin-left: 5px;
	margin-right: 0
}

.rating-rtl .caption {
	margin-right: 5px;
	margin-left: 0
}

@media print {
	.rating-container .clear-rating {
		display: none
	}
}
</style>

<title>禮車訂單列表頁 | 會員</title>
</head>
<body>
	<h3 class="bg-primary rounded text-white text-center"
		style="padding: 10px;">禮車訂單列表頁 | 會員</h3>

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
				<th class="text-center" scope="col">評價</th>
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


					<%-- ************一旦會員or廠商按下「取消訂單」、及會員按下「確認訂單已完成」，則lock住「確認訂單已完成」按鈕************ --%>
					<td class="text-center align-middle">
						<form method="post" action="${contextPath}/carOrder/carorder.do">
							<c:choose>
								<c:when test="${carOrderVO.getCod_status() >=3}">
									<input type="hidden" name="action" value="updateCarOrderStatus">
									<input type="hidden" name="cod_id" value="${carOrderVO.cod_id}">
									<input type="hidden" name="cod_status" value="3">
									<input type="submit" value="確認訂單已完成" disabled
										class="btn btn-info btn-sm">
								</c:when>
								<c:otherwise>
									<input type="hidden" name="action" value="updateCarOrderStatus">
									<input type="hidden" name="cod_id" value="${carOrderVO.cod_id}">
									<input type="hidden" name="cod_status" value="3">
									<input type="submit" value="確認訂單已完成"
										class="btn btn-info btn-sm">
								</c:otherwise>
							</c:choose>
						</form>
					</td>
					<%-- ************一旦廠商按下「確認訂單已完成」，則lock住「取消訂單」按鈕************ --%>
					<td class="text-center align-middle">
						<form method="post" action="${contextPath}/carOrder/carorder.do">
							<c:choose>
								<c:when test="${carOrderVO.getCod_status() > 1}">
									<input type="hidden" name="action" value="cancel">
									<input type="hidden" name="cod_id" value="${carOrderVO.cod_id}">
									<input type="submit" value="取消訂單" disabled
										class="btn btn-danger  btn-sm">
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

					<td class="text-center align-middle">

						<form method="post" action="${contextPath}/carOrder/carorder.do"
							class="submitReview">
							<%-- ************如果訂單狀態變成2/3(廠商/客戶確認訂單已完成)時，才能按「來去給評價」************ --%>
							<c:choose>
								<c:when test="${carOrderVO.getCod_status()==2 || carOrderVO.getCod_status()==3}">
									<button type="button" class="btn btn-primary btn-sm" 
										data-toggle="modal"
										data-target="#submitReviewModel_${carOrderVO.getCod_id()}">來去給評價</button>
								</c:when>
								<c:otherwise>
									<button type="button" class="btn btn-primary btn-sm" disabled
										data-toggle="modal"
										data-target="#submitReviewModel_${carOrderVO.getCod_id()}">來去給評價</button>
								</c:otherwise>
							</c:choose>
							<!-- Modal -->
							<div class="modal fade"
								id="submitReviewModel_${carOrderVO.getCod_id()}" tabindex="-1"
								role="dialog" aria-labelledby="exampleModalLongTitle"
								aria-hidden="true">
								<div class="modal-dialog" role="document">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title" id="exampleModalLongTitle">和大家分享本次交易的心得吧！</h5>
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
										</div>
										<%-- ************如果星數&心得都無值，用戶可以選星數並輸入交易心得************ --%>
										<c:choose>
											<c:when
												test="${carOrderVO.getCrev_star()==0 && carOrderVO.getCrev_msgs()==null}">
												<div class="modal-body">
													<div class="container">
														<div class="pt-3">
															<input id="input-2" name="input-2"
																class="rating rating-loading" data-min="0" data-max="5"
																data-step="1" value="${carOrderVO.getCrev_star()}"
																data-size="md">
														</div>

														<div class="pt-3">
															<textarea name="crev_msgs" class="form-control"
																placeholder="告訴別人你有多滿意這次的服務吧~">${carOrderVO.getCrev_msgs()}</textarea>
														</div>
													</div>
												</div>
												<div class="modal-footer">
													<button type="button" class="btn btn-secondary"
														data-dismiss="modal">取消</button>
													<input type="hidden" name="action" value="submitReview" />
													<input type="hidden" name="crev_star" /> <input
														type="hidden" name="cod_id" value="${carOrderVO.cod_id}" />
													<input type="submit" class="btn btn-primary submitReview"
														value="送出評價" />

												</div>
											</c:when>
											<%-- ************如果星數&心得都有值，則用戶僅能readonly，不可修改星數&評價************ --%>
											<c:otherwise>
												<div class="modal-body">
													<div class="container">
														<div class="pt-3">
															<input id="input-2" name="input-2"
																class="rating rating-loading" data-min="0" data-max="5"
																data-step="1" value="${carOrderVO.getCrev_star()}"
																data-size="md" readonly>
														</div>

														<div class="pt-3">
															<textarea name="crev_msgs" class="form-control"
																placeholder="告訴別人你有多滿意這次的服務吧~" readonly>${carOrderVO.getCrev_msgs()}</textarea>
														</div>
													</div>
												</div>
												<div class="modal-footer"></div>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</form>
					</td>
				</tr>
			</c:forEach>

		</tbody>

	</table>

	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<script> 
	!function(e){"use strict";"function"==typeof define&&define.amd?define(["jquery"],e):"object"==typeof module&&module.exports?module.exports=e(require("jquery")):e(window.jQuery)}(function(e){"use strict";e.fn.ratingLocales={},e.fn.ratingThemes={};var t,a;t={NAMESPACE:".rating",DEFAULT_MIN:0,DEFAULT_MAX:5,DEFAULT_STEP:.5,isEmpty:function(t,a){return null===t||void 0===t||0===t.length||a&&""===e.trim(t)},getCss:function(e,t){return e?" "+t:""},addCss:function(e,t){e.removeClass(t).addClass(t)},getDecimalPlaces:function(e){var t=(""+e).match(/(?:\.(\d+))?(?:[eE]([+-]?\d+))?$/);return t?Math.max(0,(t[1]?t[1].length:0)-(t[2]?+t[2]:0)):0},applyPrecision:function(e,t){return parseFloat(e.toFixed(t))},handler:function(e,a,n,r,i){var l=i?a:a.split(" ").join(t.NAMESPACE+" ")+t.NAMESPACE;r||e.off(l),e.on(l,n)}},a=function(t,a){var n=this;n.$element=e(t),n._init(a)},a.prototype={constructor:a,_parseAttr:function(e,a){var n,r,i,l,s=this,o=s.$element,c=o.attr("type");if("range"===c||"number"===c){switch(r=a[e]||o.data(e)||o.attr(e),e){case"min":i=t.DEFAULT_MIN;break;case"max":i=t.DEFAULT_MAX;break;default:i=t.DEFAULT_STEP}n=t.isEmpty(r)?i:r,l=parseFloat(n)}else l=parseFloat(a[e]);return isNaN(l)?i:l},_parseValue:function(e){var t=this,a=parseFloat(e);return isNaN(a)&&(a=t.clearValue),!t.zeroAsNull||0!==a&&"0"!==a?a:null},_setDefault:function(e,a){var n=this;t.isEmpty(n[e])&&(n[e]=a)},_initSlider:function(e){var a=this,n=a.$element.val();a.initialValue=t.isEmpty(n)?0:n,a._setDefault("min",a._parseAttr("min",e)),a._setDefault("max",a._parseAttr("max",e)),a._setDefault("step",a._parseAttr("step",e)),(isNaN(a.min)||t.isEmpty(a.min))&&(a.min=t.DEFAULT_MIN),(isNaN(a.max)||t.isEmpty(a.max))&&(a.max=t.DEFAULT_MAX),(isNaN(a.step)||t.isEmpty(a.step)||0===a.step)&&(a.step=t.DEFAULT_STEP),a.diff=a.max-a.min},_initHighlight:function(e){var t,a=this,n=a._getCaption();e||(e=a.$element.val()),t=a.getWidthFromValue(e)+"%",a.$filledStars.width(t),a.cache={caption:n,width:t,val:e}},_getContainerCss:function(){var e=this;return"rating-container"+t.getCss(e.theme,"theme-"+e.theme)+t.getCss(e.rtl,"rating-rtl")+t.getCss(e.size,"rating-"+e.size)+t.getCss(e.animate,"rating-animate")+t.getCss(e.disabled||e.readonly,"rating-disabled")+t.getCss(e.containerClass,e.containerClass)},_checkDisabled:function(){var e=this,t=e.$element,a=e.options;e.disabled=void 0===a.disabled?t.attr("disabled")||!1:a.disabled,e.readonly=void 0===a.readonly?t.attr("readonly")||!1:a.readonly,e.inactive=e.disabled||e.readonly,t.attr({disabled:e.disabled,readonly:e.readonly})},_addContent:function(e,t){var a=this,n=a.$container,r="clear"===e;return a.rtl?r?n.append(t):n.prepend(t):r?n.prepend(t):n.append(t)},_generateRating:function(){var a,n,r,i=this,l=i.$element;n=i.$container=e(document.createElement("div")).insertBefore(l),t.addCss(n,i._getContainerCss()),i.$rating=a=e(document.createElement("div")).attr("class","rating-stars").appendTo(n).append(i._getStars("empty")).append(i._getStars("filled")),i.$emptyStars=a.find(".empty-stars"),i.$filledStars=a.find(".filled-stars"),i._renderCaption(),i._renderClear(),i._initHighlight(),n.append(l),i.rtl&&(r=Math.max(i.$emptyStars.outerWidth(),i.$filledStars.outerWidth()),i.$emptyStars.width(r)),l.appendTo(a)},_getCaption:function(){var e=this;return e.$caption&&e.$caption.length?e.$caption.html():e.defaultCaption},_setCaption:function(e){var t=this;t.$caption&&t.$caption.length&&t.$caption.html(e)},_renderCaption:function(){var a,n=this,r=n.$element.val(),i=n.captionElement?e(n.captionElement):"";if(n.showcaption){if(a=n.fetchCaption(r),i&&i.length)return t.addCss(i,"caption"),i.html(a),void(n.$caption=i);n._addContent("caption",'<div class="caption">'+a+"</div>"),n.$caption=n.$container.find(".caption")}},_renderClear:function(){var a,n=this,r=n.clearElement?e(n.clearElement):"";if(n.showClear){if(a=n._getClearClass(),r.length)return t.addCss(r,a),r.attr({title:n.clearButtonTitle}).html(n.clearButton),void(n.$clear=r);n._addContent("clear",'<div class="'+a+'" title="'+n.clearButtonTitle+'">'+n.clearButton+"</div>"),n.$clear=n.$container.find("."+n.clearButtonBaseClass)}},_getClearClass:function(){var e=this;return e.clearButtonBaseClass+" "+(e.inactive?"":e.clearButtonActiveClass)},_toggleHover:function(e){var t,a,n,r=this;e&&(r.hoverChangeStars&&(t=r.getWidthFromValue(r.clearValue),a=e.val<=r.clearValue?t+"%":e.width,r.$filledStars.css("width",a)),r.hoverChangeCaption&&(n=e.val<=r.clearValue?r.fetchCaption(r.clearValue):e.caption,n&&r._setCaption(n+"")))},_init:function(t){var a,n=this,r=n.$element.addClass("rating-input");return n.options=t,e.each(t,function(e,t){n[e]=t}),(n.rtl||"rtl"===r.attr("dir"))&&(n.rtl=!0,r.attr("dir","rtl")),n.starClicked=!1,n.clearClicked=!1,n._initSlider(t),n._checkDisabled(),n.displayOnly&&(n.inactive=!0,n.showClear=!1,n.showcaption=!1),n._generateRating(),n._initEvents(),n._listen(),a=n._parseValue(r.val()),r.val(a),r.removeClass("rating-loading")},_initEvents:function(){var e=this;e.events={_getTouchPosition:function(a){var n=t.isEmpty(a.pageX)?a.originalEvent.touches[0].pageX:a.pageX;return n-e.$rating.offset().left},_listenClick:function(e,t){return e.stopPropagation(),e.preventDefault(),e.handled===!0?!1:(t(e),void(e.handled=!0))},_noMouseAction:function(t){return!e.hoverEnabled||e.inactive||t&&t.isDefaultPrevented()},initTouch:function(a){var n,r,i,l,s,o,c,u,d=e.clearValue||0,p="ontouchstart"in window||window.DocumentTouch&&document instanceof window.DocumentTouch;p&&!e.inactive&&(n=a.originalEvent,r=t.isEmpty(n.touches)?n.changedTouches:n.touches,i=e.events._getTouchPosition(r[0]),"touchend"===a.type?(e._setStars(i),u=[e.$element.val(),e._getCaption()],e.$element.trigger("change").trigger("rating.change",u),e.starClicked=!0):(l=e.calculate(i),s=l.val<=d?e.fetchCaption(d):l.caption,o=e.getWidthFromValue(d),c=l.val<=d?o+"%":l.width,e._setCaption(s),e.$filledStars.css("width",c)))},starClick:function(t){var a,n;e.events._listenClick(t,function(t){return e.inactive?!1:(a=e.events._getTouchPosition(t),e._setStars(a),n=[e.$element.val(),e._getCaption()],e.$element.trigger("change").trigger("rating.change",n),void(e.starClicked=!0))})},clearClick:function(t){e.events._listenClick(t,function(){e.inactive||(e.clear(),e.clearClicked=!0)})},starMouseMove:function(t){var a,n;e.events._noMouseAction(t)||(e.starClicked=!1,a=e.events._getTouchPosition(t),n=e.calculate(a),e._toggleHover(n),e.$element.trigger("rating.hover",[n.val,n.caption,"stars"]))},starMouseLeave:function(t){var a;e.events._noMouseAction(t)||e.starClicked||(a=e.cache,e._toggleHover(a),e.$element.trigger("rating.hoverleave",["stars"]))},clearMouseMove:function(t){var a,n,r,i;!e.events._noMouseAction(t)&&e.hoverOnClear&&(e.clearClicked=!1,a='<span class="'+e.clearCaptionClass+'">'+e.clearCaption+"</span>",n=e.clearValue,r=e.getWidthFromValue(n)||0,i={caption:a,width:r,val:n},e._toggleHover(i),e.$element.trigger("rating.hover",[n,a,"clear"]))},clearMouseLeave:function(t){var a;e.events._noMouseAction(t)||e.clearClicked||!e.hoverOnClear||(a=e.cache,e._toggleHover(a),e.$element.trigger("rating.hoverleave",["clear"]))},resetForm:function(t){t&&t.isDefaultPrevented()||e.inactive||e.reset()}}},_listen:function(){var a=this,n=a.$element,r=n.closest("form"),i=a.$rating,l=a.$clear,s=a.events;return t.handler(i,"touchstart touchmove touchend",e.proxy(s.initTouch,a)),t.handler(i,"click touchstart",e.proxy(s.starClick,a)),t.handler(i,"mousemove",e.proxy(s.starMouseMove,a)),t.handler(i,"mouseleave",e.proxy(s.starMouseLeave,a)),a.showClear&&l.length&&(t.handler(l,"click touchstart",e.proxy(s.clearClick,a)),t.handler(l,"mousemove",e.proxy(s.clearMouseMove,a)),t.handler(l,"mouseleave",e.proxy(s.clearMouseLeave,a))),r.length&&t.handler(r,"reset",e.proxy(s.resetForm,a),!0),n},_getStars:function(e){var t,a=this,n='<span class="'+e+'-stars">';for(t=1;t<=a.stars;t++)n+='<span class="star">'+a[e+"Star"]+"</span>";return n+"</span>"},_setStars:function(e){var t=this,a=arguments.length?t.calculate(e):t.calculate(),n=t.$element,r=t._parseValue(a.val);return n.val(r),t.$filledStars.css("width",a.width),t._setCaption(a.caption),t.cache=a,n},showStars:function(e){var t=this,a=t._parseValue(e);return t.$element.val(a),t._setStars()},calculate:function(e){var a=this,n=t.isEmpty(a.$element.val())?0:a.$element.val(),r=arguments.length?a.getValueFromPosition(e):n,i=a.fetchCaption(r),l=a.getWidthFromValue(r);return l+="%",{caption:i,width:l,val:r}},getValueFromPosition:function(e){var a,n,r=this,i=t.getDecimalPlaces(r.step),l=r.$rating.width();return n=r.diff*e/(l*r.step),n=r.rtl?Math.floor(n):Math.ceil(n),a=t.applyPrecision(parseFloat(r.min+n*r.step),i),a=Math.max(Math.min(a,r.max),r.min),r.rtl?r.max-a:a},getWidthFromValue:function(e){var t,a,n=this,r=n.min,i=n.max,l=n.$emptyStars;return!e||r>=e||r===i?0:(a=l.outerWidth(),t=a?l.width()/a:1,e>=i?100:(e-r)*t*100/(i-r))},fetchCaption:function(e){var a,n,r,i,l,s=this,o=parseFloat(e)||s.clearValue,c=s.starCaptions,u=s.starCaptionClasses;return o&&o!==s.clearValue&&(o=t.applyPrecision(o,t.getDecimalPlaces(s.step))),i="function"==typeof u?u(o):u[o],r="function"==typeof c?c(o):c[o],n=t.isEmpty(r)?s.defaultCaption.replace(/\{rating}/g,o):r,a=t.isEmpty(i)?s.clearCaptionClass:i,l=o===s.clearValue?s.clearCaption:n,'<span class="'+a+'">'+l+"</span>"},destroy:function(){var a=this,n=a.$element;return t.isEmpty(a.$container)||a.$container.before(n).remove(),e.removeData(n.get(0)),n.off("rating").removeClass("rating rating-input")},create:function(e){var t=this,a=e||t.options||{};return t.destroy().rating(a)},clear:function(){var e=this,t='<span class="'+e.clearCaptionClass+'">'+e.clearCaption+"</span>";return e.inactive||e._setCaption(t),e.showStars(e.clearValue).trigger("change").trigger("rating.clear")},reset:function(){var e=this;return e.showStars(e.initialValue).trigger("rating.reset")},update:function(e){var t=this;return arguments.length?t.showStars(e):t.$element},refresh:function(t){var a=this,n=a.$element;return t?a.destroy().rating(e.extend(!0,a.options,t)).trigger("rating.refresh"):n}},e.fn.rating=function(n){var r=Array.apply(null,arguments),i=[];switch(r.shift(),this.each(function(){var l,s=e(this),o=s.data("rating"),c="object"==typeof n&&n,u=c.theme||s.data("theme"),d=c.language||s.data("language")||"en",p={},h={};o||(u&&(p=e.fn.ratingThemes[u]||{}),"en"===d||t.isEmpty(e.fn.ratingLocales[d])||(h=e.fn.ratingLocales[d]),l=e.extend(!0,{},e.fn.rating.defaults,p,e.fn.ratingLocales.en,h,c,s.data()),o=new a(this,l),s.data("rating",o)),"string"==typeof n&&i.push(o[n].apply(o,r))}),i.length){case 0:return this;case 1:return void 0===i[0]?this:i[0];default:return i}},e.fn.rating.defaults={theme:"",language:"en",stars:5,filledStar:'<i class="fas fa-star"></i>',emptyStar:'<i class="far fa-star"></i>',containerClass:"",size:"md",animate:!0,displayOnly:!1,rtl:!1,showClear:!0,showcaption:!0,starCaptionClasses:{.5:"badge badge-pill badge-danger",1:"badge badge-pill badge-danger",1.5:"badge badge-pill badge-warning",2:"badge badge-pill badge-warning",2.5:"badge badge-pill badge-info",3:"badge badge-pill badge-info",3.5:"badge badge-pill badge-primary",4:"badge badge-pill badge-primary",4.5:"badge badge-pill badge-success",5:"badge badge-pill badge-success"},clearButton:'<i class="fa fa-minus-circle"></i>',clearButtonBaseClass:"clear-rating",clearButtonActiveClass:"clear-rating-active",clearCaptionClass:"label label-default",clearValue:null,captionElement:null,clearElement:null,hoverEnabled:!0,hoverChangeCaption:!0,hoverChangeStars:!0,hoverOnClear:!0,zeroAsNull:!0},e.fn.ratingLocales.en={defaultCaption:"{rating} Stars",starCaptions:{.5:"Half Star",1:"One Star",1.5:"One & Half Star",2:"Two Stars",2.5:"Two & Half Stars",3:"Three Stars",3.5:"Three & Half Stars",4:"Four Stars",4.5:"Four & Half Stars",5:"Five Stars"},clearButtonTitle:"Clear",clearCaption:"Not Rated"},e.fn.rating.Constructor=a,e(document).ready(function(){var t=e("input.rating");t.length&&t.removeClass("rating-loading").addClass("rating-loading").rating()})});
	
	
	
    $(document).ready(function(){
    	$(".submitReview").submit(function(e){
    		e.preventDefault();
    		console.log($(this).find("span.filled-stars").width());
     		console.log($("span.filled-stars").width());  找出被改變的span寬度
    		var wid = $(this).find("span.filled-stars").width();
    		var star = wid/33;
 			console.log(star);
    		$( "input[name='crev_star']" ).val(star);
    		return;
    		
    	});
    });
    
</script>

</body>
</html>