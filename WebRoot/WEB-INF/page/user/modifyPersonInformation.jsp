<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<title>个人资料修改</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0"
	http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="style/css/bootstrap.min.css">
<link href="style/css/bootstrap-datetimepicker.min.css" rel="stylesheet"
	media="screen">
<link rel="stylesheet" href="style/css/login.css">


<style type="text/css">
li {
	list-style: none
}

li img {
	width: 100px;
	height: 100px;
}

.tips {
	color: red
}
</style>
<script type="text/javascript" src="style/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="style/js/bootstrap.min.js"></script>

<script src="style/js/userInfo.js"></script>

<script src="style/js/uploadImg.js"></script>
</head>
<body onload="checkUserName();">
	<div class="container">
		<div class="navbar  navbar-inverse navbar-fixed-top" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<div class="container" style="width:500px;">
						<div class="col-md-1">
							<img src="style/pic/logo2.png" class="img-circle"
								style="width:50px;height:50px;">
						</div>
						<div class="col-md-5" style="padding-left:30px;">
							<a href="home.action" class="navbar-brand" style="font-size:35px;">RainBox</a>
						</div>
						<div class="col-md-6">
							<ul class="nav navbar-nav">
								<li><a href="myShare.action" style="font-size:16px;">我的分享</a>
								</li>
								<li><a href="shareCenterPage.action" style="font-size:16px;">共享中心</a>
								</li>
							</ul>
						</div>
					</div>
				</div>

				<form class="navbar-form navbar-left" role="search">
					<div class="input-group"
						style="max-width:300px; margin-left:100px;">
						<input type="text" class="form-control" placeholder="想找点什么？">
						<span class="input-group-btn">
							<button class="btn btn-default" type="button">
								<span class="glyphicon glyphicon-search"></span>
							</button> </span>
					</div>
				</form>
				<div class="navbar-collapse collapse" style="width:1200px;">
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown"><a class="dropdown-toggle"
							data-toggle="dropdown"> <span class="label label-success">
							VIP<s:property value="#session.level" /></span>&nbsp;&nbsp;&nbsp;
								<s:property value="#session.nickName" /> <b class="caret"></b> </a>
							<ul class="dropdown-menu">
								<li><a href="enterPersonInfo.action">个人信息</a>
								</li>
								<li class="divider"></li>
								<li><a href="exit.jsp" target="_top">退出</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>
	<div class="container-fluid  col-md-8 col-md-offset-2"
		style="padding-left: 50px; padding-top: 30px;">
		<h3>个人设置</h3>
		<form action="savePersonInformationPro" method="post"
			enctype="multipart/form-data" class="form-horizontal"
			style="background-color:#fff;">
			<fieldset>
				<div>
					<legend style="padding-left:20px;">基本资料</legend>
				</div>
				<div class="col-md-offset-2 control-group">
					<div class="form-group">
						<label class="control-label" style="float:left;">昵称：</label>
						<div class="col-md-4">
							<input class="form-control" onblur="checkUserName()" id="userName" name="nickName"
								value='<s:property value="#request.nickName"/>'> <span
								id="error"></span>
						</div>
						<a id="user"></a>
						<div style="padding-top:5px;">
							<!-- 验证用户名后返回提示-->
							<p id="userNameValid" style="font-size:15px;"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label">性别：</label>

						<s:if test="#request.sex=='女'">
							<label class="radio-inline"> <input type="radio"
								value="男" name="sex">男 </label>
							<label class="radio-inline" style="padding-left: 40px;">
								<input type="radio" value="女" checked="checked" name="sex">女 </label>
						</s:if>
						<s:else>
							<label class="radio-inline"> <input type="radio"
								value="男" checked="checked" name="sex">男 </label>
							<label class="radio-inline" style="padding-left: 40px;">
								<input type="radio" value="女" name="sex">女 </label>
						</s:else>

					</div>
					<div class="form-group">
						<label class="control-label" style="float:left;">生日：</label>
						<div class="input-group date form_date col-md-4" data-date=""
							data-date-format="yyyy MM dd" data-link-field="dtp_input2"
							data-link-format="yyyy-mm-dd">
							<input name="birthday" class="form-control" size="16" type="text"
								value='<s:property value="#request.birthday"/>' readonly>
							<span class="input-group-addon"> <span
								class="glyphicon glyphicon-remove"></span> </span> <span
								class="input-group-addon"> <span
								class="glyphicon glyphicon-calendar"></span> </span>
						</div>
						<input type="hidden" id="dtp_input2" value="" />
					</div>
					<div class="form-group">
						<label class="control-label">个人简介:</label>
						<div style="padding-top: 5px;">
							<div class="textarea">
								<textarea name="introduction" type="" class=""
									style="width:600px; font-size:12px; height: 200px; resize: none;"
									value='<s:property value="#request.introduction"/>'></textarea>
							</div>
						</div>
					</div>
				</div>
				<div>
					<legend style="padding-left:20px;">头像设置</legend>
				</div>
				<div class="col-md-5">
					<p style="padding-left:20px;">当前头像</p>
				</div>
				<div class="col-md-5 col-md-offset-1">
					<p>设置新头像</p>
				</div>
				<div class="col-md-2">
					<li><img id="img" src="<s:url value='%{#request.savePath}'></s:url>"
						style="width:100px ;height:100px">
					</li>
				</div>
				<div class="col-md-2 col-md-offset-2" style="margin-top:0px;">
					<p style="font-size: 10px;">支持jpg、png、bmp格式</p>
					<div>
						<input style="width:150px;" type="file" name="image"
							accept="image/*" id="filesInput" onchange="imgLoaded()">
					</div>
					<div >
          <p id="info" style="color:red;"></p>
        </div>
				</div>
				<!--以下为编辑头像空间 -->
				<div class="col-md-4 col-md-offset-1">
					<ul id="imageBox"></ul>
				</div>
				<div class="col-md-offset-5 col-md-2" style="margin-top: 50px;">
					<button class="btn btn-primary btn-lg btn-block " id="saveBtn"
						type="submit" disabled="true">保 存</button>
				</div>
			</fieldset>
		</form>
	</div>
<script type="text/javascript"
	src="style/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript"
	src="style/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	
<script type="text/javascript">
		$('.form_date').datetimepicker({
			language : 'zh-CN',
			weekStart : 1,
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 2,
			forceParse : 0
		});
</script>
</body>
</html>