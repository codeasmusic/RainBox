<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
		String myPath = request.getScheme() + "://"+ request.getServerName() + ":" 
		+ request.getServerPort() + request.getContextPath() + "/";
%>
<html>
	<head>退出</head>
	<body>
		<% session.invalidate(); %> 
		<jsp:forward page="userLogin.jsp"></jsp:forward> 
	</body>
</html>