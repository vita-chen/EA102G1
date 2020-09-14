<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<jsp:include page="/front_end/prod/head.jsp">
<jsp:param name="title" value="wait page"/>
</jsp:include>
</head>
<body>
<%@ include file="/front_end/prod/header.jsp" %>
<div class="container  ">
	 
 	<div class="modal w-100 h-75 d-flex justify-content-center align-items-center" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
	<div class="modal-content" style="background-color:gray">

  	<div class="modal-body" style="color:white">
   	等待<a id='div1'></a>秒頁面自動跳轉
  	</div>
 
	</div>
  </div>
</div>
</div>


<script type="text/javascript">
$('.modal').show();
//設定倒數秒數
var t = 4;
console.log(path);
//顯示倒數秒收
function showTime()
{
	t -= 1;
	document.getElementById('div1').innerHTML= t;
	if(t <= 0)
	{
   	 location.href=path +"/front_end/order/orderSuccess.jsp";
	}
    
	//每秒執行一次,showTime()
	setTimeout("showTime()",1000);
}

//執行showTime()
     showTime();
</script>

</body>
</html>