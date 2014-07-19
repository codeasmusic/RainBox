<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
  <title>管理员登录</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0" http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" href="style/css/bootstrap.min.css">
  <link rel="stylesheet" href="style/css/login.css">
  <script src="style/js/jquery-2.1.1.min.js"></script>
  <script src="style/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="style/js/loginYanzheng.js"></script>
</head>
<body onLoad="createCode();">
  <div class="container">
    <div class="clearfix" id="login-header">
      <div class="navbar navbar-default navbar-fixed-top" style="color:#996633;" role="navigation">
        <div class="container">
          <div class="navbar-header">
            <div class="container" style="width:130px;">
              <div class="col-md-6">
                <img src="style/pic/logo.png" class="img-circle" style="width:50px;height:50px;"></div>
              <div class="col-md-6">
                <a class="navbar-brand" style="font-size:35px;">RainBox</a>
              </div>
            </div>
          </div>
          <div class="navbar-collapse collapse" style="width:1200px;">
          <div class="col-md-4 col-md-offset-3">
            <ul class="nav navbar-nav">
              <li >
                <a href="#" style="font-size:35px; padding-left:75px;">Admin</a>
              </li>
            </ul>
          </div>
            <ul class="nav navbar-nav navbar-right" >
              <li class="active">
                <a href="./">登录</a>
              </li>
            </ul>
          </div>
          <!--/.nav-collapse --> </div>
      </div>
    </div>
  </div>
  <div class="container">
    <div class="col-md-6 col-md-offset-3 " style="height:450px;background-color:#f7f7f7;margin-top:60px;">
      <div class="row">
        <div class="col-md-8 col-md-offset-4" style="margin-top:50px;margin-left:220px">
          <img src="style/pic/user.png" class="img-circle" style="width:100px ;height:100px; "></div>
      </div>
      <div class="row" style="margin-top:25px;">
        <div class="col-md-10 col-md-offset-1" >
          <form action="adminLoginPro" method="post" class="form-horizontal" role="form">
             <fieldset>
          <div class="form-group">
            <label  class="col-sm-3 control-label" style="padding-left:0px;">管理员</label>
            <div class="col-sm-8">
              <input name="adminName" class="form-control "  placeholder="管理员名称">
              <span id="text1"></span>
              </div>
              <a id="e"></a>
          </div>
          <div class="form-group">
            <label  class="col-sm-3 control-label">密码</label>
            <div class="col-sm-8">
              <input name="password" type="password" class="form-control" id="pass1"  onblur="check2();enablebtn2();" placeholder="请输入密码">
              <span id="text2"></span>
            </div>
              <a id="pawd1" ></a>
          </div>
          <div class="form-group">
                <label class="col-sm-3 control-label" style="padding-left:0px;" >验证码</label>
                <div class="col-sm-4">
                  <input  class="form-control" type="text" id="input1" onblur="validate();enablebtn2();"required></div>
                <div class="col-sm-2" style="float:left; padding-left:0px;">
                  <input  type="button" id="checkCode" class="code" style="width:70px; height:33px; font-size:16px;" onClick="createCode()"/>
                </div>
                <a href="###" class="img-change" onClick="createCode()" style="padding-left:15px;">看不清?</a>
                <span id="aaa" style="padding-left:10px;"></span>
              </div>
        </fieldset>
          <div class="col-md-9" style="margin-left:100px;">
            <button class="btn btn-lg btn-success btn-block" id="btnSubmit" disabled="true" type="submit" >登     录</button>
          </div>
          </form>
        </div>
      </div>
    </div>
  </div>

</body>
</html>