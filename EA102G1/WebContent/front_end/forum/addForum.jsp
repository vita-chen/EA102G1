
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 設定時間 -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<%@ page import="java.util.*"%>
<%@ page import="com.forum.model.*"%>
<%@ page import="com.membre.model.*"%>

<%
	ForumVO forumVO = (ForumVO) request.getAttribute("forumVO");

	String currentLocation = request.getRequestURI();
	String queryString = request.getQueryString();
	request.setAttribute("currentLocation", currentLocation);
	request.setAttribute("queryString", queryString);


	MembreVO membrevo = (MembreVO) session.getAttribute("membrevo");
	if (membrevo==null) {
		response.sendRedirect(request.getContextPath()+"/front_end/membre/login.jsp?location="+currentLocation+"?"+queryString);
	} else {

%>

<jsp:useBean id="memSvc" scope="page" class="com.membre.model.MembreService" />

<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>addForum</title>

  <!-- Bootstrap core CSS -->
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom fonts for this template -->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href='https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
  <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>

  <!-- Custom styles for this template -->
  <link href="css/clean-blog.min.css" rel="stylesheet">

</head>

<body>

   <!-- Navigation -->
  <nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
    <div class="container">
    
<!--     	left top start -->
      <a class="navbar-brand" href="<%=request.getContextPath()%>/front_end/home/home.jsp">Wedding Navi</a>
<!--     	left top end -->      
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        Menu
        <i class="fas fa-bars"></i>
      </button>
      
<!--       top bar start -->
      <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/front_end/home/home.jsp">首頁</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/front_end/prod/index.jsp">二手商城</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/back_end/back_end_home.jsp">後台</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/front_end/forum/listAllForum.jsp">討論區</a>  
          </li>
          
<!-- login start -->  			
					
     <c:if test="${membrevo==null }">
    		<div class="widget-header icontext">
    		<c:choose>
    		<c:when test="${queryString ==null }">
    		<a href="<%=request.getContextPath()%>/front_end/membre/login.jsp?location=${currentLocation}">Login</a> |  
    		</c:when>
    		<c:otherwise>
						<a href="<%=request.getContextPath()%>/front_end/membre/login.jsp?location=${currentLocation }?${queryString}">Login</a> |  
			</c:otherwise>
			</c:choose>
						<a href="<%=request.getContextPath()%>/front_end/membre/regis.jsp"> Register</a>
			</div>
			
    </c:if>
    
    <c:if test="${membrevo !=null }">
    	<li class="nav-item text-right myItem" style="margin-right:-20px"><a class="nav-link px-2"> <img style="width:20px; height:20px;" class="rounded" src="<%=request.getContextPath() %>/membre/membre.do?action=getphoto&membre_id=${membrevo.membre_id }"></a></li>
		<li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="http://example.com">${membrevo.mem_name}</a>
          <div class="dropdown-menu">
          	<a class="dropdown-item" href="<%=request.getContextPath()%>/front_end/membre_order/membre_order_home.jsp">會員訂單</a>
            <a class="dropdown-item" href="#">Profile setting</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/order/order.do?action=getDifferentStatusOrders_buyer&order_status=All">My orders</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/order/order.do?action=getDifferentStatusOrders_seller&order_status=All">My orders (Seller Develop Only)</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/front_end/prod/myProds.jsp">My products</a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/membre/membre.do?action=logOut">登出</a>
          </div>
		</li>
    </c:if>	                                      
<!-- login end -->
                                                                   
        </ul>
      </div>
<!--       top bar end -->

    </div>
  </nav>

  <!-- Page Header -->
  <header class="masthead" style="background-image: url('img/photo-of-couple-hugging-each-other-3397032.jpg')">
    <div class="overlay"></div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="site-heading">
            <h1>Wedding Navi</h1>
            <span class="subheading">A Blog host by Professional team</span>
          </div>
        </div>
      </div>
    </div>
  </header>
     
<!-- to do -->
  <!-- Main Content -->
  		<div class="container">
    		<div class="row justify-content-center">
  				<div >
				<%-- 錯誤表列 --%>
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>								
				
				<FORM METHOD="post" ACTION="forum.do" name="form1">
					<table>
						<tr>
							<td>文章類別:</td>
							<td>
								<select name="forum_class">
									<option value="交流提問" selected>交流提問</option>
							  		<option value="閒聊" >閒聊</option>
							  		<option value="婚紗">婚紗</option>
							  		<option value="婚攝">婚攝</option>
							  		<option value="禮車">禮車</option>
							  		<option value="婚禮心事">婚禮心事</option>
							  	</select>
							</td>
							
						</tr>
						<tr>
							<td>文章標題:</td>
							<td><input type="TEXT" name="forum_title" id="title" size="45"></td>
						</tr>
						<tr>
							<td>文章內容:</td>
						</tr>
						<tr>
							<td colspan="2"><textarea name="forum_content" id="editor1"></textarea></td>
						</tr>
					</table>

					<script src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
					<script>
						CKEDITOR.replace('forum_content', {
							height : [ '500px' ]
						});
					</script>

				<br> 
				<input type="hidden" name="action" value="insert">
				<input type="hidden" name="membre_id" value= "${membrevo.membre_id}" >											                   										                    
<!-- 													先寫死，之後登入解決後再改動態寫法 -->
<!-- 				<input type="hidden" name="membre_id" value="M001"> -->
				<input type="submit" value="送出新增">
				<button type="button" onclick="addData()" >神奇小按鈕</button>
				
				
<!-- ckeditor 新增文章內容部分 -->
				
				<script>
				// Don't forget to add CSS for your custom styles.
			    CKEDITOR.addCss('figure[class*=easyimage-gradient]::before { content: ""; position: absolute; top: 0; bottom: 0; left: 0; right: 0; }' +
			      'figure[class*=easyimage-gradient] figcaption { position: relative; z-index: 2; }' +
			      '.easyimage-gradient-1::before { background-image: linear-gradient( 135deg, rgba( 115, 110, 254, 0 ) 0%, rgba( 66, 174, 234, .72 ) 100% ); }' +
			      '.easyimage-gradient-2::before { background-image: linear-gradient( 135deg, rgba( 115, 110, 254, 0 ) 0%, rgba( 228, 66, 234, .72 ) 100% ); }');

			    CKEDITOR.replace('editor1', {
			      extraPlugins: 'easyimage',
			      removePlugins: 'image',
			      removeDialogTabs: 'link:advanced',
			      toolbar: [{
			          name: 'document',
			          items: ['Undo', 'Redo']
			        },
			        {
			          name: 'styles',
			          items: ['Format']
			        },
			        {
			          name: 'basicstyles',
			          items: ['Bold', 'Italic', 'Strike', '-', 'RemoveFormat']
			        },
			        {
			          name: 'paragraph',
			          items: ['NumberedList', 'BulletedList']
			        },
			        {
			          name: 'links',
			          items: ['Link', 'Unlink']
			        },
			        {
			          name: 'insert',
			          items: ['EasyImageUpload', 'Table']
			        }
			        			        			        
			      ],
			      height: 630,
			      cloudServices_uploadUrl: 'https://33333.cke-cs.com/easyimage/upload/',
			      // Note: this is a token endpoint to be used for CKEditor 4 samples only. Images uploaded using this token may be deleted automatically at any moment.
			      // To create your own token URL please visit https://ckeditor.com/ckeditor-cloud-services/.
			      cloudServices_tokenUrl: 'https://33333.cke-cs.com/token/dev/ijrDsqFix838Gh3wGO3F77FSW94BwcLXprJ4APSp3XQ26xsUHTi0jcb1hoBt',
			      easyimage_styles: {
			        gradient1: {
			          group: 'easyimage-gradients',
			          attributes: {
			            'class': 'easyimage-gradient-1'
			          },
			          label: 'Blue Gradient',
			          icon: 'https://ckeditor.com/docs/ckeditor4/4.14.1/examples/assets/easyimage/icons/gradient1.png',
			          iconHiDpi: 'https://ckeditor.com/docs/ckeditor4/4.14.1/examples/assets/easyimage/icons/hidpi/gradient1.png'
			        },
			        gradient2: {
			          group: 'easyimage-gradients',
			          attributes: {
			            'class': 'easyimage-gradient-2'
			          },
			          label: 'Pink Gradient',
			          icon: 'https://ckeditor.com/docs/ckeditor4/4.14.1/examples/assets/easyimage/icons/gradient2.png',
			          iconHiDpi: 'https://ckeditor.com/docs/ckeditor4/4.14.1/examples/assets/easyimage/icons/hidpi/gradient2.png'
			        },
			        noGradient: {
			          group: 'easyimage-gradients',
			          attributes: {
			            'class': 'easyimage-no-gradient'
			          },
			          label: 'No Gradient',
			          icon: 'https://ckeditor.com/docs/ckeditor4/4.14.1/examples/assets/easyimage/icons/nogradient.png',
			          iconHiDpi: 'https://ckeditor.com/docs/ckeditor4/4.14.1/examples/assets/easyimage/icons/hidpi/nogradient.png'
			        }
			      },
			      easyimage_toolbar: [
			        'EasyImageFull',
			        'EasyImageSide',
			        'EasyImageGradient1',
			        'EasyImageGradient2',
			        'EasyImageNoGradient',
			        'EasyImageAlt'
			      ]
			    });
			    
			    function addData(){
			    	$("input[name='forum_title']").val("終於來到了可以挑片的這一天");

			    	CKEDITOR.instances.editor1.setData('今天來挑片除了讓我們獨自慢慢的挑，沒想到還有可樂可以喝啊。'

			    			+'要從200張挑出50張真的好難啊⋯<br>'

			    			+'回想起拍照就只拍了一天，沒想到這樣拍出來也有10個系列之多。每種都都優秀的作品。真的也是要謝謝攝影師Dragon，很多照片都還沒修就已經很美了。所以更增加了我們挑選的難度呀。很多張我們倆個真的是掙扎再掙扎，看了又看、比了又比，才辦法狠下心來。<br>'

			    			+'真的超級期待下次看修片後的照片～～<br>'

			    			+'(JUDY婚紗)找Acho副總婉貞就對了！！<br>'
			    			
			    			+'<img src="https://rcdn.weddingday.com.tw/resize/652b5fbd825f1590c0dd663af99a4b1b5d454c1b69594.jpg?width=800" />'
			    			
			    			+'<img src="https://rcdn.weddingday.com.tw/resize/b0caf6998eb8d6caded56bae4ece24115d454c290b71f.jpg?width=800" />'
			    			
			    			+'<img src="https://rcdn.weddingday.com.tw/resize/fce4cb5451fd401706379738a89ba3385d454c385bc94.jpg?width=800" />');
			    	// TODO write down your mock data here
			    }
			    
			    function sendEdit(){
			    	
			    }
				</script>
	
<!-- ckeditor 新增文章內容部分 -->			
				
				</FORM>
			</div>
		</div>
  	</div>
  <hr>
<!-- to do -->

  <!-- Footer -->
  <footer>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <ul class="list-inline text-center">
            <li class="list-inline-item">
              <a href="#">
                <span class="fa-stack fa-lg">
                  <i class="fas fa-circle fa-stack-2x"></i>
                  <i class="fab fa-twitter fa-stack-1x fa-inverse"></i>
                </span>
              </a>
            </li>
            <li class="list-inline-item">
              <a href="#">
                <span class="fa-stack fa-lg">
                  <i class="fas fa-circle fa-stack-2x"></i>
                  <i class="fab fa-facebook-f fa-stack-1x fa-inverse"></i>
                </span>
              </a>
            </li>
            <li class="list-inline-item">
              <a href="#">
                <span class="fa-stack fa-lg">
                  <i class="fas fa-circle fa-stack-2x"></i>
                  <i class="fab fa-github fa-stack-1x fa-inverse"></i>
                </span>
              </a>
            </li>
          </ul>
          <p class="copyright text-muted">Copyright &copy; Your Website 2020</p>
        </div>
      </div>
    </div>
  </footer>

  <!-- Bootstrap core JavaScript -->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Custom scripts for this template -->
  <script src="js/clean-blog.min.js"></script>

</body>

</html>
<% }%>