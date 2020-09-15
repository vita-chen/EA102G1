<%@page import="com.wporder.model.WPOrderDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.wporder.model.WPOrderVO"%>
<%@page import="com.wpdetail.model.WPDetailVO"%>
<%@page import="com.wpdetail.model.WPDetailDAO"%>
<%@page import="com.vender.model.VenderVO"%>
<%@page import="com.vender.model.VenderJDBCDAO"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.List"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="com.wpcase.model.WPCaseDAO"%>
<%@page import="com.wpcase.model.WPCaseVO"%>
<%@page import="com.membre.model.MembreVO"%>
<%@page import="com.wpimg.model.WPImgVO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	
	MembreVO membrevo = (MembreVO)session.getAttribute("membrevo");
	WPCaseVO WPCaseVO =(WPCaseVO)request.getAttribute("WPCaseVO");
	String url = request.getContextPath()+"/wed/wpcase.do?"+request.getQueryString();	
	session.setAttribute("location",url); 
	
	if(WPCaseVO == null){
		response.sendRedirect(request.getContextPath()+"/front_end/wed_photo/home_WP.jsp");
	    return;
	}
	//廠商資訊
	VenderJDBCDAO venderdao = new VenderJDBCDAO();
	List<VenderVO> ven_list = venderdao.getAll().stream()
			.filter(v -> v.getVender_id().equals(WPCaseVO.getVender_id()))
			.collect(Collectors.toList());
	
	pageContext.setAttribute("vender", ven_list.get(0));
	
	//其他方案
	String venderid = WPCaseVO.getVender_id();
	String caseno = WPCaseVO.getWed_photo_case_no();
	List<WPCaseVO> list = new WPCaseDAO().getAll();
	Set<WPCaseVO> other_set = list.stream()
			.filter( vo -> vo.getVender_id().equals(venderid) && !(vo.getWed_photo_case_no().equals(caseno)))			
			.collect(Collectors.toSet());
	
	pageContext.setAttribute("other_set",other_set);
	
	//評價內容
	WPDetailDAO wpdetail = new WPDetailDAO();
	WPOrderDAO order = new WPOrderDAO();
	List<WPDetailVO> detail_list = wpdetail.getAll().stream()
			.filter(d -> d.getWed_photo_case_no().equals(WPCaseVO.getWed_photo_case_no()))
			.collect(Collectors.toList());
	
	List<WPOrderVO> order_list = new ArrayList<WPOrderVO>();
	for(WPDetailVO detail : detail_list){
		order_list.add(order.getOne(detail.getWed_photo_order_no()));
	}
	pageContext.setAttribute("order_list",order_list);
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>${WPCaseVO.wed_photo_name } - 婚禮導航 Wedding Navi</title>
	<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+TC:wght@300;500&display=swap" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/bootstrap_menu.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/menu_style.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/icofont/icofont.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/boxicons/css/boxicons.min.css" rel="stylesheet">
	<script type="text/javascript">
    $(document).ready(function() {
        $("#service").hide();
        $(window).scroll(function() {
            // last=$("body").height()-$(window).height()
            // 滑動顯示回頂端的圖示
            if ($(window).scrollTop() >= 200) {
                $("#service").fadeIn(500);
            } else {
                $("#service").fadeOut(500);
            }
        });
        $("#service").click(function() {
            $("html,body").animate({ scrollTop: 0 }, "slow");
            // 返回到最頂上
            // return false;
        });
    });
    </script>
</head>
<style>
    body {
    margin: 0px;
}
#service {
    width: 40px;
    height: 40px;
    border-radius: 5px;
    border: 1px #fff solid;
    background-color: #FF9FBBFF;
    line-height: 40px;
    font-size: 14px;
    color: #fff;
    text-align: center;
    position: fixed;
    left: 93%;
    top: 83%;
    z-index: 1;
}


.header .title1 {
    width: 100%;
    height: 30px;
    font-size: 10px;
    font-weight: 400;
    background-color: #FFECFFFF;
    font-width: 600;
    border-bottom: solid 1px #FFEEFFFF;
}
.title1 {
    display: inline-block;
    position: fixed;
    top: 0px;
    z-index: 1;
}
.title1 p {
    display: inline;
    float: right;
    margin: 6px;
    font-size: 13px;
}
.float_right{    
    display: inline;
    float: right;
}
.btn-link{
    font-size: 13px;
}
h4 {
    float: left;
    font-size: 12px;
    margin-top: 8px;
    font-weight: 500;
}

.logo img{
    width:180px;
    height: auto;
}
.logo{
    position: fixed;
    top: 4px;
    left: 10px;
    z-index: 5;
    width:180px;
    height: 63px;
}
.title2{
    display:inline;
    width: 100%;
    text-align:right;
    position: fixed;
    top: 12px;
    z-index: 2;

}
.title2 ol{
    list-style-type: none;    
    margin-top: 17.5px;
    height: 36px;    
    background: -webkit-linear-gradient(#FFFFFFE0,#FFFFFF00);
    background: -o-linear-gradient(#FFFFFFE0,#FFFFFF00);
    background: -moz-linear-gradient(#FFFFFFE0,#FFFFFF00);
    background: linear-gradient(#FFFFFFE0,#FFFFFF00);
}
.title2 ol li{
    margin-left: 5px;
    width: 80px;
    line-height: 34.5px;
    text-align: center;
    letter-spacing: 0.5px;
    margin-right: 0px;
    display: inline-block;
}
.title2 ol li:hover {
    color: #DDDDDDFF;
}

a:visited {
    color: #545454FF;
}

a {
    color: #545454FF;
}

a:hover {
    color: #919191FF;
}
/*首圖 開始*/
.content1 .row {
    margin: 0;
}
.content1 {
    margin-top: 30px;
    height: 800px;
    border: dotted 1px #CCCCCCFF;
    background-image: url("<%=request.getContextPath() %>/img/wp_img/wpbanner01.jpg");
    background-position: 40% 50%;
    background-size: 100% auto;
    background-repeat: no-repeat;
    background-position: center;
    opacity: 0.85;

}
.content1_txt1 {
    background-color: #FFFFFF56;
    color: black;
    margin-top: 25%;
    margin-left: 42%;
    text-align: center;
    height: 30%;
    border-color: #FFFFFF00;
    border: 1px solid #FFFFFFFF;
    border-radius: 10px;
    text-shadow: #FFFFFFFF 0.1em 0.1em 0.2em;
}

.content1_txt1 img {
    width: 73%;
    border-radius: 98%;
    margin-top:8px;
    margin-bottom:8px;
    z-index: 5;
    opacity: 1;
}
/*首圖 結束*/
/*資訊 開始*/
.content2 {
    padding-top: 30px;
    padding-bottom: 15px;    
    text-align: center;
}
.content2 p{
    font-size: 22px;
}
.content2 img{
    max-width: 1000px;
    height: auto;
    margin-bottom: 15px;
}
.content2 .col-4{
    border: 1px dotted #545454FF;
    height: 390px;
}
.breadcrumb {
    background-color: #FFECFFFF;
    border-radius: 0px;
    padding: 3px 15px 3px 15px;
    height: 35px;
    margin: 0;
}
.btn-danger{
    background-color: #FF3F7FBD;
    border-color: #FF7FA6FF;
}
.btn-group{
    margin-top: 10px;
    margin-bottom: 25px;
}
/*資訊 結束*/

/*其他方案 開始*/
.img_box {
    display: inline-block;
    margin-top: 15px;
    margin-bottom: 15px;   
    box-shadow: #EBD0D0FF 3px 3px 3px 3px;    
    color: #4F4F4FFF;
}
.img_box:hover{
    box-shadow: #E0E0E0FF 3px 3px 3px 3px;
}
.img_box img {
    height: auto;
    width: 99.9%;
    /*border-radius: 5px;*/
}

.img_text h5 {
    text-align: center;
    margin-top: 20px;
    margin-bottom: 20px;
}

.text_time {
    margin-bottom: 0;
    margin-right: 5px;
    text-align: right;
    font-size: 14px;   
}

.text_new {
    margin-left: 5px;
    margin-right: 5px;
    text-align: justify;
    height: 100px;
    letter-spacing:1px;
    line-height: 25px;  
}
.img_text :hover {
    opacity: 0.6;
}
.img_box img:hover{
    opacity: 0.9;   
}
/*其他方案 結束*/
/*評價 開始*/
.review_box{
    margin-bottom: 30px;
}
.review_box p{    
    font-size: 22px;
   text-align:center;
   margin-top: 25px;
   margin-bottom: 10px;
}
.review{ 
    /*border-radius: 5px;*/
    border-left: 1.5px #FFADEDDE solid;
    border-bottom: 1.5px #FFADEDDE solid;
    margin-top: 30px;    
}
.review:hover{
    border-left: 1.5px #FFECFFFF solid;
    border-bottom: 1.5px #FFECFFFF solid;
}
.review_text{
    display:inline-block;   
    width: 83%;
}
.review_text > div{
    margin-left: 36px;
}
.review_time{    
    text-align: right;    
    padding-bottom: 3px;
}
.review_img{
    width: 80px;
    display:inline-block;    
    margin-top: 5px;    
    float: left;
}
.review_img img{
    width: 60px;
    height: 60px;
    border-radius: 5px;
}
.review_text_0{
    text-align: center;
}
span svg{
    margin-left: 3px;
    margin-right: 3px;
    font-size: 18px;
    color: #F6F608FF;
}
/*評價 結束*/


/*footer start*/
.copyright {
    text-align: center;
    background-color: #FFECFFFF;
}

.footer .col-lg-4 {
    margin-bottom: 25px;
}

.footer_con {
    height: 350px;
    background-color: #FFECFFFF;
    border: 3px #FFECFFFF solid;
    /*margin-top: 20px;*/
    padding-bottom: 20px;
}

@media (max-width: 992px) {
    .footer_con {
        height: 535px;
    }
}

.footer-about ul {
    list-style-type: none;
}

.footer-about ul li {
    line-height: 32px;
}

.footer col-md-4 {
    text-align: center;
}

.Preview img {
    width: 300px;
    height: auto;
    margin: auto;
    display: block;
    margin-bottom: 40px;
}

.foo_img img {
    width: 50px;
    height: auto;
    margin: auto;
    display: block;
}

.contact-info {
    text-align: center;
}
/*footer end*/
</style>
<body>
    <div class="header">
        <div class="title1">
            <h4></h4>
            <c:if test="${membrevo.mem_name != null}">
            <div class="float_right">
                <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/vender/vender.do">
                    <input type="hidden" name="action" value="session_off">
                    <button type="submit" class="btn btn-link" href="<%=request.getContextPath()%>/front_end/home/home.jsp">登出</button>
                </FORM>
            </div>
            </c:if>
            <p>
            	<c:if test="${membrevo.mem_name != null}">
            	你好 ${membrevo.mem_name }
            	<a href="<%=request.getContextPath()%>/front_end/membre_order/membre_wp_listall_track.jsp">　我的收藏</a>
            	<a href="<%=request.getContextPath()%>/front_end/membre_order/membre_order_wp.jsp">　訂單查詢</a>
            	</c:if>
            	<c:if test="${membrevo.mem_name == null}">
                <a href="<%=request.getContextPath()%>/front_end/membre/login.jsp">　會員登入</a>
                <a href="<%=request.getContextPath()%>/front_end/membre/regis.jsp">　我要註冊</a>
                <a href="<%=request.getContextPath()%>/front_end/vender/vender_login.jsp">　廠商專區</a>
                </c:if>
            </p>
        </div>
        <div class="title2">
            <ol>
                <li><a href="<%=request.getContextPath()%>/front_end/wed_photo/home_WP.jsp">婚禮攝影</a></li>
                <li><a href="<%=request.getContextPath()%>/front_end/carOrder/browseAllCar.jsp">禮車租借</a></li>
                <li><a href="<%=request.getContextPath()%>/front_end/dresscase/DressHome.jsp"">婚紗租借</a></li>
                <li><a href="<%=request.getContextPath()%>/front_end/prod/select_page.jsp">二手拍賣</a></li>
                <li><a href="<%=request.getContextPath()%>/front_end/forum/listAllForum.jsp">討論區</a></li>
            </ol>
        </div>
        <div class="logo"><a href="<%=request.getContextPath()%>/front_end/home/home.jsp"><img alt="Bootstrap Image Preview" src="<%=request.getContextPath() %>/img/logo-transparent(1450_400).png"></a></div>
    </div>
    
    
    <div id="service">TOP</div>
    <div class="content1">
        <div class="row ">
            <div class="col-2 content1_txt1">
                <img src="<%=request.getContextPath() %>/vender/vender.do?action=getphot&vender_id=${vender.vender_id}">
                <p>${vender.ven_name }</p>
            </div>
        </div>
    </div>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/front_end/wed_photo/home_WP.jsp">婚禮攝影</a></li>
            <li class="breadcrumb-item"><a href="<%=request.getContextPath() %>/wed/wpcase.do?action=goVenderPage&vender_id=${WPCaseVO.vender_id}">廠商資訊</a></li>
            <li class="breadcrumb-item active" aria-current="page">方案介紹</li>
        </ol>
    </nav>
    
    <div class="container">
	    <div class="row">
	        <div class="col-12 content2">
	            <div class="con2head"><p>${WPCaseVO.wed_photo_case_no } ${WPCaseVO.wed_photo_name }</p></div>
	            <div class="con2head"><p><fmt:formatNumber value="${WPCaseVO.wed_photo_price}" pattern="NT$ #,###"/></p></div>
	            
	            <div class="con2head"><pre>${WPCaseVO.wed_photo_intro }</pre></div>
	            <div class="btn-group" role="group" aria-label="Basic example">
	                       
	                       <button type="button" class="btn btn-danger collect">加入收藏</button>	                       
	                       <input type="hidden" name="membre_id" value="${membrevo.membre_id }">
	                       <input type="hidden" name="wed_photo_case_no" value="${WPCaseVO.wed_photo_case_no }">
	                       <button type="button" class="btn btn-danger Order" data-toggle="modal" data-target="#exampleModal">立即下訂</button>
	            </div>
	            <div class="con2head">
                        <p>參考作品</p>
                </div>
	            <c:if test="${imgList != null }">	
						<c:forEach var="imgvo" items="${imgList}">		
							<a href="<%= request.getContextPath() %>/wed/wpcase.do?action=Get_WPImg&wed_photo_case_no=${WPCaseVO.wed_photo_case_no}&wed_photo_imgno=${imgvo.wed_photo_imgno}"><img class="img" src="<%= request.getContextPath() %>/wed/wpcase.do?action=Get_WPImg&wed_photo_case_no=${WPCaseVO.wed_photo_case_no}&wed_photo_imgno=${imgvo.wed_photo_imgno}"></a><br>
						</c:forEach><br>
				</c:if>
				
				<div class="btn-group" role="group" aria-label="Basic example">
                        
                        <button type="button" class="btn btn-danger collect">加入收藏</button>
                        <button type="button" class="btn btn-danger Order" data-toggle="modal" data-target="#exampleModal">立即下訂</button>
                </div>
                
                    <div class="con2head">
                        <p>其他方案</p>
                    </div>
	        </div>
	    </div>
                  <div class="row justify-content-between new_case">
                   	<c:forEach var="other_set" items="${other_set }">                    	
			           <div class="col-lg-4 col-md-6 col-sm-12">
			                <a href="<%=request.getContextPath()%>/wed/wpcase.do?action=getOne_CasePage&wed_photo_case_no=${other_set.wed_photo_case_no}" target="_blank">
			                    <div class="img_box">
			                        <img src="<%=request.getContextPath()%>/wed/wpcase.do?action=getOne_WPImg&wed_photo_case_no=${other_set.wed_photo_case_no}">
			                        <div class="img_text">
			                            <h5>${other_set.wed_photo_name }</h5>
			                            <div class="text_new">
			                            	${other_set.wed_photo_intro }
			                            </div>
			                            <div class="text_time">
			                                <span style="font-style:oblique;"><fmt:formatDate value="${other_set.wed_photo_addtime }" pattern="MMM d, yyyy hh:mm aa" /></span>
			                            </div>
			                        </div>
			                    </div>
			                </a>
			            </div>
                      </c:forEach> 
                   </div>
                   
                   
                   <div class="row justify-content-between review_box">
                   		<div class="col-12 title_p">
			                <p></p>
			            </div>
                   <c:forEach var="order_list" varStatus="count" items="${order_list }">
                   <c:if test="${order_list.review_content != null }">			            
			            <div class="col-12 review">
			                <div class="review_img">
			                    <img src="<%=request.getContextPath() %>/membre/membre.do?action=getphoto&membre_id=${order_list.membre_id }" alt="">
			                </div>
			                <div class="review_text">
			                    <span>${order_list.membre_id }<svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-star-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                            <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z" />
                        </svg>${order_list.review_star }</span>
			                    <div>${order_list.review_content }</div>
			                </div>
			                <div class="review_time" style="font-style:oblique;"><fmt:formatDate value="${order_list.filming_time }" pattern="yyyy-MM-dd HH:mm" /></div>
			            </div>
			        </c:if>
        			</c:forEach>
        			</div>
    </div>


<!-- footer Start -->
    <footer>
        <section class="container-fluid footer_con">
            <div class="row footer">
                <div class="col-md-12 col-lg-4 footer-about">
                    <ul>
                        <li><a href="#" target="_blank">●關於我們</a></li>
                        <li><a href="#" target="_blank">●常見問題</a></li>
                        <li><a href="#" target="_blank">●隱私權條款</a></li>
                    </ul>
                </div>
                <div class="col-md-12 col-lg-4 footer-logo">
                    <div class="row justify-content-between">
                        <div class="col-12 Preview">
                            <img alt="Bootstrap Image Preview" src="<%=request.getContextPath()%>/img/logo-transparent(1450_400).png">
                        </div>
                        <div class="col-3 foo_img"><a href="#" target="_blank"><img alt="fb" src="<%=request.getContextPath()%>/img/img_for_footer/FB_8080.png"></a></div>
                        <div class="col-3 foo_img"><a href="#" target="_blank"><img alt="ig" src="<%=request.getContextPath()%>/img/img_for_footer/IG_8080.jpg"></a></div>
                        <div class="col-3 foo_img"><a href="#" target="_blank"><img alt="line" src="<%=request.getContextPath()%>/img/img_for_footer/LINE_logo_8080.png"></a></div>
                        <div class="col-3 foo_img"><a href=""><img alt="qrcode" src="<%=request.getContextPath()%>/img/img_for_footer/lineQR_8080.png"></a></div>
                    </div>
                </div>
                <div class="col-md-12 col-lg-4 contact-info">
                    <div>
                        ●聯絡我們
                    </div>
                    <div>
                        客服專線 0800-000-482
                    </div>
                    <div>
                        ●服務時間
                    </div>
                    <div>
                        週一至週六 09:00-18:00<br>
                        (週日與國定假日除外)
                    </div>
                </div>
            </div>
        </section>
    </footer><!-- footer End-->
    <div class="copyright">Copyright(C) WeddingNavi. All Rights Reserved.</div>
    
<!-- Modal 直接下訂 區塊-->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Order</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="formGroupExampleInput">會員編號</label>
                        <input type="text" readonly class="form-control form-control-plaintext" id="formGroupExampleInput" value="${membrevo.membre_id }" name="MEMBRE_ID">
                    </div>
                    <div class="form-group">
                        <label for="f_date1">選擇服務日期</label>
                        <input id="f_date1" name="FILMING_TIME">
                    </div>
                    <div class="form-group">
						    <label for="exampleFormControlSelect1">選擇服務地點</label>
						    <select class="form-control county" id="exampleFormControlSelect1" name="COUNTY">
						    <option value="-1" disabled selected hidden>--請選擇縣市--</option>						     						      
						    </select>
						    <select class="form-control district" id="exampleFormControlSelect2" name="DISTRICT">
						    <option value="-1" disabled selected hidden>--請選擇鄉鎮--</option>					      					      						      
						    </select>
						    <input type="text" class="form-control form-control-plaintext" id="formGroupExampleInput" placeholder="路/巷/號/樓" name="SERVICE_ADDR">
					</div>
                    <div class="form-group">
                        <label for="exampleFormControlTextarea1">訂單備註區</label>
                        <textarea class="form-control" id="exampleFormControlTextarea1" rows="3" name="ORDER_EXPLAIN"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
            	<input type="hidden" size="3" name="VENDER_ID" value="${WPCaseVO.vender_id }">
            	<input type="hidden" size="5" name="WED_PHOTO_CASE_NO" value="${WPCaseVO.wed_photo_case_no }">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary OrderSubmit" data-dismiss="modal">Save changes</button>
            </div>
        </div>
    </div>
</div>
<!-- Modal 直接下訂 區塊 End-->


    <script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.4.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
    <script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
</body>

<% 
  java.sql.Timestamp hiredate = new java.sql.Timestamp(System.currentTimeMillis());

%>

<script>
$(document).ready(function(){
	
	var url = (window.location.href).split('/')[0]+'/'+(window.location.href).split('/')[1]+'/'+(window.location.href).split('/')[2]+'/'+(window.location.href).split('/')[3]
	var membre_id = $("[name=membre_id]").val();
	var wed_photo_case_no = $("[name=wed_photo_case_no]").val();
	var vender_id = $("[name=VENDER_ID]").val();
	
  	
	
	//收藏
	$(".collect").click(function(){		
		if(membre_id == ''){
			alert('請先登入會員！');			
		}else{
		
			$.ajax({
				type: "POST",
				url: url + "/wed/wpcase.do",
				data: {
					 action:"add_Collect",
					 wed_photo_case_no:wed_photo_case_no,
					 membre_id:membre_id
				 },			
				success: function (data){
					alert(data);
				}
			})
		}
		
	})
	
	$(".OrderSubmit").click(function(){
		if(membre_id == ''){
			alert('請先登入會員')
			return;
		}
		if($('.county').val() == null){
			alert('請輸入服務地點哦!')
			return;
		}
		var filming_time = $("[name=FILMING_TIME]").val();
		var county = array[$('.county').val()][0];
		var district = array[$('.county').val()][$('.district').val()]
		var service_addr = $('[name=SERVICE_ADDR]').val()
		var	order_explain = county + district.substring(3) + service_addr +'　-　'+$("[name=ORDER_EXPLAIN]").val();
		$.ajax({
			type: "POST",
			url: url + "/wed/wpcase.do",
			data: {
				 action:"Generate_order",
				 TOOL:"ajax",
				 WED_PHOTO_CASE_NO: wed_photo_case_no,
				 VENDER_ID: vender_id,
				 MEMBRE_ID: membre_id,
				 FILMING_TIME:filming_time,
				 ORDER_EXPLAIN:order_explain
			 },			
			success: function (data){
				if(JSON.parse(data).note == "成功"){
					var str = JSON.parse(data).wed_photo_order_no;
					alert(str+' 訂單生成成功!');
					var jsonObj = {
							"type" : "order",
							"wed_photo_order_no" : JSON.parse(data).wed_photo_order_no ,
							"vender_id" : VENDER_ID
						};
					webSocket.send(JSON.stringify(jsonObj));
//	 				webSocket.send($("[name=VENDER_ID]").val());
				}
				if(JSON.parse(data).note =="失敗"){
					alert(JSON.parse(data).time);
				}
			}
		})
	})
	
	//圖片式分頁
	var text_new = document.querySelectorAll('.text_new');
	
    for (var i = 0; i < text_new.length; i++) {
        if (text_new[i].innerText.length > 32) {
            var str = text_new[i].innerText.substring(32);
            var str_new = text_new[i].innerText.replace(str, ' ...read more');
            text_new[i].innerText = str_new;
        }
    }
    
    var review = document.querySelectorAll('.review');
    var title_p = document.querySelector('.title_p p');
    title_p.innerText = review.length+'則評論';
    
    if(review.length === 0) {        
        var str = `<div class="col-12 review">
                <div class="review_img">
                    <img src="<%=request.getContextPath()%>/img/wp_img/c6.jpg" alt="">
                </div>
                <div class="review_text"> 
                    <span>_</span>                   
                    <div class="review_text_0">目前暫無評論 :D</div>
                </div>
                <div class="review_time">_</div>
            </div>`;
        $('.review_box p').after(str);
    }
    
    $.datetimepicker.setLocale('zh');
    $('#f_date1').datetimepicker({
       theme: '',              //theme: 'dark',
       timepicker:true,       //timepicker:true,
       step: 30,                //step: 60 (這是timepicker的預設間隔60分鐘)
       format:'Y-m-d H:i',         //format:'Y-m-d H:i:s',
	   value: '<%=hiredate%>', // value:   new Date(),
       //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
       //startDate:	            '2017/07/10',  // 起始日
       minDate:               '-1969-12-17' // 去除今日(不含)之前 從14天後開始選
       //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
    });
    
    var array = [
        ['台北市', '100中正區', '103大同區', '104中山區', '105松山區', '106大安區', '108萬華區', '110信義區', '111士林區', '112北投區', '114內湖區', '115南港區', '116文山區'],
        ['台北縣', '207萬里鄉', '208金山鄉', '220板橋市', '221汐止市', '222深坑鄉', '223石碇鄉', '224瑞芳鎮', '226平溪鄉', '227雙溪鄉', '228貢寮鄉', '231新店市', '232坪林鄉', '233烏來鄉', '234永和市', '235中和市', '236土城市', '237三峽鎮', '238樹林市', '239鶯歌鎮', '241三重市', '242新莊市', '243泰山鄉', '244林口鄉', '247蘆洲市', '248五股鄉', '248新莊市', '249八里鄉', '251淡水鎮', '252三芝鄉', '253石門鄉'],
        ['台中市', '400中區', '401東區', '402南區', '403西區', '404北區', '406北屯區', '407西屯區', '408南屯區'],
        ['台中縣', '411太平市', '412大里市', '413霧峰鄉', '414烏日鄉', '420豐原市', '421后里鄉', '422石岡鄉', '423東勢鎮', '424和平鄉', '426新社鄉', '427潭子鄉', '428大雅鄉', '429神岡鄉', '432大肚鄉', '433沙鹿鎮', '434龍井鄉', '435梧棲鎮', '436清水鎮', '437大甲鎮', '438外埔鄉', '439大安鄉'],
        ['台東縣', '950台東市', '951綠島鄉', '952蘭嶼鄉', '953延平鄉', '954卑南鄉', '955鹿野鄉', '956關山鎮', '957海端鄉', '958池上鄉', '959東河鄉', '961成功鎮', '962長濱鄉', '963太麻里鄉', '964金峰鄉', '965大武鄉', '966達仁鄉'],
        ['台南市', '700中西區', '701東區', '702南區', '704北區', '708安平區', '709安南區'],
        ['台南縣', '710永康市', '711歸仁鄉', '712新化鎮', '713左鎮鄉', '714玉井鄉', '715楠西鄉', '716南化鄉', '717仁德鄉', '718關廟鄉', '719龍崎鄉', '720官田鄉', '721麻豆鎮', '722佳里鎮', '723西港鄉', '724七股鄉', '725將軍鄉', '726學甲鎮', '727北門鄉', '730新營市', '731後壁鄉', '732白河鎮', '733東山鄉', '734六甲鄉', '735下營鄉', '736柳營鄉', '737鹽水鎮', '741善化鎮', '741新市鄉', '742大內鄉', '743山上鄉', '744新市鄉', '745安定鄉'],
        ['宜蘭縣', '260宜蘭市', '261頭城鎮', '262礁溪鄉', '263壯圍鄉', '264員山鄉', '265羅東鎮', '266三星鄉', '267大同鄉', '268五結鄉', '269冬山鄉', '270蘇澳鎮', '272南澳鄉', '290釣魚台'],
        ['花蓮縣', '970花蓮市', '971新城鄉', '972秀林鄉', '973吉安鄉', '974壽豐鄉', '975鳳林鎮', '976光復鄉', '977豐濱鄉', '978瑞穗鄉', '979萬榮鄉', '981玉里鎮', '982卓溪鄉', '983富里鄉'],
        ['金門縣', '890金沙鎮', '891金湖鎮', '892金寧鄉', '893金城鎮', '894烈嶼鄉', '896烏坵鄉'],
        ['南投縣', '540南投市', '541中寮鄉', '542草屯鎮', '544國姓鄉', '545埔里鎮', '546仁愛鄉', '551名間鄉', '552集集鎮', '553水里鄉', '555魚池鄉', '556信義鄉', '557竹山鎮', '558鹿谷鄉'],
        ['南海島', '817東沙群島', '819南沙群島'],
        ['屏東縣', '900屏東市', '901三地門鄉', '902霧台鄉', '903瑪家鄉', '904九如鄉', '905里港鄉', '906高樹鄉', '907鹽埔鄉', '908長治鄉', '909麟洛鄉', '911竹田鄉', '912內埔鄉', '913萬丹鄉', '920潮州鎮', '921泰武鄉', '922來義鄉', '923萬巒鄉', '924崁頂鄉', '925新埤鄉', '926南州鄉', '927林邊鄉', '928東港鎮', '929琉球鄉', '931佳冬鄉', '932新園鄉', '940枋寮鄉', '941枋山鄉', '942春日鄉', '943獅子鄉', '944車城鄉', '945牡丹鄉', '946恆春鎮', '947滿州鄉'],
        ['苗栗縣', '350竹南鎮', '351頭份鎮', '352三灣鄉', '353南庄鄉', '354獅潭鄉', '356後龍鎮', '357通霄鎮', '358苑裡鎮', '360苗栗市', '361造橋鄉', '362頭屋鄉', '363公館鄉', '364大湖鄉', '365泰安鄉', '366銅鑼鄉', '367三義鄉', '368西湖鄉', '369卓蘭鎮'],
        ['桃園縣', '320中壢市', '324平鎮市', '325龍潭鄉', '326楊梅鎮', '327新屋鄉', '328觀音鄉', '330桃園市', '333龜山鄉', '334八德市', '335大溪鎮', '336復興鄉', '337大園鄉', '338蘆竹鄉'],
        ['高雄市', '800新興區', '801前金區', '802苓雅區', '803鹽埕區', '804鼓山區', '805旗津區', '806前鎮區', '807三民區', '811楠梓區', '812小港區', '813左營區', '817東沙群島', '819南沙群島'],
        ['高雄縣', '814仁武鄉', '815大社鄉', '820岡山鎮', '821路竹鄉', '822阿蓮鄉', '823田寮鄉', '824燕巢鄉', '825橋頭鄉', '826梓官鄉', '827彌陀鄉', '828永安鄉', '829湖內鄉', '830鳳山市', '831大寮鄉', '832林園鄉', '833鳥松鄉', '840大樹鄉', '842旗山鎮', '843美濃鎮', '844六龜鄉', '845內門鄉', '846杉林鄉', '847甲仙鄉', '848桃源鄉', '849那瑪夏鄉', '851茂林鄉', '852茄萣鄉'],
        ['基隆市', '200仁愛區', '201信義區', '202中正區', '203中山區', '204安樂區', '205暖暖區', '206七堵區'],
        ['連江縣', '209南竿鄉', '210北竿鄉', '211莒光鄉', '212東引鄉'],
        ['釣魚台', '290釣魚台'],
        ['雲林縣', '630斗南鎮', '631大埤鄉', '632虎尾鎮', '633土庫鎮', '634褒忠鄉', '635東勢鄉', '636台西鄉', '637崙背鄉', '638麥寮鄉', '640斗六市', '643林內鄉', '646古坑鄉', '647莿桐鄉', '648西螺鎮', '649二崙鄉', '651北港鎮', '652水林鄉', '653口湖鄉', '654四湖鄉', '655元長鄉'],
        ['新竹市', '300北區', '300東區', '300香山區'],
        ['新竹縣', '300寶山鄉', '302竹北市', '303湖口鄉', '304新豐鄉', '305新埔鎮', '306關西鎮', '307芎林鄉', '308寶山鄉', '310竹東鎮', '311五峰鄉', '312橫山鄉', '313尖石鄉', '314北埔鄉', '315峨眉鄉'],
        ['嘉義市', '600西區', '600東區'],
        ['嘉義縣', '602番路鄉', '603梅山鄉', '604竹崎鄉', '605阿里山鄉', '606中埔鄉', '607大埔鄉', '608水上鄉', '611鹿草鄉', '612太保市', '613朴子市', '614東石鄉', '615六腳鄉', '616新港鄉', '621民雄鄉', '622大林鎮', '623溪口鄉', '624義竹鄉', '625布袋鎮'],
        ['彰化縣', '500彰化市', '502芬園鄉', '503花壇鄉', '504秀水鄉', '505鹿港鎮', '506福興鄉', '507線西鄉', '508和美鎮', '509伸港鄉', '510員林鎮', '511社頭鄉', '512永靖鄉', '513埔心鄉', '514溪湖鎮', '515大村鄉', '516埔鹽鄉', '520田中鎮', '521北斗鎮', '522田尾鄉', '523埤頭鄉', '524溪州鄉', '525竹塘鄉', '526二林鎮', '527大城鄉', '528芳苑鄉', '530二水鄉'],
        ['澎湖縣', '880馬公市', '881西嶼鄉', '882望安鄉', '883七美鄉', '884白沙鄉', '885湖西鄉']
      ]
	var str = '';
	for(var i =0 ;i<array.length;i++){
		str += '<option value="'+i+'">'+array[i][0]+'</option>';
	}
	$('.county').append(str);
	
	$('.county').change(function(){
		$('.district').empty();
		var str ='';
		var county = $(this).val();
		
		for(var i=1;i<array[county].length;i++){
			str += '<option value="'+i+'">'+array[county][i]+'</option>';
		}
		$('.district').append(str);
		
	})
    
  //WS
	var MyPoint = "/WpCaseWS/${membrevo.membre_id}";
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
	var webSocket;
	
	function connect() {
		webSocket = new WebSocket(endPointURL);
		webSocket.onopen = function(event) {
			console.log("Connect Success! - casePage");
		};
	}
	connect();
	
})
</script>
</html>