<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.pinthepost.model.*"%>

<%
	PtpVO ptpVO = (PtpVO) request.getAttribute("ptpVO");
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

					<FORM METHOD="post" ACTION="ptp.do" name="form1">
						<table>
							<tr>
								<td>新增公告主題</td>
								<td><input type="TEXT" name="ptp_subject" size="45" placeholder="今天想po點甚麼"
					value="<%=(ptpVO == null) ? "" : ptpVO.getPtp_subject()%>"></td>
							</tr>
							<tr>
								<td>新增廣告內容</td>
								<td><input type="TEXT" name="ptp_detail" size="45" placeholder="今晚我想來點?"
					value="<%=(ptpVO == null) ? "" : ptpVO.getPtp_detail()%>"></td>
							</tr>
						</table>
						<br> 
						<input type="hidden" name="action" value="insert">
						<input type="submit" value="公告上傳">
					</FORM>


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