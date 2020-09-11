<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
       <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<jsp:include page="/front_end/prod/head.jsp">
<jsp:param name="title" value="My orders (seller)"/>
</jsp:include>
<script src="<%=request.getContextPath() %>/js/prod/myOrders_seller.js" type="text/javascript" charset="utf-8"></script>
<style>
.myBg {
background-color:#cce6ff;
}
.btn {
	padding-top:0;
	padding-bottom:0;
}
.myFlex  div {
 width:80px;
}
form {
margin-block-end:0;
}

</style>
</head>
<body>
<%@ include file="/front_end/prod/header.jsp" %>
<%@ include file="/front_end/prod/header_bottom.jsp"%>
<%@ include file="/front_end/prod/error_modal.jsp"%>
<p style="display:none" id="order_status">${order_status }</p>
<div style="height:10%"></div>
<div class="container mt-2"><h2>賣方訂單管理</h2></div>
<nav class="navbar navbar-main  navbar-expand-lg navbar-light">
  <div class="container">
    <div class="collapse navbar-collapse" id="main_nav2">
      <ul class="navbar-nav w-100 d-flex justify-content-around" id="myNav">
        <li class="nav-item flex-fill">
          <a class="nav-link text-center" style="width:100%" href="<%=request.getContextPath()%>/order/order.do?action=getDifferentStatusOrders_seller&order_status=All">全部</a> 
<!--           <hr color="blue"> -->
        </li>
        
        <li class="nav-item flex-fill">
         <a class="nav-link text-center" href="<%=request.getContextPath()%>/order/order.do?action=getDifferentStatusOrders_seller&order_status=1">待出貨</a> 
        </li>
        <li class="nav-item flex-fill">
          <a class="nav-link text-center" href="<%=request.getContextPath()%>/order/order.do?action=getDifferentStatusOrders_seller&order_status=2">待收貨</a>
        </li>
        <li class="nav-item flex-fill">
          <a class="nav-link text-center" href="<%=request.getContextPath()%>/order/order.do?action=getDifferentStatusOrders_seller&order_status=3">完成</a>
        </li>
        <li class="nav-item flex-fill">
          <a class="nav-link text-center" href="<%=request.getContextPath()%>/order/order.do?action=getDifferentStatusOrders_seller&order_status=0">取消</a>
        </li>
      </ul>
    </div> <!-- collapse .// -->
  </div> <!-- container .// -->
</nav>
<c:forEach var="ordervo" varStatus="myCount" items="${orderList}">
<div class="accordion container mx-auto mt-2" id="accordionExample_${myCount.count }" >
<div class="col-md-12">
 <jsp:useBean id="membreSvc" class="com.membre.model.MembreService"/>
  <div class="card" id="myCard">
   
    <div class="card-header d-flex justify-content-start myParent myBg align-items-center" id="headingOne_${myCount.count }" data-toggle="collapse" data-target="#collapseOne_${myCount.count }" aria-expanded="true" aria-controls="collapseOne_${myCount.count }">
    	<span style="display:inline" class="ml-3">
	    	<svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-caret-up-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
			  <path d="M7.247 4.86l-4.796 5.481c-.566.647-.106 1.659.753 1.659h9.592a1 1 0 0 0 .753-1.659l-4.796-5.48a1 1 0 0 0-1.506 0z"/>
			</svg>
		</span>
 		<span style="display:none" class="ml-3">
 			<svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-caret-down-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
			  <path d="M7.247 11.14L2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z"/>
			</svg>
 		</span>
          <span class="col-3 text-center">訂單編號:${ordervo.order_no}</span><span class="col-2">總金額:${ordervo.total}</span><span>下訂時間:<fmt:formatDate value="${ordervo.order_date}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
         
          <span class="col-3 text-center">買家:${membreSvc.getOneById(ordervo.membre_id).getMem_name()}</span>
          <c:if test='${ordervo.order_status == "0"}'>
          			<span style="width:80px">已取消</span>
          </c:if>
          <c:if test='${ordervo.order_status == "1"}'>
          <button class='btn btn-light myBtn' data-toggle="modal" data-myTarget="${membreSvc.getOneById(ordervo.membre_id).getMembre_id()}" data-order_no="${ordervo.order_no }" data-target="#orderModal" id="myBtn">出貨</button>
          </c:if>   
           <c:if test='${ordervo.order_status == "2"}'>
					<span>待取貨</span>
           </c:if>
            <c:if test='${ordervo.order_status == "3"}'>
					<span>已完成</span>
           </c:if>
    </div>
    <div id="collapseOne_${myCount.count }" class="collapse show" aria-labelledby="headingOne_${myCount.count }" data-parent="#accordionExample_${myCount.count }">
    <jsp:useBean id="orderSvc" class="com.prod_order.model.OrderService"/>
	<jsp:useBean id="prodSvc" class="com.prod.model.ProdService"/>
    <c:forEach var="prodvo" items="${prodSvc.getAllByOrder_no(ordervo.order_no)}">
						<div class="w-100 d-flex justify-content-start align-items-center myFlex">
    
								<div class="img-wrap mx-3"><img src="<%= request.getContextPath() %>/prod/prod.do?action=getOne&prod_no=${prodvo.prod_no}&number=one" class="border img-sm"></div>
								<div class="prod_name align-middle text-center" style="width:15%">
									${prodvo.prod_name }
								</div>
								<div class="align-middle">
									<div class="price-wrap">$<span class="price px-1">${prodvo.price }</span></div>
								</div>
								<div class="w-25 ml-5">
									訂購數量 :<span class="price px-1">${orderSvc.getOneDetail(prodvo.prod_no, ordervo.order_no).order_qty }</span>
								</div>
							 
						</div> <!-- col.// -->
      </c:forEach>
    </div>
    </div>
  </div>
</div>
    </c:forEach>
<div class="modal fade" id="orderModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" >確認出貨?</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body d-flex justify-content-center">
        <a type="button" class="btn btn-primary mr-5" id="confirm">確定</a>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
      </div>
      <div class="modal-footer">
      </div>
    </div>
  </div>
</div>

</body>
</html>