<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="BIG5">
<title>Insert title here</title>
</head>
<body>
<c:if test="${not empty errors}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="error" items="${errors}">
			<li style="color:red">${error}</li>
		</c:forEach>
	</ul>
</c:if>
<form action="<%=request.getContextPath()%>/membre/membre.do" method = "post">
	<table>
<tr>
	<td>會員編號</td>
	<td>${membrevo.membre_id }</td>
</tr>
<tr>
	<td>姓名</td>
	<td><input type = "text" name="mem_name" value="${membrevo.mem_name }"/></td>
</tr>
<tr>
	<td>性別</td>
	<td><input type = "text" name="sex" value="${membrevo.sex }"/></td>
</tr>
<tr>
	<td>地址</td>
	<td><input type = "text" name="address" value="${membrevo.address }"/></td>
</tr>
<tr>
	<td>手機</td>
	<td><input type = "text" name="phone" value="${membrevo.phone }"/></td>
</tr>
<tr>
	<td>Email</td>
	<td><input type = "text" name="email" value="${membrevo.email }"/></td>
</tr>
<tr>
	<td>密碼</td>
	<td><input type = "text" name="passe"value="${membrevo.passe }"/></td>
</tr>

	</table>
	<input type = "hidden" name = "action"  value="updating">
	<input type="hidden" name="membre_id" value="${membrevo.membre_id }">
	<input type="hidden" name="compte" value="${membrevo.compte }">
	<input type="hidden" name="regis_time" value="${membrevo.regis_time }">
	<input type="submit" value="送出修改">
</form>
</body>
</html>