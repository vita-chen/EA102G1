<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.membre.model.*" %>

<%
	MembreService service = new MembreService();
	List<MembreVO> membreList = service.getAll();
	pageContext.setAttribute("membreList", membreList);
%>
<html>
<head>
<meta charset="BIG5">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
<title>Insert title here</title>
<style>
img {
 width:15%;
}
</style>
</head>
<body>
<a href="<%=request.getContextPath() %>/back_end/membre/select_page.jsp">回首頁</a>
<table>
		<tr>
			<td>會員編號</td>
			<td>姓名</td>
			<td>性別</td>
			<td>地址</td>
			<td>手機</td>
			<td>Email</td>
			<td>狀態</td>
			<td>帳號</td>
			<td>密碼</td>
			<td>大頭貼</td>
			<td>註冊時間</td>
		</tr>
	<c:forEach var="membrevo" items="${membreList }">
	 <tr>
				<td>${membrevo.membre_id }</td>
			<td>${membrevo.mem_name }</td>
			<td>${membrevo.sex }</td>
			<td>${membrevo.address }</td>
			<td>${membrevo.phone }</td>
			<td>${membrevo.email }</td>
			<td>${membrevo.status=="1" ? "正常":"停權"}</td>
			<td>${membrevo.compte }</td>
			<td>${membrevo.passe }</td>
			<td><img src = "<%=request.getContextPath() %>/membre/membre.do?action=getphoto&membre_id=${membrevo.membre_id }"></td>
			<td><fmt:formatDate value="${membrevo.regis_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td><form method="post" action = "<%=request.getContextPath() %>/membre/membre.do">
					<input type = "hidden" name = "membre_id" value="${membrevo.membre_id }">
					<input type = "hidden" name = "action" value ="getOneForUpdate">
					<input type = "submit" value="修改">
			       </form>
			</td>
					<td><form method="post" action = "<%=request.getContextPath() %>/membre/membre.do">
					<input type = "hidden" name = "membre_id" value="${membrevo.membre_id }">
					<input type = "hidden" name = "action" value ="delete">
					<input type = "submit" value="刪除">
			       </form>
			</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>