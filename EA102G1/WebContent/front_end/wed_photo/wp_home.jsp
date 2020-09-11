<%@page import="com.wpcase.model.WPCaseVO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="com.wpcase.model.WPCaseDAO"%>
<%@page import="com.membre.model.MembreVO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	MembreVO membrevo = (MembreVO)session.getAttribute("membrevo");
	String url = request.getContextPath() +"/front_end/wed_photo/wp_home.jsp";
	session.setAttribute("location",url); 
	
	//最新方案
	WPCaseDAO wpcasedao = new WPCaseDAO();
	List<WPCaseVO> new_list = wpcasedao.getAll().stream()
			.sorted(Comparator.comparing(WPCaseVO::getWed_photo_addtime).reversed())
			.collect(Collectors.toList())
			.subList(0,3);
	
	pageContext.setAttribute("new_list",new_list);

%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>婚禮攝影 Wed Photo 服務首頁 - Wedding Navi</title>
    <script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+TC:wght@300;500&display=swap" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/bootstrap_menu.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/menu_style.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/icofont/icofont.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/boxicons/css/boxicons.min.css" rel="stylesheet">
    <script type="text/javascript">
    $(document).ready(function() {
        $("#service").hide();
        $("#header").hide();
        $(window).scroll(function() {
            // last=$("body").height()-$(window).height()
            // 滑動顯示回頂端的圖示
            if ($(window).scrollTop() >= 200) {
                $("#service").fadeIn(500);
            } else {
                $("#service").fadeOut(500);
            }
            if ($(window).scrollTop() > 300) {
                $("#header").show();
            } else {
                $("#header").hide();
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

<style type="text/css">
#service {
    z-index: 1;
}

#footer {
    border-radius: 20px;
    border: 3px dotted #FFD7FFFF;
}

body {
    margin: 0px;
}

#footer {
    clear: both;
}

#imgsize {
    width: 200px;
    height: 220px;
}

#bannerimg {
    width: 100%;
    clear: both;
    margin-top: 0px;
}

#banner {
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
}
#header #title1 {
    width: 100%;
    height: 30px;
    font-size: 10px;
    font-weight: 400;
    background-color: #FFECFFFF;
    font-family: "微軟正黑體";
    border-bottom: solid 1px #FFEEFFFF;
}

#title1 {
    display: inline-block;
    position: fixed;
    top: 0px;
    z-index: 1;
}

#title1 h4 {
    float: left;
    font-size: 12px;
    margin-top: 8px;
    font-weight: 500;
}

#title1 p {
    display: inline;
    float: right;
    margin: 6px;
    font-size: 12px;
}

#header #title_ol {
    margin-top: 18px;
    width: 100%;
    height: 36px;
    display: inline;
    list-style-type: none;
    background-color: #FFFFFFC4;
    position: fixed;
    top: 10px;
    z-index: 2;
    padding-left: 60%;
}

#header #title_ol li {
    margin-left: 5px;
    width: 80px;
    text-align: center;
    display: inline-block;
    letter-spacing: 0.5px;
    margin-right: 0px;
}

#header a {
    text-decoration: none;
}

#title_ol li a {
    line-height: 35px;
    display: block;
    text-decoration: none;
}

#title_ol li:hover {
    color: #000000FF;
    border-bottom: #FFBDFFFF 1.5px solid;
}

a:visited {
    color: #000000FF;
}

a {
    color: #636363FF;
}

.img_bar {
    padding-right: 6px;
    padding-left: 6px;
    display: inline-block;
    height: 300px;
    background-image: url("<%=request.getContextPath()%>/img/wp_img/rose.png");
    background-repeat: no-repeat;
    background-position: center;
    float: left;
}

.img_bar:hover {
    opacity: 0.87;
    transform: scale(1.01, 1.01);
}

.img_bar6 {
    background-image: url("<%=request.getContextPath()%>/img/wp_img/v0102.jpg");
    background-position: 40% 20%;
    background-size: 600px 440px;
}

.img_bar3 {
    background-image: url("<%=request.getContextPath()%>/img/wp_img/v0206.jpg");
    background-position: 50% 50%;
    background-size: 650px 380px;
}

.img_bar4 {
    background-image: url("<%=request.getContextPath()%>/img/wp_img/v0104.jpg");
    background-position: 50% 50%;
    background-size: 460px 300px;
}

.img_bar1 {
    background-image: url("<%=request.getContextPath()%>/img/wp_img/v0308.jpg");
    background-position: 60% 50%;
    background-size: 560px 370px;
}

.img_bar5 {
    background-image: url("<%=request.getContextPath()%>/img/wp_img/v0307.jpg");
    background-position: 50% 50%;
    background-size: 350px 100%;
}

.img_bar2 {
    background-image: url("<%=request.getContextPath()%>/img/wp_img/v0303.jpg");
    background-position: 50% 50%;
    background-size: 535px 350px;
}

.img_bar9 {
    background-image: url("<%=request.getContextPath()%>/img/wp_img/v0302.jpg");
    background-position: 50% 50%;
    background-size: 360px 300px;
}

.img_bar8 {
    background-image: url("<%=request.getContextPath()%>/img/wp_img/v0305.jpg");
    background-position: 50% 40%;
    background-size: 95% 420px;
}

.img_bar7 {
    background-image: url("<%=request.getContextPath()%>/img/wp_img/v0208.jpg");
    background-position: 50% 50%;
    background-size: 450px 300px;
}

.img_bigbar {
    padding-right: 3px;
    padding-left: 3px;
    margin-top: 10px;
}

.img_box {
    display: inline-block;
    border-radius: 10px;
    margin-top: 15px;
    margin-bottom: 15px;
    height: 420px;
    border: 1px dotted #F90;
}

.img_box img {
    height: 230px;
    width: 348px;
    border-radius: 10px;
}

.img_text h5 {
    margin-top: 20px;
}

.img_text {
    margin-left: 10px;
}

.fluid {
    border: dotted #A0A0D0FF 1px;
    height: 600px;
    padding:0px;
    margin: 20px 0 20px 0;
}

.popular_vendors {
    border: 1px dotted #62E970FF;
    height: 460px;
}
</style>
<!-- <a href="https://pngtree.com/free-backgrounds">free background photos from pngtree.com</a> -->

<body>
    <div id="header" class="">
        <div id="title1">
            <h4>歡迎光臨!_平台公告 <a href="<%=request.getContextPath()%>/front_end/home/home.jsp">回首頁</a></h4>
            <p>
            	<c:if test="${membrevo.mem_name != null}">
            	你好 ${membrevo.mem_name }
            	<a href="<%=request.getContextPath()%>/front_end/membre/wp/listAllCollectWP.jsp" id="link1">　我的收藏</a>
            	<a href="<%=request.getContextPath()%>/front_end/membre/wp/listAllWPOrder.jsp" id="link1">　訂單查詢</a>
            	</c:if>
            	<c:if test="${membrevo.mem_name == null}">
                <a href="<%=request.getContextPath()%>/front_end/membre/login.jsp" id="link1">　會員登入</a>
                <a href="" id="link1">　我要註冊</a>
                <a href="" id="link1">　廠商專區</a>
                </c:if>
            </p>
        </div>
        <ol id="title_ol">
            <li><a href="<%=request.getContextPath()%>/front_end/wed_photo/wp_home.jsp">婚禮攝影</a></li>
            <li><a href="">禮車租借</a></li>
            <li><a href="">婚紗租借</a></li>
            <li><a href="">二手拍賣</a></li>
            <li><a href="">討論區</a></li>
            <li><a href="">實用工具</a></li>
            <li><a href="">搜尋區塊</a></li>
        </ol>
    </div>
    <div id="service">TOP</div>
    <div class="" id="banner"><img src="<%=request.getContextPath()%>/img/wp_img/banner.jpg" id="bannerimg"></div>
    <div class="container">
        <p>最新方案 New case</p>
        <div class="row justify-content-between new_case">
        	<c:forEach var="new_list" items="${new_list }">
	            <div class="col-4 img_box">
	                <img src="<%=request.getContextPath()%>/wed/wpcase.do?action=getOne_WPImg&wed_photo_case_no=${new_list.wed_photo_case_no}">
	                <div class="img_text">
	                	<a href="<%=request.getContextPath()%>/wed/wpcase.do?action=getOne_CasePage&wed_photo_case_no=${new_list.wed_photo_case_no}" target="_blank">
	                    <h5>${new_list.wed_photo_name }</h5></a><br>
	                    read more...${new_list.wed_photo_price }
	                </div>
	            </div>
			</c:forEach>
        </div>
        <p>照片畫廊 Photo gallery</p>
        <div class="row photo_gallery">
            <div class="col-12 img_bigbar">
                <div class="col-3 img_bar img_bar1"></div>
                <div class="col-6 img_bar img_bar2"></div>
                <div class="col-3 img_bar img_bar3"></div>
            </div>
            <div class="col-12 img_bigbar">
                <div class="col-2 img_bar img_bar4"></div>
                <div class="col-4 img_bar img_bar5"></div>
                <div class="col-6 img_bar img_bar6"></div>
            </div>
            <div class="col-12 img_bigbar">
                <div class="col-3 img_bar img_bar7"></div>
                <div class="col-6 img_bar img_bar8"></div>
                <div class="col-3 img_bar img_bar9"></div>
            </div>
        </div>
    </div>
    <a id="search"></a>
    <div class="container-fluid fluid">

				        <!-- 放搜尋篩選 -->
				        <jsp:include page="listAllWPCase.jsp" />
        
    </div>
    <div class="container">
        <p>熱門廠商 Popular vendors</p>
        <div class="row justify-content-between popular_vendors">
            <div class="col-4 img_box">
                <img src="<%=request.getContextPath()%>/img/wp_img/v0101.jpg">
                <div class="img_text">
                    <h5>文字</h5><br>
                    read more...
                </div>
            </div>
            <div class="col-4 img_box">
                <img src="<%=request.getContextPath()%>/img/wp_img/v0102.jpg">
                <div class="img_text">
                    <h5>文字</h5><br>
                    read more...
                </div>
            </div>
            <div class="col-4 img_box">
                <img src="<%=request.getContextPath()%>/img/wp_img/v0103.jpg">
                <div class="img_text">
                    <h5>文字</h5><br>
                    read more...
                </div>
            </div>
        </div>
    </div>
    <div id="footer">我是FOOTER 尚未添加內容 :)
        <p>段落</p>
        <p>段落</p>
        <p>段落</p>
        <p>段落</p>
        <p>段落</p>
        <p>段落</p>
        <p>段落</p>
        <p>段落</p>
    </div>
    <script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<%--     <script src="<%=request.getContextPath()%>/js/login.js"></script> --%>
<%--     <script src="<%=request.getContextPath()%>/js/jquery.easing.min.js"></script> --%>
    <script src="<%=request.getContextPath()%>/js/scripts.js"></script>
</body>
<script>
	var location_id = ${location}
	
	if(location_id != null){
		var t = $('#search').offset().top;
		$("html,body").animate({scrollTop:t},500);
		
	}
	

</script>
</html>