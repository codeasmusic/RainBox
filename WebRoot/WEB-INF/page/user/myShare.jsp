<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>


<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
					+ request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
  <title>我的分享</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0" http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" href="style/css/bootstrap.min.css">
  <link rel="stylesheet" href="style/css/login.css">
    <link rel="stylesheet" href="style/css/messenger-theme-flat.css">
  <link rel="stylesheet" href="style/css/messenger.css">
  <script src="style/js/jquery-2.1.1.min.js"></script>
  <script src="style/js/bootstrap.min.js"></script>
  
  <script src="style/js/messenger.min.js"></script>
  <script src="style/js/share.js"></script>
  <script type="text/javascript">
	Messenger.options = {
		    extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
		    theme: 'flat',
		};
	</script>
</head>
<body>
  <script type="text/javascript">
		$(document).ready(function() {
			$.ajax({
				type: "GET",
				url: "showMyShare.action",
				dataType: "json",
				success: function(data) {
					var value = eval("("+data+")");
					$(value.myShare).each(function(i, jsonObj){
						formatMyShareFiles(jsonObj.md5, jsonObj.fileName, jsonObj.size, jsonObj.shareTime);
					});
				},
				error:function(){
					alert("System is upgrading. Please wait...");
	            }
			});
		});
  </script>
  <div class="container">
    <div class="navbar  navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <div class="container" style="width:500px;">
            <div class="col-md-1">
              <img src="style/pic/logo2.png" class="img-circle" style="width:50px;height:50px;"></div>
            <div class="col-md-5" style="padding-left:30px;">
              <a href="home.action" class="navbar-brand" style="font-size:35px;">RainBox</a>
            </div>
            <div class="col-md-6">
              <ul class="nav navbar-nav">
                <li >
                  <a href="#" style="font-size:16px;">我的分享</a>
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
                <s:property value="#session.nickName" /> <b class="caret"></b>
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
  <div class="container">
  <div class="col-md-2 " style="height:500px;background-color:#f7f7f7;position:fixed;left:0px; top:50px;">
    <div class="row">
      <div class="col-md-8 col-md-offset-2" style="margin-top:40px;">
        <img src='<s:url value="%{#session.savePath}"></s:url>' style="width:140px;height:160px"></div>
    </div>
    <div class="row" style="margin-top:25px;">
      <div class="col-md-10 col-md-offset-1" >
        <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <span class="label label-success">
			VIP<s:property value="#session.level" /></span>&nbsp;&nbsp;&nbsp;</span>
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
          <div class="row col-md-offset-1" style="padding-left:20px; padding-top:30px;" >
            <div class="container">
            <div>
            <button class="btn btn-primary" id="cancelShare" disabled>取消分享</button>
            </div>
              <div class="panel panel-default" style="margin-top:20px;">
                <!-- Default panel contents -->
                <div class="panel-heading">
                  <p>我的分享</p>
                </div>
                <!-- Table -->
                <table class="table table-striped">
                  <thead>
                    <tr>
                      <th style="width:40px;">
                        </th>
                      <th style="width:600px;">文件名</th>
                      <th style="width:200px;">文件大小</th>
                      <th>上传时间</th>
                    </tr>
                  </thead>
                  	<tbody id="filelist"></tbody>
                </table>
              </div>
            </div>
          </div>
	</div>
</body>
</html>