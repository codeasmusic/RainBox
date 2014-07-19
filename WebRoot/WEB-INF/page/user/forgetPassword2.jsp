<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix= "s" uri ="/struts-tags" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
  <title>K2视频网盘密码找回</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0" http-equiv="Content-Type" content="text/html;">
  <meta charset="UTF-8">
  <link rel="stylesheet" href="style/css/bootstrap.min.css">
  <link rel="stylesheet" href="style/css/login.css">
  <script src="style/js/jquery-2.1.1.min.js"></script>
  <script src="style/js/bootstrap.min.js"></script>
</head>
<body>
<div class="clearfix" id="login-header">
  <div class="navbar navbar-default navbar-fixed-top" style="color:#996633;" role="navigation">
    <div class="container">
      <div class="navbar-header">
        <div class="container" style="width:130px;">
          <div class="col-md-6">
            <img src="style/pic/logo.png" class="img-circle" style="width:50px;height:50px;"></div>
          <div class="col-md-6">
            <a class="navbar-brand" style="font-size:35px;"  >RainBox</a>
          </div>
        </div>
      </div>
      <div class="navbar-collapse collapse" style="width:1200px;">
        <ul class="nav navbar-nav"></ul>
        <ul class="nav navbar-nav navbar-right" >
          <li>
            <a href="">登录</a>
          </li>
          <li >
            <a href="./">注册</a>
          </li>
        </ul>
      </div>
      <!--/.nav-collapse --> </div>
  </div>
</div>
<div class="row-fluid">
<div id="finding-pawd">
   <div id="nav">
   <div class="col-md-8 col-md-offset-2">
    <h3>找回密码</h3>
   </div>
   </div>
   <div class="col-md-8 col-md-offset-2">
     <div>
        <img src="style/img/secondStep.jpg"/>
        </div><br><br><br>
     <form class="form-horizontal">
       <p>您要找回的账号是：<s:property value="#request.userEmail"/></p>
       <p>我们已发送安全验证链接到您的邮箱，请登录邮箱点击链接以完成安全验证</p>
     </form>
   </div>
 </div>
</div>
</body>
</html>