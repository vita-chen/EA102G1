<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.prod.model.ProdVO, java.util.*" %>
<%@ page import="com.vender.model.*"%>
<%@ page import="com.pinthepost.model.*"%>
<%@ page import="com.ad.model.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html>
<html lang="en">
  <head>
  
<jsp:include page="/front_end/home/head.jsp">
<jsp:param name="title" value="Registre page"/>
</jsp:include>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>婚禮導航</title>

    <meta name="description" content="Source code generated using layoutit.com">
	<meta name="author" content="LayoutIt!">
	 <!-- Google Fonts -->
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+TC:wght@300;500&display=swap" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap_4.5.1.css">
	<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet">
	<!-- ======= 引入新style ======= -->
	<link href="<%=request.getContextPath()%>/css/bootstrap_menu.min.css" rel="stylesheet"> 
	<link href="<%=request.getContextPath()%>/css/menu_style.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/icofont/icofont.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/boxicons/css/boxicons.min.css" rel="stylesheet">

<!-- PTP carousel style -->
<style>
.ms-header {
  display: -webkit-box;
  display: flex;
  -webkit-box-align: center;
          align-items: center;
  jsutify-content: center;
/*   font-family: sans-serif; */
/*   width: 100vw; */
/*   height: 100vh; */
/*   background: -webkit-gradient(linear, left top, right bottom, from(#9dd7d5), to(#fea096)); */
/*   background: linear-gradient(to right bottom, #9dd7d5, #fea096); */
}
.ms-header__title {
  -webkit-box-flex: 1;
          flex: 1 1 100%;
  width: 100%;
/*   text-align: center; */
  font-size: 0.9rem;
/*   font-weight: bold; */
  color: black;
  text-shadow: 0px 0px 2px rgba(0, 0, 0, 0.4);
}

.ms-slider {
  display: inline-block;
  height: 1.5em;
  overflow: hidden;
  vertical-align: middle;
  -webkit-mask-image: -webkit-gradient(linear, left top, left bottom, from(transparent), color-stop(white), color-stop(white), color-stop(white), to(transparent));
  -webkit-mask-image: linear-gradient(transparent, white, white, white, transparent);
          mask-image: -webkit-gradient(linear, left top, left bottom, from(transparent), color-stop(white), color-stop(white), color-stop(white), to(transparent));
          mask-image: linear-gradient(transparent, white, white, white, transparent);
  mask-type: luminance;
  mask-mode: alpha;
}
.ms-slider__words {
  display: inline-block;
  margin: 0;
  padding: 0;
  list-style: none;
  -webkit-animation-name: wordSlider;
          animation-name: wordSlider;
  -webkit-animation-timing-function: ease-out;
          animation-timing-function: ease-out;
  -webkit-animation-iteration-count: infinite;
          animation-iteration-count: infinite;
  -webkit-animation-duration: 6s;
          animation-duration: 6s;
}
.ms-slider__word {
  display: block;
  line-height: 1.3em;
  text-align: left;
}

@-webkit-keyframes wordSlider {
  0%,
    27% {
    -webkit-transform: translateY(0%);
            transform: translateY(0%);
  }
  33%,
    60% {
    -webkit-transform: translateY(-25%);
            transform: translateY(-25%);
  }
  66%,
    93% {
    -webkit-transform: translateY(-50%);
            transform: translateY(-50%);
  }
  100% {
    -webkit-transform: translateY(-75%);
            transform: translateY(-75%);
  }
}

@keyframes wordSlider {
  0%,
    27% {
    -webkit-transform: translateY(0%);
            transform: translateY(0%);
  }
  33%,
    60% {
    -webkit-transform: translateY(-25%);
            transform: translateY(-25%);
  }
  66%,
    93% {
    -webkit-transform: translateY(-50%);
            transform: translateY(-50%);
  }
  100% {
    -webkit-transform: translateY(-75%);
            transform: translateY(-75%);
  }
}
</style>
  </head>
  <body>

<!-- ======= Header ======= -->

<header style="background-color:#ffffff;" id="" class="fixed-top header-transparent col-md-12 heaber-menu">

<!-- PTP START -->
		<div>
<%-- 				<% --%>
<!-- // 					PtpService ptpSvc = new PtpService(); -->
<!-- // 					List<PtpVO> list = ptpSvc.getAll(); -->
<!-- // 					pageContext.setAttribute("list", list); -->
<%-- 				%> --%>

				<header class="ms-header">置頂公告：					
						<div class="ms-slider">
							<ul class="ms-slider__words">
								<c:forEach var="ptpVO" items="${list}">
									<li class="ms-slider__word" ><a href="" id="${ptpVO.ptp_id}">${ptpVO.ptp_subject}-${ptpVO.ptp_detail}
																</a></li>																																															
								</c:forEach>
								
								<!-- This last word needs to duplicate the first one to ensure a smooth infinite animation -->
								<c:forEach var="ptpVO" items="${list}" begin="0" end="0">
									<li class="ms-slider__word">${ptpVO.ptp_subject}-${ptpVO.ptp_detail}</li>
								</c:forEach>
							</ul>
						</div>					
				</header>
		</div>	
<!-- PTP END -->

	<div class="container d-flex align-items-cente">

    <a href="<%=request.getContextPath()%>/front_end/home/home.jsp" target="_blank">
		<img class="logo" style="width:200px; height:63px;" alt="Bootstrap Image Preview" src="<%=request.getContextPath()%>\img\logo.png" class="rounded mx-auto d-block" alt="?">
		</a>
		<nav class="nav-menu d-none d-lg-block col-sm-10 main-nav">
			<ul>
			<li><a href="<%=request.getContextPath()%>/back_end/back_end_home.jsp" target="_blank">後台</a></li>
			<li><a href="<%=request.getContextPath()%>/front_end/vender/vender_home.jsp" target="_blank">廠商專區</a></li>
			<li><a href="<%=request.getContextPath()%>/front_end/carOrder/browseAllCar.jsp" target="_blank">禮車租借</a></li>
			<li><a href="<%=request.getContextPath()%>/front_end/wed_photo/wp_home.jsp" target="_blank">婚禮攝影</a></li>
			<li><a href="<%=request.getContextPath()%>/front_end/dresscase/DressHome.jsp" target="_blank">婚紗租借</a></li>
			<li><a href="index.html" target="_blank">討論區</a></li>
			<li><a href="<%=request.getContextPath()%>/front_end/prod/select_page.jsp" target="_blank">商場</a></li>
			<li><a href="<%=request.getContextPath()%>/front_end/membre_order/membre_order_home.jsp" target="_blank">會員訂單</a></li>
			
			<li>  
<nav class="d-flex align-items-center flex-column flex-md-row">
    <ul class="nav mr-md-auto">
    </ul>

    <ul class="nav ">
    <li class="nav-item d-flex flex-column">
<div class="dropleft my-auto">
  <a id="notice" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" data-display="static">
     	<svg id="notice" width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-bell-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
		  <path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zm.995-14.901a1 1 0 1 0-1.99 0A5.002 5.002 0 0 0 3 6c0 1.098-.5 6-2 7h14c-1.5-1-2-5.902-2-7 0-2.42-1.72-4.44-4.005-4.901z"/>
		</svg>
  </a>

  <div id="content" style="overflow-y:auto; height: 50px;" class="dropdown-menu dropdown-menu-sm-right" aria-labelledby="dropdownMenuLink">
</div>
</div>
    </li>
        			<%String currentLocation = request.getRequestURI();
					String queryString = request.getQueryString();
					request.setAttribute("currentLocation", currentLocation);
					request.setAttribute("queryString", queryString);
				%>
     <c:if test="${membrevo==null }">
    		<div class="widget-header icontext">
    		<c:choose>
    		<c:when test="${queryString ==null }">
    		<a href="<%=request.getContextPath()%>/front_end/membre/login.jsp?location=${currentLocation}">Login</a> |  
    		</c:when>
    		<c:otherwise>
						<a href="<%=request.getContextPath()%>/front_end/membre/login.jsp?location=${currentLocation }?${queryString}">Login</a> |  
			</c:otherwise>
			</c:choose>
						<a href="<%=request.getContextPath()%>/front_end/membre/regis.jsp"> Register</a>
					</div>
			
    </c:if>
    
    <c:if test="${membrevo !=null }">
    	<li  class="nav-item text-right myItem" style="margin-right:-20px"><a class="nav-link px-2"> <img style="width:100%; height:26px;" class="rounded" src="<%=request.getContextPath() %>/membre/membre.do?action=getphoto&membre_id=${membrevo.membre_id }"></a></li>
<li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="http://example.com">${membrevo.mem_name}</a>
          <div class="dropdown-menu">
          	<a class="dropdown-item" href="<%=request.getContextPath()%>/front_end/membre_order/membre_order_home.jsp">會員訂單</a>
            <a class="dropdown-item" href="#">Profile setting</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/order/order.do?action=getDifferentStatusOrders_buyer&order_status=All">My orders</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/order/order.do?action=getDifferentStatusOrders_seller&order_status=All">My orders (Seller Develop Only)</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/front_end/prod/myProds.jsp">My products</a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/membre/membre.do?action=logOut">登出</a>
          </div>
</li>
    </c:if>	
    </ul>
  </nav> <!-- nav .// --> </li>
			</ul>
			
		</nav><!-- .nav-menu -->
		
	</div>
	
	</header>
	



	<!--<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" ></script>-->

	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" ></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.1/js/bootstrap.min.js" ></script>
	
	<script type="text/javascript">
	var ptp001 = document.getElementById("PTP001");
	ptp001.addEventListener("click",href1,false);
	function href1(e){
		window.open("<%=request.getContextPath()%>/front_end/carOrder/browseAllCar.jsp");//待補 禮車方案路徑
	}
	
	var ptp002 = document.getElementById("PTP002");
	ptp002.addEventListener("click",href2,false);
	function href2(e){
		window.open("<%=request.getContextPath()%>/front_end/dresscase/DressHome.jsp");//待補 婚紗方案路徑
	}
	
	var ptp003 = document.getElementById("PTP003");
	ptp003.addEventListener("click",href3,false);
	function href3(e){
		window.open("<%=request.getContextPath()%>/front_end/wed_photo/wp_home.jsp");//待補 婚攝方案路徑
	}
	
	</script>

  </body>
</html>