<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>廠商登入</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
    <link href="http://yuancheng-001-site1.1tempurl.com/styles/css/app.css" rel="stylesheet">
    <link href="http://yuancheng-001-site1.1tempurl.com/styles/css/common.css" rel="stylesheet">
    <link href="http://yuancheng-001-site1.1tempurl.com/styles/css/myshop.css" rel="stylesheet">
</head>
<body>
    <div class="container mx-auto h-auto mt-48" style="min-height: 100%">
        <div class="container mx-auto">
        <div class="flex flex-wrap justify-center">
            <div class="w-full max-w-sm">
                <div class="flex flex-col break-words bg-white border border-2 rounded shadow-md">

                    <div class="font-semibold bg-gray-200 text-gray-700 py-3 px-6 mb-0">
                       	 廠商登入
                    </div>

						<form class="w-full p-6" method="POST"
							action="<%=request.getContextPath()%>/vender/vender.do"
							>
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
								<label 
									class="block text-gray-700 text-sm font-bold mb-2"> 帳號:
								</label> <input type="text" class="form-input w-full "
									name="ven_account">

							</div>

							<div class="flex flex-wrap mb-6">
								<label 
									class="block text-gray-700 text-sm font-bold mb-2"> 密碼:
								</label> <input type="password" class="form-input w-full "
									name="ven_pwd">

							</div>

							<div class="flex flex-wrap items-center">
								<button
									class="inline-block align-middle text-center select-none border font-bold whitespace-no-wrap py-2 px-4 rounded text-base leading-normal no-underline text-gray-100 bg-blue-500 hover:bg-blue-700 ml-auto"
									name="action" value="vender_login">登入</button>
									

								<p class="w-full text-xs text-center text-gray-700 mt-8 -mb-4">
									還沒有帳號? <a
										class="text-blue-500 hover:text-blue-700 no-underline"
										href="<%=request.getContextPath()%>/front_end/vender/vender_regis_phone.jsp">
										點我註冊</a>
								</p>
							</div>
						</form>

					</div>
            </div>
        </div>
    </div>
    </div>

</body>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
</html>  