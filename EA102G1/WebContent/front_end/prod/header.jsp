<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header class="section-header fixed-top" style="background-color: #1abaff;">

<p id="path" style="display:none"><%=request.getContextPath() %></p>
<p id="currentUser" style="display:none">${membrevo.membre_id }</p>
<section class="navbar-light">
<div class="container">
  <nav class="d-flex align-items-center flex-column flex-md-row">
    <ul class="nav mr-md-auto">
    </ul>

    <ul class="nav ">
    <li class="nav-item d-flex flex-column">
<div class="dropleft my-auto">
  <a id="notice" style="overflow-y:auto; height:80px;" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" data-display="static">
     	<svg id="notice" width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-bell-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
		  <path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zm.995-14.901a1 1 0 1 0-1.99 0A5.002 5.002 0 0 0 3 6c0 1.098-.5 6-2 7h14c-1.5-1-2-5.902-2-7 0-2.42-1.72-4.44-4.005-4.901z"/>
		</svg>
  </a>

  <div id="content"  height:30px;" class="dropdown-menu dropdown-menu-sm-right" aria-labelledby="dropdownMenuLink">
</div>
</div>
    </li>
        			<%String currentLocation = request.getRequestURI();
					String queryString = request.getQueryString();
					request.setAttribute("currentLocation", currentLocation);
					request.setAttribute("queryString", queryString);
					String sessionLocation = currentLocation +"?"+ queryString;
					session.setAttribute("sessionLocation", sessionLocation);
				%>
     <li  class="nav-item"><a href="<%=request.getContextPath()%>/prod/list.do?action=checkBuyList&location=${currentLocation }?${queryString}" class="nav-link px-2">  <i class="fa fa-heart"></i> Wishlist </a></li>
        <li  class="nav-item"><a href="<%=request.getContextPath()%>/order/shopping.do?action=goToCart&location=${currentLocation }?${queryString}" class="nav-link px-2"> <i class="fa fa-shopping-cart"></i> My Cart <span class="badge badge-pill badge-danger" id="shopping_cart_num">0</span> </a></li>
    <c:if test="${membrevo==null }">
    		<div class="widget-header icontext">
    		<c:choose>
    		<c:when test="${queryString ==null }">
    		<a href="<%=request.getContextPath()%>/front_end/membre/login.jsp?location=${currentLocation}">登入</a> |  
    		</c:when>
    		<c:otherwise>
						<a href="<%=request.getContextPath()%>/front_end/membre/login.jsp?location=${currentLocation }?${queryString}">登入</a> |  
			</c:otherwise>
			</c:choose>
						<a href="<%=request.getContextPath()%>/front_end/membre/regis.jsp">註冊</a>
					</div>
			
    </c:if>
    
    <c:if test="${membrevo !=null }">
    	<li  class="nav-item text-right myItem" style="margin-right:-20px"><a class="nav-link px-2"> 
    	<img width="100%"  class="rounded pt-1" src="<%=request.getContextPath() %>/membre/membre.do?action=getphoto&membre_id=${membrevo.membre_id }"></a></li>
<li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="http://example.com">${membrevo.mem_name}</a>
          <div class="dropdown-menu">
            <a class="dropdown-item" href="<%=request.getContextPath()%>/order/order.do?action=getDifferentStatusOrders_buyer&order_status=All">我的訂單</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/order/order.do?action=getDifferentStatusOrders_seller&order_status=All">我的訂單(賣家 開發用)</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/front_end/prod/myProds.jsp">我的商品</a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/membre/membre.do?action=logOut">登出</a>
          </div>
</li>
    </c:if>	
    </ul>
  </nav> <!-- nav .// --> 
</div> <!-- container //  -->
</section> <!-- header-top .// -->
</header> <!-- section-header.// -->