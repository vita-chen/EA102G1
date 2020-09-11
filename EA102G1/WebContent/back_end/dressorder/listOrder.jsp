<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.dressorder.model.*,com.dressorderdetail.model.*,com.vender.model.*"%>

<%
    DressOrderService drSvc = new DressOrderService();
    List<DressOrderVO> list = drSvc.getAllDrOrder();
    pageContext.setAttribute("list",list);
%>

<html>
<head>
<meta charset="UTF-8">
<title>ListDressOrder_Backend</title>

<link rel="stylesheet" href="../../vendors/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath() %>/vendors/jquery/jquery-3.4.1.min.js"></script>
<style>
	
	.wid { 
	width: 700px; 
	}
	div.container {
    border: 2px solid pink;
	}
	.btn {
		padding: 2px 10px 10px 10px;
	  	background-color: pink;
	  	border: 1px;
	  	color: grey;
	  	text-align: center;
	  	text-decoration: none;
	  	display: inline-block;
	  	font-size: 14px;
	}
	.range{
		padding-right:0;
		padding-left:0;
	} 
	
	input {
	  display: none;
	}
	label {
	  font-size: 15px;
	}
	input:checked ~ label {
	  color: orange;
	}
</style>
</head>
<body>

<!-- 顯示錯誤表列 -->
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<div class="container">
        <div class="row">
            <div class="col-12">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">訂單編號</th>
                            <th scope="col">會員編號</th>
                            <th scope="col">廠商編號</th>
                            <th scope="col">下訂時間</th>
                            <th scope="col">訂單總價</th>
                            <th scope="col">訂單內容</th>                            
                            <th scope="col">訂單狀態</th>
                            <th scope="col">檢舉狀態</th>                         
                        </tr>
                    </thead>
                    <tbody>
                    
					<%@ include file="page1.file" %>
                    <c:forEach var="orderVO" varStatus="count" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>"> 
                   
                        <tr>
                            <th scope="row"><span>${count.count}</span></th>
                            <td>${orderVO.drord_id}</td>
                            <td>${orderVO.membre_id}</td>
                            <td>${orderVO.vender_id}</td>
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
                        </tr>
                        </c:forEach>
                        </tbody>
                </table>
				<%@ include file="page2.file" %>
				
				
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
                     
<!--modal 2:檢舉內容:顯示檢舉狀況：分別列會員檢舉與廠商檢舉狀況，後台可給仲裁結果，給廠商的處罰為封鎖-->	
<div class="modal fade" id="repContent" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
                     <form method="post" action="<%=request.getContextPath()%>/front_end/dressorder/order.do" id="resForm">
                     	<!-- 內容：隱藏按鈕區，視各種狀況會出現不同的按鈕 -->
                     	<!-- 若會員提出檢舉 -->
                     	
                     	<label style="display:none;" id="showinfo_mrep" class="wid"></label>
                     	
                     	<!--下拉式選單-->
                     	<div id="selectMem" style="display:none;">
                     	<label for="mrep_st" id="labelM">審核後結果為</label>
						<select name="mrep_st" id="mrep_st">
						  <option value="2">會員檢舉成功</option>
						  <option value="3">會員檢舉失敗</option>
						</select>
						
                        <br><br></div>
                     	
                     	<!-- 若廠商提出檢舉-->
       					<label id="showinfo_vrep" class="wid"></label>
       					<!--下拉式選單-->
       					<div id="selectVen" style="display:none;">
       					<label for="vrep_st" id="labelV">審核後結果為</label>
						<select name="vrep_st" id="vrep_st">
						  <option value="2">廠商檢舉成功</option>
						  <option value="3">廠商檢舉失敗</option>
						</select>
       					<br></div>
       					
       					<div id="but">
                         <button type="button" class="btn btn-default btn_red" data-dismiss="modal" id="cancelRep">取消</button>
				        <button type="button" class="btn btn-default btn_blue" value="確定送出" id="confirmRep">確認送出</button>
				         <input type="hidden" name="action" value="backRep">
				         <input type="hidden" name="order_id" value="" id="ordID_res">
				          </div>
				   </form>
        </div> </div> </div> </div> </div></div></div>

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
		  $("#new_content").append('<div class="table-responsive"><table class="table table-hover">');
		  $("#new_content").append('<thead><tr>');
		  $("#new_content").append('<th width="220" height="40" bgcolor="white">方案名稱</th><th width="80">方案價格</th><th width="300">加購項目</th><th width="80">小計</th>');
		  $("#new_content").append('</tr></thead><tbody>');
		  
		  for(var i=0; i<n;i++){
			  if (!Array.isArray(addNaArr) || !addNaArr.length) {
				  addNaArr[i] = "無";
			  }
			  $("#new_content").append('<tr bgcolor="white" height="40"><th width="220">'+caseNaArr[i]+'</th><td width="80">'+casePrArr[i]+'</td><td width="300">'+addNaArr[i]+'</td><td width="80">'+dePrArr[i]+'</td></tr>');
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
	$('#repState').find('ORDID').val('');
	var mst = data.mSt;
	var vst = data.vSt;
	var mde = data.mDe;
	var vde = data.vDe;
	var mres = data.mRes;
	var vres = data.vRes;
	var ord_id = data.drord_id;
	$('#ordID_res').val(ord_id);
	$('#mrep_st').val(mres);
	$('#vrep_st').val(vres);
	
	if (mst===0){
		$('#selectMem').hide();
		if(vst===0){
			$('#but').hide();
			
			$('#selectVen').hide();
			$("#showinfo_vrep").hide();
			$("#showinfo_mrep").html('無人提出檢舉');
			$("#showinfo_mrep").show();
		}
		else if(vst ===1){
			$('#but').show();
			
			$("#showinfo_mrep").hide();
			$("#showinfo_vrep").html('廠商已提出檢舉，檢舉內容為:'+vde+ '<br>');
			$("#showinfo_vrep").show();
			$('#selectVen').show();
		}
		else if(vst===2){
			$('#but').hide();
			
			$('#selectVen').hide();
			$("#showinfo_mrep").hide();
			$("#showinfo_vrep").html('廠商已檢舉成功，檢舉內容為'+vde+'<br>');
			$("#showinfo_vrep").show();
		}
		else if(vst===3){
			$('#but').hide();
			
			$('#selectVen').hide();
			$("#showinfo_mrep").hide();
			$("#showinfo_vrep").html('廠商已檢舉失敗，檢舉內容為'+vde+'<br>');
			$("#showinfo_vrep").show();
		}
	}
	else if(mst ===1){
		$('#but').show();
		$("#showinfo_mrep").html('會員已提出檢舉，檢舉內容為:'+mde+ '<br>');
		$("#showinfo_mrep").show();
		$('#selectMem').show();
		
		if(vst===0){
			$('#selectVen').hide();
			$("#showinfo_vrep").hide();
		}
		else if(vst ===1){
			$("#showinfo_vrep").html('廠商已提出檢舉，檢舉內容為:'+vde+ '<br>');
			$("#showinfo_vrep").show();
			$('#selectVen').show();
		}
		else if(vst ===2){
			$("#showinfo_vrep").html('廠商已檢舉成功，檢舉內容為:'+vde+ '<br>');
			$("#showinfo_vrep").show();
			$('#selectVen').hide();
		}
		else{
			$("#showinfo_vrep").html('廠商已檢舉失敗，檢舉內容為:'+vde+ '<br>');
			$("#showinfo_vrep").show();
			$('#selectVen').hide();
		}
	}
	else if(mst ===2){
			$("#showinfo_mrep").html('會員已檢舉成功，檢舉內容為:'+mde+ '<br>');
			$("#showinfo_mrep").show();
			$('#selectMem').hide();
		if(vst ===0){
			$('#but').hide();
			$("#showinfo_vrep").hide();
			$('#selectVen').hide();
		}
		else if(vst ===1){
			$('#but').show();
			$("#showinfo_vrep").html('廠商已提出檢舉，檢舉內容為:'+vde+ '<br>');
			$("#showinfo_vrep").show();
			$('#selectVen').show();
		}
		else if(vst===2){
			$('#but').hide();
			$("#showinfo_vrep").html('廠商已檢舉成功，檢舉內容為:'+vde+ '<br>');
			$("#showinfo_vrep").show();
			$('#selectVen').hide();
		}
		else{
			$('#but').hide();
			$("#showinfo_vrep").html('廠商已檢舉失敗，檢舉內容為:'+vde+ '<br>');
			$("#showinfo_vrep").show();
			$('#selectVen').hide();
		}
	}
	else if(mst ===3){
		$("#showinfo_mrep").html('會員已檢舉失敗，檢舉內容為:'+mde+ '<br>');
		$("#showinfo_mrep").show();
		$('#selectMem').hide();
		if(vst ===0){
			$('#but').hide();
			$("#showinfo_vrep").hide();
			$('#selectVen').hide();
		}
		else if(vst ===1){
			$('#but').show();
			$("#showinfo_vrep").html('廠商已提出檢舉，檢舉內容為:'+vde+ '<br>');
			$("#showinfo_vrep").show();
			$('#selectVen').show();
		}
		else if(vst===2){
			$('#but').hide();
			$("#showinfo_vrep").html('廠商已檢舉成功，檢舉內容為:'+vde+ '<br>');
			$("#showinfo_vrep").show();
			$('#selectVen').hide();
		}
		else{
			$("#showinfo_vrep").html('廠商已檢舉失敗，檢舉內容為:'+vde+ '<br>');
			$("#showinfo_vrep").show();
			$('#selectVen').hide();
		}
	}
}
//2-3:送出審核結果
$("#confirmRep").click(function(event){
	$('#resForm').submit();
	alert("審核成功!");
})
</script>


  <!-- body 結束標籤之前，載入Bootstrap 的 JS 及其相依性安裝(jQuery、Popper) -->
  <script src="<%=request.getContextPath() %>/vendors/popper/popper.min.js"></script>
  <script src="<%=request.getContextPath() %>/vendors/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>