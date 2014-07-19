<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix= "s" uri ="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
  <title>播放</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0" http-equiv="Content-Type" 

content="text/html;">
  <meta charset="UTF-8">
  <link rel="stylesheet" href="style/css/bootstrap.min.css">
  <link rel="stylesheet" href="style/css/login.css">
  <!--link rel="stylesheet" href="css/play.css"-->
  <script src="style/js/jquery-2.1.1.min.js"></script>
  <script src="style/js/bootstrap.min.js"></script>
</head>
<body>
  <div class="container">
    <div class="navbar  navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <div class="container" style="width:500px;">
            <div class="col-md-1">
              <img src="style/pic/logo2.png" class="img-circle" style="width:50px;height:50px;"></div>
            <div class="col-md-5" style="padding-left:30px;width:100px;">
              <a href="home.action" class="navbar-brand">RainBox</a>
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
	                VIP<s:property value="#session.level" /></span>&nbsp;&nbsp;&nbsp;
	                <s:property value="#session.nickName" /><b class="caret"></b>
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
  <div class="container" style="margin-top:0px;">
    <div class="page">
      <h2 class="title" id="movieName"><s:property value="#request.fileName"/></h2>
      <div class="col-md-8 " style="height:350px;">
        <video width="150%" height="150%" controls autoplay >
          <source src='<s:property value="#request.playLink"/>' type="video/mp4"></video>
      </div>
    </div>
  </div>
</body>
</html>