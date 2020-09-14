<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.dresscase.model.*,com.vender.model.*,com.dressaddon.model.*"%>

<%
	Object account = session.getAttribute("vendervo");                  // 從 session內取出 (key) account的值
	if (account == null) {                                             // 如為 null, 代表此user未登入過 , 才做以下工作
	  response.sendRedirect(request.getContextPath()+"/front_end/vender/vender_login.jsp");   //*工作2 : 請該user去登入網頁(login.html) , 進行登入
	  return;
	}
	
	VenderVO vvO = (VenderVO)session.getAttribute("vendervo");	
	
    DressAddOnService daSvc = new DressAddOnService();
    List<DressAddOnVO> list = daSvc.findByVender(vvO.getVender_id());
    pageContext.setAttribute("list",list);
    
    VenderService vSvc = new VenderService();
    VenderVO vVO = vSvc.findByPrimaryKey(vvO.getVender_id());
    request.setAttribute("venderVO",vVO);
%>
 
 <!DOCTYPE html>
<html lang="en">
<head>
	<title>廠商專區</title>
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
	

<link rel="stylesheet" href="../../vendors/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath() %>/vendors/jquery/jquery-3.4.1.min.js"></script>



</head>
<body>
	<div class="wrapper">
	<!-- topbar -->
<%@ include file="/front_end/vender/vender_home_topbar.jsp" %>
	<!-- header -->
<%@ include file="/front_end/vender/vender_home_header.jsp" %>
<div class="main-panel"style="overflow-y:auto;">
<div class="content">
<div class="page-inner">
					<div class="page-header">
					
						<h4 class="page-title">廠商</h4>
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
								<a href="#">婚紗管理/上架</a>
							</li>
							<li class="separator">
								<i class="flaticon-right-arrow"></i>
							</li>
							<li class="nav-item">
								<a href="#">婚紗加購列表/上架</a>
							</li>

						</ul>	
</div>
<!-- 塞資料的地方 -->
<!-- 塞資料的地方 -->
<div class="page-category">
				

<div class="">
        <div class="">
            <div class="col-25">
            	<div>
            	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front_end/addon/addon.do">
				  <input type="submit" class="btn btn-primary" value="刊登新加購項目">
				   <input type="hidden" name="vender_id" value="${venderVO.vender_id }">
				   <input type="hidden" name="action"	value="seeInsert">
				 </FORM>
            	</div>
				<table class="table table-hover">
					<thead>
					 <tr>
						<th scope="col">#</th>
						<th scope="col">項目編號</th>
						<th scope="col">項目名稱</th>
						<th scope="col">項目價格</th>
						<th scope="col">項目上架狀態</th>
						<th scope="col">項目種類</th>
						<th scope="col"></th>
					</tr>
					</thead>	
	 	<tbody>
	 <%@ include file="page1.file" %>
	<c:forEach var="addonVO" varStatus="count" items="${list}">
		
		<tr>
			<th scope="row" width="5"><span>${count.count}</span></th>
			<td width="20">${addonVO.dradd_id}</td>
			<td width="200">${addonVO.dradd_na}</td>
			<td>${addonVO.dradd_pr}</td>
			<td width="200">
			<c:if test="${addonVO.dradd_st==0}">下架中</c:if>
			<c:if test="${addonVO.dradd_st==1}">上架中</c:if>
			<td width="200">${addonVO.dradd_type}</td>
			<td>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front_end/addon/addon.do">
			  <input type="submit" class="btn btn-primary" value="更新加購方案">
			   <input type="hidden" name="dradd_id" value="${addonVO.dradd_id}">
			   <input type="hidden" name="action"	value="getOne_For_Update">
			 </FORM>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</div>
</div>
</div>

</div>
<!-- 塞資料的地方 -->
<!-- 塞資料的地方 -->
</div>
</div>

</div>
</div>
  <script src="<%=request.getContextPath() %>/vendors/popper/popper.min.js"></script>
  <script src="<%=request.getContextPath() %>/vendors/bootstrap/js/bootstrap.min.js"></script>
  <script src="<%=request.getContextPath() %>/js/wp/wpOrder_mem.js" charset="utf-8" type="text/javascript"></script>

</body>
</html>