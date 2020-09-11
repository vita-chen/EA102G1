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
<html>
<head>
	<meta charset="UTF-8">
	<title>Wed Photo Order 訂單查詢 - Wedding Navi</title>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+TC:wght@300;500&display=swap" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/css/menu_style.css" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/css/icofont/icofont.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/css/boxicons/css/boxicons.min.css" rel="stylesheet">
<style type="text/css">
.col-2 {
    padding-right: 20px;
    padding-left: 20px;
}

.col-10 {
    padding: 0;
}

.list-group-item.active {
    z-index: 2;
    color: #3D3D3DFF;
    background-color: #EAEAEAFF;
    border-color: #C4C4C4FF;
}
.img_banner{
	height: 200px;
	border: dotted 2px #D6D6D6FF;
}

select {
    cursor: pointer;
    -webkit-appearance: none;
    border-radius: 4px;
    border: 1px solid #dcdfe6;
    box-sizing: border-box;
    color: #606266;
    font-size: inherit;
    height: 35px;
    line-height: 35px;
    padding: 0 15px;
    width: 20%;
    font-family: inherit;
}

ul {
    margin-bottom: 20px;
}

.btn-secondary:hover {
    color: #292b2c;
    background-color: #FFFFFFFF;
    border-color: #adadad;
}

.content_1 {
    height: 65px;    
    box-shadow:  0px 0px 3px 0px #C4C4C4FF;
}

.content_1 p {
    display: inline;
}

p ul {
    float: left;
}
ul li {
	position: relative;
    float: right;    
    display: inline-block;
    text-align:right;
    
    height: 20px;
}
.choose{
	padding: 5px 13px 6px 13px;
	margin-left: 3px;
	margin-bottom: 1px;
}
.content_1 p{
	padding-left: 12px;
	font-size: 25px;
	font-weight: 700;	
}
.content_2 {
    height: 65px;
    box-shadow: 0px 0px 3px 0px #C4C4C4FF;

}
#con_form{
	position: relative;
	width: 870px;  
	bottom: 15px;
	right: 28px;
}
.btnw{
	padding: 5px 16px 6px 16px;
}
.badge-danger{
	
	margin-left: 3px;
}
.footer{
    height: 200px;
    border: dotted 2px #C9C9C9FF;
    margin-top: 50px;
}
.badge-dark {
    background-color: #686868FF;
}
tbody tr > th{
    width: 5%;
}
</style>
<body>
<div class="header">
    <div class="welcome">
        <p>這邊是 歡迎小文字			你好${membrevo.mem_name }</p>
    </div>
    <div class="img_banner">圖片區</div>
</div>

<div class="container">
        <div class="row">
            <div class="col-12">
                <div>
                    <ol>
						<li><a href="<%=request.getContextPath()%>/front_end/home/home.jsp" id="">婚禮導航</a></li>
                        <li><a href="<%=request.getContextPath()%>/front_end/wed_photo/wp_home.jsp" id="">婚禮攝影</a></li>
                        <li><a href="<%=request.getContextPath()%>/front_end/prod/index.jsp" id="">二手商城</a></li>
                    </ol>
                </div>
            </div>
        </div>
        <div class="row justify-content-around">
			<div class="col-2">
				<div class="list-group">
					<a href="<%=request.getContextPath()%>/front_end/membre/wp/listAllWPOrder.jsp" class="list-group-item list-group-item-action active">訂單查詢<span class="badge badge-danger badge-pill">10</span></a>
					<a href="<%=request.getContextPath()%>/front_end/membre/wp/listAllCollectWP.jsp" class="list-group-item list-group-item-action ">收藏方案<span class="badge badge-danger badge-pill">3</span></a>
				</div>
			</div>
        
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
	                            <button type="button" class="btn btn-secondary btnw" data-toggle="modal" data-target="#exampleModal"
	                            value="${wpordervo.wed_photo_order_no }" name="read_more">查看詳情
								</button></td>
                            <td>
                             	<div class="btn-group">
                             		<button type="button" class="btn btn-secondary complete btnw" name="${wpordervo.wed_photo_order_no }"
                                     data-toggle="modal" data-target="#exampleModal3"> 完成訂單 </button>
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
			                <li class="page-item Previous"><a class="page-link" tabindex="-1">Previous</a></li>
			                <li class="page-item Next"><a class="page-link">Next</a></li>
		            	</ul>
	        		</nav>
   		 		</div>
            </div>
        </div>
</div>
<div class="footer">footer區 尚未添加內容</div>
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
                                <span>(評價可改?)　 歡迎給我們更好的評價與鼓勵　您的支持是我們服務的動力^O^/</span>
                            </form>
                        </div>
                        <div class="tab-pane fade" id="pills-Report" role="tabpanel" aria-labelledby="pills-contact-tab">
                            <form>
                                <div class="form-group">
                                    <label for="recipient-name" class="col-form-label">檢舉狀態 Report:</label>
                                    <input type="text" class="form-control" id="recipient-name-odstu" name="wp_mrep_s" disabled>
                                </div>
                                <div class="form-group">
                                    <label for="message-text" class="col-form-label">檢舉內容 Message:</label>
                                    <textarea class="form-control" id="message-text" name="wp_mrep_d2" rows="10" disabled></textarea>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="hidden" name="wed_photo_order_no" value="">
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
    						<div class="col-6">
                            	<label for="recipient-name" class="col-form-label">會員編號 Member:</label>
                            	<input type="text" class="form-control" id="recipient-name-mem" name="membre_id" disabled>
                        	</div>
                        	<div class="col-6">
                            	<label for="recipient-name" class="col-form-label">廠商編號 Vender:</label>
                            	<input type="text" class="form-control" id="recipient-name-ven" name="vender_id" disabled>
                        	</div>                   
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
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>                    
                    <button type="button" class="btn btn-primary complete_submit" data-dismiss="modal">Complete Order</button>
                </div>
            </div>
        </div>
    </div>
<!-- Modal2 評價 訂單完成區塊 --> 
    <script src="<%=request.getContextPath() %>/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath() %>/js/popper.min.js"></script>
    <script src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath() %>/js/login.js"></script>
    <script src="<%=request.getContextPath() %>/js/jquery.easing.min.js"></script>
    <script src="<%=request.getContextPath() %>/js/scripts.js"></script>
  <script src="<%=request.getContextPath() %>/js/wp/wpOrder_mem.js" charset="utf-8" type="text/javascript"></script>
  <script src="<%=request.getContextPath() %>/js/wp/tableSort.js" charset="utf-8" type="text/javascript"></script>
</body>
<script>
$(document).ready(function() {
	
	
    $("tbody > tr").each(function(i, item) {
        $(this).children().eq(0).html('<span class="badge badge-dark">'+(i+1)+'</span>');
    })
	
})
</script>
</html>