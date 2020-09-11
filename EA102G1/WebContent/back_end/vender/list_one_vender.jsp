<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.vender.model.*"%>

<%
  VenderVO venderVO = (VenderVO) request.getAttribute("venderVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
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
						<th>廠商狀態</th>
						<th>註冊時間</th>
					</tr>
					<tr>
						<td><%=venderVO.getVender_id()%></td>
						<td><%=venderVO.getVen_name()%></td>
						<td><%=venderVO.getVen_addr()%></td>
						<td><%=venderVO.getVen_phone()%></td>
						<td><%=venderVO.getVen_contact()%></td>
						<td><%=venderVO.getVen_mail()%></td>
						<td>
							<button type="button" class="btn btn-primary" data-toggle="modal"
								data-target="#exampleModalCenter">廠商名片</button>
							<div class="modal fade" id="exampleModalCenter" tabindex="-1"
								role="dialog" aria-labelledby="exampleModalCenterTitle"
								aria-hidden="true">
								<div class="modal-dialog modal-dialog-centered" role="document">
									<img style="width: 75%; height: 75%;"
										src="<%=request.getContextPath() %>/vender/vender.do?action=getphot&vender_id=${venderVO.vender_id }">
								</div>
							</div>
						</td>
						<td><%=venderVO.getIs_enable() == 0 ? "未驗證" : "已驗證"%>
							<FORM METHOD="post"
								ACTION="<%=request.getContextPath()%>/vender/vender.do"
								style="margin-bottom: 0px;">
								<input type="submit" value="驗證"> <input type="hidden"
									name="empno" value="${empVO.empno}"> <input
									type="hidden" name="action" value="getOne_For_Update">
							</FORM></td>
						<td><%=venderVO.getIs_vaild() == 1 ? "權限正常" : "被停權"%>
							<FORM METHOD="post"
								ACTION="<%=request.getContextPath()%>/vender/vender.do"
								style="margin-bottom: 0px;">
								<input type="submit" value="封鎖"> <input type="hidden"
									name="empno" value="${empVO.empno}"> <input
									type="hidden" name="action" value="delete">
							</FORM></td>
						<td><%=venderVO.getVen_regis_time()%></td>
					</tr>
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