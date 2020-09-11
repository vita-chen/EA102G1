<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"
	language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.carExt.model.*"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%
	CarExtVO carExtVo = (CarExtVO) request.getAttribute("carExtVo");
pageContext.setAttribute("contextPath", request.getContextPath()); // 把request.getContextPath()塞到pageContext，並取一個變數叫做contextPath去存。為了取得專案路徑而寫 

%>


<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    
<!-- Google Fonts -->
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+TC:wght@300;500&display=swap" rel="stylesheet">

	<title>加購商品上架頁 |廠商</title>


<style>
body{
    font-family: 'Noto Sans TC', sans-serif;

}
</style>




</head>

<body>
	<form method="post" enctype="multipart/form-data"
		action="${contextPath}/carExt/carext.do">
		<div class="container" style="margin-top: 30px;">
		

			<div class="img-thumbnail" style="padding: 20px;">
				<h4 class="bg-primary rounded text-white text-center"
					style="padding: 10px;">加購商品上架頁 |廠商</h4>

				<!-- 錯誤表列 -->
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>


				<div class="form-group">
					<label for="cext_cat_id">禮車加購商品類別</label> <select id="cext_cat_id"
						name="cext_cat_id" class="form-control">
						<option value="1">車頭彩</option>
						<option value="2">車門手把花</option>
						<option value="3">司機代駕/日</option>
						<option value="4">甘蔗/竹子吸盤固定架</option>
					</select>
				</div>

				<div class="form-group">
					<label for="cext_name">加購品名稱：</label> <input type="text"
						id="cext_name" name="cext_name"
						value="<%=(carExtVo == null) ? "車頭彩·拉拉熊" : carExtVo.getCext_name()%>"
						class="form-control" />
				</div>

				<div class="form-group">
					<label for="cext_price">價錢：</label> <input type="text"
						id="cext_price" name="cext_price"
						value="<%=(carExtVo == null) ? "500" : carExtVo.getCext_price()%>"
						class="form-control" />
				</div>
				
				<div class="form-group">
					<label>圖片上傳</label> <input type="file" name="cext_pic" multiple
						class="form-control" /> 
				</div>


				<input type="hidden" name="action" value="insert">
				<p>
					<input type="submit" value="上架" class="btn btn-primary" />
				</p>


			</div>
		</div>

	</form>
<!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" ></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" ></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" ></script>
</body>
</html>