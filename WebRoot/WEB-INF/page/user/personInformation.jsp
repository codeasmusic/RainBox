<%@ page language="java" import="java.util.*" import="com.bean.Operation" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<%
		String myPath = request.getScheme() + "://"+ request.getServerName() + ":" 
		+ request.getServerPort() + request.getContextPath() + "/";
%>

<!DOCTYPE html>
<html>
<head>
<title>个人资料</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0"
	http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=myPath%>style/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=myPath%>style/css/login.css">
	<link rel="stylesheet" href="style/css/messenger-theme-flat.css">
	<link rel="stylesheet" href="style/css/messenger.css">
<link href="<%=myPath%>style/css/bootstrap-datetimepicker.min.css" rel="stylesheet"
	media="screen">
	<script src="style/js/messenger.min.js"></script>
	<script type="text/javascript">
	Messenger.options = {
		    extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
		    theme: 'flat',
		};
	</script>
</head>
<body>
	<div class="container">
		<div class="navbar  navbar-inverse navbar-fixed-top" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<div class="container" style="width:500px;">
						<div class="col-md-1">
							<img src="<%=myPath%>style/pic/logo2.png" class="img-circle"
								style="width:50px;height:50px;">
						</div>
						<div class="col-md-5" style="padding-left:30px;">
							<a href="home.action" class="navbar-brand" style="font-size:35px;">RainBox</a>
						</div>
						<div class="col-md-6">
							<ul class="nav navbar-nav">
								<li><a href="myShare.action" style="font-size:16px;">我的分享</a></li>
								<li><a href="shareCenterPage.action" style="font-size:16px;">共享中心</a></li>
							</ul>
						</div>
					</div>
				</div>

				<div class="navbar-collapse collapse" style="width:1200px;">
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown"><a class="dropdown-toggle"
							data-toggle="dropdown"> <span class="label label-success">
							VIP<s:property value="#session.level" /></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<s:property value="#session.nickName" /> <b class="caret"></b> </a>
							<ul class="dropdown-menu">
								<li><a href="#">个人信息</a></li>
								<li class="divider"></li>
								<li><a href="exit.jsp" target="_top">退出</a></li>
							</ul></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>
	<div class="container " style="margin-left:0px;">
		<div class="col-md-2"
			style="height:500px;background-color:#f7f7f7;position:fixed;left:0px;top:50px; ">
			<div class="row">
				<div class="col-md-8 col-md-offset-2" style="margin-top:40px;">
					<img src='<s:url value="%{#session.savePath}"></s:url>'
					  	 style="width:100px ;height:100px">
				</div>
			</div>
			<div class="row" style="margin-top:25px;">
				<div class="col-md-10 col-md-offset-1">
					<label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="label label-success">
						VIP<s:property value="#session.level" /></span>&nbsp;&nbsp;&nbsp;
						<s:property value="#session.nickName" /> </label>
					<div class="col-md-offset-1">
						<a href="modifyPersonInformationPro.action" class="img-change" style="padding-left: 30px;">修改资料</a>
					</div>
				</div>
			</div>
			<div class="row">
				<table class="table table-striped"
					style="margin-top:30px;text-align:center;">
					<thead>
						<tr>
							<th style="text-align:center">文件总数</th>
							<th style="text-align:center">分享数量</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><s:property value="#session.fileNum" />
							</td>
							<td><s:property value="#session.shareNum" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="row">
				<div class="col-md-10 col-md-offset-1">
					<div style="margin-left:45px;">
						<label><s:property value="#session.usedCapacity" /> </label> 
						<label>&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;</label>
						<label><s:property value="#session.capacity" /> </label>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-offset-2"
			style="padding-left: 50px; padding-top: 30px; width: 100%;">
			<form class="form-horizontal" role="form">
				<fieldset>
					<div>
						<legend style="padding-left:20px;">基本资料</legend>
					</div>
					<div class="col-md-offset-2 control-group">
						<div class="form-group">
							<label class="control-label" style="float:left;">昵称：</label>
							<div class="col-md-4">
								<p style="padding-top:5px; font-size:15px;">
									<s:property value="#session.nickName" />
								</p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label" style="float:left;">性别：</label>
							<div class="col-md-4">
								<p style="padding-top:5px; font-size:15px;">
									<s:property value="#session.sex" />
								</p>
							</div>
						</div>
						<div class="form-group">
							<label for="dtp_input2" class="control-label" style="float:left;">生日：</label>
							<div class="col-md-4">
								<p style="padding-top:5px; font-size:15px;">
									<s:property value="#session.birthday" />
								</p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label">个人简介：</label>
							<div>
								<p style="padding-top:5px; font-size:15px;">
									<s:property value="#session.introduction" />
								</p>
							</div>
						</div>
					</div>
					<div>
						<legend style="padding-left:20px; margin-top:50px;">历史信息</legend>
					</div>
					<p style="font-size:10px">
						以下是您最近几次的操作记录，如有异常，请在核实后尽快 <a data-toggle="modal" href="#changePawd"
							style="text-decoration: underline;">修改密码</a>
					</p>

					<div class="span10">
						<table class="table table-hover table-striped">
							<thead>
								<tr>
									<th>时间</th>
									<th>操作</th>
									<th>内容</th>
								</tr>
							</thead>
							<tbody>
								<%
								List<Operation> array = null; 
								array = (List<Operation>)request.getAttribute("operation");
								if(array != null){
								for(Operation op:array){ %>
									<tr>
										<td><%= op.getOpTime()%></td>
										<td><%= op.getOpType()%></td>
										<td><a href="###" style="text-decoration: underline;"><%= op.getOpObject()%></a></td>
									</tr>
								<% } }%>
							</tbody>
						</table>
					</div>
				</fieldset>
			</form>

		</div>
	</div>
	
	    <div class="container">
    <div id="changePawd" class="modal fade">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <a class="close" data-dismiss="modal">×</a>
            <h3>修改密码</h3>
          </div>
          <div class="modal-body">
     <div class="form-horizontal">
     <div class="form-group">
            <label  class="col-sm-2 control-label">原密码：</label>
            <div class="col-sm-6">
              <input type="password" class="form-control" id="oldPass" onblur="check3();enablebtn3();" placeholder="密码长度为6~20位">
              <span id="info1"></span>
            </div>
              <a id="icon1" ></a>
          </div>
       <div class="form-group">
            <label  class="col-sm-2 control-label">新密码：</label>
            <div class="col-sm-6">
              <input type="password" class="form-control" id="pass1" onblur="check();enablebtn3();" placeholder="密码长度为6~20位">
              <span id="info2"></span>
            </div>
              <a id="icon2" ></a>
          </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label" style="padding-left:0px;">确认密码：</label>
            <div class="col-sm-6">
              <input type="password" class="form-control" id="pass2" onblur="check2();enablebtn3();" placeholder="密码长度为6~20位">
              <span id="info3"></span>
            </div>
              <a id="icon3" ></a>
          </div>
        <div class="form-group">
        <div class="col-sm-8">
        <div class="col-sm-4 col-sm-offset-5" style="padding-left:30px;">
              
            <p id="message" font="red"></p>
          </div>
          </div>
          </div>
          
     </div>
          </div>
          <div class="modal-footer">
          <button class="btn btn-lg btn-success" id="btnSubmit3" disabled="true" onclick="changePwd()" type="submit">确定</button>
          <button class="btn btn-lg btn-success" data-dismiss="modal">关闭</button>
          </div>
        </div>
      </div>
    </div>
  </div>

	<script type="text/javascript" src="<%=myPath%>style/js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="<%=myPath%>style/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=myPath%>style/js/changePassword.js"></script>
    <script type="text/javascript">
    	function changePwd(){
    		$.ajax({
    			type: "GET",
    			url: "modifyPasswordPro.action",
    			data: {
    				oldPassword: $("#oldPass").val(),
    				newPassword: $("#pass1").val(),
    			},
    			dataType: "json",
    			success: function(data) {
    				var value = eval("(" + data + ")");
    				document.getElementById("message").innerHTML = value.result;
    			}
    		});
    	}
    </script>
</body>
</html>