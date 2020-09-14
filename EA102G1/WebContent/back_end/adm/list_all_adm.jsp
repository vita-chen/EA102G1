<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.adm.model.*"%>
<%
AdmVO admVO = (AdmVO) request.getAttribute("admVO"); 

	AdmService admSvc = new AdmService();
	List<AdmVO> list = admSvc.getAll();
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
						<th>管理員編號</th>
						<th>Email</th>
						<th>姓名</th>
						<th>登入權限</th>
						<th>開通廠商權限</th>
						<th>檢舉&廣告權限</th>
						<th>權限管理</th>
					</tr>
					<%@ include file="page1.file"%>
					<c:forEach var="admVO" items="${list}" begin="<%=pageIndex%>"
						end="<%=pageIndex+rowsPerPage-1%>">

						<tr>
							<td>${admVO.adm_id}</td>
							<td>${admVO.adm_account}</td>
							<td>${admVO.adm_name}</td>
							<th>${admVO.adm_1=="1" ? "有":"無"}</th>
							<th>${admVO.adm_2=="1" ? "有":"無"}</th>
							<th>${admVO.adm_3=="1" ? "有":"無"}</th>
							<th>
<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#${admVO.adm_id}">
  權限設定
</button>
							
<div class="modal fade" id="${admVO.adm_id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalCenterTitle">權限設定</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>

<form method="POST" action="<%=request.getContextPath()%>/adm/adm.do" enctype="multipart/form-data">
      <div class="modal-body">
<input style="display:none;"type="TEXT" name="adm_id" value="${admVO.adm_id}"> 
<input style="display:none;"type="TEXT" name="adm_account" value="${admVO.adm_account}">
<input style="display:none;"type="TEXT" name="adm_pwd" value="${admVO.adm_pwd}">
<input style="display:none;"type="TEXT" name="adm_name" value="${admVO.adm_name}">

<a>登入權限　　　　:　</a><select name="adm_1">
 <option value="0">關閉</option>
　<option value="1">開啟</option>
</select><br><br>
<a>開通廠商權限　　:　</a><select name="adm_2">
 <option value="0">關閉</option>
　<option value="1">開啟</option>
</select><br><br>
<a>檢舉&廣告權限　:　</a><select name="adm_3">
 <option value="0">關閉</option>
　<option value="1">開啟</option>
</select><br><br>


      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
        <button type="hidden" class="btn btn-primary" name="action" value="update_adm">確認修改</button>
      </div>
      </form>
    </div>
  </div>
</div>
							
							
							</th>
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