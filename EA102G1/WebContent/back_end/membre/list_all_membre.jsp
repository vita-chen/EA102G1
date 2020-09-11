<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.membre.model.*" %>

<%
	MembreService service = new MembreService();
	List<MembreVO> membreList = service.getAll();
	pageContext.setAttribute("membreList", membreList);
%>

<!DOCTYPE html>
<html lang="en">

<head>
<%@ include file="/back_end/back_end_home_head.jsp" %>
</head>

<body class="loading" data-layout-config='{"leftSideBarTheme":"dark","layoutBoxed":false, "leftSidebarCondensed":false, "leftSidebarScrollable":false,"darkMode":false, "showRightSidebarOnStart": true}'>
<!--header-->
<%@ include file="/back_end/back_end_home_header.jsp" %>
<div class="content-page">
<div class="content">
<!--topbar-->
<%@ include file="/back_end/back_end_home_topbar.jsp" %>
<!--中間塞資料的地方-->
<div>
<%-- 錯誤表列 --%>
					<c:if test="${not empty errorMsgs}">
						<font style="color: red">請修正以下錯誤:</font>
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li style="color: red">${message}</li>
							</c:forEach>
						</ul>
					</c:if>

					<table>
						<tr>
			<td>會員編號</td>
			<td>姓名</td>
			<td>性別</td>
			<td>地址</td>
			<td>手機</td>
			<td>Email</td>
			<td>賣場狀態</td>
			<td>大頭貼</td>
			<td>註冊時間</td>	
			<td>修改資料</td>		
						</tr>
						
						<c:forEach var="membrevo" items="${membreList }">

							<tr>
								<td>${membrevo.membre_id }</td>
								<td>${membrevo.mem_name }</td>
								<td>${membrevo.sex }</td>
								<td>${membrevo.address }</td>
								<td>${membrevo.phone }</td>
								<td>${membrevo.email }</td>
								<td>${membrevo.status=="1" ? "開啟":"關閉"}</td>
								<td>
									<button type="button" class="btn btn-primary"
										data-toggle="modal" data-target="#${membrevo.membre_id}">
										會員大頭貼</button>

									<div class="modal fade" id="${membrevo.membre_id}"
										tabindex="-1" role="dialog"
										aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
										<div class="modal-dialog modal-dialog-centered"
											role="document">

											<img style="width: 75%; height: 75%;"
												src="<%=request.getContextPath() %>/membre/membre.do?action=getphoto&membre_id=${membrevo.membre_id }">

										</div>
									</div>
								</td>
								<td>${membrevo.regis_time}</td>
								<td>
									<form method="post"
										action="<%=request.getContextPath()%>/membre/membre.do">
										<input type="hidden" name="membre_id"
											value="${membrevo.membre_id }"> <input type="hidden"
											name="action" value="getOneForUpdate"> <input
											type="submit" value="修改">
									</form>
								</td>
							</tr>
						</c:forEach>
					</table>				
</div>
<!--中間塞資料的地方-->		
<!--footer-->				
<%@ include file="/back_end/back_end_home_footer.jsp" %>
</div>
</div>			
<!--js-->
<%@ include file="/back_end/back_end_home_js.jsp" %>
</body>
</html>