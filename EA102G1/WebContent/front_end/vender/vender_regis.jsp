<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.vender.model.*"%>

<%
	VenderVO venderVO = (VenderVO) request.getAttribute("venderVO");

Object phonee = session.getAttribute("phone");

String [] phone = {phonee.toString()};
request.setAttribute("phone", phone);

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>廠商註冊</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
    <link href="http://yuancheng-001-site1.1tempurl.com/styles/css/app.css" rel="stylesheet">
    <link href="http://yuancheng-001-site1.1tempurl.com/styles/css/common.css" rel="stylesheet">
    <link href="http://yuancheng-001-site1.1tempurl.com/styles/css/myshop.css" rel="stylesheet">
</head>
<body>
<%-- 錯誤表列 --%>
	<div class="container mx-auto h-auto mt-8" style="min-height: 100%">
		<div class="container mx-auto">
			<div class="flex flex-wrap justify-center">
				<div class="w-full max-w-sm">
					<div
						class="flex flex-col break-words bg-white border-2 rounded shadow-md">
						<div
							class="font-semibold bg-gray-200 text-gray-700 py-3 px-6 mb-0">廠商註冊</div>
						<form class="w-full p-6" method="POST"
							action="<%=request.getContextPath()%>/vender/vender.do"
							name="form1" enctype="multipart/form-data">
							<input type="hidden" name="_token"
								value="cBZ6DP971sTfePEQRX1kYYG6uCSqMhTke4Ik1Hh3">
							<div>
								<c:if test="${not empty errorMsgs}">
									<font style="color: red">請修正以下錯誤:</font>
									<ul>
										<c:forEach var="message" items="${errorMsgs}">
											<li style="color: red">${message}</li>
										</c:forEach>
									</ul>
								</c:if>
							</div>

							<div class="flex flex-wrap mb-6">
							
								<label for="name"
									class="block text-gray-700 text-sm font-bold mb-2"> 帳號:
								</label> <input class="form-input w-full"type="TEXT" name="ven_account" size="45" 
			 value="<%= (venderVO==null)? "123456" : venderVO.getVen_account()%>" />

							</div>

							<div class="flex flex-wrap mb-6">
								<label for="email"
									class="block text-gray-700 text-sm font-bold mb-2"> 密碼:
								</label> <input class="form-input w-full"type="password" name="ven_pwd" size="45"
			 value="<%= (venderVO==null)? "1234" : venderVO.getVen_pwd()%>" />

							</div>

							<div class="flex flex-wrap mb-6">
								<label for="address"
									class="block text-gray-700 text-sm font-bold mb-2">
									廠商名稱: </label> <input class="form-input w-full"type="TEXT" name="ven_name" size="45" 
			 value="<%= (venderVO==null)? "" : venderVO.getVen_name()%>" />

							</div>

							<div class="flex flex-wrap mb-6">

								<label for="縣市1"
									class="block text-gray-700 text-sm font-bold mb-2">
									廠商地址: </label>
									　
<select id="縣市1"name="addr1"></select>
<select id="鄉鎮市區1"name="addr2"></select>
<input class="form-input w-full" type="TEXT" name="addr3" size="45"
			  value="<%= (venderVO==null)?  "": venderVO.getVen_addr()%>" />
 
 
							</div>

							<div class="flex flex-wrap mb-6">
								<label for="password-confirm"
									class="block text-gray-700 text-sm font-bold mb-2"> 手機:
								</label> <input readonly="readonly" class="form-input w-full"type="TEXT" name="ven_phone" size="45"
			 value="${phone[0]}" />
							</div>

							<div class="flex flex-wrap mb-6">
								<label for="phone"
									class="block text-gray-700 text-sm font-bold mb-2">
									連絡人: </label> <input class="form-input w-full"type="TEXT" name="ven_contact" size="45"
			 value="<%= (venderVO==null)? "劉先生" : venderVO.getVen_contact()%>" /> <span id="phone-error"
									class="text-xs"></span>
							</div>

							<div>

								<div class="flex flex-wrap mb-6">
									<label for="phone"
										class="block text-gray-700 text-sm font-bold mb-2">
										Email: </label> <input class="form-input w-full"type="email" name="ven_mail" size="45"
			 value="<%= (venderVO==null)? "a0908280731@gmail.com" : venderVO.getVen_mail()%>" /></span>
								</div>

								<div>

									<div class="flex flex-wrap mb-6">
										<label for="phone"
											class="block text-gray-700 text-sm font-bold mb-2">
											上傳名片(審核一定要上傳!): </label> <input class="form-input w-full"type="file" name="ven_evidence_pic" size="45"
			 value="<%= (venderVO==null)? "100" : venderVO.getVen_evidence_pic()%>" />
										<span id="phone-error" class="text-xs"></span>
										<img style="height: 50%;width: 50%;border:0;"  />

									</div>

									<div class="flex flex-wrap">
										<button type="hidden" name="action" value="insert"
											class="inline-block align-middle text-center select-none border font-bold whitespace-no-wrap py-2 px-4 rounded text-base leading-normal no-underline text-gray-100 bg-blue-500 hover:bg-blue-700 ml-auto">
											註冊</button>

										<p class="w-full text-xs text-center text-gray-700 mt-8 -mb-4">
											已經有帳號了? <a
												class="text-blue-500 hover:text-blue-700 no-underline"
												href="<%=request.getContextPath()%>/front_end/vender/vender_login.jsp">
												點我登入 </a>
										</p>
									</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
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
        $('img').attr('src', e.target.result);
      };
      
      // 使用 readAsDataURL 將圖片轉成 Base64
      fr.readAsDataURL(file);
    });
  </script>
</html>  