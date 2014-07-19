<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix= "s" uri ="/struts-tags" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<title>我的网盘</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- Bootstrap -->
	<link rel="stylesheet" href="style/css/bootstrap.min.css">
	<link rel="stylesheet" href="style/css/cikonss.css">
	
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
	
	<script src="style/js/spark-md5.min.js"></script>
	<script src="style/js/ajaxfileupload.js"></script>
	
	  <script src="style/js/messenger.min.js"></script>
	  <script src="style/js/home.js"></script>
	  <script type="text/javascript">
		Messenger.options = {
			    extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
			    theme: 'flat',
			};
		</script>
	<!-->
	<script src="js/bootstrap.file-input.js"></script>
	<--></head>
<body>
		<script>
		
	</script>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$.ajax({
				type: "GET",
				url: "enterFolder.action",
				data: {
					folderId : $("#upperFolderId").val()
				},
				dataType: "json",
				success: function(data) {
					var value = eval("("+data+")");
					var share;
					$(value.all).each(function(i, jsonObj){
						share = "否";
						if(jsonObj.type=="folder"){
							formatFolder(jsonObj.folderId, jsonObj.folderName, jsonObj.setTime);
						}
						else{
							if(jsonObj.share=="1"){
								share = "是";
							}
							
							formatFile(jsonObj.md5, jsonObj.fileName, jsonObj.size, jsonObj.uploadTime, share);
						}
					});
				},
				error:function(){
					alert("System is upgrading. Please wait...");
	            }
			});
		});
	</script>
	
	<script type="text/javascript">
		function searchMyFiles() {
			$.ajax({
				type: "GET",
				url: "searchMyFiles.action",
				data: {
					content : $("#content").val()
				},
				dataType: "json",
				success: function(data) {
					var value = eval("("+data+")");
					var share = "否";
					
					$("#filelist").empty();
					$(value.all).each(function(i, jsonObj){
						if(jsonObj.share.indexOf("1")!=-1){
							share = "是";
						}
						formatFile(jsonObj.md5, jsonObj.fileName, jsonObj.size, jsonObj.uploadTime, share);
					});
				},
			});
		}
	</script>
	
	<script type="text/javascript">
	function showName(){
		$.each($(":checkbox"),function() {
			if ($(this).is(":checked")) {
				var tempElement = this;
				var Nowname=tempElement.parentNode.nextSibling.childNodes[1].innerHTML;
				document.getElementById("newname").value=Nowname;
			}
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
								<a class="navbar-brand" style="font-size:35px;">RainBox</a>
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

					<div class="navbar-form navbar-left" role="search">
						<div class="input-group" style="max-width:300px; margin-left:100px;">
							<input id="content" type="text" class="form-control"  placeholder="想找点什么？">
							<span class="input-group-btn">
								<button class="btn btn-default" onclick="searchMyFiles()" type="button">
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
		<div class="container " style="margin-left:0px;margin-top:50px">
			<div class="row">
				<div class="col-md-2" style="height:500px;background-color:#f7f7f7;position:fixed;left:0px;top:50px; ">
					<div class="row">
						<div class="col-md-8 col-md-offset-2" style="margin-top:40px;">
							<img src='<s:url value="%{#session.savePath}"></s:url>' style="width:140px ;height:160px "></div>
					</div>
					<div class="row" style="margin-top:25px;">
						<div class="col-md-10 col-md-offset-1" >
							<label >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<span class="label label-success">
								VIP<s:property value="#session.level" /></span>&nbsp;&nbsp;&nbsp;
								<s:property value="#session.nickName" />
							</label>
						</div>
					</div>
					<div class="row">
						<table class="table table-striped" style="margin-top:30px;text-align:center;">
							<thead>
								<tr>
									<th style="text-align:center">已用空间</th>
									<th style="text-align:center">总空间</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><s:property value="#session.usedCapacity" />G</td>
									<td><s:property value="#session.capacity" /></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="col-md-10 col-md-offset-2" >
					<div class="row" style="position:fixed; top:50px; background-color:#EEEEEE;height:65px;margin-left:30px; width:1120px; ">
						<button class="btn btn-primary " data-toggle="modal" data-target="#upload" style="margin:12px 20px;" id="up">
							<span class="glyphicon glyphicon-upload"></span>
							上传文件
						</button>
						<button type="button" class="btn btn-default btn-large"style="margin:12px 5px;" data-toggle="modal" data-target="#createfolder" id="create">
							<span class="glyphicon glyphicon-folder-open"></span>
							新建文件夹
						</button>
						<button type="button" class="btn btn-default btn-large  "data-toggle="modal" data-target="#renameModal"style="margin:12px 5px;" onclick="showName()" disabled="true" id="rename">
							<span class="glyphicon glyphicon-pencil"></span>
							重命名
						</button>
						<button type="button" class="btn btn-default btn-large  "style="margin:12px 5px;" data-toggle="modal" data-target="#shareModal" disabled="true" id="share">
							<span class="glyphicon glyphicon-share"></span>
							分享
						</button>
						<button type="button" class="btn btn-default btn-large "style="margin:12px 5px;"disabled="true" id="download">
							<span class="glyphicon glyphicon-cloud-download"></span>
							下载
						</button>
						<button type="button" class="btn btn-default btn-large "style="margin:12px 5px;" data-toggle="modal" data-target="#deleteModal"disabled="true"id="delete">
							<span class="glyphicon glyphicon-trash"></span>
							删除
						</button>
					</div>
					<div class="row" style="margin-left:10px;">
						<div class="container" style="position:fixed;left:220px;padding:0px;margin-left:20px">
							<div class="panel panel-default" style="width:1120px;" >
								<!-- Default panel contents -->
								<div class="panel-heading">

									<ol class="breadcrumb" style="margin:0px;width:1000px;" id="path">
										<li class="active" value='<s:property value="#session.userEmail"/>root'>开始</li>
									</ol>
								</div>
								<!-- Table -->
								<table class="table table-striped">
									<thead>
										<tr>
											<th style="width:40px;"></th>
											<th style="width:600px;">文件名</th>
											<th style="width:200px;">文件大小</th>
											<th>上传时间</th>
											<th>共享</th>
										</tr>
									</thead>
									<tbody id="filelist"></tbody>
								</table>
						</div>
				</div>
		</div>
		<div class="modal fade" id="upload" data-backdrop="static"  style="position:fixed; top:150px;">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title">上传文件</h4>
					</div>
					<div class="modal-body">
						<input class="btn btn-primary" type="file" id="uploadfile"  name="uploadfile" accept="video/avi">
						<input type="hidden" id="fileMD5"></div>
					<div class="row">
						<div class="col-md-6" style="margin-left:20px;">
							<div class="input-group" >
								<input type="text" class="form-control" id="tagContent">
								<span class="input-group-btn">
									<button class="btn btn-default" type="button" onclick="addTags()">
										<span class="glyphicon glyphicon-tags"></span>
									</button>
								</span>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6" style="margin-left:20px; margin-top:10px;" id="addtags"></div>
					</div>
					
					<div class="progress progress-bar-striped active" style="width:550px;margin:20px;">
					  <div class="progress-bar " role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" id="bar" style="width: 0%;">
					    0%
					  </div>
					</div>
					
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeUp()">关闭</button>
						<button type="button" class="btn btn-primary" id="upfile">上传</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
<div class="modal fade" id="createfolder" data-backdrop="static"  style="position:fixed; top:150px;">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title">新建文件夹</h4>
					</div>
					<div class="modal-body">
						<div class="input-group">
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-folder-close"></span>
							</span>
							<div class="col-md-10">
							<input type="text" class="form-control" placeholder="请输入新建文件夹的名称" id="newFoldername" onblur="checkFolderName()" >
						</div>
						<a id="folderNameIcon"></a>
							</div>
						<div id="info" style="color:red;">文件夹名称应小于20个汉字</div>
						<a id="folderNameError"></a>
						<div class="modal-footer">
						    <button type="button"  class="btn" data-dismiss="modal">取消</button>
							<button type="button" onclick="createNewFolder()" class="btn btn-primary" disabled="true" id="createFolderButton">新建</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->
		</div>
		<div class="modal fade" id="renameModal" data-backdrop="static"  style="position:fixed; top:150px;">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title">重命名</h4>
					</div>
					<div class="modal-body">
						<div class="input-group">
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-pencil"></span>
							</span>
							<div class="col-md-10">
							<input type="text" class="form-control" placeholder="请输新的文件（夹）名称" id="newname" onblur="checkNewFloderName()"></div>
							<a id="newFolderNameIcon"></a>
						</div>
						<a id="newFolderNameError"></a>
						<div class="modal-footer">
						    <button type="button"  class="btn" data-dismiss="modal">取消</button>
							<button type="button"  class="btn btn-primary" id="renameButton" disabled="true" data-dismiss="modal">确认修改</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->
		</div>
		<div class="modal fade" id="shareModal" data-backdrop="static"  style="position:fixed; top:150px;">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title">分享</h4>
					</div>
					<div class="modal-body">
						<div class="input-group">
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-pencil"></span>
							</span>
							<input type="text" class="form-control" placeholder="这里是公开链接" id="shareURL"></div>
						<div class="modal-footer">
							<button type="button"  class="btn btn-primary" id="shareButton" onclick="shareMessage()" data-dismiss="modal">完成</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal #session.userEmail-->
		</div>
		<div class="modal fade" id="deleteModal" data-backdrop="static"  style="position:fixed; top:150px;">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h3 class="modal-title">删除</h3>
					</div>
					<div class="modal-body">
						<h5>确认删除已选择的文件吗？</h5>
					</div>
					<div class="modal-footer">
							<button type="button"  class="btn btn-danger" id="deleteButton" data-dismiss="modal">确认</button>
						</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<input type="hidden" id="shareOrNot">
			<input type="hidden" id="upperFolderId" value='<s:property value="#session.userEmail"/>root'>
			<input type="hidden" id="usedCapacity" value='<s:property value="#session.usedCapacity"/>'>
			<input type="hidden" id="capacity" value='<s:property value="#session.capacity"/>'>
	</body>
</html>