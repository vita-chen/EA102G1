<%@page import="com.membre.model.MembreVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.dresscasetrack.model.*,com.dresscase.model.*"%>
<%@ page import="com.dressorder.model.*,com.dressorderdetail.model.*,com.vender.model.*"%>
<%@ page import="com.membre.model.MembreVO"%>

<%
	MembreVO membrevo = (MembreVO)session.getAttribute("membrevo");

	if(membrevo == null){
	String url = request.getContextPath() +"/front_end/dressorder/ListAllTrack_membre.jsp";
	session.setAttribute("location",url);
	response.sendRedirect(request.getContextPath()+"/front_end/membre/login.jsp");
    return;
	}

    DressCaseTrackService dcSvc = new DressCaseTrackService();
    List<DressCaseTrackVO> list = dcSvc.findByMembre(membrevo.getMembre_id());
    pageContext.setAttribute("list",list);
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>會員訂單</title>
	<meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />
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
	

<script src="<%=request.getContextPath() %>/vendors/jquery/jquery-3.4.1.min.js"></script>

<style type="text/css">
	
	/* 評價的星星數 */



</style>



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
								<a href="#">收藏管理</a>
							</li>
							<li class="separator">
								<i class="flaticon-right-arrow"></i>
							</li>
							<li class="nav-item">
								<a href="#">婚紗收藏清單</a>
							</li>
						</ul>	
</div>
<!-- 塞資料的地方 -->
<!-- 塞資料的地方 -->
<div class="page-category">
				

<!-- 顯示錯誤表列 -->
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<div class="">
        <div class="">
            <div class="col-25">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">收藏方案編號</th>
                            <th scope="col">收藏方案名稱</th>      
                            <th scope="col">前往方案</th> 
                            <th scope="col">刪除</th>                   
                        </tr>
                        
                    </thead>
                    <tbody >
                    <c:forEach var="caseVO" varStatus="count" items="${list}"> 
                        <tr>
                            <th scope="row"><span>${count.count}</span></th>
                            <td >${caseVO.drcase_id}</td>
                            <!--顯示相對應的方案名稱 -->
                            <jsp:useBean id="drSvc" class="com.dresscase.model.DressCaseService"></jsp:useBean>
                            	<c:forEach var="drVO" items="${drSvc.all}"> 
                            		<c:if test="${drVO.drcase_id eq caseVO.drcase_id}">
		                          		<td>${drVO.drcase_na}</td>
                           			</c:if>
                           		</c:forEach>
                            
                            <td>
                            <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front_end/dresscase/dress.do" style="margin-bottom: 0px;">
							     <button type="submit" value="查看方案">查看方案</button>
							     <input type="hidden" name="drcase_id"  value="${caseVO.drcase_id}">
							     <input type="hidden" name="action"	value="getOne">
					    	</FORM>
				    		</td>
				    		<!--取消收藏-->
				    		<td>
				    		<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front_end/dresstrack/track.do" style="margin-bottom: 0px;">
							     <button type="submit" value="取消">取消收藏</button>
							     <input type="hidden" name="drcase_id"  value="${caseVO.drcase_id}">
							     <input type="hidden" name="action"	value="delTrack">
							     <input type="hidden" name="membre_id" value="${membrevo.membre_id}">
					    	</FORM>
							</td>
				    		
                        </tr>
                        
                        </c:forEach>
                        </tbody>
                </table>
            </div>
        </div>
    </div>  


  <script src="<%=request.getContextPath() %>/vendors/popper/popper.min.js"></script>
  <script src="<%=request.getContextPath() %>/vendors/bootstrap/js/bootstrap.min.js"></script>
  <script src="<%=request.getContextPath() %>/js/wp/wpOrder_mem.js" charset="utf-8" type="text/javascript"></script>


</div>
<!-- 塞資料的地方 -->
<!-- 塞資料的地方 -->
</div>
</div>

</div>
</div>


</body>
</html>