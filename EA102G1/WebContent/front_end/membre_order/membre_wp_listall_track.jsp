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
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>婚攝收藏清單</title>
	<meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />

	<!-- Fonts and icons -->
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
		
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+TC:wght@300;500&display=swap" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/css/menu_style.css" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/css/icofont/icofont.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/css/boxicons/css/boxicons.min.css" rel="stylesheet">
	

</head>
<body>
	<div class="wrapper">
	<!-- topbar -->
	
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
								<a href="#">收藏管理</a>
							</li>
							<li class="separator">
								<i class="flaticon-right-arrow"></i>
							</li>
							<li class="nav-item">
								<a href="#">婚攝收藏清單</a>
							</li>
						</ul>	
</div>
<!-- 塞資料的地方 -->
<!-- 塞資料的地方 -->
<div class="page-category">
				
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
	                            <td><button type="button" class="btn btn-secondary btnw Cancel" value="${WPCaseVO.wed_photo_case_no }">取消收藏</button></td>
	                            <td><button type="button" class="btn btn-secondary btnw Order" data-toggle="modal" data-target="#exampleModal" value="${WPCaseVO.vender_id }" name="${WPCaseVO.wed_photo_case_no}">直接下訂</button></td>
	                        </tr>
                       
                    </c:forEach>
                    </tbody>                    
                </table>
                <input type="hidden" name="membre_id" value="${membrevo.membre_id }">
                <div class="content_2">
                    <nav aria-label="Page navigation example">
                        <ul class="pagination justify-content-center">
                            <li class="page-item Previous"><a class="page-link" tabindex="-1">First</a></li>
                            <li class="page-item Next"><a class="page-link">Last</a></li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
   
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
                            <input id="f_date1" name="FILMING_TIME">
                        </div>
                        <div class="form-group">
						    <label for="exampleFormControlSelect1">選擇服務地點</label>
						    <select class="form-control county" id="exampleFormControlSelect1" name="COUNTY">
						    <option value="-1" disabled selected hidden>--請選擇縣市--</option>						     						      
						    </select>
						    <select class="form-control district" id="exampleFormControlSelect2" name="DISTRICT">
						    <option value="-1" disabled selected hidden>--請選擇鄉鎮--</option>					      					      						      
						    </select>
						    <input type="text" class="form-control form-control-plaintext" id="formGroupExampleInput" placeholder="路/巷/號/樓" name="SERVICE_ADDR">
					  	</div>
                        <div class="form-group">
                            <label for="exampleFormControlTextarea1">訂單備註區</label>
                            <textarea class="form-control" id="exampleFormControlTextarea1" rows="3" name="ORDER_EXPLAIN"></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                	<input type="hidden" size="3" name="VENDER_ID" value="">
                	<input type="hidden" size="5" name="WED_PHOTO_CASE_NO" value="">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary OrderSubmit" data-dismiss="modal">Save changes</button>
                </div>
            </div>
        </div>
    </div>

<!-- 塞資料的地方 -->
<!-- 塞資料的地方 -->
</div>
</div>

</div>
</div>
    <!-- Modal 直接下訂 區塊 End-->
    <script src="<%=request.getContextPath() %>/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath() %>/js/popper.min.js"></script>
    <script src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
<%--     <script src="<%=request.getContextPath() %>/js/login.js"></script> --%>
<%--     <script src="<%=request.getContextPath() %>/js/jquery.easing.min.js"></script> --%>
<%--     <script src="<%=request.getContextPath() %>/js/scripts.js"></script> --%>
    <script src="<%=request.getContextPath() %>/js/wp/tableSort.js" charset="utf-8" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<% 
  java.sql.Timestamp hiredate = new java.sql.Timestamp(System.currentTimeMillis());
%>

<script type="text/javascript">
$(document).ready(function() {
		
	var MyPoint = "/WpCaseWS/${membrevo.membre_id}";
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
	var webSocket;

	function connect() {
		// create a websocket 共同編輯(行程安排) 電影院訂票(即時顯示座位狀況)
		webSocket = new WebSocket(endPointURL);

		webSocket.onopen = function(event) {
			console.log("Connect Success!");
		};

		webSocket.onmessage = function(event) {
			var jsonObj = JSON.parse(event.data);
		
		};

		webSocket.onclose = function(event) {
			console.log("Disconnected!");
		};
	}
	connect();
	
	
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
		console.log($(this).val());
	})
	$(".OrderSubmit").click(function(){
		if($('.county').val() == null){
			alert('請輸入服務地點哦!')
			return;
		}
		var vender_id = $("[name=VENDER_ID]").val();
		var membre_id = $("[name=MEMBRE_ID]").val();
		var filming_time = $("[name=FILMING_TIME]").val();
// 		var	order_explain = $("[name=ORDER_EXPLAIN]").val();
		var wed_photo_case_no = $("[name=WED_PHOTO_CASE_NO]").val();
		var county = array[$('.county').val()][0];
		var district = array[$('.county').val()][$('.district').val()]
		var service_addr = $('[name=SERVICE_ADDR]').val()
		var	order_explain = county + district.substring(3) + service_addr +'　-　'+$("[name=ORDER_EXPLAIN]").val();
		
		console.log(order_explain)
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
				if(JSON.parse(data).note == "失敗"){
					alert(JSON.parse(data).time);
				}
				if(JSON.parse(data).note == "成功"){ //成功返回訂單編號
					alert(JSON.parse(data).wed_photo_order_no+' 訂單生成成功!');
					
					var jsonObj = {
							"type" : "order",
							"wed_photo_order_no" : JSON.parse(data).wed_photo_order_no ,
							"vender_id" : vender_id
						};
					webSocket.send(JSON.stringify(jsonObj));
				}
				
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
    
    var array = [
            ['台北市', '100中正區', '103大同區', '104中山區', '105松山區', '106大安區', '108萬華區', '110信義區', '111士林區', '112北投區', '114內湖區', '115南港區', '116文山區'],
            ['台北縣', '207萬里鄉', '208金山鄉', '220板橋市', '221汐止市', '222深坑鄉', '223石碇鄉', '224瑞芳鎮', '226平溪鄉', '227雙溪鄉', '228貢寮鄉', '231新店市', '232坪林鄉', '233烏來鄉', '234永和市', '235中和市', '236土城市', '237三峽鎮', '238樹林市', '239鶯歌鎮', '241三重市', '242新莊市', '243泰山鄉', '244林口鄉', '247蘆洲市', '248五股鄉', '248新莊市', '249八里鄉', '251淡水鎮', '252三芝鄉', '253石門鄉'],
            ['台中市', '400中區', '401東區', '402南區', '403西區', '404北區', '406北屯區', '407西屯區', '408南屯區'],
            ['台中縣', '411太平市', '412大里市', '413霧峰鄉', '414烏日鄉', '420豐原市', '421后里鄉', '422石岡鄉', '423東勢鎮', '424和平鄉', '426新社鄉', '427潭子鄉', '428大雅鄉', '429神岡鄉', '432大肚鄉', '433沙鹿鎮', '434龍井鄉', '435梧棲鎮', '436清水鎮', '437大甲鎮', '438外埔鄉', '439大安鄉'],
            ['台東縣', '950台東市', '951綠島鄉', '952蘭嶼鄉', '953延平鄉', '954卑南鄉', '955鹿野鄉', '956關山鎮', '957海端鄉', '958池上鄉', '959東河鄉', '961成功鎮', '962長濱鄉', '963太麻里鄉', '964金峰鄉', '965大武鄉', '966達仁鄉'],
            ['台南市', '700中西區', '701東區', '702南區', '704北區', '708安平區', '709安南區'],
            ['台南縣', '710永康市', '711歸仁鄉', '712新化鎮', '713左鎮鄉', '714玉井鄉', '715楠西鄉', '716南化鄉', '717仁德鄉', '718關廟鄉', '719龍崎鄉', '720官田鄉', '721麻豆鎮', '722佳里鎮', '723西港鄉', '724七股鄉', '725將軍鄉', '726學甲鎮', '727北門鄉', '730新營市', '731後壁鄉', '732白河鎮', '733東山鄉', '734六甲鄉', '735下營鄉', '736柳營鄉', '737鹽水鎮', '741善化鎮', '741新市鄉', '742大內鄉', '743山上鄉', '744新市鄉', '745安定鄉'],
            ['宜蘭縣', '260宜蘭市', '261頭城鎮', '262礁溪鄉', '263壯圍鄉', '264員山鄉', '265羅東鎮', '266三星鄉', '267大同鄉', '268五結鄉', '269冬山鄉', '270蘇澳鎮', '272南澳鄉', '290釣魚台'],
            ['花蓮縣', '970花蓮市', '971新城鄉', '972秀林鄉', '973吉安鄉', '974壽豐鄉', '975鳳林鎮', '976光復鄉', '977豐濱鄉', '978瑞穗鄉', '979萬榮鄉', '981玉里鎮', '982卓溪鄉', '983富里鄉'],
            ['金門縣', '890金沙鎮', '891金湖鎮', '892金寧鄉', '893金城鎮', '894烈嶼鄉', '896烏坵鄉'],
            ['南投縣', '540南投市', '541中寮鄉', '542草屯鎮', '544國姓鄉', '545埔里鎮', '546仁愛鄉', '551名間鄉', '552集集鎮', '553水里鄉', '555魚池鄉', '556信義鄉', '557竹山鎮', '558鹿谷鄉'],
            ['南海島', '817東沙群島', '819南沙群島'],
            ['屏東縣', '900屏東市', '901三地門鄉', '902霧台鄉', '903瑪家鄉', '904九如鄉', '905里港鄉', '906高樹鄉', '907鹽埔鄉', '908長治鄉', '909麟洛鄉', '911竹田鄉', '912內埔鄉', '913萬丹鄉', '920潮州鎮', '921泰武鄉', '922來義鄉', '923萬巒鄉', '924崁頂鄉', '925新埤鄉', '926南州鄉', '927林邊鄉', '928東港鎮', '929琉球鄉', '931佳冬鄉', '932新園鄉', '940枋寮鄉', '941枋山鄉', '942春日鄉', '943獅子鄉', '944車城鄉', '945牡丹鄉', '946恆春鎮', '947滿州鄉'],
            ['苗栗縣', '350竹南鎮', '351頭份鎮', '352三灣鄉', '353南庄鄉', '354獅潭鄉', '356後龍鎮', '357通霄鎮', '358苑裡鎮', '360苗栗市', '361造橋鄉', '362頭屋鄉', '363公館鄉', '364大湖鄉', '365泰安鄉', '366銅鑼鄉', '367三義鄉', '368西湖鄉', '369卓蘭鎮'],
            ['桃園縣', '320中壢市', '324平鎮市', '325龍潭鄉', '326楊梅鎮', '327新屋鄉', '328觀音鄉', '330桃園市', '333龜山鄉', '334八德市', '335大溪鎮', '336復興鄉', '337大園鄉', '338蘆竹鄉'],
            ['高雄市', '800新興區', '801前金區', '802苓雅區', '803鹽埕區', '804鼓山區', '805旗津區', '806前鎮區', '807三民區', '811楠梓區', '812小港區', '813左營區', '817東沙群島', '819南沙群島'],
            ['高雄縣', '814仁武鄉', '815大社鄉', '820岡山鎮', '821路竹鄉', '822阿蓮鄉', '823田寮鄉', '824燕巢鄉', '825橋頭鄉', '826梓官鄉', '827彌陀鄉', '828永安鄉', '829湖內鄉', '830鳳山市', '831大寮鄉', '832林園鄉', '833鳥松鄉', '840大樹鄉', '842旗山鎮', '843美濃鎮', '844六龜鄉', '845內門鄉', '846杉林鄉', '847甲仙鄉', '848桃源鄉', '849那瑪夏鄉', '851茂林鄉', '852茄萣鄉'],
            ['基隆市', '200仁愛區', '201信義區', '202中正區', '203中山區', '204安樂區', '205暖暖區', '206七堵區'],
            ['連江縣', '209南竿鄉', '210北竿鄉', '211莒光鄉', '212東引鄉'],
            ['釣魚台', '290釣魚台'],
            ['雲林縣', '630斗南鎮', '631大埤鄉', '632虎尾鎮', '633土庫鎮', '634褒忠鄉', '635東勢鄉', '636台西鄉', '637崙背鄉', '638麥寮鄉', '640斗六市', '643林內鄉', '646古坑鄉', '647莿桐鄉', '648西螺鎮', '649二崙鄉', '651北港鎮', '652水林鄉', '653口湖鄉', '654四湖鄉', '655元長鄉'],
            ['新竹市', '300北區', '300東區', '300香山區'],
            ['新竹縣', '300寶山鄉', '302竹北市', '303湖口鄉', '304新豐鄉', '305新埔鎮', '306關西鎮', '307芎林鄉', '308寶山鄉', '310竹東鎮', '311五峰鄉', '312橫山鄉', '313尖石鄉', '314北埔鄉', '315峨眉鄉'],
            ['嘉義市', '600西區', '600東區'],
            ['嘉義縣', '602番路鄉', '603梅山鄉', '604竹崎鄉', '605阿里山鄉', '606中埔鄉', '607大埔鄉', '608水上鄉', '611鹿草鄉', '612太保市', '613朴子市', '614東石鄉', '615六腳鄉', '616新港鄉', '621民雄鄉', '622大林鎮', '623溪口鄉', '624義竹鄉', '625布袋鎮'],
            ['彰化縣', '500彰化市', '502芬園鄉', '503花壇鄉', '504秀水鄉', '505鹿港鎮', '506福興鄉', '507線西鄉', '508和美鎮', '509伸港鄉', '510員林鎮', '511社頭鄉', '512永靖鄉', '513埔心鄉', '514溪湖鎮', '515大村鄉', '516埔鹽鄉', '520田中鎮', '521北斗鎮', '522田尾鄉', '523埤頭鄉', '524溪州鄉', '525竹塘鄉', '526二林鎮', '527大城鄉', '528芳苑鄉', '530二水鄉'],
            ['澎湖縣', '880馬公市', '881西嶼鄉', '882望安鄉', '883七美鄉', '884白沙鄉', '885湖西鄉']
          ]
	var str = '';
	for(var i =0 ;i<array.length;i++){
		str += '<option value="'+i+'">'+array[i][0]+'</option>';
	}
	$('.county').append(str);
	
	$('.county').change(function(){
		$('.district').empty();
		var str ='';
		var county = $(this).val();
		
		for(var i=1;i<array[county].length;i++){
			str += '<option value="'+i+'">'+array[county][i]+'</option>';
		}
		$('.district').append(str);
		
	})

})
</script>


</body>
</html>