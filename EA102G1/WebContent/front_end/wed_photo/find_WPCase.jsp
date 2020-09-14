<%@page import="com.membre.model.MembreVO"%>
<%@page import="com.wpcase.model.WPCaseService"%>
<%@page import="java.util.List"%>
<%@page import="com.wpcase.model.WPCaseVO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 	MembreVO membrevo = (MembreVO)session.getAttribute("membrevo");
	String url = request.getContextPath() +"/front_end/wed_photo/wp_home.jsp";
	session.setAttribute("location",url); 

	List<WPCaseVO> list = (List<WPCaseVO>)request.getAttribute("casename_list");
	
	if(list == null){
		
		WPCaseService WPSvc = new WPCaseService();
		list = WPSvc.getAll();
		pageContext.setAttribute("list",list);
		
	}else{
			
		pageContext.setAttribute("list",list);
		
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>查方案</title>
    <script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+TC:wght@300;500&display=swap" rel="stylesheet">
    <!--  <link href="https://fonts.googleapis.com/css2?family=Caveat&display=swap" rel="stylesheet"> -->
    <link href="https://fonts.googleapis.com/css2?family=Reenie+Beanie&display=swap" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/bootstrap_menu.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/menu_style.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/icofont/icofont.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/boxicons/css/boxicons.min.css" rel="stylesheet">
    <script type="text/javascript">
    $(document).ready(function() {
        $('.banner_text').animate({
            opacity: '0.95',
            top: '150px' //移到距離上面150px的位置
        }, 1000);

        $(".service").hide();
        $(window).scroll(function() {
            // last=$("body").height()-$(window).height()
            // 滑動顯示回頂端的圖示
            if ($(window).scrollTop() >= 200) {
                $(".service").fadeIn(500);
            } else {
                $(".service").fadeOut(500);
            }
            if ($(window).scrollTop() > 1050) {
                $('.popular_vendors').animate({
                    height: '660px'
                }, 950);
            }
        });
        $(".service").click(function() {
            $("html,body").animate({ scrollTop: 0 }, "slow");
            // 返回到最頂上
            // return false;
        });
    });
    </script>
</head>
<style type="text/css">
body {
    margin: 0px;
}

.service {
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

.banner {
    height: 800px;
    background-image: url('<%=request.getContextPath()%>/img/wp_img/wpbanner02.jpg');
    background-repeat: no-repeat;
    background-position: 50% 50%;
    background-size: 100%;
    filter: saturate(1);
    filter: contrast(1.2);
    opacity: 0.9;
}

.banner_text {
    font-size: 62px;
    text-shadow: #FFCDEDBF 0.1em 0.1em 0.2em;
    position: absolute;
    top: 37%;
    left: 9%;
    opacity: 0;
    /*font-family: 'Caveat', cursive;*/
    font-family: 'Reenie Beanie', cursive;
    font-weight: 500;

}

.breadcrumb {
    background-color: #FFECFFFF;
    border-radius: 0px;
    padding: 3px 15px 3px 15px;
    height: 35px;
    margin: 0;
}

/*title start*/
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
    font-size: 12px;
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

.logo img {
    width: 180px;
    height: auto;
}

.logo {
    position: fixed;
    top: 4px;
    left: 10px;
    z-index: 5;
    width: 180px;
    height: 63px;
}

.title2 {
    display: inline;
    width: 100%;
    text-align: right;
    position: fixed;
    top: 12px;
    z-index: 2;
}

.title2 ol {
    list-style-type: none;
    margin-top: 17.5px;
    height: 36px;
    background: -webkit-linear-gradient(#FFFFFFE0, #FFFFFF00);
    background: -o-linear-gradient(#FFFFFFE0, #FFFFFF00);
    background: -moz-linear-gradient(#FFFFFFE0, #FFFFFF00);
    background: linear-gradient(#FFFFFFE0, #FFFFFF00);
}

.title2 ol li {
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

/*title end*/

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


/*最新方案 開始*/
.img_box {
    display: inline-block;
    margin-top: 15px;
    margin-bottom: 15px;
    color: #4F4F4FFF;
	border: 1px solid #CECECEFF;
    border-radius: 3px;
}

.img_box h5 {
    margin-left: 5px;
    margin-top: 20px;
    margin-bottom: 10px;
}

.img_text1 {
    margin-left: 5px;
}

.img_text1 span {
    float: right;
    font-size: 20px;
    color: #FF1E7FFF;
    margin-right: 5px;
}

.img_text2 {
    margin-left: 25px;
    margin-right: 5px;
    margin-top: 20px;
    text-align: justify;
    height: 90px;
    letter-spacing: 1px;
    line-height: 25px;
}

.img_text3 {
    display: block;
    margin-bottom: 0px;
    height: 20px;
    border-bottom: 5px solid #FFECFFFF;
}

.img_box img {
    height: auto;
    width: 99.8%;
}

.text_time {
    margin-right: 5px;
    text-align: right;
    font-size: 14px;
    background: -webkit-linear-gradient(#FFECFFFF, #FFFFFF00);
    background: -o-linear-gradient(#FFECFFFF, #FFFFFF00);
    background: -moz-linear-gradient(#FFECFFFF, #FFFFFF00);
    background: linear-gradient(#FFECFFFF, #FFFFFF00);
}

.img_box img:hover {
    opacity: 0.9;
}

/*最新方案 結束*/

/*搜尋區塊 開始*/
.condition_area h7 {
    display: block;
    margin-top: 8px;
    margin-bottom: 8px;    
}

.btn-warning {
    margin-left: 15px;
    margin-right: 10px;
    padding-left: 15px;
    padding-right: 15px;
    height: 8%;
}

h7 label {
    margin-bottom: 0;
}

.form_text, .form_text2{
    margin-left: 15px;
    margin-right: 5px;
    margin-bottom: 8px;
    width: 67%;
}
.form_text2 input {
    display: inline-block;
    width: 47.2%;
}

.form_text2 input:nth-child(2) {
    float: right;
}

.Pagination {
    margin-top: 30px;
    margin-bottom: 30px;
    height: 50px;    
}

.content {
    margin-top: 40px;
}

.condition {
    margin-top: 40px;
}

@media (max-width: 767px) {
    .condition {
        height: 240px;
    }
}

/*搜尋區塊 結束/
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
                <a href="<%=request.getContextPath()%>/front_end/membre/login.jsp" id="link1">　會員登入</a>
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
        <div class="logo"><a href="<%=request.getContextPath()%>/front_end/home/home.jsp"><img alt="Bootstrap Image Preview" src="<%=request.getContextPath()%>/img/logo-transparent(1450_400).png"></a></div>
    </div>
    <div class="service">TOP</div>
    <div class="banner">
        <div class="banner_text">Come to a happy wedding!</div>
    </div>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/front_end/home/home.jsp">婚禮導航</a></li>
            <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/front_end/wed_photo/home_WP.jsp">婚禮攝影</a></li>
            <li class="breadcrumb-item active" aria-current="page"><a href="<%=request.getContextPath()%>/front_end/wed_photo/find_WPCase.jsp">查看方案</a></li>
        </ol>
    </nav>
        <div class="container-fluid search">
        <div class="row">
            <div class="col-1 search_A"></div>
            <div class="col-10 search_B">
                <div class="row">
                    <div class="col-12 condition">                        
                        <div class="row">
                            <div class="col-lg-3 col-sm-12 condition_area">
                                <h7><label for="inquireCase">搜尋方案</label></h7>
                                <form METHOD="post" ACTION="<%=request.getContextPath()%>/wed/wpcase.do">
                                    <div class="row">
                                        <div class="form_text">
                                            <input class="form-control" type="text" name="search_case" 
                                            value="全天" id="inquireCase">
                                        </div>
                                            <button type="submit" class="btn btn-warning">送出</button>
                                            <input type="hidden" name="action" value="inquireCase">
                                    </div>
                                </form>
                            </div>
                            <div class="col-lg-3 col-sm-12 condition_area">
                               <h7><label for="inquireCasebyPrice">價格篩選</label></h7>
                                <form METHOD="post" ACTION="<%=request.getContextPath()%>/wed/wpcase.do">
                                    <div class="row">
                                        <div class="form_text2">
                                            <input type="text" class="form-control" name="min" 
                                            value="18000" id="inquireCasebyPrice">-
                                            <input type="text" class="form-control" name="max" value="32000">
                                        </div>
                                        <button type="submit" class="btn btn-warning">送出</button>
                                        <input type="hidden" name="action" value="inquireCasebyPrice">
                                    </div>
                                </form>
                            </div>
                            <div class="col-lg-3 col-sm-12 condition_area">
                                <h7><label for="inquireCasebyAddr">地區篩選</label></h7>
                                <form METHOD="post" ACTION="<%=request.getContextPath()%>/wed/wpcase.do">
                                    <div class="row">
                                        <div class="form_text">
                                            <input class="form-control" type="text" name="search_case_addr" 
                                            value="新北" id="inquireCasebyAddr">
                                        </div>
                                            <button type="submit" class="btn btn-warning">送出</button>
                                            <input type="hidden" name="action" value="inquireCasebyAddr">
                                    </div>
                                </form>
                            </div>
                            <div class="col-lg-3 col-sm-12 condition_area"><h7></h7></div>
                        </div>
                    </div>
                    <div class="col-12 content">
                        <div class="row">
                            <div class="col-12">
                            </div>
                            <div class="col-12">
                                <div class="row content_row">
                                <c:forEach var="WPCaseVO" varStatus="count" items="${list}">
									<c:if test="${WPCaseVO.wed_photo_status == 0}">
                                    <div class="col-lg-4 col-md-6 col-sm-12">
                                        <a href="<%=request.getContextPath()%>/wed/wpcase.do?action=getOne_CasePage&wed_photo_case_no=${WPCaseVO.wed_photo_case_no}" target="_blank">
                                            <div class="img_box">
                                                <h5>${WPCaseVO.wed_photo_name}</h5>
                                                <div class="img_text1">${WPCaseVO.wed_photo_case_no} <span><fmt:formatNumber value="${WPCaseVO.wed_photo_price}" pattern="NT$ #,###"/></span></div>
                                                <img src="<%=request.getContextPath()%>/wed/wpcase.do?action=getOne_WPImg&wed_photo_case_no=${WPCaseVO.wed_photo_case_no}">
                                                <div class="text_time">
                                                    <span style="font-style:oblique;"><fmt:formatDate value="${WPCaseVO.wed_photo_addtime }" pattern="MMM d, yyyy hh:mm aa" /></span>
                                                </div>
                                                <div class="img_text2">
													${WPCaseVO.wed_photo_intro }
                                                </div>
                                                <div class="img_text3"></div>
                                            </div>
                                        </a>
                                    </div>
                                    </c:if></c:forEach>
                                    <div class="col-12 Pagination">
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
                    </div>
                </div>
            </div>
            <div class="col-1 search_A"></div>
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
    <script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<%--     <script src="<%=request.getContextPath()%>/js/login.js"></script> --%>
<%--     <script src="<%=request.getContextPath()%>/js/jquery.easing.min.js"></script> --%>
    <script src="<%=request.getContextPath()%>/js/scripts.js"></script>
</body>
<script type="text/javascript">
$(document).ready(function() {
    $("#title").each(function() {
        $("tr").click(function() {
            $(this).next("#content").toggle();
        });
    });

    var img_text2 = document.querySelectorAll('.img_text2');
    for (var i = 0; i < img_text2.length; i++) {
        if (img_text2[i].innerText.length > 52) {
            var str = img_text2[i].innerText.substring(52);
            var str_new = img_text2[i].innerText.replace(str, '...read more');
            img_text2[i].innerText = str_new;
        }
    }

    var content_row = document.getElementsByClassName("content_row");

    var col_row = document.querySelectorAll(".content_row .col-lg-4"); //抓取所有內容格
    var num = col_row.length; //區域內所有格數 
    var ary = [];

    for (var i = 0; i < num.length; i++) {
        ary.push(num[i]);
    }

    /*-----設定每頁顯示行數------*/
    var pageSize = 9; 		//*
    /*-------------------------*/

    var totalPage = Math.ceil(num / pageSize); //算出總共有幾頁
    var str = '';
    for (var i = 0; i < totalPage; i++) { //產生對應按鈕數
        str += '<li class="page-item" value="'+(i+1)+'"><a class="page-link">'+(i+1)+'</a></li>';
    }
    $(".Next").before(str);

    var btn = document.querySelectorAll('.page-item'); //每個按鈕註冊除了第一個跟最後一個
    for (var i = 1; i < btn.length - 1; i++) {
        btn[i].addEventListener('click', goPage.bind(this, i));
    }

    var current_page = 0;

    var previous = document.querySelector('.Previous'); //第一頁按鈕 個別註冊
    previous.addEventListener('click', goPage.bind(this, 1));
    var next = document.querySelector('.Next'); //最末頁按鈕 個別註冊
    next.addEventListener('click', goPage.bind(this, totalPage));


    function goPage(page) { //切換頁數函式 page > 當前頁數
        // console.log('有進來嗎? 當前頁數 ' + page);

        current_page = page;
        console.log('第'+current_page+'頁');

        
        $('.page-item').each(function() {
            if ($(this).val() == page) {
                $(this).css("color", "#D8D8D8FF");
            } else {
                $(this).css("color", "#000000FF");
            }
        })
        var startRow = (page - 1) * pageSize + 1; //開始顯示的行
        var endRow = page * pageSize; //結束顯示的行
        endRow = (endRow > num) ? num : endRow; //num > 表格所有行數

        //遍歷顯示資料實現分頁
        for (var i = 1; i < (num + 1); i++) {
            var trrow = col_row[i - 1];
            if (i >= startRow && i <= endRow) {
                trrow.style.display = "block";
            } else {
                trrow.style.display = "none";
            }
        }
    }

    goPage(1);
});
</script>
</html>