<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%String wed_photo_order_no = (String)request.getAttribute("wed_photo_order_no"); 
// 	request.getAttribute("account");
	
%>
<jsp:useBean id="wpOrderSvc" scope="page" class="com.wporder.model.WPOrderService" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Wed Photo Order 婚禮攝影服務 訂單查詢 - 廠商頁面</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/vendors/bootstrap/css/bootstrap.min.css">
<style type="text/css">
select{	
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
}
.btn-outline-info{
	padding: 1px 20px 0px 20px;
}
.btn-info{
	padding: 1px 20px 0px 20px;
}
.btn-danger{
	padding: 1px 20px 0px 20px;
}
.range_wei{
	padding-right:0;
	padding-left:0;
}
.container{
	margin-top:10px;
	margin-left: 0px;
}
.fixright{
   display: block;
   width: 300px; 
   padding: 5px 5px 5px 5px;   
   position: fixed;
   right: 20px;
   top: 80px;
}
.WStest{
    height: 50px;
    border: #A89898 1px dotted;
    border-radius: 8px;
    padding-left: 10px;
    margin-top: 10px;
    margin-bottom: 10px;
}
#mem_img{
width:100px;
height :100px;
}
</style>
<body>
<div class="container">
<div class="fixright"></div>
        <div class="row">
            <div class="col-12">
                <ul>
                    <li>
                        <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/wed/wpcase.do">
                            <b>選擇訂單編號:</b>
                            <select size="1" name="select_order_no" id="select_order_no"></select>
                        </FORM>
                    </li>                    
                  </ul>
            </div>
        </div>
        <div class="row">
            <div class="col-12">
                <table class="table table-hover tablerow" id="tableSort">
                    <thead>
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
                    <c:if test="${wpordervo.vender_id == vendervo.vender_id}">
                        <tr>
                            <th scope="row"></th>
                            <td>${wpordervo.wed_photo_order_no }</td>
                            <td>${wpordervo.membre_id }</td>
                            <td>${wpordervo.vender_id }</td>
                            <td><fmt:formatDate value="${wpordervo.filming_time }" pattern="yyyy-MM-dd HH:mm" /></td>
                            <td><fmt:formatDate value="${wpordervo.wed_photo_odtime }" pattern="yyyy-MM-dd HH:mm" /></td>
                            <td>
	                            <button type="button" class="btn btn-outline-info" data-toggle="modal" data-target="#exampleModal"
	                            value="${wpordervo.wed_photo_order_no }" name="read_more">查看詳情
								</button></td>
                            <td>
								<div class="btn-group">									
                                    <button type="button" class="btn btn-info complete" name="${wpordervo.wed_photo_order_no }"
                                     data-toggle="modal" data-target="#exampleModal3"> 完成訂單 </button>
                                    <input type="hidden" name="order_status_decide" value="${wpordervo.order_status }">
									<input type="hidden" name="wp_vrep_s_decide" value="${wpordervo.wp_vrep_s }">
									<input type="hidden" name="wp_mrep_s_decide" value="${wpordervo.wp_mrep_s }">
                                    <button type="button" class="btn btn-info dropdown-toggle px-3" data-toggle="dropdown"
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
                    </c:if></c:forEach></tbody>
                </table>
            </div>
            <div class="col-12">
                <div class="content_2">
	        		<nav aria-label="Page navigation example">
		            	<ul class="pagination justify-content-center">
			                <li class="page-item Previous"><a class="page-link" tabindex="-1">First</a></li>
			                <li class="page-item Next"><a class="page-link">Last</a></li>
		            	</ul>
	        		</nav>
   		 		</div>
            </div>
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
	    <a class="nav-link active" id="pills-home-tab" data-toggle="pill" href="#pills-Order" role="tab"
	      aria-controls="pills-home" aria-selected="true">訂單狀況 Order</a>
	  </li>
	  <li class="nav-item">
	    <a class="nav-link" id="pills-contact-tab" data-toggle="pill" href="#pills-Report" role="tab"
	      aria-controls="pills-contact" aria-selected="false">檢舉內容 Report</a>
	  </li>
	  <li class="nav-item">
	    <a class="nav-link" id="pills-profile-tab" data-toggle="pill" href="#pills-Appraise" role="tab"
	      aria-controls="pills-profile" aria-selected="false">評價內容 Appraise</a>
	  </li>
	  <li class="nav-item">
	    <a class="nav-link" id="pills-profile-tab" data-toggle="pill" href="#pills-Membre" role="tab"
	      aria-controls="pills-profile" aria-selected="false">會員資訊 Member</a>
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
		    <input type="range" class="custom-range" id="customRange2" min="0" max="5" value="" disable>
		  </div>
		  <span class="font-weight-bold text-primary ml-2 valueSpan2"></span>
		</div>
                  	</div>
           <div class="form-group">
               <label for="message-text" class="col-form-label">評價內容 Message:</label>
               <textarea class="form-control" id="message-text" name="review_content" rows="10" disabled></textarea>
           </div>
       </form>	  
	  </div>
	  <div class="tab-pane fade" id="pills-Report" role="tabpanel" aria-labelledby="pills-contact-tab">
	  	<form>
           <div class="form-group">
               <label for="recipient-name" class="col-form-label">檢舉狀態 Report:</label>
               <input type="text" class="form-control" id="recipient-name-odstu" name="wp_vrep_s" disabled>
           </div>
           <div class="form-group">
               <label for="message-text" class="col-form-label">檢舉內容 Message:</label>
               <textarea class="form-control" id="message-text" name="wp_vrep_d2" rows="10" disabled></textarea>
           </div>
       	</form>
	  </div>
	  	  <div class="tab-pane fade" id="pills-Membre" role="tabpanel" aria-labelledby="pills-contact-tab">
	  	  <img alt="" id="mem_img">
	  	<form>
           <div class="form-group">
               <label for="recipient-name" class="col-form-label">會員姓名 Name:</label>
               <input type="text" class="form-control" id="recipient-name" name="mem" disabled>
           </div>
           <div class="form-group">
               <label for="recipient-phone" class="col-form-label">會員電話 Phone:</label>
               <input type="text" class="form-control" id="recipient-phone" name="v_phone" disabled>
           </div>
           <div class="form-group">
               <label for="recipient-mail" class="col-form-label">信箱 E-mail:</label>
               <input type="text" class="form-control" id="recipient-mail" name="v_mail" disabled>
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
                        	<div class="col-12">
                            	<label for="message-text-rep" class="col-form-label">檢舉內容描述 Message :</label>
                            	<textarea class="form-control" id="message-text-rep" placeholder="請勿空白" name="wp_vrep_d" rows="10"></textarea>
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
                    <h5 class="modal-title" id="exampleModalLabel">Thanks & Complete</h5>
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
  <!-- body 結束標籤之前，載入Bootstrap 的 JS 及其相依性安裝(jQuery、Popper) -->
  <script src="<%=request.getContextPath() %>/vendors/jquery/jquery-3.4.1.min.js"></script>
  <script src="<%=request.getContextPath() %>/vendors/popper/popper.min.js"></script>
  <script src="<%=request.getContextPath() %>/vendors/bootstrap/js/bootstrap.min.js"></script>
  <script src="<%=request.getContextPath() %>/js/wp/wpOrder_ven.js" charset="utf-8" type="text/javascript"></script>
  <script src="<%=request.getContextPath() %>/js/wp/tableSort.js" charset="utf-8" type="text/javascript"></script>
</body>
</html>