<%@page import="com.membre.model.MembreVO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%	String wed_photo_order_no = (String)request.getAttribute("wed_photo_order_no"); 

	MembreVO membrevo = (MembreVO)session.getAttribute("membrevo");
	if(membrevo == null){
		String url = request.getContextPath() +"/front_end/membre/wp/listAllWPOrder.jsp";
		session.setAttribute("location",url);
		response.sendRedirect(request.getContextPath()+"/front_end/membre/login.jsp");
	    return;
	}
%>
<jsp:useBean id="wpOrderSvc" scope="page" class="com.wporder.model.WPOrderService" />

<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>會員訂單</title>
	<meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />

	<!-- Fonts and icons -->
	<link rel="icon" href="<%=request.getContextPath()%>/assets/img/icon.ico" type="image/x-icon"/>

	<!-- Fonts and icons -->
	<script src="<%=request.getContextPath()%>/assets/js/plugin/webfont/webfont.min.js"></script>
	<script>
		WebFont.load({
			google: {"families":["Lato:300,400,700,900"]},
			custom: {"families":["Flaticon", "Font Awesome 5 Solid", "Font Awesome 5 Regular", "Font Awesome 5 Brands", "simple-line-icons"], urls: ['<%=request.getContextPath()%>/assets/css/fonts.min.css']},
			active: function() {
				sessionStorage.fonts = true;
			}
		});
	</script>
	<!-- third party css -->
	<link href="<%=request.getContextPath()%>/css/jquery-jvectormap-1.2.2.css" rel="stylesheet" type="text/css" />
	<!-- CSS Files -->
	<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/atlantis.min.css">
	<!-- CSS Just for demo purpose, don't include it in your project -->
	<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/demo.css">
	<link href="<%=request.getContextPath()%>/css/icons.min.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/css/app.min.css" rel="stylesheet" type="text/css" id="light-style" />
	


</head>
<body>
	<div class="wrapper">
	<!-- topbar -->
	
<%@ include file="/front_end/membre_order/membre_topbar.jsp" %>
	<!-- header -->
<%@ include file="/front_end/membre_order/membre_header.jsp" %>
<div class="main-panel"style="overflow-y:auto;">
<div class="content">
<div class="page-inner">
					<div class="page-header">
					
						<h4 class="page-title">會員</h4>
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
								<a href="#">服務類訂單管理</a>
							</li>
							<li class="separator">
								<i class="flaticon-right-arrow"></i>
							</li>
							<li class="nav-item">
								<a href="#">婚攝訂單</a>
							</li>
						</ul>	
</div>
<!-- 塞資料的地方 -->
<!-- 塞資料的地方 -->
<div class="page-category">
				

        
            <div class="col-10">
                <div class="content_1">
                    <p>訂單管理頁面</p><input type="hidden" name="membre_id" value="${membrevo.membre_id }">
                    <ul>
                        <li>
                            <FORM id="con_form" METHOD="post" ACTION="<%=request.getContextPath()%>/wed/wpcase.do">
                                <b>選擇訂單編號：</b>
                                <select size="1" name="select_order_no" id="select_order_no"></select>
                                <button type="button" class="btn btn-secondary choose">送出</button>
                            </FORM>
                        </li>
                    </ul>
                </div>
                 
                 <table class="table table-hover col-10" id="tableSort">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col" data-type="num">#</th>
                            <th scope="col" data-type="string">訂單編號</th>
                            <th scope="col" data-type="string">會員編號</th>
                            <th scope="col" data-type="string">廠商編號</th>
                            <th scope="col" data-type="date">到場拍攝日期</th>
                            <th scope="col" data-type="date">訂單產生日期</th>
                            <th scope="col">詳細資訊</th>                            
                            <th scope="col">訂單狀態</th>                            
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="wpordervo" varStatus="count" items="${wpOrderSvc.getAll()}" >
                    <c:if test="${wpordervo.membre_id == membrevo.membre_id }">
                        <tr>
                            <th scope="row"></th>
                            <td>${wpordervo.wed_photo_order_no }</td>
                            <td>${wpordervo.membre_id }</td>
                            <td>${wpordervo.vender_id }</td>
                            <td><fmt:formatDate value="${wpordervo.filming_time }" pattern="yyyy-MM-dd HH:mm" /></td>
                            <td><fmt:formatDate value="${wpordervo.wed_photo_odtime }" pattern="yyyy-MM-dd HH:mm" /></td>
                            <td>
	                            <button type="button" class="btn btn-secondary btnw read_more" data-toggle="modal" data-target="#exampleModal"
	                            value="${wpordervo.wed_photo_order_no }" name="${wpordervo.vender_id }">查看詳情
								</button></td>
                            <td>
                             	<div class="btn-group">
                             		<button type="button" class="btn btn-secondary complete btnw" name="${wpordervo.wed_photo_order_no }"
                                     data-toggle="modal" data-target="#exampleModal3" value="${wpordervo.vender_id }"> 完成訂單 </button>
                             		<input type="hidden" name="order_status_decide" value="${wpordervo.order_status }">
									<input type="hidden" name="wp_vrep_s_decide" value="${wpordervo.wp_vrep_s }">
									<input type="hidden" name="wp_mrep_s_decide" value="${wpordervo.wp_mrep_s }">
                                    
                                    <button type="button" class="btn btn-secondary dropdown-toggle px-3" data-toggle="dropdown"
                                    	aria-haspopup="true" aria-expanded="false">
                                        <span class="sr-only">Toggle Dropdown</span>
                                    </button>
                                    <div class="dropdown-menu">
                                        <a class="dropdown-item report" data-toggle="modal" data-target="#exampleModal2" value="${wpordervo.wed_photo_order_no }">我要檢舉</a>
                                        <a class="dropdown-item cancle" value="${wpordervo.wed_photo_order_no }" >取消訂單</a>                                        
                                    </div>
                             	</div>
                             </td>
                        </tr>
                    </c:if>
                    </c:forEach>
                  	</tbody>
                </table>
                 
                <div class="content_2">
	        		<nav aria-label="Page navigation example">
		            	<ul class="pagination justify-content-center">
			                <li class="page-item Previous"><a class="page-link" tabindex="-1">First</a></li>
			                <li class="page-item Next"><a class="page-link">Last</a></li>
		            	</ul>
	        		</nav>
   		 		</div>
            </div>

    <!-- Modal 訂單詳情區塊 -->
	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Order Details</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" id="pills-home-tab" data-toggle="pill" href="#pills-Order" role="tab" aria-controls="pills-home" aria-selected="true">訂單狀況 Order</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="pills-profile-tab" data-toggle="pill" href="#pills-Appraise" role="tab" aria-controls="pills-profile" aria-selected="false">評價內容 Appraise</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="pills-contact-tab" data-toggle="pill" href="#pills-Report" role="tab" aria-controls="pills-contact" aria-selected="false">檢舉內容 Report</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="pills-contact-tab" data-toggle="pill" href="#pills-Member" role="tab" aria-controls="pills-contact" aria-selected="false">會員資訊 Member</a>
                        </li>
                    </ul>
                    <div class="tab-content pt-2 pl-1" id="pills-tabContent">
                        <div class="tab-pane fade show active" id="pills-Order" role="tabpanel" aria-labelledby="pills-home-tab">
                            <form>
                                <div class="form-group">
                                    <label for="recipient-name" class="col-form-label">訂單狀態 order status:</label>
                                    <input type="text" class="form-control" id="recipient-name-odstu" name="order_status" disabled>
                                </div>
                                <div class="form-group">
                                    <label for="message-text" class="col-form-label">訂單備註 Message:</label>
                                    <textarea class="form-control" id="message-text" name="order_explain" rows="10"></textarea>
                                </div>
                                <a href="" target="_blank" class="case_a"><span class="case_info"></span></a>
                            </form>                            
                        </div>
                        <div class="tab-pane fade" id="pills-Appraise" role="tabpanel" aria-labelledby="pills-profile-tab">
                            <form>
                                <!--            <div class="form-group"> -->
                                <!--                <label for="recipient-name" class="col-form-label">評價等級 Appraise:</label> -->
                                <!--                <input type="text" class="form-control" id="recipient-name-odstu" name="review_star" > -->
                                <!--            </div> -->
                                <div class="col-12 range_wei">
                                    <label for="customRange2" class="col-form-label">評價等級 Range :</label>
                                    <div class="d-flex justify-content-center my-4">
                                        <div class="w-75">
                                            <input type="range" class="custom-range" id="customRange2" min="0" max="5" value="">
                                        </div>
                                        <span class="font-weight-bold text-primary ml-2 valueSpan2"></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="message-text" class="col-form-label">評價內容 Message:</label>
                                    <textarea class="form-control" id="message-text" name="review_content" rows="10"></textarea>
                                </div>
                                <span>歡迎給我們更好的評價與鼓勵　您的支持是我們服務的動力^O^/</span>
                            </form>
                        </div>
                        <div class="tab-pane fade" id="pills-Report" role="tabpanel" aria-labelledby="pills-contact-tab">
                            <form>
                                <div class="form-group">
                                    <label for="recipient-name" class="col-form-label">檢舉狀態 Report:</label>
                                    <input type="text" class="form-control" id="recipient-name" name="wp_mrep_s" disabled>
                                </div>
                                <div class="form-group">
                                    <label for="message-text" class="col-form-label">檢舉內容 Message:</label>
                                    <textarea class="form-control" id="message-text" name="wp_mrep_d2" rows="10" disabled></textarea>
                                </div>
                            </form>
                        </div>
                        <div class="tab-pane fade" id="pills-Member" role="tabpanel" aria-labelledby="pills-contact-tab">
                            <form>
                                <div class="form-group">
                                    <label for="recipient-name" class="col-form-label">廠商聯絡人 Name:</label>
                                    <input type="text" class="form-control" id="recipient-name" name="contact" disabled>
                                </div>
                                <div class="form-group">
                                    <label for="recipient-phone" class="col-form-label">廠商手機 Phone:</label>
                                    <input type="text" class="form-control" id="recipient-phone" name="phone" disabled>
                                </div>
                                <div class="form-group">
                                    <label for="recipient-email" class="col-form-label">廠商E-mail :</label>
                                    <input type="text" class="form-control" id="recipient-email" name="e-mail" disabled>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="hidden" name="wed_photo_order_no" value="">
                    <input type="hidden" name="vender_id" value="">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary update_order_Ajax" data-dismiss="modal">Save changes</button>
                </div>
            </div>
        </div>
    </div>
	<!--Modal 訂單詳情區塊 -->
    <!-- Modal2 檢舉區塊 -->
    <div class="modal fade" id="exampleModal2" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Report</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form METHOD="post" ACTION="<%=request.getContextPath()%>/wed/wpcase.do">
                    	<div class="form-row">    						                
                        	<div class="col-12">
                            	<label for="message-text-rep" class="col-form-label">檢舉內容描述 Message :</label>
                            	<textarea class="form-control" id="message-text-rep" placeholder="請勿空白" name="wp_mrep_d" rows="10"></textarea>
                        	</div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                	<input type="hidden" name="wed_photo_order_no" value="">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>                    
                    <button type="button" class="btn btn-primary report_submit" data-dismiss="modal">Save changes</button>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal2 評價 訂單完成區塊 -->
    <div class="modal fade" id="exampleModal3" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Thanks & Appraise & Complete</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form METHOD="post" ACTION="<%=request.getContextPath()%>/wed/wpcase.do">
                    	<div class="form-row">
                    		<div class="col-12">
                    		<figure class="figure">
							  <img src="<%=request.getContextPath()%>/img/wp_img/thanks.jpg" class="figure-img img-fluid z-depth-1"
							    alt="..." style="width: 500px;height:275px">
							  <figcaption class="figure-caption text-right">Honored to serve you</figcaption>
							</figure></div>
    						<div class="col-12">
    							<label for="customRange1" class="col-form-label">評價等級 Range :</label>
                            	<div class="d-flex justify-content-center my-4">
								  <div class="w-75">
								    <input type="range" class="custom-range" id="customRange1" min="0" max="5">
								  </div>
								  <span class="font-weight-bold text-primary ml-2 valueSpan"></span>
								</div>
                        	</div>
                        	<div class="col-12">
                            	<label for="message-text-rev" class="col-form-label">評價內容 Message :</label>
                            	<textarea class="form-control" id="message-text-rev" name="review_content_submit" placeholder="您可以在此留下評價，或直接完成訂單"></textarea>
                        	</div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                	<input type="hidden" name="wed_photo_order_no" value="">
                	<input type="hidden" name="vender_id" value="">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>                    
                    <button type="button" class="btn btn-primary complete_submit" data-dismiss="modal">Complete Order</button>
                </div>
            </div>
        </div>
    </div>
<!-- Modal2 評價 訂單完成區塊 --> 
<!-- 塞資料的地方 -->
<!-- 塞資料的地方 -->
</div>
</div>

</div>
</div>
<%@ include file="/front_end/membre_order/membre_js.jsp" %>	
    <script src="<%=request.getContextPath() %>/js/login.js"></script>
    <script src="<%=request.getContextPath() %>/js/jquery.easing.min.js"></script>
    <script src="<%=request.getContextPath() %>/js/scripts.js"></script>
  <script src="<%=request.getContextPath() %>/js/wp/wpOrder_mem.js" charset="utf-8" type="text/javascript"></script>
  <script src="<%=request.getContextPath() %>/js/wp/tableSort.js" charset="utf-8" type="text/javascript"></script>

<script>
$(document).ready(function() {
	
	
    $("tbody > tr").each(function(i, item) {
        $(this).children().eq(0).html('<span class="badge badge-dark">'+(i+1)+'</span>');
    })
	
})
</script>	


</body>
</html>