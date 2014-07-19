<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix= "s" uri ="/struts-tags" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<title>共享中心</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- Bootstrap -->
	<link rel="stylesheet" href="style/css/bootstrap.min.css">
	<link rel="stylesheet" href="style/css/messenger-theme-flat.css">
    <link rel="stylesheet" href="style/css/messenger.css">
	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
	<script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
	<script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
	<![endif]-->
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="style/js/jquery-2.1.1.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="style/js/bootstrap.min.js"></script>
	<script src="style/js/messenger.min.js"></script>
    <script src="style/js/share.js"></script>
    <script type="text/javascript">
	Messenger.options = {
		    extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
		    theme: 'flat',
		};
	</script>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			$.ajax({
				type: "GET",
				url: "shareCenter.action",
				
				success: function(data) {
					var value = eval("("+data+")");
					$(value.share).each(function(i, jsonObj){
						formatRow(jsonObj.md5, jsonObj.fileName, jsonObj.userEmail, "style/pic/user.png", 
								  jsonObj.size, jsonObj.uploadTime, jsonObj.tag1, jsonObj.tag2, 
								  jsonObj.tag3, jsonObj.tag4, jsonObj.tag5,  "下载", "转存至我的网盘", "播放");
					});
				},
				error:function(){
					alert("System is upgrading. Please wait...");
	            }
			});
		});
	</script>
	
	<script type="text/javascript">
		function searchSharedFiles() {
			$.ajax({
				type: "GET",
				url: "searchSharedFiles.action",
				data: {
					content : $("#content").val()
				},
				dataType: "json",
				success: function(data) {
					var value = eval("("+data+")");
					$("#filelist").empty();
					$(value.all).each(function(i, jsonObj){
						formatRow(jsonObj.md5, jsonObj.fileName, jsonObj.userEmail, "style/pic/user.png", 
								  jsonObj.size, jsonObj.uploadTime, jsonObj.tag1, jsonObj.tag2, 
								  jsonObj.tag3, jsonObj.tag4, jsonObj.tag5, "转存至我的网盘", "下载", "播放");
					});
				},
			});
		}
	</script>

	<div class="container" style="height:50px;">
		<div class="navbar  navbar-inverse navbar-fixed-top" role="navigation" >
			<div class="container">
				<div class="navbar-header">
					<div class="container" id="navbar" style="width:500px;">
						<div class="col-md-1">
							<img src="style/pic/logo2.png" class="img-circle" style="width:50px;height:50px;"></div>
						<div class="col-md-5" id="navbar-brand" style="padding-left:30px;">
							<a href="home.action" class="navbar-brand" style="font-size:35px;">RainBox</a>
						</div>
						<div class="col-md-6">
							<ul class="nav navbar-nav">
								<li >
									<a href="myShare.action" style="font-size:16px;">我的分享</a>
								</li>
								<li class="active">
									<a href="#" style="font-size:16px;" >共享中心</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="navbar-form navbar-left" role="search">
					<div class="input-group" style="max-width:300px; margin-left:100px;">
						<input id="content" type="text" class="form-control"  placeholder="想找点什么？">
						<span class="input-group-btn">
							<button class="btn btn-default" onclick="searchSharedFiles()" type="button">
								<span class="glyphicon glyphicon-search"></span>
							</button>
						</span>
					</div>
				</div>
				<div class="navbar-collapse collapse" style="width:1200px;">
					<ul class="nav navbar-nav navbar-right" >
						<li class="dropdown">
							<a  class="dropdown-toggle" data-toggle="dropdown">
								<span class="label label-success">
								VIP<s:property value="#session.level" /></span>&nbsp;&nbsp;&nbsp;
								<s:property value="#session.nickName" /><b class="caret"></b>
							</a>
							<ul class="dropdown-menu">
								<li>
									<a href="enterPersonInfo.action">个人信息</a>
								</li>
								<li class="divider"></li>
								<li>
									<a href="exit.jsp" target="_top">退出</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>
	<div class="container" style="margin-top:25px;" id="filelist">
		<div class="row">
			<table class="table" style="margin:0px;">
				<thead>
					<tr>
						<th>
							<div class="row">
								<div class="col-md-5" style="padding-left:40px;">文件名</div>
								<div class="col-md-2" style="padding-left:35px;" >上传者</div>
								<div class="col-md-1" style="padding-left:5px;">文件大小</div>
								<div class="col-md-2 col-md-offset-2" >上传时间</div>
							</div>
						</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>