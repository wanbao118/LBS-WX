<%@ page language="java" import="java.util.*" pageEncoding="utf-8"
	contentType="text/html; charset=utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
  	<meta charset="utf-8">
    <title>SpiderNet</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template.">
    <meta name="author" content="Muhammad Usman">

    <!-- The styles -->
    <link
		href='<%=path %>/bower_components/bootstrap-val/bootstrapValidator.css'
		rel='stylesheet'>

    <link href="<%=path %>/css/bootstrap-cerulean.min.css" rel="stylesheet">

    <link href="<%=path %>/css/charisma-app.css" rel="stylesheet">
    <link href='<%=path %>/bower_components/fullcalendar/dist/fullcalendar.css' rel='stylesheet'>
    <link href='<%=path %>/bower_components/fullcalendar/dist/fullcalendar.print.css' rel='stylesheet' media='print'>
    <link href='<%=path %>/bower_components/chosen/chosen.min.css' rel='stylesheet'>
    <link href='<%=path %>/bower_components/colorbox/example3/colorbox.css' rel='stylesheet'>
    <link href='<%=path %>/bower_components/responsive-tables/responsive-tables.css' rel='stylesheet'>
    <link href='<%=path %>/bower_components/bootstrap-tour/build/css/bootstrap-tour.min.css' rel='stylesheet'>
    <link href='<%=path %>/css/jquery.noty.css' rel='stylesheet'>
    <link href='<%=path %>/css/noty_theme_default.css' rel='stylesheet'>
    <link href='<%=path %>/css/elfinder.min.css' rel='stylesheet'>
    <link href='<%=path %>/css/elfinder.theme.css' rel='stylesheet'>
    <link href='<%=path %>/css/jquery.iphone.toggle.css' rel='stylesheet'>
    <link href='<%=path %>/css/uploadify.css' rel='stylesheet'>
    <link href='<%=path %>/css/animate.min.css' rel='stylesheet'>

    <!-- jQuery -->
    <script src="<%=path %>/bower_components/jquery/jquery.min.js"></script>

    <!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- The fav icon -->
    <link rel="shortcut icon" href="img/favicon.ico">
	<style type="text/css">
	</style>
</head>

<body class="login-back">
			<div class="frame-box">
				<div class="frame1">
					<div class="frame1div" >
					<img src="<%=path %>/img/log_login.png" ></img>
				</div>
				
				<div class="frame2">

					<div class="frame2-concent">
						<div class="login-box" style="width:80%;padding-left:100px;">
						            <div class="title" style="font-family:segoesc;font-size:35px;color:#FFFFFF">
						               SpiderNet
						            </div>
						            <div class="alert alert-success" style="display: none;"></div>
						            <form id="loginForm" class="form-horizontal" method="post">
						                <fieldset>
											
											<div class="form-group">
												
								              <div type="text" class="username">用户名</div>
								                <div class="group">
								                    <div class="col-sm-12">
								                        <input type="text" class="form-control " style="height:46px" placeholder="" id="userName" name="userName">
								                    </div>
								                </div>
						                    <div class="clearfix"></div><br>
						
						                    <div type="text" class="username">密码</div>
											<div class = "group">                       
						                    <div class="col-sm-12">
						                        <input type="password" class="form-control" id="password" style="height:46px" name="password" placeholder="">
						                    </div>
						                    </div>
						                    </div>
						                    <div class="clearfix"></div>
																<p class="center col-md-5">
						                        <button type="submit" id="submitBtn" class="btn btn-primary btn-primary-one" style="">登录</button>
						                    </p>
						                </fieldset>
						            </form>

						</div>
					</div>
				</div>
		  </div>		
	</div>	
	
<script type="text/javascript">
var path = "<%=path%>";

</script>
	
<!-- external javascript -->

<script src="<%=path %>/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="<%=path %>/bower_components/bootstrap-val/bootstrapValidator.min.js"></script>

<!-- library for cookie management -->
<script src="<%=path %>/js/jquery.cookie.js"></script>
<!-- calender plugin -->
<script src='<%=path %>/bower_components/moment/min/moment.min.js'></script>
<script src='<%=path %>/bower_components/fullcalendar/dist/fullcalendar.min.js'></script>
<!-- data table plugin -->
<script src='<%=path %>/js/jquery.dataTables.min.js'></script>

<!-- select or dropdown enhancer -->
<script src="<%=path %>/bower_components/chosen/chosen.jquery.min.js"></script>
<!-- plugin for gallery image view -->
<script src="<%=path %>/bower_components/colorbox/jquery.colorbox-min.js"></script>
<!-- notification plugin -->
<script src="<%=path %>/js/jquery.noty.js"></script>
<!-- library for making tables responsive -->
<script src="<%=path %>/bower_components/responsive-tables/responsive-tables.js"></script>
<!-- tour plugin -->
<script src="<%=path %>/bower_components/bootstrap-tour/build/js/bootstrap-tour.min.js"></script>
<!-- star rating plugin -->
<script src="<%=path %>/js/jquery.raty.min.js"></script>
<!-- for iOS style toggle switch -->
<script src="<%=path %>/js/jquery.iphone.toggle.js"></script>
<!-- autogrowing textarea plugin -->
<script src="<%=path %>/js/jquery.autogrow-textarea.js"></script>
<!-- multiple file upload plugin -->
<script src="<%=path %>/js/jquery.uploadify-3.1.min.js"></script>
<!-- history.js for cross-browser state change on ajax -->
<script src="<%=path %>/js/jquery.history.js"></script>
<!-- application script for Charisma demo -->
<script src="<%=path %>/js/charisma.js"></script>
<script src="<%=path %>/js/login.js"></script>

</body>
</html>
