<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.vender.model.*"%>

<%
	VenderVO venderVO = (VenderVO) request.getAttribute("venderVO");

Object account = session.getAttribute("vendervo"); 

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>廠商資料修改</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
    <link href="http://yuancheng-001-site1.1tempurl.com/styles/css/app.css" rel="stylesheet">
    <link href="http://yuancheng-001-site1.1tempurl.com/styles/css/common.css" rel="stylesheet">
    <link href="http://yuancheng-001-site1.1tempurl.com/styles/css/myshop.css" rel="stylesheet">
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
<form  method="POST" action="<%=request.getContextPath()%>/vender/vender.do"
	   name="form1" enctype="multipart/form-data">
	   
  <div class="form-group row">
    <label for="staticEmail" class="col-sm-1 col-form-label">廠商編號</label>
    <div class="col-sm-3">
      <input type="text" readonly class="form-control-plaintext" name="vender_id" value="${vendervo.vender_id}">
    </div>
  </div>
  <div class="form-group row">
    <label for="inputPassword" class="col-sm-1 colcol-form-label">密碼</label>
    <div class="col-sm-3">
      <input type="password" class="form-control" name="ven_pwd" value="${vendervo.ven_pwd}">
    </div>
  </div>
  
    <div class="form-group row">
    <label for="inputPassword" class="col-sm-1 col-form-label">廠商名稱</label>
    <div class="col-sm-3">
      <input type="text" class="form-control" name="ven_name" value="${vendervo.ven_name}">
    </div>
  </div>
    <div class="form-group row">
    <label for="inputPassword" class="col-sm-1 col-form-label">住址</label>
    <div class="col-sm-3">
<select id="縣市1"name="addr1"></select>
<select id="鄉鎮市區1"name="addr2"></select>
<input class="form-control" type="TEXT" name="addr3" size="45" value="${vendervo.ven_addr}" />
    </div>
  </div>
    <div class="form-group row">
    <label for="inputPassword" class="col-sm-1 col-form-label">電話</label>
    <div class="col-sm-3">
      <input type="text" class="form-control" name="ven_phone" value="${vendervo.ven_phone}">
    </div>
  </div>
    <div class="form-group row">
    <label for="inputPassword" class="col-sm-1 col-form-label">負責人</label>
    <div class="col-sm-3">
      <input type="text" class="form-control" name="ven_contact" value="${vendervo.ven_contact}">
    </div>
  </div>
  <div class="form-group row">
    <label for="inputPassword" class="col-sm-1 col-form-label">EMAIL</label>
    <div class="col-sm-3">
      <input type="text" class="form-control" name="ven_mail" value="${vendervo.ven_mail}">
    </div>
  </div>
  <div class="form-group row">
    	<label for="exampleFormControlFile1"class="col-sm-1 col-form-label">名片</label>
    	<div class="col-sm-2">
			<input type="file" class="" name="ven_evidence_pic">
		</div>
  </div>
  <div class="form-group row">
  <img id="imgg" style="height: 30%;width: 30%;border:0;"
	src="<%=request.getContextPath() %>/vender/vender.do?action=getphot&vender_id=${vendervo.vender_id}">
  </div>

<button type="submit" class="btn btn-primary col-sm-4" name="action" value="update_modify">修改資料</button>

</form>

	<br>



</body>
<script src="<%=request.getContextPath()%>/js/vender/regis.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>

  <script>
    $('input').on('change', function(e){      
      const file = this.files[0];
      
      const fr = new FileReader();
      fr.onload = function (e) {
        $('#imgg').attr('src', e.target.result);
      };
      
      // 使用 readAsDataURL 將圖片轉成 Base64
      fr.readAsDataURL(file);
    });
  </script>
</html>  