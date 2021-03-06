$(document).ready(function(){
	var url = (window.location.href).split('/')[0]+'/'+(window.location.href).split('/')[1]+'/'+(window.location.href).split('/')[2]+'/'+(window.location.href).split('/')[3]
	console.log(url);
	
	$("tbody > tr").each(function(i,item){
		$(this).children().eq(0).html('<span class="badge badge-secondary">'+(i+1)+'</span>');
	})
	
	 const $valueSpan = $('.valueSpan');
	 const $value = $('#customRange1');
	 $valueSpan.html($value.val());
	 $value.on('input change', () => {	//range出現的奇怪錯誤訊息 但是可以執行
	   $valueSpan.html($value.val());
	 });
	 
	 
		$.ajax({
			type: "POST",
			url: url + "/wed/wpcase.do",
				dataType: "json",
				data: {
					action:"select_order_no_Ajax"
				},
				success:function(data){
					$("[name='select_order_no']").empty();
					$("[name='select_order_no']").append("<option value='0'> 請選擇 </option>");
					$(data).each(function(i, item){
						var membre_id = $("[name='membre_id']").val();
						console.log(membre_id)
						if(membre_id == item.membre_id){
							$("[name='select_order_no']").append("<option value='"+item.wed_photo_order_no+"'>"+item.wed_photo_order_no+"</option>");
						}
//	 					$("[name='select_order_no']").append("<option></option>");
//	 					$("option").eq(i).attr("value",item.wed_photo_order_no);
//	 					$("option").eq(i).text(item.wed_photo_order_no);
					});
				}
		})
		
		
		//訂單按鈕的判斷
		$(".complete").each(function(){
			var order_status = $(this).siblings().eq(0).attr("value"); //訂單狀態
			var wp_vrep_s = $(this).siblings().eq(1).attr("value"); //廠商檢舉狀態
			var wp_mrep_s = $(this).siblings().eq(2).attr("value"); //會員檢舉狀態
			if(order_status == 2){
				//取消
				$(this).text("訂單取消");
				$(this).prop("disabled",true);
				$(this).attr("class","btn btn-danger");
				$(this).nextAll(".btn-secondary").prop("disabled",true);
				$(this).nextAll(".btn-secondary").attr("class","btn btn-danger dropdown-toggle px-3");
			}
			else if(order_status == 1 && wp_mrep_s != 0){
				//檢舉
				$(this).text("　檢舉　");
				$(this).prop("disabled",true);
				$(this).nextAll(".btn-secondary").prop("disabled",true);
			}else if(order_status == 1 && wp_vrep_s != 0){
				//被檢舉 不能按完成與取消
				$(this).prop("disabled",true);
				//刪除線效果 但還是可以按取消
				$(this).nextAll(".dropdown-menu").children().eq(1).css("text-decoration","line-through");
			}else if(order_status == 3){
				//完成
				$(this).prop("disabled",true);
				$(this).nextAll(".btn-secondary").prop("disabled",true);
			}else{
				//可以按完成
			}
		})
		
		// 查詢更多訂單資訊
	$(".read_more").click(function(){
		
		$("[name='wed_photo_order_no']").val($(this).attr("value"));
		$("[name='vender_id']").val($(this).attr("name"));
		 const $valueSpan = $('.valueSpan2');
		 const $value = $('#customRange2'); //input type=range 的	 
		 $value.on('input change', () => {	//range出現的奇怪錯誤訊息 但是可以執行
		   $valueSpan.html($value.val());
		 });
		$.ajax({
			type: "POST",
			 url: url + "/wed/wpcase.do",
			 data: {
				 action:"getOne_Order_Ajax",
				 wed_photo_order_no:$(this).attr("value"),
				 identity:"member"
			 },			
			 success: function (data){
				$('[name="order_status"]').val(JSON.parse(data).order_status);
				$('[name="order_explain"]').val(JSON.parse(data).order_explain);//order_explain
// 				$('[name="review_star"]').val(JSON.parse(data).review_star);//review_star
				$('#customRange2').attr("value",JSON.parse(data).review_star);
				$valueSpan.html($value.val());
				$('[name="review_content"]').val(JSON.parse(data).review_content);//review_content
				$('[name="wp_mrep_s"]').val(JSON.parse(data).wp_mrep_s);//wp_mrep_s
				$('[name="wp_mrep_d2"]').val(JSON.parse(data).wp_mrep_d);//wp_mrep_d
				$('.case_info').text(JSON.parse(data).wed_photo_case_no+' '+JSON.parse(data).wed_photo_name)
				$('.case_a').attr('href',url+'/wed/wpcase.do?action=getOne_CasePage&wed_photo_case_no='+JSON.parse(data).wed_photo_case_no)
				$('[name="contact"]').val(JSON.parse(data).contact);
				$('[name="phone"]').val(JSON.parse(data).v_phone);
				$('[name="e-mail"]').val(JSON.parse(data).v_mail);
			 }
		 })
	})
	$(".complete").click(function(){
		$("[name='wed_photo_order_no']").val($(this).attr("name"));
		$("[name='vender_id']").val($(this).attr("value"));
	})
	// 點選訂單完成 出現填寫評價表格(非必填) 才進資料庫
	$(".complete_submit").click(function(){
		$.ajax({
			type: "POST",
			 url: url + "/wed/wpcase.do",
			 data: {
				 action:"getOne_Order_Ajax_2",
				 wed_photo_order_no:$("[name='wed_photo_order_no']").val(),
				 vender_id:$("[name='vender_id']").val(),
				 identity:"member"
			 },
			 success: function (data){
				 var order_status = JSON.parse(data).order_status;
				 var wp_vrep_s = JSON.parse(data).wp_vrep_s;
				 
				 if(order_status != 2 && wp_vrep_s == 0){
					 $.ajax({
						 type: "POST",
						 url: url + "/wed/wpcase.do",
						 data: {
							 action:"complete_order",
							 identity:"member",
							 vender_id:$("[name='vender_id']").val(),
							 wed_photo_order_no:$("[name='wed_photo_order_no']").val(),
							 review_star:$('.valueSpan').text(),
							 review_content:$("[name='review_content_submit']").val()
						 },			
						 success: function (data){	 
							 alert(data+' 訂單完成囉!');
								$(":button").each(function(){
									if(data == $(this).attr("name")){
										$(this).prop("disabled",true);
										$(this).nextAll(".btn-secondary").prop("disabled",true);
									}
								})				 
						 }
					 })
				 }else{
					 alert('無法完成訂單! 請重新整理');
				 }
			 }
		})
	})
	
	$(".cancle").click(function(){
		var wed_photo_order_no = $(this).attr("value");
		var ans = confirm('確定取消訂單嗎？');
		if(ans){
			$.ajax({
				 type: "POST",
				 url: url + "/wed/wpcase.do",
				 data: {
					 action:"getOne_Order_Ajax_2",
					 wed_photo_order_no:wed_photo_order_no
				 },			
				 success: function (data){
					 var order_status = JSON.parse(data).order_status;
					 var wp_vrep_s = JSON.parse(data).wp_vrep_s;
					 if(order_status == 1 && wp_vrep_s == 0){
						 $.ajax({
							 type: "POST",
							 url: url + "/wed/wpcase.do",
							 data: {
								 action:"cancel_order",
								 wed_photo_order_no:wed_photo_order_no
							 },			
							 success: function (data){
								 alert(data+' 訂單取消囉!');
									$(":button").each(function(){
										if(data == $(this).attr("name")){
											$(this).text("訂單取消");
											$(this).prop("disabled",true);
											$(this).attr("class","btn btn-danger");
											$(this).nextAll(".btn-secondary").prop("disabled",true);
											$(this).nextAll(".btn-secondary").attr("class","btn btn-danger dropdown-toggle px-3");
										}
									})
							 }
						 })
					 }else{
						 alert('無法取消訂單! 請重新整理');
					 }
				 }
			})
			
		}		
	})	
	$(".report").click(function(){
		$("[name='wed_photo_order_no']").val($(this).attr("value"));
	})
	$(".report_submit").click(function(){
		var wed_photo_order_no = $("[name='wed_photo_order_no']").val();
		var wp_mrep_d = $("[name='wp_mrep_d']").val();
		$.ajax({
			 type: "POST",
			 url: url + "/wed/wpcase.do",
			 data: {
				 action:"getOne_Order_Ajax_2",
				 wed_photo_order_no:wed_photo_order_no
			 },			
			 success: function (data){
				 var order_status = JSON.parse(data).order_status;
				 
				 if(order_status != 1){
					 alert('無法提出檢舉! 請重新整理');					 
				 }else{
					 $.ajax({
						 type: "POST",
						 url: url + "/wed/wpcase.do",
						 data: {
							 action:"mem_report",
							 wp_mrep_d:wp_mrep_d,
							 wed_photo_order_no:wed_photo_order_no
							 //取得類別為report 的 value="${wpordervo.wed_photo_order_no }"值 傳給後端找資料	 
						 },			
						 success: function (data){ //retrun wed_photo_order_no
							 alert(data+' 檢舉內容已送出!');
							$(":button").each(function(){
								if(data == $(this).attr("name")){
									$(this).text("　檢舉　");
									$(this).prop("disabled",true);
									$(this).nextAll(".btn-secondary").prop("disabled",true);
								}
							})
						 }
					 })
				 }
			 }
		})
	})
	$(".update_order_Ajax").click(function(){
		$.ajax({
			type: "POST",
			url: url + "/wed/wpcase.do",
			data: {
				action:"update_order_Ajax",
				order_status:$("[name='order_status']").val(),
				wed_photo_order_no:$("[name='wed_photo_order_no']").val(),
				vender_id:$("[name='vender_id']").val(),
				order_explain:$("[name='order_explain']").val(),
				review_star:$('.valueSpan2').text(),
				review_content:$("[name='review_content']").val()				
			},
			success: function(data){
 				alert(data);
			}			
		})	
	})
	$("[name='select_order_no']").change(function(){
		var order_no = $("option:selected").attr("value");

		$("tr").each(function(){
			if(order_no == $(this).children().eq(1).text() || "訂單編號" == $(this).children().eq(1).text()){
				$(this).show();
			}else if(order_no == 0){
				$(this).show();
// 				$("tr:hidden").show();
			}else{
				$(this).hide();
			}
		})	
	})

})