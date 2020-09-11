<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.vender.model.*"%>

<%
session.invalidate();
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="refresh" content="30;url=<%=request.getContextPath()%>/front_end/home/home.jsp">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
    <link href="http://yuancheng-001-site1.1tempurl.com/styles/css/app.css" rel="stylesheet">
    <link href="http://yuancheng-001-site1.1tempurl.com/styles/css/common.css" rel="stylesheet">
    <link href="http://yuancheng-001-site1.1tempurl.com/styles/css/myshop.css" rel="stylesheet">
<meta charset="utf-8">
<title>婚禮導航</title>
</head>
<body>

<div class="card"  style="width: 440px;margin:90px auto;">
  <div class="card-body">
    <h5 class="card-title">註冊成功<br><br>請到註冊信箱查看密碼<br>並聯絡主管開通帳號<br><br>
     等待<a id='div1'></a>秒頁面自動跳轉</h5>
    <a href="<%=request.getContextPath()%>/front_end/home/home.jsp" class="btn btn-primary">手動轉跳首頁</a>
    <p>
    <br>
  </div>
</div>


<script type="text/javascript">

//設定倒數秒數
var t = 30;

//顯示倒數秒收
function showTime()
{
    t -= 1;
    document.getElementById('div1').innerHTML= t;
    
    if(t==0)
    {
    	return;
    }
    
    //每秒執行一次,showTime()
    setTimeout("showTime()",1000);
}

//執行showTime()
showTime();
</script>

</body>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
</html>

