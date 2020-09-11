<%@page import="com.wpcase.model.WPCaseVO"%>
<%@page import="com.wpcollect.model.WPCollectVO"%>
<%@page import="java.util.List"%>
<%@page import="com.wpcase.model.WPCaseService"%>
<%@page import="com.membre.model.MembreVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	MembreVO membrevo = (MembreVO)session.getAttribute("membrevo");
	if(membrevo == null){
		String url = request.getContextPath() +"/front_end/membre/wp/listAllCollectWP.jsp";
		session.setAttribute("location",url);
		response.sendRedirect(request.getContextPath()+"/front_end/membre/login.jsp");
	    return;
	}
	String membre_id = membrevo.getMembre_id();
	WPCaseService wpsvc = new WPCaseService();
	List<WPCollectVO> list = wpsvc.selCollect(membre_id);
	
	List<WPCaseVO> wpcaselist = wpsvc.search_case(list);	//由收藏VO取得方案VO
	pageContext.setAttribute("wpcaselist",wpcaselist);
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Collect Wed-photo Case - 查詢收藏方案</title>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+TC:wght@300;500&display=swap" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/css/menu_style.css" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/css/icofont/icofont.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/css/boxicons/css/boxicons.min.css" rel="stylesheet">
</head>
<style type="text/css">
.xdsoft_datetimepicker .xdsoft_datepicker {
         width:  300px;   /* width:  300px; */
}
.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
         height: 151px;   /* height:  151px; */
}
.col-2 {
    padding-right: 20px;
    padding-left: 20px;
}

.col-10 {
    padding: 0;
}

.img_banner {
    height: 200px;
    border: dotted 2px #D6D6D6FF;
}

.list-group-item.active {
    z-index: 2;
    color: #3D3D3DFF;
    background-color: #EAEAEAFF;
    border-color: #C4C4C4FF;
}

.btn-secondary:hover {
    color: #292b2c;
    background-color: #FFFFFFFF;
    border-color: #adadad;
}

.content_1 {
    height: 65px;
    box-shadow: 0px 0px 3px 0px #C4C4C4FF;

}

.content_2 {
    height: 65px;
    box-shadow: 0px 0px 3px 0px #C4C4C4FF;

}

.content_1 p {
    padding-left: 12px;
    font-size: 25px;
    font-weight: 700;
}

.btnw {
    padding: 5px 16px 6px 16px;
}

.footer {
    height: 200px;
    border: dotted 2px #C9C9C9FF;
    margin-top: 50px;
}

.badge-dark {
    background-color: #686868FF;
}
tbody tr > th{
    width: 5%;
}
</style>

<body>
    <div class="header">
        <div class="welcome">
            <p>這邊是 歡迎小文字		你好${membrevo.mem_name }</p>
        </div>
        <div class="img_banner">圖片區</div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div>
                    <ol>
                        <li><a href="<%=request.getContextPath()%>/front_end/home/home.jsp" id="">婚禮導航</a></li>
                        <li><a href="<%=request.getContextPath()%>/front_end/wed_photo/wp_home.jsp" id="">婚禮攝影</a></li>
                        <li><a href="<%=request.getContextPath()%>/front_end/prod/index.jsp" id="">二手商城</a></li>
                    </ol>
                </div>
            </div>
        </div>
        <div class="row justify-content-around">
            <div class="col-2">
                <div class="list-group">
                    <a href="<%=request.getContextPath()%>/front_end/membre/wp/listAllWPOrder.jsp" class="list-group-item list-group-item-action">訂單查詢</a>
                    <a href="<%=request.getContextPath()%>/front_end/membre/wp/listAllCollectWP.jsp" class="list-group-item list-group-item-action active">收藏方案</a>
                </div>
            </div>
            <div class="col-10">
                <div class="content_1">
                    <p>方案收藏頁面</p>
                </div>
                <table class="table table-hover tablerow" id="tableSort">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col-1" data-type="num">#</th>
                            <th scope="col-1" data-type="string">方案編號</th>
                            <th scope="col-1" data-type="string">廠商編號</th>
                            <th scope="col-5" data-type="string">方案名稱</th>
                            <th scope="col-1">取消收藏</th>
                            <th scope="col-1">立即下訂</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="WPCaseVO" items="${wpcaselist}">
                    
	                        <tr>
	                            <th scope="row"></th>
	                            <td><a href="<%=request.getContextPath()%>/wed/wpcase.do?action=getOne_CasePage&wed_photo_case_no=${WPCaseVO.wed_photo_case_no}" target="_blank">${WPCaseVO.wed_photo_case_no}</a></td>
	                            <td>${WPCaseVO.vender_id }</td>
	                            <td>${WPCaseVO.wed_photo_name }</td>
	                            <td><button type="button" class="btn btn-secondary btnw Cancel" value="${WPCaseVO.wed_photo_case_no }">Cancel</button></td>
	                            <td><button type="button" class="btn btn-secondary btnw Order" data-toggle="modal" data-target="#exampleModal" value="${WPCaseVO.vender_id }" name="${WPCaseVO.wed_photo_case_no}">Order</button></td>
	                        </tr>
                       
                    </c:forEach>
                    </tbody>                    
                </table>
                <input type="hidden" name="membre_id" value="${membrevo.membre_id }">
                <div class="content_2">
                    <nav aria-label="Page navigation example">
                        <ul class="pagination justify-content-center">
                            <li class="page-item Previous"><a class="page-link" tabindex="-1">Previous</a></li>
                            <li class="page-item Next"><a class="page-link">Next</a></li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
    <div class="footer">footer區 尚未添加內容</div>
    <!-- Modal 直接下訂 區塊-->
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Order</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="form-group">
                            <label for="formGroupExampleInput">會員編號</label>
                            <input type="text" readonly class="form-control form-control-plaintext" id="formGroupExampleInput" value="${membrevo.membre_id }" name="MEMBRE_ID">
                        </div>
                        <div class="form-group">
                            <label for="f_date1">選擇服務日期</label>
<!--   type="date" class="form-control" -->
                            <input id="f_date1" name="FILMING_TIME">
                        </div>
                        <div class="form-group">
                            <label for="exampleFormControlTextarea1">訂單備註區</label>
                            <textarea class="form-control" id="exampleFormControlTextarea1" rows="3" name="ORDER_EXPLAIN"></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                	<input type="text" size="3" name="VENDER_ID" value="">
                	<input type="text" size="5" name="WED_PHOTO_CASE_NO" value="">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary OrderSubmit" data-dismiss="modal">Save changes</button>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal 直接下訂 區塊 End-->
    <script src="<%=request.getContextPath() %>/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath() %>/js/popper.min.js"></script>
    <script src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath() %>/js/login.js"></script>
    <script src="<%=request.getContextPath() %>/js/jquery.easing.min.js"></script>
    <script src="<%=request.getContextPath() %>/js/scripts.js"></script>
    <script src="<%=request.getContextPath() %>/js/wp/tableSort.js" charset="utf-8" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
</body>

<% 
  java.sql.Timestamp hiredate = new java.sql.Timestamp(System.currentTimeMillis());

%>

<script type="text/javascript">
$(document).ready(function() {
	
	$.datetimepicker.setLocale('zh');
    $('#f_date1').datetimepicker({
       theme: '',              //theme: 'dark',
       timepicker:true,       //timepicker:true,
       step: 30,                //step: 60 (這是timepicker的預設間隔60分鐘)
       format:'Y-m-d H:i',         //format:'Y-m-d H:i:s',
	   value: '<%=hiredate%>', // value:   new Date(),
       //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
       //startDate:	            '2017/07/10',  // 起始日
       minDate:               '-1969-12-17' // 去除今日(不含)之前 從14天後開始選
       //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
    });

	var url = (window.location.href).split('/')[0]+'/'+(window.location.href).split('/')[1]+'/'+(window.location.href).split('/')[2]+'/'+(window.location.href).split('/')[3]
	
    $("tbody > tr").each(function(i, item) {
        $(this).children().eq(0).html('<span class="badge badge-dark">'+(i+1)+'</span>');
    })
	$(".Order").click(function(){
		$("[name=VENDER_ID]").val($(this).val());
		$("[name=WED_PHOTO_CASE_NO]").val($(this).attr('name'));
		console.log($(this).attr('name'));
	})
	$(".OrderSubmit").click(function(){
		var vender_id = $("[name=VENDER_ID]").val();
		var membre_id = $("[name=MEMBRE_ID]").val();
		var filming_time = $("[name=FILMING_TIME]").val();
		var	order_explain = $("[name=ORDER_EXPLAIN]").val();
		var wed_photo_case_no = $("[name=WED_PHOTO_CASE_NO]").val();
		
		$.ajax({
			type: "POST",
			url: url + "/wed/wpcase.do",
			data: {
				 action:"Generate_order",
				 TOOL:"ajax",
				 WED_PHOTO_CASE_NO:wed_photo_case_no,
				 VENDER_ID:vender_id,
				 MEMBRE_ID:membre_id,
				 FILMING_TIME:filming_time,
				 ORDER_EXPLAIN:order_explain				 
			 },			
			success: function (data){
				
				alert(data);
				
			}
		})
	})
    $(".Cancel").click(function() {
    	
    	var wed_photo_case_no = $(this).val();
    	var membre_id = $("[name=membre_id]").val();
//     	console.log(wed_photo_case_no);
//     	console.log(membre_id);
        
        $(".Cancel").each(function(i){
			if($(this).val() == wed_photo_case_no){
				console.log('第'+i+'個'); //要+1
				var selector = 'tbody tr:nth-child('+(i+1)+')';
			    var thistr = document.querySelector(selector);
			    console.log(thistr);
			    
			    var ans = confirm('確定取消收藏嗎？'); // true or false
// 			    console.log(ans);
			    if(ans){
		        	$.ajax({
						type: "POST",
						url: url + "/wed/wpcase.do",
						data: {
							 action:"dis_Collect",
							 wed_photo_case_no:wed_photo_case_no,
							 membre_id:membre_id
						 },			
						success: function (data){
							alert(data);
							thistr.remove();
						}
					})
		        }
			}			
		})
    })

})
</script>
</html>