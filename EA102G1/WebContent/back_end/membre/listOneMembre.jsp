<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
   <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<meta charset="BIG5">
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
		</tr>
	</table>
</body>
</html>