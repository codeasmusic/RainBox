<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
  <title>管理员用户管理</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0" http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" href="style/css/bootstrap.min.css">
  <link rel="stylesheet" href="style/css/login.css">
  
  <link rel="stylesheet" href="style/css/messenger-theme-flat.css">
  <link rel="stylesheet" href="style/css/messenger.css">
	
  <script src="style/js/jquery-2.1.1.min.js"></script>
  <script src="style/js/bootstrap.min.js"></script>
  <script src="style/js/userManager.js"></script>
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
		function searchUser() {
			$.ajax({
				type: "GET",
				url: "searchUser.action",
				data: {
					userEmail : $("#content").val()
				},
				dataType: "json",
				success: function(data) {
					$("#userlist").empty();
					var value = eval("("+data+")");
					var count = 0;
					$(value.user).each(function(i, jsonObj){
						count += 1;
						var status = "否";
						if(jsonObj.isDisable==0){
							status = "是";
						}
						formateUser(jsonObj.userEmail, jsonObj.nickName, jsonObj.level, status);
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
                  <a href="#" style="font-size:16px;">用户管理</a>
                </li>
                <li >
                  <a href="enterFilePage.action" style="font-size:16px;">文件管理</a>
                </li>
              </ul>
            </div>
          </div>
        </div>

        <div class="navbar-form navbar-left" role="search">
          <div class="input-group" style="max-width:300px; margin-left:100px;">
            <input type="text" id="content" class="form-control" placeholder="想找点什么？">
            <span class="input-group-btn">
              <button class="btn btn-default" type="button" onclick="searchUser()">
                <span class="glyphicon glyphicon-search"></span>
              </button>
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
                  <a href="adminExit.jsp">退出</a>
                </li>
              </ul>
            </li>
          </ul>
        </div>
        <!--/.nav-collapse --> </div>
    </div>
  </div>
  <div class="container">
    <div class="panel panel-default" style="width:1120px; margin-top:30px;" >
      <!-- Default panel contents -->
      <div class="panel-heading" style="height:50px;padding:8px;">
        <div class="col-md-7 col-md-offset-5" style="margin-left:520px;">
          <button class="btn btn-primary" style="margin-right:15px;" id="checkInfo" disabled="true" data-toggle="modal" data-target="#infoModal">查看资料</button>
          <button class="btn btn-danger" style="margin-right:15px;" id="deleteUser" disabled="true" data-toggle="modal" data-target="#deleteModal">删除用户</button>
          <button class="btn btn-warning" style="margin-right:15px;" id="disableUser" disabled="true" data-toggle="modal" data-target="#disableModal">禁用用户</button>
          <button class="btn btn-info" style="margin-right:15px;" id="activeUser" disabled="true" data-toggle="modal" data-target="#activeModal">激活用户</button>
          <button class="btn btn-warning" style="margin-right:15px;" id="setLevel" data-toggle="modal" data-target="#setModal">设置级别--容量关系</button>
        </div>
      </div>
      <!-- Table -->
      <table class="table table-striped">
        <thead>
          <tr>
            <th style="width:40px;">
              <input type="checkbox" id="chooseAll"></th>
            <th style="width:400px;">注册邮箱</th>
            <th style="width:400px;">用户昵称</th>
            <th>等级</th>
            <th>是否禁用</th>
          </tr>
        </thead>
        <tbody id="userlist"></tbody>
      </table>
    </div>
  </div>
  <div class="container">
  <div class="col-md-5 col-md-offset-4" style="margin-left:450px;">
    <ul class="pagination" id="pagelist"></ul>
  </div>
</div>
  <div class="container">
    <div class="modal fade" id="infoModal" data-backdrop="static"  style="position:fixed; top:150px;">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">Ta的个人资料</h4>
          </div>
          <div class="modal-body">
            <div class="row">
              <div class="col-md-6">
                <div class="row" style="padding:0px 15px; margin:8px;">
                  <label>注册邮箱:</label>
                  <label id="emailInfo"></label>
                </div>
                <div class="row" style="padding:0px 15px;margin:8px;">
                  <label>昵称:</label>
                  <label id="nicknameInfo"></label>
                </div>
                <div class="row" style="padding:0px 15px;margin:8px;">
                  <label>性别:</label>
                  <label id="sexInfo"></label>
                </div>
                <div class="row" style="padding:0px 15px;margin:8px;">
                  <label>生日:</label>
                  <label id="birthdayInfo"></label>
                </div>
                <div class="row" name="level" style="padding:0px 15px;margin:8px;">
                  <label>级别:</label>
                  <select>
                    <option>VIP1</option>
                    <option>VIP2</option>
                    <option>VIP3</option>
                    <option>VIP4</option>
                    <option>VIP5</option>
                    <option>VIP6</option>
                    <option>VIP7</option>
                    <option>VIP8</option>
                    <option>VIP9</option>
                    <option>VIP10</option>
                  </select>
                </div>
                <div class="row" style="padding:0px 15px;margin:8px;">
                  <label>个人简介:</label>
                  <label id="introductionInfo">ta很懒，什么都没有留下</label>
                </div>
              </div>
              <div class="col-md-6">
                <img id="headImg" src="style/pic/user.png" style="width:200px;height:200px"></div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn "  data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" id="saveInfoButton" 
            	onclick="setLevel()" data-dismiss="modal">保存修改</button>
          </div>
          <!-- /.modal-content --> </div>
        <!-- /.modal-dialog --> </div>
      <!-- /.modal --> </div>
  </div>
  <div class="container">
    <div class="modal fade" id="deleteModal" data-backdrop="static"  style="position:fixed; top:150px;">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="modal-title">删除用户</h3>
          </div>
          <div class="modal-body">
            <h4>是否删除所选用户?</h4>
          </div>
          <div class="modal-footer">
            <button type="button"  class="btn"  data-dismiss="modal">取消</button>
            <button type="button"  class="btn btn-danger" id="deleteButton" data-dismiss="modal">确认</button>
          </div>
          <!-- /.modal-content --> </div>
        <!-- /.modal-dialog --> </div>
      <!-- /.modal --> </div>
  </div>
  <div class="container">
    <div class="modal fade" id="disableModal" data-backdrop="static"  style="position:fixed; top:150px;">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="modal-title">禁用用户</h3>
          </div>
          <div class="modal-body">
            <h4>是否禁用所选用户?</h4>
          </div>
          <div class="modal-footer">
            <button type="button"  class="btn"  data-dismiss="modal">取消</button>
            <button type="button"  class="btn btn-warning" id="disableButton" data-dismiss="modal">确认</button>
          </div>
          <!-- /.modal-content --> </div>
        <!-- /.modal-dialog --> </div>
      <!-- /.modal --> </div>
  </div>
  <div class="container">
    <div class="modal fade" id="activeModal" data-backdrop="static"  style="position:fixed; top:150px;">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="modal-title">激活用户</h3>
          </div>
          <div class="modal-body">
            <h4>是否激活所选用户?</h4>
          </div>
          <div class="modal-footer">
            <button type="button"  class="btn"  data-dismiss="modal">取消</button>
            <button type="button"  class="btn btn-info" id="activeButton" data-dismiss="modal">确认</button>
          </div>
          <!-- /.modal-content --> </div>
        <!-- /.modal-dialog --> </div>
      <!-- /.modal --> </div>
  </div>
  <div class="container">
    <div class="modal fade" id="setModal" data-backdrop="static"  style="position:fixed; top:150px;">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="modal-title">级别--容量关系</h3>
          </div>
          <div class="modal-body">
            <label>一级对应</label>
            <input type="text" id="storage" style="width:50px;">
            <label>GB</label>
          </div>
          <div class="modal-footer">
            <button type="button"  class="btn"  data-dismiss="modal">取消</button>
            <button type="button"  class="btn btn-primary" id="setButton" data-dismiss="modal" 
            		onclick="setCapacity()">保存</button>
          </div>
          <!-- /.modal-content --> </div>
        <!-- /.modal-dialog --> </div>
      <!-- /.modal --> </div>
  </div>
  <script type="text/javascript">
  	function setCapacity(){
  		$.ajax({
  			type: "GET",
  			url: "setCapacity.action",
  			data: {
  				capacity: $("#storage").val()
  			},
  			dataType: "json",
  			success: function(data) {
  				Messenger().post({
					  message: data,
					  hideAfter: 5
				});
  			}
  		});
  	}
  </script>
  
    <script type="text/javascript">
  	function setLevel(){
  		var level;
  		$("option").each(function(){
  			if($(this).is(":selected") == true) {
  				level = $(this).text();
  			}
  		});
  		$.ajax({
  			type: "GET",
  			url: "setLevel.action",
  			data: {
  				userEmail: $("#emailInfo").text(),
  				level: level
  			},
  			dataType: "json",
  			success: function(data) {
  				Messenger().post({
					  message: data,
					  hideAfter: 5
				});
  			}
  		});
  	}
  </script>
  
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
			$("#userlist tr:eq("+i+")").css("display","none");
		}
		$(".active").removeAttr("class");
		for(var i=(number-1)*eachPage;i<number*eachPage;i++)
		{
			$("#userlist tr:eq("+i+")").css("display","");
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