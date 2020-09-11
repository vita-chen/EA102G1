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
								<td>公告編號:<font color=red><b>*</b></font></td>
								<td><%=ptpVO.getPtp_id()%></td>
							</tr>

							<tr>
								<td>公告標題:</td>
								<td><input type="TEXT" name="ptp_subject" size="45"
									value="<%=ptpVO.getPtp_subject()%>"></td>
							</tr>

							<tr>
								<td>公告明細:</td>
								<td><textarea name="ptp_detail" rows="6" cols="40" required></textarea>
								</td>
							</tr>


						</table>
						<br> 
						<input type="hidden" name="action" value="update">
						<input type="hidden" name="ptp_id" value="<%=ptpVO.getPtp_id()%>">
						<input type="submit" value="送出修改">
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