<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.pinthepost.model.*"%>

<%
	PtpService ptpSvc = new PtpService();
	List<PtpVO> list = ptpSvc.getAll();
	pageContext.setAttribute("list", list);
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
							<th>公告編號</th>
							<th>公告標題</th>
							<th>公告內容</th>
							<th>刊登時間</th>
							<th>修改</th>

						</tr>

						<%@ include file="page1.file"%>


						<c:forEach var="ptpVO" items="${list}" begin="<%=pageIndex%>"
							end="<%=pageIndex+rowsPerPage-1%>">

							<tr>
								<td>${ptpVO.ptp_id}</td>
								<td>${ptpVO.ptp_subject}</td>
								<td>${ptpVO.ptp_detail}</td>
								<td>${ptpVO.ptp_date}</td>



								<td>
									<FORM METHOD="post"
										ACTION="<%=request.getContextPath()%>/back_end/ptp/ptp.do"
										style="margin-bottom: 0px;">
										<input type="submit" value="修改"> <input type="hidden"
											name="ptp_id" value="${ptpVO.ptp_id}"> <input
											type="hidden" name="action" value="getOne_For_Update">
									</FORM>
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