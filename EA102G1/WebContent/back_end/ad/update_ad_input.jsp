<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ad.model.*"%>

<%
	AdVO adVO = (AdVO) request.getAttribute("adVO");
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
					<font style="color:red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color:red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>
				
				<FORM METHOD="post" ACTION="ad.do" name="form1"
				enctype="multipart/form-data">
				
				<table>
					<tr>
						<td>廣告編號:<font color=red><b>*</b></font></td>
						<td><%=adVO.getAd_id()%></td>
					</tr>
					
					<tr>
						<td>廣告圖片:</td>
						<td><input type="file" name="ad_pic" size="45"
						value="<%=(adVO == null) ? "100" : adVO.getAd_pic()%>" /></td>
					</tr>
					
					<tr>
						<td>廣告明細:</td>
						<td><input type="TEXT" name="ad_detail" size="45" value="<%=adVO.getAd_detail()%>" /></td>
					</tr>
				
				
				</table>
				<br>
				<input type="hidden" name="action" value="update">
				<input type="hidden" name="ad_id" value="<%=adVO.getAd_id()%>">
				<input type="submit" value="送出修改"></FORM>


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