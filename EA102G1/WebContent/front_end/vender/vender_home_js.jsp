<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.vender.model.*"%>

<script type="text/javascript">

//廠商狀態
var i_off = ${vendervo.is_vaild};//封鎖狀態
var x_off = ${vendervo.is_enable};//驗證狀態
//驗證狀態跳轉
function show()
{    
    if(i_off==0)
    {
    	document.location.href="<%=request.getContextPath()%>/front_end/home/session_off_blockade.jsp";		
    }else if(x_off==0)
    {
    	document.location.href="<%=request.getContextPath()%>/front_end/home/session_off_verification.jsp";
    }
    
}

show();

</script>

	<!--   Core JS Files   -->
	<script src="<%=request.getContextPath()%>/assets/js/core/jquery.3.2.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/assets/js/core/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/assets/js/core/bootstrap.min.js"></script>

	<!-- jQuery UI -->
	<script src="<%=request.getContextPath()%>/assets/js/plugin/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
	<script src="<%=request.getContextPath()%>/assets/js/plugin/jquery-ui-touch-punch/jquery.ui.touch-punch.min.js"></script>

	<!-- jQuery Scrollbar -->
	<script src="<%=request.getContextPath()%>/assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>


	<!-- Chart JS -->
	<script src="<%=request.getContextPath()%>/assets/js/plugin/chart.js/chart.min.js"></script>

	<!-- jQuery Sparkline -->
	<script src="<%=request.getContextPath()%>/assets/js/plugin/jquery.sparkline/jquery.sparkline.min.js"></script>

	<!-- Chart Circle -->
	<script src="<%=request.getContextPath()%>/assets/js/plugin/chart-circle/circles.min.js"></script>

	<!-- Datatables -->
	<script src="<%=request.getContextPath()%>/assets/js/plugin/datatables/datatables.min.js"></script>

	<!-- Bootstrap Notify -->
	<script src="<%=request.getContextPath()%>/assets/js/plugin/bootstrap-notify/bootstrap-notify.min.js"></script>

	<!-- jQuery Vector Maps -->
	<script src="<%=request.getContextPath()%>/assets/js/plugin/jqvmap/jquery.vmap.min.js"></script>
	<script src="<%=request.getContextPath()%>/assets/js/plugin/jqvmap/maps/jquery.vmap.world.js"></script>

	<!-- Sweet Alert -->
	<script src="<%=request.getContextPath()%>/assets/js/plugin/sweetalert/sweetalert.min.js"></script>

	<!-- Atlantis JS -->
	<script src="<%=request.getContextPath()%>/assets/js/atlantis.min.js"></script>
