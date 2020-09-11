<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<section class="border-bottom" style="margin-top:40px">
<nav class="navbar navbar-main  navbar-expand-lg navbar-light"  style="background-color: #1abaff">
  <div class="container" >
  	<a class="navbar-brand" href="<%=request.getContextPath()%>/front_end/home/home.jsp"><img src="<%=request.getContextPath() %>/img/logo-transparent(1450_400).png" class="logo rounded mx-auto d-block"></a>
<!--     <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#main_nav2" aria-controls="main_nav" aria-expanded="false" aria-label="Toggle navigation"> -->
<!--       <span class="navbar-toggler-icon"></span> -->
<!--     </button> -->

    <div class="navbar col-md-8 mx-auto" >
      <ul class="navbar-nav w-100 d-flex justify-content-around">
        <li class="nav-item">
          <a class="nav-link" href="<%=request.getContextPath()%>/front_end/home/home.jsp">禮車租借</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="<%=request.getContextPath()%>/front_end/carOrder/browseAllCar.jsp">婚禮攝影</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="<%=request.getContextPath()%>/front_end/dresscase/DressHome.jsp">婚紗租借</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="<%=request.getContextPath()%>/front_end/prod/select_page.jsp">商城</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="<%=request.getContextPath()%>/front_end/membre_order/membre_order_home.jsp">會員專區</a>
        </li>
      </ul>
<!--       <form class="form-inline my-2 my-lg-0"> -->
<!-- 		    <div class="input-group"> -->
<!--           <input type="text" class="form-control" placeholder="Search"> -->
<!--           <div class="input-group-append"> -->
<!--             <button class="btn btn-primary" type="submit"> -->
<!--               <i class="fa fa-search"></i> -->
<!--             </button> -->
<!--           </div> -->
<!--         </div> -->
<!-- 		  </form> -->
    </div> <!-- collapse .// -->
  </div> <!-- container .// -->
</nav>
</section> <!-- header-main .// -->