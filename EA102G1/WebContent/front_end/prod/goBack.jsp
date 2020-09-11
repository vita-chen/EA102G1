<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="container">
<a href="#" id="goBack" class="btn btn-outline-primary btn-sm" role="button" aria-pressed="true">回上一頁</a>
</div>
<script>
document.getElementById("goBack").addEventListener("click", function(e){
	history.back();
})
</script>