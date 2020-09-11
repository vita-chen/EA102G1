<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.dresscase.model.*,com.dressaddon.model.*"%>
<%@ page import="com.vender.model.*" %>

<%
Object account = session.getAttribute("vendervo");                  // 從 session內取出 (key) account的值
if (account == null) {                                             // 如為 null, 代表此user未登入過 , 才做以下工作
  response.sendRedirect(request.getContextPath()+"/front_end/vender/vender_login.jsp");   //*工作2 : 請該user去登入網頁(login.html) , 進行登入
  return;
}
%>
<!DOCTYPE html>
<html>
<head>
<title>廠商增加加購項目 addAddOn.jsp</title>
<meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="generator" content="Mobirise v5.0.29, mobirise.com">
  <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
  <link rel="shortcut icon" href="<%=request.getContextPath()%>/assets/images/logo5.png" type="image/x-icon">
  <meta name="description" content="">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/bootstrap/css/bootstrap.min2.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/bootstrap/css/bootstrap-grid.min.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/bootstrap/css/bootstrap-reboot.min.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/tether/tether.min.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/animatecss/animate.min.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/theme/css/style.css">
  <link rel="preload" as="style" href="<%=request.getContextPath()%>/assets/mobirise/css/mbr-additional.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/mobirise/css/mbr-additional.css" type="text/css">

<style>
.container{
	margin-top:50px
}
</style>
</head>
<body>
  <section class="mbr-section form1 cid-s9oXjSjn9k" id="form1-11">
    
    <div class="container">
        <div class="row justify-content-center">
            <div class="title col-12 col-lg-8">
                <h2 class="mbr-section-title align-center pb-3 mbr-fonts-style display-5">增加加購方案</h2>
                
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row justify-content-center">
            <div class="media-container-column col-lg-8">
                <!---Formbuilder Form--->
                <form action="<%=request.getContextPath()%>/front_end/addon/addon.do" method="POST" class="mbr-form">
                    <div class="row">
                        <div hidden="hidden" data-form-alert-danger="" class="alert alert-danger col-12">
                        </div>
                    </div>
                    <div class="dragArea row">
                        <div class="col-md-4  form-group" data-for="drcase_na">
                            <label for="name-form1-11" class="form-control-label mbr-fonts-style display-7">加購項目名稱</label>
                            <input type="text" name="dradd_na" required="required" class="form-control display-7" id="name-form1-11">
                        </div>
                        <div class="col-md-4  form-group" data-for="drcase_pr">
                            <label for="email-form1-11" class="form-control-label mbr-fonts-style display-7">加購項目價格</label>
                            <input type="text" name="dradd_pr" required="required" class="form-control display-7" id="email-form1-11">
                        </div>
                        
                        <!--下拉式選單:上架狀態 -->
                        <div data-for="dradd_st" class="col-md-4  form-group">
                            <label for="phone-form1-11" class="form-control-label mbr-fonts-style display-7">加購項目上架狀態</label>
                            <select name="dradd_st"  class="form-control display-7" id="phone-form1-11">
                            	<option value="1">上架</option>
      							<option value="0">下架</option>
                           	</select>
                        </div>
                        <!--下拉式選單:種類 -->
                        <div data-for="dradd_type" class="col-md-4  form-group">
                            <label for="phone-form1-11" class="form-control-label mbr-fonts-style display-7">加購項目種類</label>
                            	<jsp:useBean id="typeSvc" scope="page" class="com.dressaddtype.model.DressAddTypeService" />
                            		<select name="dradd_type"  class="form-control display-7" id="phone-form1-11">
                            		<!--從資料庫中，撈出所有種類-->
                            			<c:forEach var="typeVO" items="${typeSvc.all}">
                            				<option value="${typeVO.dradd_type}">${typeVO.dradd_type}</option>
                            			</c:forEach>
                           			</select>
                        </div>
                        
                        <div class="col-md-12 input-group-btn align-center">
                            <button type="submit" class="btn btn-primary btn-form display-4">確定刊登</button>
                        	<input type="hidden" name="action" value="insert">
                        	<input type="hidden" name="vender_id" value="${venderVO.vender_id }">
                        </div>
                    </div>
                </form><!---Formbuilder Form--->
            </div>
        </div>
    </div>
</section>


  <script src="<%=request.getContextPath()%>/assets/web/assets/jquery/jquery.min2.js"></script>
  <script src="<%=request.getContextPath()%>/assets/popper/popper.min.js"></script>
  <script src="<%=request.getContextPath()%>/assets/bootstrap/js/bootstrap.min.js"></script>
  <script src="<%=request.getContextPath()%>/assets/tether/tether.min.js"></script>
  <script src="<%=request.getContextPath()%>/assets/smoothscroll/smooth-scroll.js"></script>
  <script src="<%=request.getContextPath()%>/assets/viewportchecker/jquery.viewportchecker.js"></script>
  <script src="<%=request.getContextPath()%>/assets/theme/js/script.js"></script>
  
 <div id="scrollToTop" class="scrollToTop mbr-arrow-up"><a style="text-align: center;"><i class="mbr-arrow-up-icon mbr-arrow-up-icon-cm cm-icon cm-icon-smallarrow-up"></i></a></div>
    <input name="animation" type="hidden">
</body>
</html>