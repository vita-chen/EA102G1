<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.vender.model.*"%>

<%
	VenderService venderSvc = new VenderService();
    List<VenderVO> list = venderSvc.get_all_verification();
    pageContext.setAttribute("list",list);
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
							<th>廠商編號</th>
							<th>廠商名稱</th>
							<th>住址</th>
							<th>電話</th>
							<th>聯絡人</th>
							<th>Email</th>
							<th>圖片</th>
							<th>驗證狀態</th>
							<th>資料修改信</th>
							<th>註冊時間</th>					
						</tr>
						<%@ include file="page1.file"%>
						<c:forEach var="venderVO" items="${list}" begin="<%=pageIndex%>"
							end="<%=pageIndex+rowsPerPage-1%>">

							<tr>
								<td>${venderVO.vender_id}</td>
								<td>${venderVO.ven_name}</td>
								<td>${venderVO.ven_addr}
								<a href="https://www.google.com.tw/maps/place/${venderVO.ven_addr}" title="GOOGLE地圖" target="_blank">
								<img style="width: 20px; height: 20px;"
								src="<%=request.getContextPath()%>/img/GOOGLE_MAP.png">
								</a>
								</td>
								<td>${venderVO.ven_phone}</td>
								<td>${venderVO.ven_contact}</td>
								<td>${venderVO.ven_mail}</td>
								<td>
									<!-- 圖片神奇寫法== -->
									<button type="button" class="btn btn-primary"
										data-toggle="modal" data-target="#${venderVO.vender_id}">
										廠商名片</button>

									<div class="modal fade" id="${venderVO.vender_id}"
										tabindex="-1" role="dialog"
										aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
										<div class="modal-dialog modal-dialog-centered"
											role="document">

											<img style="width: 75%; height: 75%;"
												src="<%=request.getContextPath() %>/vender/vender.do?action=getphot&vender_id=${venderVO.vender_id }">

										</div>
									</div>
								</td>
								

								<td>
								${venderVO.is_enable=="0" ? "未驗證":"已驗證"}
							
									<FORM METHOD="post"
										ACTION="<%=request.getContextPath()%>/vender/vender.do"
										style="margin-bottom: 0px;">
										<input type="submit" value="驗證"> <input type="hidden"
											name="vender_id" value="${venderVO.vender_id}"> <input
											type="hidden" name="action" value="update_vender_enable">
									</FORM>
								</td>
								<td>
								寄送
								
									<FORM METHOD="post"
										ACTION="<%=request.getContextPath()%>/vender/vender.do"
										style="margin-bottom: 0px;">
										<input type="submit" value="寄送"> 
										<input type="hidden" name="ven_account" value="${venderVO.ven_account}"> 
										<input type="hidden" name="ven_mail" value="${venderVO.ven_mail}">
										<input type="hidden" name="action" value="vender_modifyemail">
									</FORM>
								</td>
								<td>${venderVO.ven_regis_time}</td>
							</tr>
						</c:forEach>
					</table>
					<%@ include file="page2.file"%>
				
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