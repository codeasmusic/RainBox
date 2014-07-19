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
    <style type="text/css">
.code{
background-image:url(yanzheng.jpg);
font-family:Arial,宋体;
font-style:italic;
color:green;
border:0;
padding:2px 3px;
letter-spacing:3px;
font-weight:bolder;
}
.unchanged {
border:0;
}
</style>
  <script language="javascript" type="text/javascript">
var code ; //在全局 定义验证码
function createCode(){ 
code = new Array();
var codeLength = 4;//验证码的长度
var checkCode = document.getElementById("checkCode");
checkCode.value = "";

var selectChar = new Array(0,1,2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z');

for(var i=0;i<codeLength;i++) {
   var charIndex = Math.floor(Math.random()*36);
   code +=selectChar[charIndex];
}
if(code.length != codeLength){
   createCode();
}
checkCode.value = code;
}

function validate () {
var inputCode = document.getElementById("input1").value.toUpperCase();

if(inputCode.length <=0) {
   return false;
}
else if(inputCode != code ){
   document.getElementById("aaa").innerHTML="<span class='glyphicon glyphicon-remove'></span>";
   createCode();
   return false;
}
else {
   document.getElementById("aaa").innerHTML="<span class='glyphicon glyphicon-ok'></span>";
   return true;
}
}
</script>
  <link rel="stylesheet" href="style/css/bootstrap.min.css">
  <link rel="stylesheet" href="style/css/login.css">
  <script src="style/js/jquery-2.1.1.min.js"></script>
  <script src="style/js/bootstrap.min.js"></script>
</head>
<body onLoad="createCode();">
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
        <img src="style/img/firstStep.jpg"/>
        </div><br><br><br>
        <form action="sendEmail" method="post" class="form-horizontal">
          <p>请输入您要找回的账号：</p>
          <div class="form-group">
            <div class="col-sm-5 controls">
              <input id="inputEmail" name="userEmail" class="form-control" type="text" placeholder="请输入您的邮箱"/>
            </div>
          </div>
          <div class="form-group">
                <div class="col-sm-2">
                  <input  class="form-control" type="text" placeholder="验证码" id="input1" onblur="validate();" required></div>
                <div class="col-sm-1" style="float:left; padding-left:0px;">
                  <input  type="button" id="checkCode" class="code" style="width:70px; height:33px; font-size:16px;" onClick="createCode()"/>
                </div>
                <div class="col-sm-2">
                <a href="###" class="img-change" onClick="createCode()" style="padding-left:0px;">看不清？</a><span id="aaa" style="padding-left:10px;"></span>
              </div>
              </div>
          <div class="col-sm-2" style="padding-left:0px;">
            <button class="btn btn-lg btn-success btn-block" type="submit">下   一   步</button>
          </div>
          <s:property value="#request.error"/>
        </form>
      </div>
    </div>
  </div>
</body>
</html>