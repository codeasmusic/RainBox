<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
	<title>文件管理</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" http-equiv="Content-Type" content="text/html;">
	<meta charset="UTF-8">
	<link rel="stylesheet" href="style/css/bootstrap.min.css">
	<link rel="stylesheet" href="style/css/login.css">
	
	<link rel="stylesheet" href="style/css/messenger-theme-flat.css">
	<link rel="stylesheet" href="style/css/messenger.css">
	
	<script src="style/js/jquery-2.1.1.min.js"></script>
	<script src="style/js/bootstrap.min.js"></script>
	<script src="style/js/fileManager.js"></script>
	<script src="style/js/messenger.min.js"></script>
	<script type="text/javascript">
	Messenger.options = {
		    extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
		    theme: 'flat',
		};
	</script>
</head>
<body>
	<script type="text/javascript">
		function searchByFileName() {
			$.ajax({
				type: "GET",
				url: "searchByFileName.action",
				data: {
					content : $("#content").val()
				},
				dataType: "json",
				success: function(data) {
					var count = 0;
					$("#filelist").empty();
					var value = eval("("+data+")");
					$(value.search).each(function(i, jsonObj){
						count += 1;
						formatFile(jsonObj.md5, jsonObj.fileName, jsonObj.userEmail, jsonObj.uploadTime);
					});
					setPage(count);
				}
			});
		}
  	</script>
  	
  	<script type="text/javascript">
		function searchByTag() {
			$.ajax({
				type: "GET",
				url: "searchByTag.action",
				data: {
					content : $("#content").val()
				},
				dataType: "json",
				success: function(data) {
					var count = 0;
					$("#filelist").empty();
					var value = eval("("+data+")");
					$(value.search).each(function(i, jsonObj){
						count += 1;
						formatFile(jsonObj.md5, jsonObj.fileName, jsonObj.userEmail, jsonObj.uploadTime);
					});
					setPage(count);
				}
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
							<a class="navbar-brand" style="font-size:35px;" >RainBox</a>
						</div>
						<div class="col-md-6">
							<ul class="nav navbar-nav">
								<li >
									<a href="enterUserPage.action" style="font-size:16px;">用户管理</a>
								</li>
								<li >
									<a href="#" style="font-size:16px;">文件管理</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="navbar-form navbar-left" role="search">
					<div class="input-group" style="max-width:300px; margin-left:100px;">
						<input id="content" type="text" class="form-control" placeholder="想找点什么？">
						<span class="input-group-btn">
							<button class="btn btn-default dropdown-toggle" data-toggle="dropdown" >
								<span class="glyphicon glyphicon-search"></span>
							</button>
							<ul class="dropdown-menu" role="menu">
								<li>
									<a href="#" onclick="searchByFileName()">搜索文件</a>
								</li>
								<li>
									<a href="#" onclick="searchByTag()">搜索标签</a>
								</li>
							</ul>
						</span>
					</div>
				</div>
				<div class="navbar-collapse collapse" style="width:1200px;">
					<ul class="nav navbar-nav navbar-right" >
						<li class="dropdown">
							<a  class="dropdown-toggle" data-toggle="dropdown">
								Admin <b class="caret"></b>
							</a>
							<ul class="dropdown-menu">
								<li>
									<a href="adminExit.jsp" target="_top">退出</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>
<div class="container" style="margin-top:50px;">
	<div class="row">
		<div class="col-md-12">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<th style="width:370px;">文件名</th>
						<th>上传者</th>
						<th>上传时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="filelist"></tbody>
			</table>
		</div>
	</div>
</div>
<div class="container">
  <div class="col-md-5 col-md-offset-4" style="margin-left:450px;">
    <ul class="pagination" id="pagelist"></ul>
  </div>
</div>
<div class="modal fade" id="checkInfo" data-backdrop="static"  >
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">详细信息</h4>
			</div>
			<div class="modal-body">
				<h3><strong id="nameInfo"></strong></h3>
				<div class="row" id="infoLine">
					<label style="margin:20px;">
						上传者：
						<label id="uploaderInfo"></label>
					</label>
				</div>
				<div class="row" id="infoLine">
					<label style="margin:20px;">
						上传日期：
						<label id="timeInfo"></label>
					</label>
				</div>
				<div class="row" id="infoLine">
					<label style="margin:20px;">
						文件大小：
						<label id="sizeInfo"></label>
					</label>
				</div>
				<div class="row" id="infoLine">
					<label style="margin:20px;" id="tags">
						标签：
						<span class="label label-success" id="tag1"></span>
						<span class="label label-success" id="tag2"></span>
						<span class="label label-success" id="tag3"></span>
						<span class="label label-success" id="tag4"></span>
						<span class="label label-success" id="tag5"></span>
					</label>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button"  class="btn btn-primary" id="InfoButton" data-dismiss="modal">完成</button>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</div>
<div class="modal fade" id="deleteVideo" data-backdrop="static"  >
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h3 class="modal-title">删除</h3>
			</div>
			<div class="modal-body">
				<h4 >确认要删除该文件吗？</h4>
			</div>
			<div class="modal-footer">
			<button class="btn" id="deleteButton" data-dismiss="modal">取消</button>
				<button class="btn btn-danger" id="deleteButton" onclick="deleteFile()" data-dismiss="modal">确认</button>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</div>
	<input type="hidden" id="filemd5">
	<input type="hidden" id="uploader">
	<input type="hidden" id="time">
	
	<script type="text/javascript">
	    var pages=6;
		var leftTo=document.createElement("li");
		leftTo.innerHTML="<a href='#' id='left' onclick='turnLeft(this)'>&laquo;</a>";
		var rightTo=document.createElement("li");
		rightTo.innerHTML="<a href='#' id='right' onclick='turnRight(this)'>&raquo;</a>";
		document.getElementById("pagelist").appendChild(leftTo);
		for(var i=1;i<=pages;i++)
		{
			var toX=document.createElement("li");
			toX.innerHTML="<a href='#' onclick='turnX(this)' style='display:none' id='page"+i+"'></a>";
			document.getElementById("pagelist").appendChild(toX);
		}
		document.getElementById("pagelist").appendChild(rightTo);
	</script>
	
	<script type="text/javascript">
	var itemnumber;
	var eachPage=10;
	var pages=6;
	var actualPages;
	
	function setPage(value){
		itemnumber = value;
		actualPages=Math.ceil(itemnumber/eachPage);
		for(var i=1;i<=pages;i++)
		{
			$("#page"+i).css("display","none");
			$("#page"+i).text("");
		}
		if(itemnumber/eachPage>=pages)
		{
			for(var i=1;i<=pages;i++)
		{
			$("#page"+i).css("display","");
			$("#page"+i).text(i);
		}
		$("#left").attr("class","disable");
		if(itemnumber/eachPage==pages)
		{
			$("#right ").attr("class","disable");
		}
		}
		else
		{
			for(var i=1;i<=actualPages;i++)
		{
			$("#page"+i).css("display","");
			$("#page"+i).text(i);
		}
		$("#right").attr("class","disable");
		$("#left").attr("class","disable");
		}
		$("#page1").click();
	}
		
	function turnX (id) {
		var number=id.innerHTML;
		for(var i=0;i<itemnumber;i++)
		{
			$("#filelist tr:eq("+i+")").css("display","none");
		}
		$(".active").removeAttr("class");
		for(var i=(number-1)*eachPage;i<number*eachPage;i++)
		{
			$("#filelist tr:eq("+i+")").css("display","");
		}
		id.parentNode.setAttribute("class","active");
	}

	function turnLeft(id)
	{
		if(id.getAttribute("class")!="disable")
		{
			var firstPage=parseInt($("#page1").text()-pages);
		for(var i=1;i<=pages;i++)
		{
			document.getElementById("page"+i).parentNode.setAttribute("style","");
			$("#page"+i).text(parseInt(firstPage+i-1));
		}
		if(firstPage==1)
		{
			id.setAttribute("class","disable");
			$("#right").attr("class","");
		}
		$("#page1").click();
		}
	}
	function turnRight(id)
	{
		if(id.getAttribute("class")!="disable")
		{
			var firstPage=parseInt($("#page"+pages).text())+1;
		for(var i=1;i<=pages;i++)
		{
			if(parseInt(firstPage+i-1)<=actualPages){
				$("#page"+i).text(parseInt(firstPage+i-1));
			}
			else
			{
				document.getElementById("page"+i).parentNode.setAttribute("style","display:none");
				$("#right").attr("class","disable");
				$("#left").attr("class","");
			}
		}
		$("#page1").click();
		}
	}		
	</script>
</body>
</html>