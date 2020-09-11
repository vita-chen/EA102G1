<%@page import="com.membre.model.MembreVO"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.wpcase.model.*"%>
<%@page import="com.wporder.model.WPOrderVO"%>
<%
  	WPOrderVO WPOrderVO = (WPOrderVO) request.getAttribute("WPOrderVO");
	WPCaseVO WPCaseVO = (WPCaseVO) request.getAttribute("WPCaseVO");
	if(WPCaseVO == null){
		response.sendRedirect(request.getContextPath()+"/front_end/wed_photo/wp_home.jsp");
	    return;
	}
	MembreVO membrevo = (MembreVO)session.getAttribute("membrevo");
	if(membrevo == null){
		String url = request.getContextPath() +"/front_end/wed_photo/placeWPOrder.jsp";
		session.setAttribute("location",url);
		response.sendRedirect(request.getContextPath()+"/front_end/membre/login.jsp");
	    return;
	}
%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
	table{
		width: 40%;
		border: dashed 1px #FFB6FFFF;		
		font-family: 微軟正黑體,Bookman Old Style;		
	}
	textarea{
		width: 350px;
		height: 250px;
	}
</style>
<meta charset="utf-8">
<title>place_an_order - 婚禮攝影服務 下訂單</title>
</head>
<body>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/wed/wpcase.do">
	<table>
		<tr>
			<td>方案名稱</td>
			<td>${WPCaseVO.wed_photo_case_no }${WPCaseVO.wed_photo_name }</td>
		</tr>
		<tr>
			<td>廠商編號</td>
			<td>${WPCaseVO.vender_id }</td>
		</tr>
		<tr>
			<td>會員編號</td>
			<td><input type="text" name="MEMBRE_ID" value="${membrevo.membre_id }" ></td>
		</tr>
		<tr>
			<td>選擇服務日期</td>
			<td><input id="f_date1" type="text" name="FILMING_TIME"></td>
		</tr>
		<tr>
			<td>訂單備註</td>
			<td><textarea name="ORDER_EXPLAIN" placeholder="300字內"><%= (WPOrderVO==null)? "" : WPOrderVO.getOrder_explain()%></textarea></td>
		</tr>
		<tr>
			<td>價格</td>
			<td>${WPCaseVO.wed_photo_price }</td>
		</tr>
	</table>
<input type="hidden" name="WED_PHOTO_CASE_NO" value="${WPCaseVO.wed_photo_case_no }"/>
<input type="hidden" name="WED_PHOTO_NAME" value="${WPCaseVO.wed_photo_name }"/>
<input type="hidden" name="VENDER_ID" value="${WPCaseVO.vender_id }"/>
<input type="hidden" name="WED_PHOTO_PRICE" value="${WPCaseVO.wed_photo_price }"/>
<input type="hidden" name="TOOL" value="servlet"/>
<input type="hidden" name="action" value="Generate_order">
<br><input type="submit" value="確認下單">
</form>
</body>
<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<% 
  java.sql.Timestamp hiredate = null;
  try {
	    hiredate = WPOrderVO.getFilming_time();
   } catch (Exception e) {
	    hiredate = new java.sql.Timestamp(System.currentTimeMillis());
   }
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>
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
        
        
   
        // ----------------------------------------------------------以下用來排定無法選擇的日期-----------------------------------------------------------

        //      1.以下為某一天之前的日期無法選擇
        //      var somedate1 = new Date('2017-06-15');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() <  somedate1.getYear() || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});

        
        //      2.以下為某一天之後的日期無法選擇
        //      var somedate2 = new Date('2017-06-15');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() >  somedate2.getYear() || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});


        //      3.以下為兩個日期之外的日期無法選擇 (也可按需要換成其他日期)
        //      var somedate1 = new Date('2017-06-15');
        //      var somedate2 = new Date('2017-06-25');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() <  somedate1.getYear() || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
        //		             ||
        //		            date.getYear() >  somedate2.getYear() || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});
        
</script>
</html>