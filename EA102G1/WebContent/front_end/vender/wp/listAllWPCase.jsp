<%@page import="com.vender.model.VenderVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.wpcase.model.*"%>
<%
	WPCaseService WPSvc = new WPCaseService();
	List<WPCaseVO> list = WPSvc.getAll();
	pageContext.setAttribute("list", list);
	
// 	VenderVO vender = (VenderVO)session.getAttribute("vendervo");
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>ListAll WedPhoto Case - 婚禮攝影服務 全部方案</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/vendors/bootstrap/css/bootstrap.min.css">
<style type="text/css">
table {
    width: 100%;
}
.container{
	margin-top:10px;
	margin-left: 0px;
}
.btn-outline-info{
	margin-bottom:10px;
}
.btn-info{
	padding: 1px 15px 1px 15px;
}
.delete{
	border-radius: 4px;
}
</style>
</head>
<body>
<div class="container">
<div class="row">
<div class="col-12">
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front_end/vender/wp/vender_addWPcase.jsp">
<button type="submit" class="btn btn-outline-info">New Case</button>
</FORM>
</div>
</div>
<div class="row">
<div class="col-12">
<table  class="table table-hover" id="tableSort">		
<thead>
	<tr>
	    <th scope="col" data-type="num">#</th>
	    <th scope="col" data-type="string">方案編號</th>
	    <th scope="col" data-type="string">方案名稱</th>
	    <th scope="col" data-type="date">上架日期</th>
	    <th scope="col" data-type="string">價格</th>
	    <th scope="col" data-type="string">狀態</th>
	    <th scope="col">修改刪除</th>
	</tr>
</thead>
<tbody>
<c:forEach var="WPCaseVO" varStatus="count" items="${list}">
<c:if test="${WPCaseVO.vender_id == vendervo.vender_id}">
	<tr>
		<th scope="row"></th>
		<td><a href="<%=request.getContextPath()%>/wed/wpcase.do?action=getOne_CasePage&wed_photo_case_no=${WPCaseVO.wed_photo_case_no}" target="_blank">${WPCaseVO.wed_photo_case_no}</a></td>
		<td>${WPCaseVO.wed_photo_name}</td>
		<td><fmt:formatDate value="${WPCaseVO.wed_photo_addtime}" pattern="yyyy-MM-dd HH:mm" /></td>			
		<td><fmt:formatNumber value="${WPCaseVO.wed_photo_price}" pattern="#,###"/></td>
		<td><c:if test="${WPCaseVO.wed_photo_status == 0}"><c:out value="上架"/></c:if>
			<c:if test="${WPCaseVO.wed_photo_status == 1}"><c:out value="下架"/></c:if>
		</td>
		<td>
			<div class="btn-group" role="group" aria-label="Basic example">
	            <form METHOD="post"	ACTION="<%=request.getContextPath()%>/wed/wpcase.do">
	                <button type="submit" class="btn btn-info">修改</button>
	                <input type="hidden" name="wed_photo_case_no" value="${WPCaseVO.wed_photo_case_no}">
					<input type="hidden" name="action" value="getone_for_update">
	            </form>
            	<button type="button" class="btn btn-info delete" value="${WPCaseVO.wed_photo_case_no}">刪除</button>            
        	</div>
		</td>
		
<%-- 		<FORM METHOD="post"	ACTION="<%=request.getContextPath()%>/wed/wpcase.do" style="float:left;"> --%>
<!-- 				<input type="submit" value="修改"> -->
<%-- 				<input type="hidden" name="wed_photo_case_no" value="${WPCaseVO.wed_photo_case_no}"> --%>
<!-- 				<input type="hidden" name="action" value="getone_for_update"></FORM> -->
<%-- 			<FORM METHOD="post"	ACTION="<%=request.getContextPath()%>/wed/wpcase.do" style="margin-left: 10px;"> --%>
<!-- 				<input type="submit" value="刪除"> -->
<%-- 				<input type="hidden" name="wed_photo_case_no" value="${WPCaseVO.wed_photo_case_no}"> --%>
<!-- 				<input type="hidden" name="action" value="delete"></FORM> -->
		
	</tr>
</c:if>
</c:forEach>
</tbody>
</table>
</div>
</div>
</div>

<!-- body 結束標籤之前，載入Bootstrap 的 JS 及其相依性安裝(jQuery、Popper) -->
<script src="<%=request.getContextPath() %>/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath() %>/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath() %>/vendors/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath() %>/js/wp/tableSort.js" charset="utf-8" type="text/javascript"></script>
</body>
<script type="text/javascript">
$(document).ready(function(){	

	$("tbody > tr").each(function(i,item){
		$(this).children().eq(0).html('<span class="badge badge-info">'+(i+1)+'</span>');
	})	
	
	var url = (window.location.href).split('/')[0]+'/'+(window.location.href).split('/')[1]+'/'+(window.location.href).split('/')[2]+'/'+(window.location.href).split('/')[3]

	$(".delete").click(function(){
		var thisone = $(this).val();
		
		$(".delete").each(function(i){
			if($(this).val() == thisone){
				console.log('第'+i+'個'); //要+1
				var selector = 'tbody tr:nth-child('+(i+1)+')';
			    var thistr = document.querySelector(selector);
			    console.log(thistr);
			    
			    var ans = confirm('確定刪除方案嗎？'); // true or false
			    console.log(ans);
				if(ans){
					$.ajax({
						 type: "POST",
						 url: url + "/wed/wpcase.do",
						 data: {
							 action:"delete",
							 wed_photo_case_no:$(this).val()					
						 },
						 success: function (data){
							 if(data == '成功'){
								 alert(thisone+'刪除成功!');
								 thistr.remove();
							 }else{
								 alert('此筆方案已產生訂單 無法刪除!')
							 }
							 
						 }
					})			
				};
			}			
		})
		
		
		
	})
})
</script>
</html>