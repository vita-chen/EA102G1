<%@page import="com.membre.model.MembreVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.dresscasetrack.model.*,com.dresscase.model.*"%>

<%
	MembreVO membrevo = (MembreVO)session.getAttribute("membrevo");

	if(membrevo == null){
	String url = request.getContextPath() +"/front_end/dressorder/ListAllTrack_membre.jsp";
	session.setAttribute("location",url);
	response.sendRedirect(request.getContextPath()+"/front_end/membre/login.jsp");
    return;
	}

    DressCaseTrackService dcSvc = new DressCaseTrackService();
    List<DressCaseTrackVO> list = dcSvc.findByMembre(membrevo.getMembre_id());
    pageContext.setAttribute("list",list);
%>

<html>
<head>
<title>婚紗收藏管理 - listTrack.jsp</title>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
  
  table {
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<html>
<head>
<meta charset="UTF-8">
<title>ListDressOrder_vender</title>

<link rel="stylesheet" href="../../vendors/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath() %>/vendors/jquery/jquery-3.4.1.min.js"></script>
<style type="text/css">

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
	
	td{
	text-align: left;
	}
</style>
</head>
<body>

<!-- 顯示錯誤表列 -->
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<div class="container">
        <div class="row">
            <div class="col-12">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">收藏方案編號</th>
                            <th scope="col">收藏方案名稱</th>      
                            <th scope="col">前往方案</th> 
                            <th scope="col">刪除</th>                   
                        </tr>
                        
                    </thead>
                    <tbody >
                    <c:forEach var="caseVO" varStatus="count" items="${list}"> 
                        <tr>
                            <th scope="row"><span>${count.count}</span></th>
                            <td >${caseVO.drcase_id}</td>
                            <!--顯示相對應的方案名稱 -->
                            <jsp:useBean id="drSvc" class="com.dresscase.model.DressCaseService"></jsp:useBean>
                            	<c:forEach var="drVO" items="${drSvc.all}"> 
                            		<c:if test="${drVO.drcase_id eq caseVO.drcase_id}">
		                          		<td>${drVO.drcase_na}</td>
                           			</c:if>
                           		</c:forEach>
                            
                            <td>
                            <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front_end/dresscase/dress.do" style="margin-bottom: 0px;">
							     <button type="submit" value="查看方案">查看方案</button>
							     <input type="hidden" name="drcase_id"  value="${caseVO.drcase_id}">
							     <input type="hidden" name="action"	value="getOne">
					    	</FORM>
				    		</td>
				    		<!--取消收藏-->
				    		<td>
				    		<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front_end/dresstrack/track.do" style="margin-bottom: 0px;">
							     <button type="submit" value="取消">取消收藏</button>
							     <input type="hidden" name="drcase_id"  value="${caseVO.drcase_id}">
							     <input type="hidden" name="action"	value="delTrack">
							     <input type="hidden" name="membre_id" value="${membrevo.membre_id}">
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