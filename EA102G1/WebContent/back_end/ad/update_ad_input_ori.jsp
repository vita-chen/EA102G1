<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.ad.model.*"%>

<%
	AdVO adVO = (AdVO) request.getAttribute("adVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>圖片資料修改 - update_ad_input.jsp</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<!-- App favicon -->
<link rel="shortcut icon" href="assets/images/favicon.ico">

<!-- third party css -->
<link
	href="<%=request.getContextPath()%>/css/jquery-jvectormap-1.2.2.css"
	rel="stylesheet" type="text/css" />
<!-- third party css end -->

<!-- App css -->
<link href="<%=request.getContextPath()%>/css/icons.min.css"
	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/app.min.css"
	rel="stylesheet" type="text/css" id="light-style" />
<link href="<%=request.getContextPath()%>/css/app-dark.min.css"
	rel="stylesheet" type="text/css" id="dark-style" />

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body>

<h3>圖片資料修改 - update_ad_input.jsp</h3>

<!-- start -->

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="ad.do" name="form1"
enctype="multipart/form-data">

<table>
	<tr>
		<td>廣告編號:<font color=red><b>*</b></font></td>
		<td><%=adVO.getAd_id()%></td>
	</tr>
	
	<tr>
		<td>廣告圖片:</td>
		<td><input type="file" name="ad_pic" size="45"
		value="<%=(adVO == null) ? "100" : adVO.getAd_pic()%>" /></td>
	</tr>
	
	<tr>
		<td>廣告明細:</td>
		<td><input type="TEXT" name="ad_detail" size="45" value="<%=adVO.getAd_detail()%>" /></td>
	</tr>


</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="ad_id" value="<%=adVO.getAd_id()%>">
<input type="submit" value="送出修改"></FORM>

<!-- end -->

</body>
</html>