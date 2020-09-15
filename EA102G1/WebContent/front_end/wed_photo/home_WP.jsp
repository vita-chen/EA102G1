<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashSet"%>
<%@page import="com.vender.model.VenderVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.vender.model.VenderJDBCDAO"%>
<%@page import="com.wpcase.model.WPCaseService"%>
<%@page import="com.wpcase.model.WPCaseVO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="com.wpcase.model.WPCaseDAO"%>
<%@page import="com.membre.model.MembreVO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%

	MembreVO membrevo = (MembreVO)session.getAttribute("membrevo");
	String url = request.getContextPath() +"/front_end/wed_photo/wp_home.jsp";
	session.setAttribute("location",url); 
	
	//最新方案
	WPCaseDAO wpcasedao = new WPCaseDAO();
	List<WPCaseVO> new_list = wpcasedao.getAll().stream()
			.filter(v -> v.getWed_photo_status() == 0)
			.sorted(Comparator.comparing(WPCaseVO::getWed_photo_addtime).reversed())
			.collect(Collectors.toList())
			.subList(0,3);
	
	pageContext.setAttribute("new_list",new_list);
	
	//廠商
	WPCaseService WPSvc = new WPCaseService();
	List<VenderVO> list = WPSvc.list_vender();	
	pageContext.setAttribute("list_vender",list);

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>婚禮攝影 Wed Photo 服務首頁 - Wedding Navi</title>
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
            if($(window).scrollTop() > 1050){
                $('.popular_vendors').animate({
                    height:'660px'
                },950);
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
    background-image: url('<%=request.getContextPath() %>/img/wp_img/wpbanner02.jpg');
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

.search {
    /*height: 300px;*/
    /*border: 1px #383838FF solid;*/
}

/*最新方案 開始*/
.new_case_container p {
    font-size: 22px;
    margin-top: 15px;
}

.new_case_container p span {
    font-size: 14px;
    margin-top: 15px;
    padding: 1px 8px 1px 8px;
    float: right;
    display: block;
    color: #FFF;
    font-weight: 300;
    background-color: #FF0000D0;
}

.new_case_container p span:hover {
    box-shadow: #BABABAFF 3px 3px 3px;
    background-color: #FF4747FF;
    color: black;
    font-weight: 400;
}

.new_case_container {
    /*border: 1px #383838FF solid;*/
    padding-bottom: 20px;
}

.img_box {
    display: inline-block;
    margin-top: 15px;
    margin-bottom: 15px;
    border-radius: 5px;
    box-shadow: #EBD0D0FF 3px 3px 3px 3px;
    height: 420px;
    color: #4F4F4FFF;
}
@media (max-width: 768px) {
    .img_box {
        height: 530px;
    }
}
.img_box:hover {
    box-shadow: #E0E0E0FF 3px 3px 3px 3px;
}

.img_box img {
    height: auto;
    width: 99.8%;
    border-radius: 5px;
}

.img_text h5 {
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
    margin-left: 25px;
    margin-right: 5px;
    text-align: justify;
    height: 100px;
    letter-spacing: 1px;
    line-height: 25px;
}

.img_text:hover {
    opacity: 0.5;
}

.img_box img:hover {
    opacity: 0.9;
}

.new_case_icon {
    position: absolute;
    top: 15px;
    padding: 3px 10px 3px 10px;
    background-color: #FF0000BD;
    border-radius: 5px;
    color: #FFFFFFFF;
    z-index: 1;
}

/*最新方案 結束*/

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

/*熱門廠商 開始*/
.ven_container {
    height: 665px;
    background-image: url('<%=request.getContextPath() %>/img/wp_img/wpbanner03.jpg');
    background-attachment: fixed;
    background-repeat: no-repeat;
    background-size: 100%;
    background-position: 55% 35%;
    filter: saturate(1.4);
    filter: contrast(1.3);
    opacity: 0.78;
}

.col-4 {
    /*border: 1px #FFB1FFFF solid;*/
}

.ven_p {
    height: 30px;
}

.ven_p p {
    font-size: 22px;
    margin-top: 140px;
    color: #FFF;
    text-shadow: black 0.1em 0.1em 0.2em;
}

.ven_p p span {
    float: right;
    margin-top: 10px;
    font-size: 14px;
    font-weight: 300;
    display: block;
    color: black;
    background-color: #FFF;
    padding: 1px 8px 1px 8px;
    text-shadow: 0 0 0;

}

.ven_p p span:hover {
    background-color: transparent;
    color: #FFF;
    font-weight: 400;
    text-shadow: #FFFFFF3A 0.1em 0.1em 1em;
    border: 1px #FFF solid;
}

.popular_vendors {
    height: 1100px;
}

.ven_box {
    text-align: center;
    color: black;
    border: 0.1px #FFF solid;
    border-radius: 10px;
    padding: 3px 5px 3px 5px;
    /*height: 280px;*/
    opacity: 1;
    z-index: 6;
    background-color: #FFFFFF58;
    text-shadow: #fff 0.1em 0.1em 0.2em;
}
.ven_box:hover{
    border:5px #FFF solid;
    margin:-5px;
    padding: 0px;
    box-shadow: black 0.1em 0.1em 0.2em;
}
.img_text3{
    margin-bottom: 10px;
}

.ven_icon img {
    width: 120px;
    height: 120px;
    border-radius: 95px;
    display: block;
    margin: 8px auto 8px;
    border: 0.5px #FFF solid;
    opacity: 1;
}
span svg {
    font-size: 20px;
    color: #F8E506FF;

}                     
/*熱門廠商 結束*/
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
            <li class="breadcrumb-item active" aria-current="page">婚禮攝影</li>
        </ol>
    </nav>
     <div class="container new_case_container">
        <p>最新方案 New case <a href="<%=request.getContextPath()%>/front_end/wed_photo/find_WPCase.jsp"><span>看更多</span></a></p>
        <div class="row justify-content-between new_case">
        
        <c:forEach var="new_list" items="${new_list }">
            <div class="col-lg-4 col-md-6 col-sm-12">
                <a href="<%=request.getContextPath()%>/wed/wpcase.do?action=getOne_CasePage&wed_photo_case_no=${new_list.wed_photo_case_no}" target="_blank">
                    <div class="img_box">
                        <div class="new_case_icon">New</div>
                        <img src="<%=request.getContextPath()%>/wed/wpcase.do?action=getOne_WPImg&wed_photo_case_no=${new_list.wed_photo_case_no}">
                        <div class="img_text">   
                            <h5>${new_list.wed_photo_name }</h5>
                            <div class="text_new">
								${new_list.wed_photo_intro }
                            </div>
                            <div class="text_time">
                                <span style="font-style:oblique;"><fmt:formatDate value="${new_list.wed_photo_addtime }" pattern="MMM d, yyyy hh:mm aa" /></span>
                            </div>
                        </div>
                    </div>
                </a>
            </div>
         </c:forEach> 
         </div>
    </div>
        <div class="container-fluid search">
        <!-- 放搜尋篩選 -->
    </div>
    <div class="container-fluid ven_container">
        <div class="container xx">            
            <div class="row popular_vendors">
                <div class="col-12 ven_p"><p>熱門廠商 Popular vendors<a href="<%=request.getContextPath()%>/front_end/wed_photo/find_WPVender.jsp"><span>看更多</span></a></p></div>
                <jsp:useBean id ="service" class="com.wpcase.model.WPCaseService"/>
                <c:forEach var="vender" items="${list_vender }" end="2">
                <div class="col-lg-4 col-md-6 col-sm-12">
                    <a href="<%=request.getContextPath() %>/wed/wpcase.do?action=goVenderPage&vender_id=${vender.vender_id}" target="_blank">
                        <div class="ven_box">
                            <div class="ven_icon"><img src="<%=request.getContextPath() %>/vender/vender.do?action=getphot&vender_id=${vender.vender_id}" alt=""></div>
                            <div class="ven_text">
                                <h5>${vender.ven_name }<span>
                                                            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-star-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                                                <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z" />
                                                            </svg>${service.ven_star(vender.vender_id) }</span></h5>
                                <div class="img_text3">
                                  <span class="review">${vender.ven_review_count }則評價</span>
                                    <span class="case">${service.countCase(vender.vender_id)}筆方案</span>
                                    <span class="addr">${vender.ven_addr }</span>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
                </c:forEach>
            </div>
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

    var text_new = document.querySelectorAll('.text_new');
    for (var i = 0; i < text_new.length; i++) {
        if (text_new[i].innerText.length > 32) {
            var str = text_new[i].innerText.substring(32);
            var str_new = text_new[i].innerText.replace(str, '...read more');
            text_new[i].innerText = str_new;
        }
    }
    
    var addr = document.querySelectorAll('.addr');
    for (var i = 0; i < addr.length; i++) {
        var str = addr[i].innerText.substring(0, 3) + ' ' + addr[i].innerText.substring(3, (addr[i].innerText.indexOf('區') + 1 || addr[i].innerText.indexOf('鄉') + 1));
        addr[i].innerHTML = str;
    }
});
</script>
</html>