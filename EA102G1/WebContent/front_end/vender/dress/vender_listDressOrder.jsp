<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.dressorder.model.*,com.dressorderdetail.model.*,com.vender.model.*,com.membre.model.*"%>


<%
Object account = session.getAttribute("vendervo");                  // 從 session內取出 (key) account的值
if (account == null) {                                             // 如為 null, 代表此user未登入過 , 才做以下工作
  response.sendRedirect(request.getContextPath()+"/front_end/vender/vender_login.jsp");   //*工作2 : 請該user去登入網頁(login.html) , 進行登入
  return;
}

	VenderVO vVO = (VenderVO)session.getAttribute("vendervo");	
    DressOrderService drSvc = new DressOrderService();
    List<DressOrderVO> list = drSvc.findByVender(vVO.getVender_id());
    pageContext.setAttribute("list",list); 
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>廠商專區</title>
	<meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />
	<link rel="icon" href="<%=request.getContextPath()%>/assets/img/icon.ico" type="image/x-icon"/>

	<!-- Fonts and icons -->
	<script src="<%=request.getContextPath()%>/assets/js/plugin/webfont/webfont.min.js"></script>
	<script>
		WebFont.load({
			google: {"families":["Lato:300,400,700,900"]},
			custom: {"families":["Flaticon", "Font Awesome 5 Solid", "Font Awesome 5 Regular", "Font Awesome 5 Brands", "simple-line-icons"], urls: ['<%=request.getContextPath()%>/assets/css/fonts.min.css']},
			active: function() {
				sessionStorage.fonts = true;
			}
		});
	</script>
	<!-- third party css -->
	<link href="<%=request.getContextPath()%>/css/jquery-jvectormap-1.2.2.css" rel="stylesheet" type="text/css" />

	<!-- CSS Files -->
	<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/atlantis.min.css">

	<!-- CSS Just for demo purpose, don't include it in your project -->
	<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/demo.css">
	<link href="<%=request.getContextPath()%>/css/icons.min.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/css/app.min.css" rel="stylesheet" type="text/css" id="light-style" />
	

<link rel="stylesheet" href="../../vendors/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath() %>/vendors/jquery/jquery-3.4.1.min.js"></script>



</head>
<body>
	<div class="wrapper">
	<!-- topbar -->
<%@ include file="/front_end/vender/vender_home_topbar.jsp" %>
	<!-- header -->
<%@ include file="/front_end/vender/vender_home_header.jsp" %>
<div class="main-panel"style="overflow-y:auto;">
<div class="content">
<div class="page-inner">
					<div class="page-header">
					
						<h4 class="page-title">廠商</h4>
						<ul class="breadcrumbs">
							<li class="nav-home">
								<a href="#">
									<i class="flaticon-home"></i>
								</a>
							</li>
							<li class="separator">
								<i class="flaticon-right-arrow"></i>
							</li>
							<li class="nav-item">
								<a href="#">婚紗訂單管理</a>
							</li>

						</ul>	
</div>
<!-- 塞資料的地方 -->
<!-- 塞資料的地方 -->
<div class="page-category">
				

<!-- 顯示錯誤表列 -->
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<div class="">
        <div class="">
            <div class="col-25">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">訂單編號</th>
                            <th scope="col">會員編號</th>
                            <th scope="col">下訂時間</th>
                            <th scope="col">訂單總價</th>
                            <th scope="col">訂單內容</th>                            
                            <th scope="col">訂單狀態</th>
                            <th scope="col">檢舉狀態</th>
                            <th scope="col">動作</th>                            
                        </tr>
                    </thead>
                    <tbody>
                    <%@ include file="page1.file" %>
                    <c:forEach var="orderVO" varStatus="count" items="${list}"> 
                   
                        <tr>
                            <th scope="row"><span>${count.count}</span></th>
                            <td>${orderVO.drord_id}</td>
                            <td>${orderVO.membre_id}</td>
                           
                            <td><fmt:formatDate value="${orderVO.drord_time}" pattern="yyyy-MM-dd HH:mm" /></td>
                            <td>${orderVO.drord_pr}</td>
                            <!--訂單內容:使用modal另開新視窗，讓user能看到所訂購的方案與加購項目列表-->
                            <td>
                            <button type="button" data-toggle="modal" data-target="#orderContent" data-id="${orderVO.drord_id}">查看明細</button></td>
                            
                            <td>
                            <c:if test="${orderVO.drord_fin_st ==1}">會員尚未租借</c:if>
                            <c:if test="${orderVO.drord_fin_st ==2}">訂單取消</c:if>
                            <c:if test="${orderVO.drord_fin_st ==3}">會員已歸還物品</c:if>
                            <c:if test="${orderVO.drord_fin_st ==4}">廠商已收到物品</c:if>
                            <c:if test="${orderVO.drord_fin_st ==5}">訂單結案，會員已評價</c:if></td>
                            <td><button type="button" data-toggle="modal" data-target="#repContent" data-id="${orderVO.drord_id}">查看詳情</button></td>
                            
                            <c:if test="${orderVO.drord_fin_st ==3}">
                            <td><button type="button" data-toggle="modal" data-target="#confirmFin" data-id="${orderVO.drord_id}">確認完成</button></td></c:if>
                            <c:if test="${orderVO.drord_fin_st ==4 || orderVO.drord_fin_st ==5}"><td>廠商已確認完成</td></c:if>
                            <c:if test="${orderVO.drord_fin_st ==1 || orderVO.drord_fin_st ==2}"><td>---</td></c:if>
                        </tr>
                        </c:forEach>
                        </tbody>
                </table>
                <%@ include file="page2.file" %>
                <div>溫馨訂單流程小提醒: <br>下訂成功 -> 領取禮服或西裝 -> 會員歸還時按下"完成訂單" -> 廠商確認收到時按下"完成訂單" -> 會員對訂單評價 -> 訂單結案</div>
            </div>
        </div>
    </div>  
    
    <!--modal 1:訂單內容:顯示訂單內容：方案與加購項目列表  -->
<div class="modal fade" id="orderContent" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
       <div class="modal-content">
          <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">查看訂單內容</h5>
              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
          </div>
          <div class="modal-body">
             <div class="planel_boxs">
                 <div class="row" id="new_content"> 
                 <!--後台的資料用js放這兒！ -->
                     <div> </div> </div></div>  </div> </div></div></div>	
                     
<!--modal 2:檢舉內容:顯示檢舉狀況：若未檢舉，可以增加描述，其他情形都只能看; 此action可讓m_rep_st由0變成1-->	
<div class="modal fade" id="repContent" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
       <div class="modal-content">
          <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">查看檢舉狀態</h5>
              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
          </div>
          <div class="modal-body">
             <div class="planel_boxs">
                 <div class="row"> 
                     <div class="row_line col-md-12 col-sm-12 col-xs-12" id="repState">
                     <div style="text-align:left;">
                     	<!-- 內容：隱藏按鈕區，視各種狀況會出現不同的按鈕 -->
                     	<label style="display:none;" id="showinfo"></label>
       
        			<form method="post" action="<%=request.getContextPath()%>/front_end/dressorder/order.do" id="venRepForm">
        			<div style="text-align:left;"> 
        			<label style="display:none;" id="labelRep" class="wid">若要檢舉，請輸入您的檢舉原因:</label>
        			
                         <input type="text" class="form-controls content" style="display:none; width:400px;" name="vrep_de" id="textRep">
                         <label style="display:none;" id="repState" class="wid"></label>
                    </div>
                          <div class="rt" id="but">
				           <button type="button" class="btn btn-default btn_red" data-dismiss="modal" id="cancelRep" >取消</button>
				           <button type="button" class="btn btn-default btn_blue" value="確定送出" id="confirmRep">確認送出</button>
				   			<input type="hidden" name="order_id" value="" id="OODDD">
				   			<input type="hidden" name="action" value="venRep">
				   			</div>
				   </form>
        </div> </div> </div> </div> </div></div></div>
        
        
<!-- model3: 確認完成訂單-->
<div class="modal fade" id="confirmFin" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">完成訂單</h5>
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
      </div>
      <div class="modal-body">
        謝謝您提供婚紗租賃的服務！
        <br><br>
      <div class="modal-footer" id="con_final">
      <form action="<%=request.getContextPath()%>/front_end/dressorder/order.do" method="post" id="conVen">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">還要再確認</button>
        <button type="button" class="btn btn-primary" id="confirmFin2" >顧客已歸還</button>
        <input type="hidden" name="action" value="vfinOrder">
        <input type="hidden" name="order_id" value="" id="conORDID">
      </form>
      </div>
    </div>
    </div>
  </div>
</div>


<script>
//1-1. 訂單
$('#orderContent').on('show.bs.modal', function (event) {
      var btnThis = $(event.relatedTarget); //觸發事件的按钮
      var modal = $(this);  //當前modal
      var modalId = btnThis.data('id');   //解析出data-id的内容
      //送後台
      $.ajax({
    	type:"POST",
    	url:"<%=request.getContextPath()%>/front_end/dressorder/order.do",
    	dataType:"JSON",
    	data:{
    		action:'SeeDetail',
    		order_id :modalId
    	},
    	success:function(data,status,xhr){
    		seeDetail(data);
    	},
    	error:function(jqXhr,textStatus,errorMessage){
			alert("傳送失敗!"+errorMessage);
    	}
      })        
		})
//1-2:訂單內容顯示
	function seeDetail(data){
		var n = data['row'];
		var caseNaArr = JSON.parse(data.caseNa);
		var casePrArr = JSON.parse(data.casePr);
		var addNaArr = JSON.parse(data.addNa);
		var dePrArr = JSON.parse(data.dePr);
		
		  $("#new_content").html('');
		  $("#new_content").append('<div class="table-responsive" style="text-align:left;"><table class="table table-hover">');
		  $("#new_content").append('<thead><tr>');
		  $("#new_content").append('<th width="220" height="40" bgcolor="white">  方案名稱</th><th width="80">方案價格</th><th width="300">加購項目</th><th width="80">小計</th>');
		  $("#new_content").append('</tr></thead><tbody>');
		  
		  for(var i=0; i<n;i++){
			  if(addNaArr[i].length===0){
					$("#new_content").append('<tr bgcolor="white" height="40"><th width="220">'+caseNaArr[i]+'</th><td width="80">'+casePrArr[i]+'</td><td width="300">無 </td><td width="80">'+dePrArr[i]+'</td></tr>');
				}
			  else{
					$("#new_content").append('<tr bgcolor="white" height="40"><th width="220">'+caseNaArr[i]+'</th><td width="80">'+casePrArr[i]+'</td><td width="300">'+addNaArr[i]+'</td><td width="80">'+dePrArr[i]+'</td></tr>');
				}
		  }
		  $("#new_content").append('</tbody></table>');
	
}
//2-1. 檢舉
$('#repContent').on('show.bs.modal', function (event) {
      var btnThis = $(event.relatedTarget); //觸發事件的按钮
      var modal = $(this);  //當前modal
      var modalId = btnThis.data('id');   //解析出data-id的内容
      //送後台
      $.ajax({
    	type:"POST",
    	url:"<%=request.getContextPath()%>/front_end/dressorder/order.do",
    	dataType:"JSON",
    	data:{
    		action:'SeeRep',
    		order_id :modalId
    	},
    	success:function(data,status,xhr){
    		seeReport(data);
    	},
    	error:function(jqXhr,textStatus,errorMessage){
			alert("傳送失敗!"+errorMessage);
    	}
      })        
		})
//2-2. 檢舉內容顯示
function seeReport(data){
	var mst = data.mSt;
	var vst = data.vSt;
	var mde = data.mDe;
	var vde = data.vDe;
	var mres = data.mRes;
	var vres = data.vRes;
	var ord_id = data.drord_id;
	//hidden訂單id:供給之後用
	$('#OODDD').val(ord_id);
	//廠商檢舉，enable the button
	if(vst === 0){
		$("#showinfo").hide();
		$("#textRep").html('');
		$("#but").show();
		$("#labelRep").show();
		$("#textRep").show();
		$("#con_final").show();
	}
	//廠商提出檢舉且尚未審理完畢，顯示"檢舉處理中"
	else if(vst===1){
		$("#but").hide();
		$("#textRep").hide();
		$("#labelRep").hide();
		$('#showinfo').html("平台處理檢舉中，請稍候");
		$("#showinfo").show();
	}
	//廠商檢舉成功
	else if(vst ===2){
		$("#but").hide();
		$("#labelRep").hide();
		$("#textRep").hide();
		$("#showinfo").html('恭喜您，檢舉成功');
		$("#showinfo").show();
	}
	//廠商檢舉失敗
	else if(vst===3){
		$("#but").hide();
		$("#labelRep").hide();
		$("#textRep").hide();
		$("#showinfo").html('很抱歉，因為一些因素，您的檢舉並沒有成功，若有疑問請歡迎撥打客服');
		$("#showinfo").show();
	}
	//其他：請聯絡專人處理
	else{
		$("#but").hide();
		$("#labelRep").hide();
		$("#textRep").hide();
		$("#showinfo").html('狀態異常，請儘速與我們聯絡');
		$("#showinfo").show();
	}
}
//2-3:送出檢舉內容
$("#confirmRep").click(function(){
    $('#venRepForm').submit();
})

//3-1:完成訂單:打開視窗
$('#confirmFin').on('show.bs.modal', function (event) {
	  alert("hi I'm here!");
      var btnThis = $(event.relatedTarget); //觸發事件的按钮
      var modal = $(this);  //當前modal
      
      var modalId = btnThis.data('id');   //解析出data-id的内容
      alert(modalId);
      $('#con_final').append('<input type="text" id="conFinal" style="display:none;" value="'+modalId+'">');
})

//3-2:確認完成
$('#confirmFin2').click(function(){
	$('#conORDID').val($('#conFinal').val());
	$('#conVen').submit();
});
</script>

  <!-- body 結束標籤之前，載入Bootstrap 的 JS 及其相依性安裝(jQuery、Popper) -->
  <script src="<%=request.getContextPath() %>/vendors/popper/popper.min.js"></script>
  <script src="<%=request.getContextPath() %>/vendors/bootstrap/js/bootstrap.min.js"></script>
  <script src="<%=request.getContextPath() %>/js/wp/wpOrder_mem.js" charset="utf-8" type="text/javascript"></script>

</div>
</div>
</div>

</div>
</div>


</body>
</html>

