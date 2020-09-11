<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.wpcase.model.*"%>
<%
	WPCaseVO WPCaseVO = (WPCaseVO) request.getAttribute("WPCaseVO");
%>
<jsp:useBean id="wpcasevo" scope="page"	class="com.wpcase.model.WPCaseVO" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add WedPhoto Case - 婚禮攝影服務 新增方案頁面</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
</head>
<style type="text/css">
div.form-group2 {
    margin-bottom: 10px;
}
.imagecheck {
  margin: 0;
  position: relative;
  cursor: pointer; }

.imagecheck-input {
  position: absolute;
  z-index: -1;
  opacity: 0; }

.imagecheck-figure {
  border: 1px solid rgba(0, 40, 100, 0.12);
  border-radius: 3px;
  margin: 0;
  position: relative; }

.imagecheck-input:focus ~ .imagecheck-figure {
  border-color: #1572E8;
  box-shadow: 0 0 0 2px rgba(70, 127, 207, 0.25); }
.imagecheck-input:checked ~ .imagecheck-figure {
  border-color: rgba(0, 40, 100, 0.24); }

.imagecheck-figure:before {
  content: '';
  position: absolute;
  top: .25rem;
  left: .25rem;
  display: block;
  width: 1rem;
  height: 1rem;
  pointer-events: none;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  background: #1572E8 url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 8 8'%3E%3Cpath fill='%23fff' d='M6.564.75l-3.59 3.612-1.538-1.55L0 4.26 2.974 7.25 8 2.193z'/%3E%3C/svg%3E") no-repeat center center/50% 50%;
  color: #fff;
  z-index: 1;
  border-radius: 3px;
  opacity: 0;
  transition: .3s opacity; }

.imagecheck-input:checked ~ .imagecheck-figure:before {
  opacity: 1; }

.imagecheck-image {
  max-width: 100%;
  opacity: .64;
  transition: .3s opacity; }
  .imagecheck-image:first-child {
    border-top-left-radius: 2px;
    border-top-right-radius: 2px; }
  .imagecheck-image:last-child {
    border-bottom-left-radius: 2px;
    border-bottom-right-radius: 2px; }

.imagecheck:hover .imagecheck-image {
  opacity: 1; }

.imagecheck-input:focus ~ .imagecheck-figure .imagecheck-image, .imagecheck-input:checked ~ .imagecheck-figure .imagecheck-image {
  opacity: 1; }

.imagecheck-caption {
  text-align: center;
  padding: .25rem .25rem;
  color: #9aa0ac;
  font-size: 0.875rem;
  transition: .3s color; }

.imagecheck:hover .imagecheck-caption {
  color: #495057; }

.imagecheck-input:focus ~ .imagecheck-figure .imagecheck-caption, .imagecheck-input:checked ~ .imagecheck-figure .imagecheck-caption {
  color: #495057; }
.form-control-file{
	margin-bottom:10px;
}
.col-3{
display:inline;
}

</style>
<body>
	<div class="container1">
	<div class="row">
                <div class="col-12">
                <%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color: red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color: red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
                
                </div></div>
        <form action="<%=request.getContextPath()%>/wed/wpcase.do"
		method="post" enctype="multipart/form-data">
            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <label for="formGroupExampleInput">廠商編號</label>
                        <input type="text" class="form-control" id="formGroupExampleInput" name="VENDER_ID" value="${vendervo.vender_id}" disabled >
                        <input type="hidden" class="form-control" id="formGroupExampleInput" name="VENDER_ID" value="${vendervo.vender_id}" >
                    </div>
                    <div class="form-group">
                        <label for="formGroupExampleInput2">方案名稱</label>
                        <input type="text" class="form-control" id="formGroupExampleInput2" placeholder="20 characters limit" name="WED_PHOTO_NAME" value="<%=(WPCaseVO == null) ? "" : WPCaseVO.getWed_photo_name()%>" maxlength="20">
                    </div>
                    <div class="form-group">
                        <label for="exampleFormControlTextarea1">婚攝方案簡介</label>
                        <textarea class="form-control" id="exampleFormControlTextarea1" rows="8" name="WED_PHOTO_INTRO" placeholder="Less than 500 words"><%=(WPCaseVO == null) ? "" : WPCaseVO.getWed_photo_intro()%></textarea>
                    </div>
                    <div class="form-group">
                        <label for="formGroupExampleInput3">婚攝方案價格</label>
                        <input type="text" class="form-control" id="formGroupExampleInput3" name="WED_PHOTO_PRICE" placeholder="Within 6 digits" value="<%=(WPCaseVO == null) ? "" : WPCaseVO.getWed_photo_price()%>">
                    </div>
                    <div class="custom-control custom-radio custom-control-inline">
                        <input type="radio" id="customRadioInline1" name="WED_PHOTO_STATUS" value="0" class="custom-control-input" checked>
                        <label class="custom-control-label" for="customRadioInline1">上架狀態　</label>
                    </div>
                    <div class="custom-control custom-radio custom-control-inline">
                        <input type="radio" id="customRadioInline2" name="WED_PHOTO_STATUS" value="1" class="custom-control-input">
                        <label class="custom-control-label" for="customRadioInline2">下架狀態</label>
                    </div>
                </div>
                <div class="col-md">
                    <div class="form-group2" id="preview">                    	
                        	<label for="upfile1">選擇圖片 choose file</label>
                        	<input type="file" class="form-control-file" id="upfile1" name="upfile1" multiple>                    	
                    </div>
                    <input type="hidden" name="action" value="insert">
                    <button type="button" id="removeA" class="btn btn-outline-danger">delete</button>
                    <button type="submit" class="btn btn-outline-primary">Submit add</button>
                </div>
            </div>
        </form>
    </div>
    <!-- body 結束標籤之前，載入Bootstrap 的 JS 及其相依性安裝(jQuery、Popper) -->
    <script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.4.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
    <script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
</body>
<script type="text/javascript">
function init() {
    var upfile1 = document.getElementById("upfile1");
    var preview = document.getElementById('preview');
    upfile1.addEventListener('change', function(e) { 
        var files = e.srcElement.files;
        if (files) {       				
            for (var i = 0; i < files.length; i++) {
                var file = files[i];              
                if (file.type.indexOf('image') > -1) {                 
                    var reader = new FileReader();
                    // 在FileReader物件上註冊load事件 - 載入檔案完成的意思
                    reader.addEventListener('load', function(e) {
                        // 取得結果 提示：從e.target.result取得讀取到結果
                        var result = e.srcElement.result;                        
                        // 新增img元素
                        var row = document.createElement('div');
                        row.setAttribute('class','row');
                        var col = document.createElement('div');
                       	col.setAttribute('class', 'col-3 col-sm-3');
                        var label = document.createElement('label');
                        label.setAttribute('class','imagecheck mb-4');
                        var check = document.createElement('input');
                        check.setAttribute('type', 'checkbox');
                        check.setAttribute('class', 'imagecheck-input');
                        var figure = document.createElement('figure');
                        figure.setAttribute('class','imagecheck-figure');
                        var input = document.getElementsByTagName('input');
                        var img = document.createElement('img');
                        img.setAttribute('class', 'img');
                        // 賦予src屬性
                        img.src = result;                   
                        img.style.maxHeight = "100px";
                        // 放到div裡面
                       	figure.append(img);
                        label.append(check,figure);
                        col.append(label);
                        row.append(col);
                        preview.append(row);  
                    });           
                    reader.readAsDataURL(file); //trigger!!
                } else {
                    alert('請上傳圖片！');
                }
            }
        }
    });
    removeA.addEventListener('click', function(e) {
        var check = document.getElementsByClassName('imagecheck-input');
        var img = document.getElementsByClassName('img');
        var remove = [];
        for (var i = 0; i < check.length; i++) {
            if (check[i].checked) {
            	remove.push(check[i].parentElement.parentElement.parentElement); //整個div刪除
            	remove.push(img[i]);
            }
        }
        for (var j = 0; j < remove.length; j++) {
            remove[j].remove();
        }
    });
}
window.onload = init;
</script>
</html>