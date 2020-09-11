<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%request.setCharacterEncoding("UTF-8"); %>
<jsp:include page="/front_end/prod/head.jsp">
<jsp:param name="title" value="新增商品"/>
</jsp:include>
<style>
  
  #myRow {
    
  position: relative;
  width: 50%;
  }
.uploadImage {
  opacity: 1;
  display: block;
  width: 100%;
  height: auto;
  transition: .5s ease;
  backface-visibility: hidden;
}
    
  .middle {
  transition: .5s ease;
  opacity: 0;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  text-align: center;
}
.preview {
	position:relative;
}
#myRow:hover img {
  opacity: 0.3;
}

#myRow:hover .middle {
  opacity: 1;
}

.text {
  background-color: #f2f2f2;
  color: black;
  font-size: 16px;
  padding: 16px 32px;
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
      <h4 class="card-title mb-4">新增商品</h4>
      <form action="<%=request.getContextPath() %>/prod/prod.do" method="post" enctype="multipart/form-data">
			<label>名稱</label>
          <div class="form-group">
			 <input type="text" class="form-control" name="prod_name" value="${prodvo.prod_name }" maxlength="20"/>
          </div> <!-- form-group// -->
          <label>價格</label>
          <div class="form-group">
          <input type="text" class="form-control" name="price" value="${prodvo.price }" maxlength="8"/>
          </div> <!-- form-group// -->
          <label>數量</label>
            <div class="form-group">
			<input type="text" class="form-control" name="prod_qty" value="${prodvo.prod_qty }" maxlength="4"/>
          </div> <!-- form-group// -->
          		<jsp:useBean id="typeDao" class="com.prod_type.model.TypeDAO"/>
				<div class="form-group">
					  <label>類別</label>
					<select name="type_no" style="margin-left:20px">
					<c:forEach var="typevo" items="${typeDao.all }">
						<option value="${typevo.type_no }" ${(prodvo.type_no==typevo.type_no)?'selected':'' }>${typevo.type_name }
					</c:forEach>
				</select>
			<label class="btn btn-info" style="margin-left:100px">
			<input type = "file" style="display:none" name="pic" id="myFile" multiple/>
			<i class="fa fa-photo"></i> 上傳圖片
			</label>
			<input type="hidden" name="action" value="add">
			<input type="hidden" name="pass" value="" id="picToPass">
			<input type="submit"  class="form-control" value="新增商品">	
				</div> <!-- form-group end.// -->
      </form>
      </div> <!-- card-body.// -->
    </div> <!-- card .// -->
	</aside> <!-- col.// -->
<main class="col-md-8 mt-5">

<div class="row d-flex w-100" id = "myRow">
</div> <!-- row end.// -->

	</main> <!-- col.// -->

</div>

</div> <!-- container .//  -->
</section>
<script>
function init(){

	// 1. 抓取DOM元素
	var picToPass = document.getElementById("picToPass");
	var passContent ="";
	var myRow = document.getElementById("myRow");
	var myFile = document.getElementById("myFile");
	myFile.addEventListener('click', function(e){
		myFile.value="";
		var previews = document.querySelectorAll("#myRow .preview");
		for (let i = 0; i < previews.length; i++) {
			previews[i].remove();
		}
	})
	
	myFile.addEventListener('change',function(e){
		passContent="";
		var count=0;
		var files = e.srcElement.files;
		console.log(files);
		if(files){
			for(var i = 0 ; i < files.length; i++){
				var img;
				var file = files[i];
				if(file.type.indexOf('image') > -1){
					generateImage(count, file);
					count++;		
				} else {   
				alert('請上傳圖片！');
			} 
		}
	}
});
	function generateImage(i, file) {
		// new a FileReader
		var reader = new FileReader();
    	// 在FileReader物件上註冊load事件 - 載入完成檔案的意思
    	reader.addEventListener('load', function(e){

    		var result = e.srcElement.result;
    		var image = document.createElement('img');
    		image.setAttribute("class", "uploadImage");
    		image.setAttribute("src", result);
    	      var preview = document.createElement("div");
    	      preview.setAttribute('class', 'preview col-md-4');
    	      var middle = document.createElement("button")
    	      middle.setAttribute('class', 'middle btn');
    	      var text = document.createElement("div");
    	      text.setAttribute('class', 'text');
    	      text.setAttribute('index', i);
    	      text.textContent="刪除";
    	      middle.append(text);
    	      preview.append(image);
    	      preview.append(middle);
    	      myRow.append(preview);
    		
    	});	
    	// 使用FileReader物件上的 readAsDataURL(file) 的方法，傳入要讀取的檔案，並開始進行讀取
    	reader.readAsDataURL(file); // File IO
    }	
	
	myRow.addEventListener("click", function(e){
		var index = e.srcElement.getAttribute('index');
		passContent += index;
		picToPass.value=passContent;
		e.srcElement.closest('.preview').remove();
	})
}
window.onload = init;
</script>
</body>
</html>