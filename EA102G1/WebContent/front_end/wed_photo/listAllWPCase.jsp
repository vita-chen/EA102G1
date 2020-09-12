<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.wpcase.model.*"%>
<%
	List<WPCaseVO> list = (List<WPCaseVO>)request.getAttribute("casename_list");
	
	if(list == null){
		
		WPCaseService WPSvc = new WPCaseService();
		list = WPSvc.getAll();
		pageContext.setAttribute("list",list);
		
	}else{
			
		pageContext.setAttribute("list",list);
		
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>ListAllWedCase.jsp</title>
    <script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+TC:wght@300;500&display=swap" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/bootstrap_menu.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/menu_style.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/icofont/icofont.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/boxicons/css/boxicons.min.css" rel="stylesheet">
<style type="text/css">
	table {
	    width: 100%;
	}
	
	div.container-fluid {
	    border: 1px solid red;
	}

	.btn-warning {
	    padding: 1px 5px 1px 5px;
	}
	.xx{
	    float: right;
	}
	.yy{
	    padding-right: 0;
	}
	.container-fluid{
    	height: 600px;
	}
	.content_2{	 	
	    position: absolute;
	    bottom: 0px;
	    left: 50%;
	    right: 50%;
	 }
	 tbody tr > th{
    	width: 5%;
	}
</style>
</head>
<body>

<div class="container-fluid">
<p>方案篩選 Case select</p>
<div class="row">
<div class="col-lg-2 col-md-12">
                <!-- 左邊的條件篩選區 -->
                <div class="row">
                    <div class="col-12">
                       <a href="<%=request.getContextPath()%>/front_end/wed_photo/wp_home.jsp#search">搜尋方案</a> 
                        <form METHOD="post" ACTION="<%=request.getContextPath()%>/wed/wpcase.do">
                            <div class="row">
                                <div class="col">
                                    <input class="form-control form-control-sm" type="text" value="全天" name="search_case">
                                </div>
                                <div class="col yy">
                                    <button type="submit" class="btn btn-warning xx">送出</button>
                                    <input type="hidden" name="action" value="inquireCase">
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-12">
                        價格篩選
                        <form METHOD="post" ACTION="<%=request.getContextPath()%>/wed/wpcase.do">
                            <div class="row">
                                <div class="col">
                                    <input type="text" class="form-control form-control-sm" name="min" value="18000">
                                </div>
                                <div class="col">
                                    <input type="text" class="form-control form-control-sm" name="max" value="32000">
                                </div>
                                <button type="submit" class="btn btn-warning">送出</button>
                                <input type="hidden" name="action" value="inquireCasebyPrice">
                            </div>
                        </form>
                    </div>
                    <div class="col-12">
                       <a href="<%=request.getContextPath()%>/front_end/wed_photo/wp_home.jsp#search">地區搜尋</a> 
                        <form METHOD="post" ACTION="<%=request.getContextPath()%>/wed/wpcase.do">
                            <div class="row">
                                <div class="col">
                                    <input class="form-control form-control-sm" type="text" value="新北" name="search_case_addr">
                                </div>
                                <div class="col yy">
                                    <button type="submit" class="btn btn-warning xx">送出</button>
                                    <input type="hidden" name="action" value="inquireCasebyAddr">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
</div>
	<div class="col-10">
		<table  class="table table-hover tablerow">
			<thead>
				<tr>
				    <th scope="col">#</th>
				    <th scope="col">方案編號</th>
				    <th scope="col">方案名稱</th>
				    <th scope="col">上架日期</th>
				    <th scope="col">我要下訂</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="WPCaseVO" varStatus="count" items="${list}">
			<c:if test="${WPCaseVO.wed_photo_status == 0}">
				<tr>
					<td>${count.count}</td>
					<td><a href="<%=request.getContextPath()%>/wed/wpcase.do?action=getOne_CasePage&wed_photo_case_no=${WPCaseVO.wed_photo_case_no}" target="_blank">${WPCaseVO.wed_photo_case_no}</a></td>
					<td>${WPCaseVO.wed_photo_name}</td>
					<td><fmt:formatDate value="${WPCaseVO.wed_photo_addtime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>	
					<td><FORM METHOD="post" ACTION="<%=request.getContextPath()%>/wed/wpcase.do">
						     <input type="submit" value="我要下訂">
						     <input type="hidden" name="wed_photo_case_no"  value="${WPCaseVO.wed_photo_case_no}">
						     <input type="hidden" name="action"	value="place_an_order">
						</FORM>
					</td>
				</tr>
			</c:if></c:forEach>
			</tbody>
		</table>
	</div>    

</div>
	<div class="content_2"> <!-- 分頁框框 -->
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <li class="page-item Previous"><a class="page-link" tabindex="-1">Previous</a></li>
                <li class="page-item Next"><a class="page-link">Next</a></li>
            </ul>
        </nav>
    </div>
</div>
<!-- body 結束標籤之前，載入Bootstrap 的 JS 及其相依性安裝(jQuery、Popper) -->
<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath() %>/js/wp/tableSort.js" charset="utf-8" type="text/javascript"></script>
</body>
</html>