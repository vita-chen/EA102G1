<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%

//此頁面有廠商VO 方案List


%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
${VenderVO.vender_id } - ${VenderVO.ven_name} - ${VenderVO.ven_addr} - ${VenderVO.ven_phone} - ${VenderVO.ven_mail}
<br>
<c:forEach var="list" items="${list_case }">
${list.wed_photo_case_no } - ${list.wed_photo_name }<br>
</c:forEach>
</body>
</html>