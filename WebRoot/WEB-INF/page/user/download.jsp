<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix= "s" uri ="/struts-tags" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<title>文件名.avi下载</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" http-equiv="Content-Type" content="text/html;">
	<meta charset="UTF-8">
	<link rel="stylesheet" href="style/css/bootstrap.min.css">
	<link rel="stylesheet" href="style/css/login.css">
	<link rel="stylesheet" href="style/css/messenger-theme-flat.css">
	<link rel="stylesheet" href="style/css/messenger.css">
	
	<script src="style/js/jquery-2.1.1.min.js"></script>
	<script src="style/js/bootstrap.min.js"></script>
	<script src="style/js/messenger.min.js"></script>
	<script type="text/javascript">
	Messenger.options = {
		    extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
		    theme: 'flat',
		};
	</script>
	<style type="text/css">
	.title {
  text-align:center;
	}
	.page  {
	  width: auto;
	  height: 500px;
	  margin-top:30px;
	  -moz-box-shadow: 0 2px 10px 1px rgba(0, 0, 0, 0.2);
	  -webkit-box-shadow: 2px 2px 10px 1px rgba(0, 0, 0, 0.2);
	  box-shadow: 0 2px 10px 1px rgba(0, 0, 0, 0.2);
	  position: relative;
	}

	</style>
</head>
<body>
	<script type="text/javascript">
		function cancelShare() {
			$.ajax({
				type: "GET",
				url: "cancelShare.action",
				data: {
					MD5 : $("#md5").val(),
					uploadTime : $("#uploadTime").val()
				},
				dataType: "json",
				
				success: function(data) {
					var value = eval("("+data+")");
					window.location.href="shareCenterPage.action";
				},
			});
		}
	</script>
	<div class="container">
		<div class="navbar  navbar-inverse navbar-fixed-top" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<div class="container" style="width:500px;">
						<div class="col-md-1">
							<img src="style/pic/logo2.png" class="img-circle" style="width:50px;height:50px;"></div>
						<div class="col-md-5" style="padding-left:30px;">
							<a href="home.action" class="navbar-brand" style="font-size:35px;">RainBox</a>
						</div>
						<div class="col-md-6">
							<ul class="nav navbar-nav">
								<li >
									<a href="myShare.action" style="font-size:16px;">我的分享</a>
								</li>
								<li >
									<a href="shareCenterPage.action" style="font-size:16px;">共享中心</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="navbar-collapse collapse" style="width:1200px;">
					<ul class="nav navbar-nav navbar-right" >
						<li class="dropdown">
							<a  class="dropdown-toggle" data-toggle="dropdown">
								<span class="label label-success">
								VIP<s:property value="#session.level"/></span>&nbsp;&nbsp;&nbsp;
								<s:property value="#session.nickName"/> <b class="caret"></b>
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
			</div>
		</div>
	</div>
	<div class="container">
		<div class="page">
			<div class="row">
				<div class="col-md-9" style="background-color:#ffffff;height:500px">
					<div class="row" style="margin:25px 10px;">
						<div class="col-md-6" >
							<img src="style/pic/movie.jpg" style="height:20px;width:20px">
							<label id="filename"><s:property value="#request.fileInfo.fileName"/></label>
						</div>
						<div class="col-md-4 col-md-offset-2" style="">
							<s:if test="#request.isSharer">
								<button class="btn btn-info" style="" 
									data-toggle="modal" data-target="#cancelShareModal">
									<span class="glyphicon glyphicon-share"></span>
									取消分享
								</button>
							</s:if>
							
							<button class="btn btn-info" 
							onclick="window.location.href='<s:property value="#request.downloadLink"/>'">
								<span class="glyphicon glyphicon-download"></span>
								下载(<s:property value="#request.fileInfo.size"/>)
							</button>
						</div>
					</div>
					<div class="row" style="height:300px;border:1px solid #F1F2EE;margin:38px 25px;">
						<div class="row" style="text-align:center;margin:auto;padding:100px">
							<div class="col-md-12" style="margin:10px 0px;">
								<img src="style/pic/movie.jpg" style="height:80px;width:80px "></div>
							<div class="col-md-12" >
								<label>
									大小：
									<label id="filesize"><s:property value="#request.fileInfo.size"/></label>
								</label>
							</div>
						</div>
					</div>
					<div class="row" style="margin:25px 10px;">
						<div class="col-md-6" >
							<label >
								分享时间:
								<label id="uploadtime">
									<s:property value="#request.fileInfo.uploadTime"/>
								</label>
							</label>
						</div>
					</div>
				</div>
				<input id="md5" type="hidden" value="<s:property value='#request.fileInfo.md5'/>"/>
				<input id="uploadTime" type="hidden" value="<s:property value='#request.longUploadTime'/>"/>
				
				
				<div class="col-md-3" style="background-color:#eeeeee;height:500px;padding-top:50px">
					<div class="row">
						<div class="col-md-8 col-md-offset-2" style="margin-top:40px;text-align:center">
							<img src='<s:url value="style/uploadHead/%{#request.sharer.headName}"></s:url>' class="img-circle" 
								 style="width:80% ;height:80% ;box-shadow: 10px 10px 5px #888888;"></div>
					</div>
					<div class="row" style="margin-top:25px;">
						<div class="col-md-12"  style="text-align:center">
							<label >
								<span class="label label-success">
								VIP<s:property value="#request.sharer.level"/></span>&nbsp;&nbsp;&nbsp;
								<s:property value="#request.sharer.nickName"/>
							</label>
						</div>
					</div>
					<div class="row">
						<table class="table table-striped" style="margin-top:30px;text-align:center;">
							<thead>
								<tr>
									<!-- <th style="text-align:center">文件总数</th>
									<th style="text-align:center">分享数量</th> -->
								</tr>
							</thead>
							<tbody>
								<tr><!--
									<td><s:property value="#session.fileNum"/></td>
									<td><s:property value="#session.shareNum"/></td> -->
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="cancelShareModal" data-backdrop="static"  style="position:fixed; top:150px;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h3 class="modal-title">取消分享</h3>
				</div>
				<div class="modal-body">
					<h5>是否确认取消该文件的分享？</h5>
				</div>
				<div class="modal-footer">
					<button onclick="cancelShare()"  class="btn btn-primary" id="shareButton" data-dismiss="modal">确认</button>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
	</div>
</body>
</html>