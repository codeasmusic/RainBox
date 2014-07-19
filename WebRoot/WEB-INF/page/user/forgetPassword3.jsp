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
  <script src="style/js/resetPassword.js"></script>
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
            <a class="navbar-brand" style="font-size:35px;" >RainBox</a>
          </div>
        </div>
      </div>
      <div class="navbar-collapse collapse" style="width:1200px;">
        <ul class="nav navbar-nav"></ul>
        <ul class="nav navbar-nav navbar-right" >
          <li>
            <a href="userLogin.jsp">登录</a>
          </li>
          <li>
            <a href="userRegister.jsp">注册</a>
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
        <img src="style/img/thirdStep.jpg"/>
        </div><br><br><br>
     
     <form action="reset" method="post" class="form-horizontal">
     <div class="col-sm-offset-1" style="padding-left:38px;">
       <p>您要找回的账号是：<s:property value="#request.userEmail"/></p>
       <input type="hidden" name="userEmail" value=<s:property value="#request.userEmail"/> />
       <input type="hidden" name="id" value=<s:property value="#request.id"/> />
     </div>
       <div class="form-group">
            <label  class="col-sm-1 control-label">新密码</label>
            <div class="col-sm-4">
              <input type="password" name="newPassword" class="form-control" id="pass1" onblur="check();enablebtn2();" placeholder="密码长度为6~20位">
              <span id="text2"></span>
            </div>
              <a id="pawd1" ></a>
          </div>
        <div class="form-group">
            <label  class="col-sm-1 control-label" style="padding-left:0px;">确认密码</label>
            <div class="col-sm-4">
              <input type="password" class="form-control" id="pass2" onblur="check2();enablebtn2();" placeholder="密码长度为6~20位">
              <span id="text3"></span>
            </div>
              <a id="pawd2" ></a>
          </div>
        <div class="col-sm-2 col-sm-offset-1" style="padding-left:3px;">
            <button class="btn btn-lg btn-success btn-block" id="btnSubmit2" disabled="true" type="submit">确   定</button>
          </div>
     </form>
   </div>
 </div>
</div>
</body>
</html>