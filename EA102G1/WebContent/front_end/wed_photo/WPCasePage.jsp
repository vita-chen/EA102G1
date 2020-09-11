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
		response.sendRedirect(request.getContextPath()+"/front_end/wed_photo/wp_home.jsp");
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
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>${WPCaseVO.wed_photo_name } - 婚禮導航 Wedding Navi</title>
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
/*     font-family: "微軟正黑體"; */
    border-bottom: solid 1px #FFEEFFFF;
}

#title1 {
    display: inline-block;
    position: fixed;
    top: 0px;
    z-index: 1;
}

h4 {
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

.content1 .row {
    margin: 0;
}

.content1 {
    margin-top: 30px;
    height: 800px;
    border: dotted 1px #CCCCCCFF;
    background-image: url("<%=request.getContextPath()%>/img/wp_img/wpcasebanner.jpg");
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
    height: 220px;
    border-color: #FFFFFF00;
    border: 1px solid #FFFFFFFF;
    border-radius: 10px;
    text-shadow: #FFFFFFFF 0.1em 0.1em 0.2em;

}

.content1_txt1 img {
    border-radius: 95px;
}

.content2 {
	padding-top: 30px;
	padding-bottom: 30px;
    border: dotted 1px #D9BFFFFF;
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
.img_box {
    display: inline-block;
    border-radius: 10px;
    margin-top: 15px;
    margin-bottom: 15px;
    height: 420px;
    border: 1px dotted #F90;
    text-align: left;
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
</style>
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
                <a href="<%=request.getContextPath()%>/front_end/membre/login.jsp" id="link1" id="link1">　會員登入</a>
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
            <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/front_end/wed_photo/wp_home.jsp">婚禮攝影</a></li>
            <li class="breadcrumb-item"><a href="#">廠商簡介</a></li>
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
	                       <button type="button" class="btn btn-danger">我要詢問</button>
	                       <button type="button" class="btn btn-danger collect">加入收藏</button>	                       
	                       <input type="hidden" name="membre_id" value="${membrevo.membre_id }">
	                       <input type="hidden" name="wed_photo_case_no" value="${WPCaseVO.wed_photo_case_no }">
	                       <button type="button" class="btn btn-danger">立即下訂</button>
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
                        <button type="button" class="btn btn-danger">我要詢問</button>
                        <button type="button" class="btn btn-danger collect">加入收藏</button>
                        <button type="button" class="btn btn-danger">立即下訂</button>
                </div>
                
                    <div class="con2head">
                        <p>其他方案</p>
                    </div>
                    <div class="row justify-content-between new_case">
                    	<c:forEach var="other_set" items="${other_set }">
	                        <div class="col-4 img_box">
	                            <img src="<%=request.getContextPath()%>/wed/wpcase.do?action=getOne_WPImg&wed_photo_case_no=${other_set.wed_photo_case_no}">
	                            <div class="img_text">
	                            <a href="<%=request.getContextPath()%>/wed/wpcase.do?action=getOne_CasePage&wed_photo_case_no=${other_set.wed_photo_case_no}" target="_blank">
	                                <h5>${other_set.wed_photo_name }</h5></a><br>
	                                read more...${other_set.wed_photo_price }
	                            </div>
	                        </div>
                       </c:forEach> 
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


    <script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.4.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
    <script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
</body>
<script>
$(document).ready(function(){
	
	var url = (window.location.href).split('/')[0]+'/'+(window.location.href).split('/')[1]+'/'+(window.location.href).split('/')[2]+'/'+(window.location.href).split('/')[3]
	
	$(".collect").click(function(){
		var membre_id = $("[name=membre_id]").val();
		var wed_photo_case_no = $("[name=wed_photo_case_no]").val();
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
	
	
})
</script>
</html>