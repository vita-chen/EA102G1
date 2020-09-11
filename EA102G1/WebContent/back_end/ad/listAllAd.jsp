<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ad.model.*"%>

<%
	AdService AdSvc = new AdService();
	List<AdVO> list = AdSvc.getAll();
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
						<th>編號</th>
						<th>圖片</th>
						<th>明細</th>
						<th>開始時間</th>
						<th>結束時間</th>
						<th>修改</th>
					</tr>

					<!-- 待處理 -->
					<%@ include file="page1.file"%>


					<c:forEach var="adVO" items="${list}" begin="<%=pageIndex%>"
						end="<%=pageIndex+rowsPerPage-1%>">

						<tr>
							<td>${adVO.ad_id}</td>

							<td>
								<!-- 圖片神奇寫法== -->
								<button type="button" class="btn btn-primary"
									data-toggle="modal" data-target="#${adVO.ad_id}">廣告圖片</button>

								<div class="modal fade" id="${adVO.ad_id}" tabindex="-1"
									role="dialog" aria-labelledby="exampleModalCenterTitle"
									aria-hidden="true">
									<div class="modal-dialog modal-dialog-centered" role="document">

										<img style="width: 75%; height: 75%;"
											src="<%=request.getContextPath()%>/back_end/ad/ad.do?action=getphot&ad_id=${adVO.ad_id }">

									</div>
								</div>
							</td>

							<td>${adVO.ad_detail}</td>
							<td>${adVO.ad_start_time}</td>
							<td>${adVO.ad_end_time}</td>

							<td>
								<FORM METHOD="post"
									ACTION="<%=request.getContextPath()%>/back_end/ad/ad.do"
									style="margin-bottom: 0px;">
									<input type="submit" value="修改"> <input type="hidden"
										name="ad_id" value="${adVO.ad_id}"> <input
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