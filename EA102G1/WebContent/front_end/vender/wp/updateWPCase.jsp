<%@page import="com.wpimg.model.WPImgVO"%>
<%@ page import="com.wpcase.model.*"%>
<%@ page contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<style type="text/css">
table {
	width: 40%;
	border: dashed 1px #FFB6FFFF;
}

textarea {
	width: 350px;
	height: 320px;
}
</style>
<title>update wed-photo case - 婚禮攝影服務 方案修改頁面</title>
</head>
<body>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color: red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color: red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
<form action="<%=request.getContextPath()%>/wed/wpcase.do"
	method="post" enctype="multipart/form-data">

	<table>
		<tr>
			<td>方案編號</td>
			<td>${WPCaseVO.wed_photo_case_no}</td>
		</tr>
		<tr>
			<td>廠商編號</td>
			<td>${WPCaseVO.vender_id}</td>
		</tr>
		<tr>
			<td>婚攝方案名稱</td>
			<td><input type="text" name="WED_PHOTO_NAME" size="40"
				maxlength="20" value="${WPCaseVO.wed_photo_name}" /></td>
		</tr>
		<tr>
			<td>婚攝方案簡介</td>
			<td><textarea name="WED_PHOTO_INTRO">${WPCaseVO.wed_photo_intro}</textarea></td>			
		</tr>
		<tr>
			<td>選擇方案狀態</td>
			<td><label for="0"><input type="radio" name="WED_PHOTO_STATUS" value="0" id="0" checked />上架狀態　 </label>
				<label for="1"><input type="radio" name="WED_PHOTO_STATUS" value="1" id="1" />下架狀態</label></td>
		</tr>
		<tr>
			<td>婚攝方案價格</td>
			<td><input type="text" name="WED_PHOTO_PRICE" value="${WPCaseVO.wed_photo_price}" /></td>
		</tr>
		<tr>
			<td>選擇圖片</td>
			<td><input type="file" name="upfile1" multiple></td>
		</tr>
	</table><br>

<input type="hidden" name="action" value="update">
<input type="hidden" name="WED_PHOTO_CASE_NO" value="${WPCaseVO.wed_photo_case_no}" />
<input type="hidden" name="VENDER_ID" value="${WPCaseVO.vender_id}" />
<input type="submit" value="送出修改"><br>

<c:if test="${imgList != null }">		
	<c:forEach var="imgvo" items="${imgList}">		
		<img class="img" src="<%= request.getContextPath() %>/wed/wpcase.do?action=Get_WPImg&wed_photo_case_no=${WPCaseVO.wed_photo_case_no}&wed_photo_imgno=${imgvo.wed_photo_imgno}"><br>
	</c:forEach>
</c:if>

</form>
</body>
<c:if test="${WPCaseVO.wed_photo_status == 1}">
	<script>
		$("input[type=radio]")[1].checked = true;
	</script>
</c:if>
</html>