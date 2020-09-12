
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 設定時間 -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<%@ page import="java.util.*"%>
<%@ page import="com.forum.model.*"%>
<%@ page import="com.membre.model.*"%>

<%
	ForumVO forumVO = (ForumVO) request.getAttribute("forumVO"); //ForumServlet.java (Concroller) 存入req的forumVO物件 (包括幫忙取出的forumVO, 也包括輸入資料錯誤時的forumVO物件)
	
	String currentLocation = request.getRequestURI();
	String queryString = request.getQueryString();
	request.setAttribute("currentLocation", currentLocation);
	request.setAttribute("queryString", queryString);
	
	MembreVO membrevo = (MembreVO) session.getAttribute("membrevo");
	if (membrevo==null) {
		response.sendRedirect(request.getContextPath()+"/front_end/membre/login.jsp?location="+currentLocation+"?"+queryString);
	} else {
%>

<jsp:useBean id="memSvc" scope="page" class="com.membre.model.MembreService" />

<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>update_forum_input</title>

  <!-- Bootstrap core CSS -->
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom fonts for this template -->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href='https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
  <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>

  <!-- Custom styles for this template -->
  <link href="css/clean-blog.min.css" rel="stylesheet">

</head>

<body>

   <!-- Navigation -->
  <nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
    <div class="container">
    
<!--     	left top start -->
      <a class="navbar-brand" href="<%=request.getContextPath()%>/front_end/home/home.jsp">Wedding Navi</a>
<!--     	left top end -->      
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        Menu
        <i class="fas fa-bars"></i>
      </button>
      
<!--       top bar start -->
      <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/front_end/home/home.jsp">首頁</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/front_end/prod/index.jsp">二手商城</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/back_end/back_end_home.jsp">後台</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/front_end/forum/listAllForum.jsp">討論區</a>
          </li>
          
<!-- login start -->  			
					
     <c:if test="${membrevo==null }">
    		<div class="widget-header icontext">
    		<c:choose>
    		<c:when test="${queryString ==null }">
    		<a href="<%=request.getContextPath()%>/front_end/membre/login.jsp?location=${currentLocation}">Login</a> |  
    		</c:when>
    		<c:otherwise>
						<a href="<%=request.getContextPath()%>/front_end/membre/login.jsp?location=${currentLocation }?${queryString}">Login</a> |  
			</c:otherwise>
			</c:choose>
						<a href="<%=request.getContextPath()%>/front_end/membre/regis.jsp"> Register</a>
			</div>
			
    </c:if>
    
    <c:if test="${membrevo !=null }">
    	<li class="nav-item text-right myItem" style="margin-right:-20px"><a class="nav-link px-2"> <img style="width:20px; height:20px;" class="rounded" src="<%=request.getContextPath() %>/membre/membre.do?action=getphoto&membre_id=${membrevo.membre_id }"></a></li>
		<li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="http://example.com">${membrevo.mem_name}</a>
          <div class="dropdown-menu">
          	<a class="dropdown-item" href="<%=request.getContextPath()%>/front_end/membre_order/membre_order_home.jsp">會員訂單</a>
            <a class="dropdown-item" href="#">Profile setting</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/order/order.do?action=getDifferentStatusOrders_buyer&order_status=All">My orders</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/order/order.do?action=getDifferentStatusOrders_seller&order_status=All">My orders (Seller Develop Only)</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/front_end/prod/myProds.jsp">My products</a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/membre/membre.do?action=logOut">登出</a>
          </div>
		</li>
    </c:if>	                                      
<!-- login end -->          
          
        </ul>
      </div>
<!--       top bar end -->
      
    </div>
  </nav>

  <!-- Page Header -->
  <header class="masthead" style="background-image: url('img/mainimg01_20501200.jpg')">
    <div class="overlay"></div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="site-heading">
            <h1>Wedding Navi</h1>
            <span class="subheading">A Blog host by Professional team</span>
          </div>
        </div>
      </div>
    </div>
  </header>

<!-- to do -->
  <!-- Main Content -->
  <div class="container">
    <div class="row justify-content-center">
    
      <div>
				<%-- 錯誤表列 --%>
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>				

				<FORM METHOD="post" ACTION="forum.do" name="form1">
					<table>
						<tr>
							<td>文章編號:<font color=red><b>*</b></font></td>
							<td><%=forumVO.getForum_id()%></td>
						</tr>
						<tr>
							<td>會員編號:<font color=red><b>*</b></font></td>
							<td><%=forumVO.getMembre_id()%></td>
						</tr>
						<tr>
							<td>文章類別:</td>
							<td>
								<select name="forum_class" id="forum_class">
									<option value="交流提問" selected>交流提問</option>
							  		<option value="閒聊" >閒聊</option>
							  		<option value="婚紗">婚紗</option>
							  		<option value="婚攝">婚攝</option>
							  		<option value="禮車">禮車</option>
							  		<option value="婚禮心事">婚禮心事</option>
							  	</select>
							</td>
						</tr>
						<tr>
							<td>文章標題:</td>
							<td><input type="TEXT" name="forum_title" size="45"
								value="<%=forumVO.getForum_title()%>" /></td>
						</tr>
						<tr>
							<td>文章內容:</td>
						</tr>
						<tr>
							<td colspan="2"><textarea name="forum_content">${forumVO.forum_content}</textarea>
							</td>
						</tr>


					</table>
					<!-- ckeditor -->
					<script src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
					<script>
						CKEDITOR.replace('forum_content', {
							height : [ '500px' ]
						});
					</script>

						<br> 
						<input type="hidden" name="action" value="update">
						<input type="hidden" name="forum_id" value="${forumVO.forum_id}"> 
						<input type="hidden" name="membre_id" value="${forumVO.membre_id}"> 
						<input type="submit" value="送出修改">
				</FORM>
			</div>
      
    </div>
  </div>

  <hr>
<!-- to do -->

  <!-- Footer -->
  <footer>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <ul class="list-inline text-center">
            <li class="list-inline-item">
              <a href="#">
                <span class="fa-stack fa-lg">
                  <i class="fas fa-circle fa-stack-2x"></i>
                  <i class="fab fa-twitter fa-stack-1x fa-inverse"></i>
                </span>
              </a>
            </li>
            <li class="list-inline-item">
              <a href="#">
                <span class="fa-stack fa-lg">
                  <i class="fas fa-circle fa-stack-2x"></i>
                  <i class="fab fa-facebook-f fa-stack-1x fa-inverse"></i>
                </span>
              </a>
            </li>
            <li class="list-inline-item">
              <a href="#">
                <span class="fa-stack fa-lg">
                  <i class="fas fa-circle fa-stack-2x"></i>
                  <i class="fab fa-github fa-stack-1x fa-inverse"></i>
                </span>
              </a>
            </li>
          </ul>
          <p class="copyright text-muted">Copyright &copy; Your Website 2020</p>
        </div>
      </div>
    </div>
  </footer>

  <!-- Bootstrap core JavaScript -->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Custom scripts for this template -->
  <script src="js/clean-blog.min.js"></script>

</body>

</html>
<% }%>