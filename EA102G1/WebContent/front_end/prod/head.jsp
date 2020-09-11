<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-store" />
<!-- IE可能不見得有效 -->
<META HTTP-EQUIV="EXPIRES" CONTENT="0">
<!-- 設定成馬上就過期 -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title><%=request.getParameter("title") %></title>
 <!-- Google Fonts -->
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+TC:wght@300;500&display=swap" rel="stylesheet">
<!-- jQuery -->
<script src="<%= request.getContextPath() %>/js/jquery-2.0.0.min.js" type="text/javascript"></script>
<!-- Bootstrap4 files-->
<script src="<%= request.getContextPath() %>/js/bootstrap.bundle.min.js" type="text/javascript"></script>
<link href="<%= request.getContextPath() %>/css/bootstrap.css" rel="stylesheet" type="text/css"/>

<!-- Font awesome 5 -->
<link href="<%= request.getContextPath() %>/fonts/fontawesome/css/all.min.css" type="text/css" rel="stylesheet">

<!-- custom style -->
<link href="<%= request.getContextPath() %>/css/ui.css" rel="stylesheet" type="text/css"/>
<link href="<%= request.getContextPath() %>/css/responsive.css" rel="stylesheet" media="only screen and (max-width: 1200px)" />

<!-- custom javascript -->
<script src="<%= request.getContextPath() %>/js/prod/script.js" type="text/javascript"></script>

<style>
	body{
    font-family: 'Noto Sans TC', sans-serif;
    font-size:18px;
    background-color:#d5f0f552;
} /* 統一全站字型 */

	.myItem {
		width:40px;
	}
</style>