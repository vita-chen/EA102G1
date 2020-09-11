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
<html>
<head>
<meta charset="UTF-8">
<title>評價</title>
    <link href="http://yuancheng-001-site1.1tempurl.com/styles/css/app.css" rel="stylesheet">
    <link href="http://yuancheng-001-site1.1tempurl.com/styles/css/common.css" rel="stylesheet">
    <link href="http://yuancheng-001-site1.1tempurl.com/styles/css/myshop.css" rel="stylesheet">
<link rel="stylesheet" href="../../vendors/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath() %>/vendors/jquery/jquery-3.4.1.min.js"></script>
<style type="text/css">

	
	/* 評價的星星數 */
	.wrapper {
	  display: inline-block;
	}
	.wrapper * {
	  float: right;
	}
	input {
	  display: none;
	}
	label {
	  font-size: 20px;
	}
	
	input:checked ~ label {
	  color: orange;
	}
</style>
</head>
<body>

<div class="container mx-auto h-auto mt-48" style="min-height: 100%">
        <div class="container mx-auto">
        <div class="flex flex-wrap justify-center">
            <div class="w-full max-w-sm">
                <div class="flex flex-col break-words bg-white border border-2 rounded shadow-md">

                    <div class="font-semibold bg-gray-200 text-gray-700 py-3 px-6 mb-0">
                       	 評價訂單
                    </div>

      <div class="modal-body" style="">
        恭喜您完成終身大事，希望您喜歡我們的服務!
        <br><br>
        請留下您的評價星數，並給予您珍貴的意見。
        <br><br>
        <!-- star -->
        <div class="wrapper2" style="
-webkit-transform:scaleX(-1);
">
		  <input type="checkbox" id="r1" name="rg1" value="5">
		  <label for="r1">&#10038;</label>
		  <input type="checkbox" id="r2" name="rg1" value="4">
		  <label for="r2">&#10038;</label>
		  <input type="checkbox" id="r3" name="rg1" value="3">
		  <label for="r3">&#10038;</label>
		  <input type="checkbox" id="r4" name="rg1" value="2">
		  <label for="r4">&#10038;</label>
		  <input type="checkbox" id="r5" name="rg1" value="1">
		  <label for="r5">&#10038;</label>
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
		  $("#new_content").append('<div class="table-responsive"><table class="table table-hover">');
		  $("#new_content").append('<thead><tr>');
		  $("#new_content").append('<th width="220" height="40" bgcolor="white">方案名稱</th><th width="80">方案價格</th><th width="300">加購項目</th><th width="80">小計</th>');
		  $("#new_content").append('</tr></thead><tbody>');
		  
		  for(var i=0; i<n;i++){
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
		$("#showinfo").html('');
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
$('#confirmRev').on("click",function(event){
	//取內容(已成功)
	var revContent = $('#message-text').val();
	//取星數：
	var num = [];
	$('input[type=checkbox]:checked').each(function(){
		num.push($(this).val());
	});
	//檢查該陣列不為空值後，在陣列中取最大值
	if (!Array.isArray(num) || !num.length){
		alert("請勾選評價星數，謝謝");
		return;
	}
	var max = num[0];
	for(var i=0;i<num.length;i++){
		if(num[i]>max){
			max = num[i];
		}	
	}
	$('#star').val(max);
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
</body>
</html>