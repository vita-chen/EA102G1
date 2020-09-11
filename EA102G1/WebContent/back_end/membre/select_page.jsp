<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
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
<a href ="listAllMembre.jsp">ListAllMember</a>
<form action = "<%= request.getContextPath()%>/membre/membre.do">
請輸入會員編號<input type = "text" name = "membre_id">
					  <input type = "hidden" name = "action" value = "getOneById">
					  <input type = "submit" value = "查詢">
</form>
<jsp:useBean id="service" class="com.membre.model.MembreService"/>
<form action="<%= request.getContextPath()%>/membre/membre.do" method = "post">
選擇會員編號
<select name="membre_id">
	<c:forEach var="membrevo" items="${service.all }">
		<option value="${membrevo.membre_id }">${membrevo.membre_id }
	</c:forEach>
</select>
<input type="hidden" name="action" value="getOneById">
<input type="submit" value="送出查詢">
</form>
</body>
</html>