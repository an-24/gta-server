<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Gelicon Team Application</title>
    <link type="text/css" rel="stylesheet" href="resources/css/jquery-ui.css">
    <link rel="shortcut icon" href="resources/images/favicon.png" type="image/png">
    <link type="text/css" rel="stylesheet" href="resources/css/jquery.dataTables.css">
    <link type="text/css" rel="stylesheet" href="resources/css/jquery.multiselect.css">
    <link type="text/css" rel="stylesheet" href="resources/css/toastr.css">
    <link type="text/css" rel="stylesheet" href="resources/css/main.css">

	<script src="resources/js/lib/jquery-min.js"></script>
	<script src="resources/js/lib/jquery.cookie.js"></script>
	<script src="resources/js/lib/jquery-ui.js"></script>
	<script src="resources/js/lib/jquery.dataTables.js"></script>
	<script src="resources/js/lib/jquery.multiselect.js"></script>
	<script src="resources/js/lib/jquery.multiselect.ru.js"></script>
	<script src="resources/js/lib/jquery.form-validator.js"></script>
	<script src="resources/js/lib/toastr.js"></script>
	
	<script src="resources/js/main.js"></script>
	<script src="resources/js/utils.js"></script>

</head>
<body>
	<div class="top">
		<div class="user">
			<c:if test="${user==null}"><a href="login">Log in</a></c:if>
			<c:if test="${user!=null}"><span style="margin-right:10px;font-size:large">[${user}]</span><a href="action/logout" onclick="$.removeCookie('token')">Log out</a></c:if>
		</div>
		<a href="."><img alt="GTA" src="resources/images/logo.png"></a>
		<div class="mainmenu">
			<a href=".">Home</a>
			<a href="proj">Projects</a>
		</div>
	</div>
	<div class="content">
		<c:url var="innerurl" value="${base}/inner/${menu}"/>
		<c:import url="${innerurl}"/>
	</div>
	<div class="footer">
	</div>
</body>
</html>