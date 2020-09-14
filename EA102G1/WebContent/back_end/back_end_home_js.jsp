<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<script src="<%=request.getContextPath()%>/js/vendor.min.js"></script>
<script src="<%=request.getContextPath()%>/js/app.min.js"></script>
<script src="<%=request.getContextPath()%>/js/apexcharts.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery-jvectormap-1.2.2.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery-jvectormap-world-mill-en.js"></script>
<script src="<%=request.getContextPath()%>/js/demo.dashboard.js"></script>

<script type="text/javascript">

//管理員狀態
var ii_off = ${admvo.adm_1};//未開通狀態
//驗證狀態跳轉
function show()
{    
    if(ii_off==0)
    {
    	document.location.href="<%=request.getContextPath()%>/front_end/home/session_off_verification.jsp";		
    }  
}
show();
</script>