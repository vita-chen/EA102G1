<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"
	language="java"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.car.model.*"%>

<%
	CarVO carVO = (CarVO) request.getAttribute("carVO");
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


<title>禮車上架頁 |廠商</title>
<style>
body{
    font-family: 'Noto Sans TC', sans-serif;

}
</style>
</head>
<body>
	<form method="post" enctype="multipart/form-data"
		action="${contextPath}/car/car.do">
		<div class="container" style="margin-top: 30px;">

			<div class="img-thumbnail" style="padding: 20px;">
				<h4 class="bg-primary rounded text-white text-center"
					style="padding: 10px;">禮車上架頁 |廠商</h4>
				<div>
					<!-- 錯誤表列 -->
					<c:if test="${not empty errorMsgs}">
						<font style="color: red">請修正以下錯誤:</font>
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li style="color: red">${message}</li>
							</c:forEach>
						</ul>
					</c:if>

				</div>

				<div class="form-group">
					<label for="cbrand">車子品牌</label> <input type="text" id="cbrand"
						name="cbrand"
						value="<%=(carVO == null) ? "特斯拉" : carVO.getCbrand()%>"
						class="form-control" />
				</div>

				<div class="form-group">
					<label for="cmodel">車子型號：</label> <input type="text" id="cmodel"
						name="cmodel"
						value="<%=(carVO == null) ? "Model3" : carVO.getCmodel()%>"
						class="form-control" />
				</div>

				<div class="form-group">
					<label for="cintro">車子介紹：</label>
					<textarea id="cintro" name="cintro" class="form-control"> </textarea>
				</div>

				<div class="form-group">
					<label for="cprice">租一天的租金：</label> <input type="text" id="cprice"
						name="cprice"
						value="<%=(carVO == null) ? "10000" : carVO.getCprice()%>"
						class="form-control" />
				</div>

				<div class="form-group">
					<label>照片1</label> <input type="file" name="cpic" multiple
						class="form-control" /> 
						<label>照片2</label> <input type="file"
						name="cpic" multiple class="form-control" /> 
						<label>照片3</label> <input
						type="file" name="cpic" multiple class="form-control" />
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