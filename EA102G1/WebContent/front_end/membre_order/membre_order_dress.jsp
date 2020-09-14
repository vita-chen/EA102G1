<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.dressorder.model.*,com.dressorderdetail.model.*,com.vender.model.*"%>
<%@ page import="com.membre.model.MembreVO"%>

<%	
	MembreVO membrevo = (MembreVO)session.getAttribute("membrevo");
	if(membrevo == null){
		String url = request.getContextPath() +"/front_end/dressorder/listOrder_Membre.jsp";
		session.setAttribute("location",url);
		response.sendRedirect(request.getContextPath()+"/front_end/membre/login.jsp");
	    return;
	}
	
    DressOrderService drSvc = new DressOrderService();
    List<DressOrderVO> list = drSvc.findByMembre(membrevo.getMembre_id());
    pageContext.setAttribute("list",list);
    
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>會員訂單</title>
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

<script src="<%=request.getContextPath() %>/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>

<style>
	.pink{
		color:deeppink;
	}

</style>
</head>
<body>
	<div class="wrapper">
	
<%@ include file="/front_end/membre_order/membre_topbar.jsp" %>
	<!-- header -->
<%@ include file="/front_end/membre_order/membre_header.jsp" %>
<div class="main-panel"style="overflow-y:auto;">
<div class="content">
<div class="page-inner">
					<div class="page-header">
					
						<h4 class="page-title">會員</h4>
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
								<a href="#">服務類訂單管理</a>
							</li>
							<li class="separator">
								<i class="flaticon-right-arrow"></i>
							</li>
							<li class="nav-item">
								<a href="#">婚紗訂單</a>
							</li>
						</ul>	
</div>
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
                            <th scope="col">廠商名稱</th>
                            <th scope="col">下訂時間</th>
                            <th scope="col">訂單總價</th>
                            <th scope="col">訂單內容</th>                            
                            <th scope="col">訂單狀態</th>
                            <th scope="col">檢舉狀態</th>
                            <th scope="col">動作</th>                            
                        </tr>
                    </thead>
                    <tbody>
                    <%@ include file="page1.file"%>
                    <c:forEach var="orderVO" varStatus="count" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>"> 
                   		
                        <tr>
                            <th scope="row"><span>${count.count}</span></th>
                            
                            <td>${orderVO.drord_id}</td>
                            <!--顯示相對應的廠商名稱 -->
                            <jsp:useBean id="venSvc" class="com.vender.model.VenderService"></jsp:useBean>
                            	<c:forEach var="venVO" items="${venSvc.all}"> 
                            		<c:if test="${orderVO.vender_id eq venVO.vender_id}">
		                          		<td>${venVO.ven_name}</td>
                           			</c:if>
                           		</c:forEach>
                           		
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
                            <c:if test="${orderVO.drord_fin_st ==5}">訂單結案，會員已評價</c:if>
                            </td>
                            <!-- 檢舉 -->
                            <td>
                            <button type="button" data-toggle="modal" data-target="#repContent" data-id="${orderVO.drord_id}">查看詳情
                            </button>
                            </td>
                            <!-- 訂單狀態為1:點選完成訂單 -->
                            <c:if test="${orderVO.drord_fin_st == 1}">
                            <td>
                            <button type="button" data-toggle="modal" data-target="#complete" data-id="${orderVO.drord_id}" id="con1">
                            完成訂單</button></td></c:if>
                            <!-- 訂單狀態為2:訂單已取消-->
                            <c:if test="${orderVO.drord_fin_st == 2}">
                            <td>訂單已取消</td></c:if>
                            <!-- 訂單狀態3:等帶廠商確認中 -->
                            <c:if test="${orderVO.drord_fin_st == 3}">
                            <td>廠商確認中</td></c:if>
                            <!--訂單狀態為4:會員才能進行評價-->
                            <c:if test="${orderVO.drord_fin_st ==4}">
                            <td><button type="button" data-toggle="modal" data-target="#review" data-id="${orderVO.drord_id}" id="yo">評價訂單</button></td>
<!--                             <input type="button" value="評價訂單" onclick="self.location.href='http://localhost:8081/EA102G1_G1/front_end/membre_order/dress.jsp'"> -->
                            </c:if>
                            <!--訂單狀態為5:已評價，-->
                            <c:if test="${orderVO.drord_fin_st == 5}">
                            <td id="finished">訂單已結案與評價</td></c:if>
                            
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
                 <div class="row" id="new_content" style="text-align:left;"> 
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
                     <div class="row_line col-md-12 col-sm-12 col-xs-12">
                     <div style="text-align:left;">
                     	<!-- 內容：隱藏按鈕區，視各種狀況會出現不同的按鈕 -->
                     	<label style="display:none;" id="showinfo" width="700"></label>
                     	
        			<form method="post" action="<%=request.getContextPath()%>/front_end/dressorder/order.do" id="rep">
        			
        			<label style="display:none;" id="labelRep">若要檢舉，請輸入您的檢舉原因:</label>
        			
                         <input type="text" class="form-controls content" style="display:none; width:400px;" name="mrep_de" id="textRep">
                         </div><br><br>
                         
                         <div class="rt" id="but">
				           <button type="button" class="btn btn-default btn_blue" id="cancelRep" data-dismiss="modal">取消</button>
				           <button type="button" class="btn btn-default btn_red"  id="confirmRep">確定送出</button>
				   			<input type="hidden" name="action" value="confirmRep">
				   			<!--因order_id為浮動，故先創造一個空的，等modal打開時用js傳進其order_id值-->
				   			<input type="hidden" name="order_id" value="" id="OODDD">
				   		</div>
				   	</form>
				   
        </div> </div> </div> </div> 
                	
        </div></div></div>
<!-- model3: 確認完成訂單-->
<div class="modal fade" id="complete" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">完成訂單</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body" id="divCon">
        恭喜您完成終身大事，希望您喜歡我們的服務!
        <br><br>
        在此與您確認，租用的禮服、西裝或相關加購物品，<br>
        都已經順利歸還給廠商了嗎？
        <br><br>
       
      <div class="modal-footer">
      	 <form method="post" action="<%=request.getContextPath()%>/front_end/dressorder/order.do" id="comp">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">好像還沒</button>
        <button type="button" class="btn btn-primary" id="confirmComp" >確認無誤</button>
        <!-- 建一個空的ord_id input，當視窗觸發時用js塞ord_id進去 -->
        <input type="hidden" name="order_id" value="" id="COMOD">
        <input type="hidden" name="action" value="memComp"></form>
      </div>
    </div>
    </div>
  </div>
</div>
<!-- model4: 評價訂單-->
<div class="modal fade" id="review" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">評價訂單</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        恭喜您完成終身大事，希望您喜歡我們的服務!
        <br><br>
        請留下您的評價，並給予您珍貴的意見。
        <br><br>
        <!-- star -->
		 <div class="col-12">
				<label for="customRange1" class="col-form-label">評價分數:</label>
                  	<div class="d-flex justify-content-center my-4">
				  <div class="w-90 pink">
				    <input type="range" class="custom-range" id="revStar" name="revStar" min="1" max="5">
				  </div>
				  <span class="font-weight-bold text-primary ml-2 valueSpan"></span>
				</div>
           </div>
		 
		<div id="revFinal">
        <form method="post" action="<%=request.getContextPath()%>/front_end/dressorder/order.do" id="rev">
          <div class="form-group">
            <label for="message-text" class="col-form-label">評價內容:</label>
            <textarea class="form-control" id="message-text" name="review_con"></textarea>
      	
      		<div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" id="confirmRev" >確認</button>
        <input type="hidden" name="action" value="review">
        <input type="hidden" name="order_id" id="ord" value="">
         <input type="hidden" name="numOfStar" id="star" value=" ">
      	</div>
      </div>
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
    		$("#new_content").html('');
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
		  $("#new_content").append('<th width="220" height="40" bgcolor="white">方案名稱</th><th width="80">方案價格</th><th width="300">加購項目</th><th width="80">小計</th>');
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
	
	//會員檢舉:讓使用者可輸入檢舉，enable the button
	if(mst === 0){
		$("#showinfo").hide();
		$("#labelRep").show();
		$("#textRep").show();
		$("#but").show();
	}
	//會員或廠商其中一方提出檢舉且尚未審理完畢，顯示"檢舉處理中"
	else if(mst ===1 ){
		$("#but").hide();
		$('#showinfo').html("平台處理檢舉中，請稍候");
		$("#showinfo").show();
		
		$("#labelRep").hide();
		$("#textRep").hide();
	}
	//會員檢舉成功
	else if(mst ===2){
		$("#but").hide();
		$("#showinfo").html('恭喜您，檢舉成功');
		$("#showinfo").show();
		$("#labelRep").hide();
		$("#textRep").hide();
	}
	//會員檢舉失敗
	else if(mst===3){
		$("#but").hide();
		$("#showinfo").html('很抱歉，因為一些因素，您的檢舉並沒有成功，若有疑問請歡迎撥打客服');
		$("#showinfo").show();
		$("#labelRep").hide();
		$("#textRep").hide();
	}
	//只有廠商檢舉成功的狀況，需要通知會員
	else if(vst ===2){
		$("#but").hide();
		$("#showinfo").html('很抱歉，因為一些因素，廠商向我們反應了一些意見');
		$("#showinfo").show();
		$("#labelRep").hide();
		$("#textRep").hide();
		
	}
	//其他：請聯絡專人處理
	else{
		$("#but").hide();
		$("#showinfo").html('狀態異常，請儘速與我們聯絡');
		$("#showinfo").show();
		$("#labelRep").hide();
		$("#textRep").hide();
	}
}

/*2-3:送出檢舉內容 */
$('#confirmRep').click(function(){
	$('#rep').submit();
})
//3-1:評價訂單：打開視窗
$('#review').on('show.bs.modal', function (event) {
      var btnThis = $(event.relatedTarget); //觸發事件的按钮
      var modal = $(this);  //當前modal
      var modalId = btnThis.data('id');   //解析出data-id的内容
      $('#revFinal').append('<input type="text" id="final" style="display:none;" value="'+modalId+'">');
      
})
	
//3-2: 送出評價內容與星數
$('#confirmRev').click(function(event){
    $('#star').val($('#revStar').val());
	$('#ord').val($('#final').val());
	$('#rev').submit();
})

//4-1: 會員點選完成訂單，跳出modal視窗前，先將order_id傳入並以hidden的方式放在COMOD裡
$('#complete').on('show.bs.modal', function (event) {
      var btnThis = $(event.relatedTarget); //觸發事件的按钮
      var modal = $(this);  //當前modal
      var modalId = btnThis.data('id');   //解析出data-id的内容
      $('#COMOD').val(modalId);
});

//4-2: 會員在modal視窗內點選確認歸還
$('#confirmComp').click(function(){
	/* 直接form表單submit*/
	$('#comp').submit();
})
</script>

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