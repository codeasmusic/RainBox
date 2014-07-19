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
<title>K2视频网盘登录</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0"
	http-equiv="Content-Type" content="text/html;">
<meta charset="UTF-8">
<link rel="stylesheet" href="style/css/bootstrap.min.css">
<link rel="stylesheet" href="style/css/login.css">
<script type="text/javascript" src="style/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="style/js/bootstrap.min.js"></script>
<script type="text/javascript" src="style/js/loginYanzheng.js"></script>
</head>
<body onLoad="createCode();">
	<div class="clearfix" id="login-header">
		<div class="navbar navbar-default navbar-fixed-top"
			style="color:#996633;" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<div class="container" style="width:130px;">
						<div class="col-md-6">
							<img src="style/pic/logo.png" class="img-circle"
								style="width:50px;height:50px;">
						</div>
						<div class="col-md-6">
							<a class="navbar-brand" style="font-size:35px;">RainBox</a>
						</div>
					</div>
				</div>
				<div class="navbar-collapse collapse" style="width:1200px;">
					<ul class="nav navbar-nav"></ul>
					<ul class="nav navbar-nav navbar-right">
						<li class="active"><a href="./">登录</a></li>
						<li><a href="userRegister.jsp">注册</a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>
	<div id="login-middle">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<div class="carousel slide" id="carousel-87772">
						<ol class="carousel-indicators">
							<li data-slide-to="0" data-target="#carousel-87772"></li>
							<li data-slide-to="1" data-target="#carousel-87772"></li>
							<li data-slide-to="2" data-target="#carousel-87772"
								class="active"></li>
						</ol>
						<div class="carousel-inner">
							<div class="item">
								<img alt="" src="style/img/slide1.jpg" />
								<div class="carousel-caption col-md-5 col-md-offset-1">
									<h4>共享视频</h4>
									<p>在共享中心总能找到你所需要的视频</p>
								</div>
							</div>
							<div class="item">
								<img alt="" src="style/img/slide2.jpg" />
								<div class="carousel-caption col-md-5 col-md-offset-1">
									<h4>6G空间</h4>
									<p>注册用户即可获得6G免费空间</p>
								</div>
							</div>
							<div class="item active">
								<img alt="" src="style/img/slide3.jpg" />
								<div class="carousel-caption col-md-5 col-md-offset-1">
									<h4>开启更大空间</h4>
									<p>用户等级越高，空间容量越大</p>
								</div>
							</div>
						</div>
						<a data-slide="prev" href="#carousel-87772"
							class="left carousel-control">‹</a> <a data-slide="next"
							href="#carousel-87772" class="right carousel-control">›</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="login" type="login-form">
		<div class="container-fluid">
			<div class="row-fluid">

				<h3>
					<em>登录</em>
				</h3>
				<form action="loginPro" method="post" class="form-horizontal">
					<fieldset>
						<div class="form-group">
							<label class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-9">
								<input class="form-control " id="email" name="email"
									onblur="isEmail();enablebtn();" placeholder="邮箱地址"> <span
									id="text1"></span>
							</div>
							<a id="e"></a>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">密码</label>
							<div class="col-sm-9">
								<input type="password" class="form-control" id="pass1"
									name="password" onblur="check();enablebtn();"
									placeholder="密码长度为6~20位"> <span id="text2"></span>
							</div>
							<a id="pawd1"></a>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" style="padding-left:0px;">验证码</label>
							<div class="col-sm-4">
								<input class="form-control" type="text" id="input1"
									onblur="validate();enablebtn();" required>
							</div>
							<div class="col-sm-2" style="float:left; padding-left:0px;">
								<input type="button" id="checkCode" class="code"
									style="width:70px; height:33px; font-size:16px;"
									onClick="createCode()" />
							</div>
							<a href="###" class="img-change" onClick="createCode()"
								style="padding-left:15px;">看不清？</a> <span id="aaa"
								style="padding-left:10px;"></span>
						</div>
						<div class="form-group" style="padding-left:0px; padding-top:0px;">
							<div class="col-sm-4 col-sm-offset-2">
								<a href="forgetPassword.action" class="img-change" style="padding-left:15px;">忘记密码？</a>
							</div>
						</div>
					</fieldset>

					<div class="col-md-8 col-md-offset-3" style="padding-left:0px;">
						<button class="btn btn-lg btn-success btn-block" id="btnSubmit"
							disabled="true" type="submit">登 录</button>
						<div class="col-md-offset-4">
							<font color="red"><s:property value="#request.error" /></font>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	
		<!--页脚 -->
<footer class="footer">
  <div >
    <p>Designed and built by <a>RainBox Development Group</a></p>
    <ul>
      当前版本： v1.1
    </ul>
  </div>
</footer>
</body>
</html>
