<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.vender.model.*"%>
<% 	
VenderJDBCDAO dao = new VenderJDBCDAO();

int []off = {dao.get_all_off(),dao.get_all_adm_off()};

request.setAttribute("off", off);

%>
<!-- Topbar Start -->
<div class="navbar-custom">

<div style="z-index:10;position:absolute;top:18px;left:300px;font-size:15px;">
<a id="vender_off"href="<%=request.getContextPath()%>/back_end/vender/list_all_verification_vender.jsp">${off[0] }名待驗證廠商</a>
　　<a id="adm_off"href="<%=request.getContextPath()%>/back_end/adm/list_all_adm.jsp">${off[1] }名待開通管理員</a>
</div>
  <script>
  	document.getElementById("vender_off").style.display="none";//隱藏
  	document.getElementById("adm_off").style.display="none";//隱藏
  	var vender_off = ${off[0] };
  	var adm_off = ${off[1] };
  	if(vender_off>=1){
  		document.getElementById("vender_off").style.color = '#FF0000';
  		document.getElementById("vender_off").style.display="";//顯示
  	}
  	if(adm_off>=1){
  		document.getElementById("adm_off").style.color = '#FF0000';
  		document.getElementById("adm_off").style.display="";//顯示
  	}

  </script>
  
	<ul class="list-unstyled topbar-right-menu float-right mb-0">

		<a class="nav-link  nav-user  mr-0">

			<FORM class="float-right" METHOD="post"
				ACTION="<%=request.getContextPath()%>/vender/vender.do">
				<input type="hidden" name="action" value="session_off">
				<button type="submit"
					href="<%=request.getContextPath()%>/front_end/home/home.jsp"
					class="float-right btn btn-dark">登出</button>
			</FORM> <span style="font-size: 14px; color: #000000"
			class="float-left mr-2"> <span>您好~${admvo.adm_name}</span> <span
				style="font-size: 14px; color: #000000" class="account-position">管理編號:${admvo.adm_id}</span>
		</span>
		</a>		
		

	</ul>
	<button class="button-menu-mobile open-left disable-btn">
		<i class="mdi mdi-menu"></i>
	</button>

	<div class="app-search dropdown d-none d-lg-block">
		<FORM METHOD="post"
			ACTION="<%=request.getContextPath()%>/vender/vender.do">
			<input type="text" name="vender_id" placeholder="請輸廠商編號如V001">
			<input type="hidden" name="action" value="getOne_For_Display">
			<input type="submit" value="查詢廠商">
		</FORM>
	</div>
	
	<div>
		<c:if test="${not empty errorMsgs}">
			<font style="color: red">請修正以下錯誤:</font>
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li style="color: red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>
	</div>
</div>
<!-- end Topbar -->