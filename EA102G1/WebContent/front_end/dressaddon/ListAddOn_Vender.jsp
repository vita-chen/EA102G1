<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.dresscase.model.*,com.vender.model.*,com.dressaddon.model.*"%>

<%
	Object account = session.getAttribute("vendervo");                  // 從 session內取出 (key) account的值
	if (account == null) {                                             // 如為 null, 代表此user未登入過 , 才做以下工作
	  response.sendRedirect(request.getContextPath()+"/front_end/vender/vender_login.jsp");   //*工作2 : 請該user去登入網頁(login.html) , 進行登入
	  return;
	}
	
	VenderVO vvO = (VenderVO)session.getAttribute("vendervo");	
	
    DressAddOnService daSvc = new DressAddOnService();
    List<DressAddOnVO> list = daSvc.findByVender(vvO.getVender_id());
    pageContext.setAttribute("list",list);
    
    VenderService vSvc = new VenderService();
    VenderVO vVO = vSvc.findByPrimaryKey(vvO.getVender_id());
    request.setAttribute("venderVO",vVO);

	
%>
<!DOCTYPE html>
<html>
<head>

<!--第71行Vender_id須改成從session中的venderVO拿-->
<title>婚紗加購項目管理 - listAddOn_Vender.jsp</title>

<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.4.1.min.js"></script>
<style>
	.container{
		margin-top:50px
	}
	div.container {
    border: 2px solid pink;
	}
	.btn {
		padding: 2px 10px 10px 10px;
	  	background-color: pink;
	  	border: 1px;
	  	color: grey;
	  	text-align: center;
	  	text-decoration: none;
	  	display: inline-block;
	  	font-size: 14px;
	}
	.range{
		padding-right:0;
		padding-left:0;
	} 
	input {
	  display: none;
	}
	label {
	  font-size: 30px;
	}
	input:checked ~ label {
	  color: orange;
	}
</style>
</head>

<!--table start -->
<div class="container">
        <div class="row">
            <div class="col-12">
            	<div>
            	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front_end/addon/addon.do">
				  <input type="submit" class="btn btn-primary" value="刊登新加購項目">
				   <input type="hidden" name="vender_id" value="${venderVO.vender_id }">
				   <input type="hidden" name="action"	value="seeInsert">
				 </FORM>
            	</div>
				<table class="table table-hover">
					<thead>
					 <tr>
						<th scope="col">#</th>
						<th scope="col">項目編號</th>
						<th scope="col">項目名稱</th>
						<th scope="col">項目價格</th>
						<th scope="col">項目上架狀態</th>
						<th scope="col">項目種類</th>
						<th scope="col"></th>
					</tr>
					</thead>	
	 	<tbody>
	 <%@ include file="page1.file" %>
	<c:forEach var="addonVO" varStatus="count" items="${list}">
		
		<tr>
			<th scope="row" width="5"><span>${count.count}</span></th>
			<td width="20">${addonVO.dradd_id}</td>
			<td width="100">${addonVO.dradd_na}</td>
			<td>${addonVO.dradd_pr}</td>
			<td width="150">
			<c:if test="${addonVO.dradd_st==0}">下架中</c:if>
			<c:if test="${addonVO.dradd_st==1}">上架中</c:if>
			<td width="150">${addonVO.dradd_type}</td>
			<td>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front_end/addon/addon.do">
			  <input type="submit" class="btn btn-primary" value="更新項目資料">
			   <input type="hidden" name="dradd_id" value="${addonVO.dradd_id}">
			   <input type="hidden" name="action"	value="getOne_For_Update">
			 </FORM>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</div>
</div>
</div>
</body>
</html>