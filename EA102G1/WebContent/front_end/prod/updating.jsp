<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="head.jsp">
<jsp:param name="title" value="Edit product"/>
</jsp:include>
<style>
 #addPic, input[value="送出修改"] {
    margin: 10px;
    width: 90%;
    margin-left: 15px;
    color: white;
    background-color: #52a3ea;
    letter-spacing: 0.5rem;
    font-weight: 800;
 }
  label.btn {
 	width: 90%;
    margin-top: 20px;
    letter-spacing: 0.5rem;
 }
</style>
</head>
<body>
<%@ include file="/front_end/prod/header.jsp" %>
<%@ include file="/front_end/prod/header_bottom.jsp"%>
<%@ include file="/front_end/prod/error_modal.jsp"%>
<section class="section-content padding-y">
<div class="container">

<div class="row">
	<aside class="col-md-4 mt-5">
			<div class="card mx-auto" style="max-width: 380px;">
      <div class="card-body">
      <h4 class="card-title mb-4">修改商品</h4>
      <form action="<%=request.getContextPath() %>/prod/prod.do" method="post">
			<label>名稱</label>
          <div class="form-group">
          
			 <input type="text" class="form-control" name="prod_name" value="${prodvo.prod_name}" maxlength="20">
          </div> <!-- form-group// -->
          <label>價格</label>
          <div class="form-group">
          <input type="text" class="form-control" name="price" value="${prodvo.price }" maxlength="6">
          </div> <!-- form-group// -->
          <label>數量</label>
            <div class="form-group">
			<input type="text" class="form-control" name="prod_qty" value="${prodvo.prod_qty }" maxlength="3">
          </div> <!-- form-group// -->
          		<jsp:useBean id="typeDao" class="com.prod_type.model.TypeDAO"/>
				<div class="form-group">
					  <label>類別 : </label>
					<select class="ml-3" name="type_no">
					<c:forEach var="typevo" items="${typeDao.all }">
						<option value="${typevo.type_no }" ${(prodvo.type_no==typevo.type_no)?'selected':'' }>${typevo.type_name }
					</c:forEach>
				</select>
				</div> <!-- form-group end.// -->
			<input type="hidden" name="action" value="update">
			<input type="hidden" name="prod_no" value="${prodvo.prod_no }">
			<input type="submit"  class="form-control" value="送出修改">	
      </form>
      </div> <!-- card-body.// -->
    </div> <!-- card .// -->
    <div class="card mx-auto mt-5" style="max-width: 380px;">
	<form class = "text-center" action="<%=request.getContextPath()%>/prod/prod.do" method="post"enctype="multipart/form-data">
			<label class="btn btn-info">
			<input type = "file" style="display:none" name="pic" id="myFile" multiple/>
			<i class="fa fa-photo"></i> 上傳圖片
			</label>
	<input type = 'hidden' name="action" value="addPic"/>
	<input type = "hidden" name="prod_no" value="${prodvo.prod_no }"	/>
	<input type = "submit" class="form-control"  id="addPic" value="送出新增"/>	
	</form>	
</div>
	</aside> <!-- col.// -->
	<main class="col-md-8 mt-5">


<div class="row">

<c:if test="${not empty piclist }">
<c:forEach var="picvo" items="${piclist}" >
	<div class="col-md-4 myPic">
		<figure class="card card-product-grid">
			<div class="img-wrap"> 
				<img src="<%=request.getContextPath()%>/prod/prod.do?action=getOne&number=many&pic_no=${picvo.pic_no}">
<!-- 				<a class="btn-overlay" href="#"><i class="fa fa-search-plus"></i> Quick view</a> -->
			</div> <!-- img-wrap.// -->
			<button class="btn btn-light h-25 myBut">
					<p style="display:none" class="pic_no">${picvo.pic_no }</p>
					<svg width="1.5em" height="1.5em" viewBox="0 0 16 16" class="bi bi-x cross" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
					  <path fill-rule="evenodd" d="M11.854 4.146a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708-.708l7-7a.5.5 0 0 1 .708 0z"/>
					  <path fill-rule="evenodd" d="M4.146 4.146a.5.5 0 0 0 0 .708l7 7a.5.5 0 0 0 .708-.708l-7-7a.5.5 0 0 0-.708 0z"/>
					</svg>
			</button>
		</figure>
	</div> <!-- col.// -->
	</c:forEach>
</c:if>

</div> <!-- row end.// -->

	</main> <!-- col.// -->

</div>

</div> <!-- container .//  -->
</section>
<script>
var path = document.getElementById("path").textContent;
myButs=document.getElementsByClassName("myBut");
for (let i = 0; i < myButs.length; i++) {
	myButs[i].addEventListener("click", function(e){
		this.closest(".myPic").remove();
		var pic_no = this.closest(".myBut").children[0].textContent;
		deletePic(pic_no);
	})
}

function deletePic(pic_no){
	console.log(path);
	var xhr = new XMLHttpRequest();
	var url = path+"/prod/prod.do?action=deletePic&pic_no="+pic_no;
	xhr.open("get", url, true);
	xhr.send(null);
}

</script>

</body>
</html>