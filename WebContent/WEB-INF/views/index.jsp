<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<fmt:setLocale value="${loc}" />
<fmt:bundle basename="biz.gelicon.gta.server.i18n.Bundle">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Gelicon Team Application</title>
    <link type="text/css" rel="stylesheet" href="resources/css/jquery-ui.css">
    <link rel="shortcut icon" href="resources/images/favicon.png" type="image/png">
    <link type="text/css" rel="stylesheet" href="resources/css/jquery.dataTables.css">
    <link type="text/css" rel="stylesheet" href="resources/css/jquery.multiselect.css">
    <link type="text/css" rel="stylesheet" href="resources/css/toastr.css">
    <link type="text/css" rel="stylesheet" href="resources/css/main.css">
    <link type="text/css" rel="stylesheet" href="resources/css/jquery.fancybox-1.3.4.css">

	<script src="resources/js/lib/jquery-min.js"></script>
	<script src="resources/js/lib/jquery.cookie.js"></script>
	<script src="resources/js/lib/jquery-ui.js"></script>
	<script src="resources/js/lib/jquery.dataTables.js"></script>
	<script src="resources/js/lib/jquery.multiselect.js"></script>
	<script src="resources/js/lib/jquery.multiselect.ru.js"></script>
	<script src="resources/js/lib/jquery.form-validator.js"></script>
	<script src="resources/js/lib/toastr.js"></script>
	<script src="resources/js/lib/jquery.fancybox-1.3.4.js"></script>
	<script src="resources/js/lib/cadesplugin.js"></script>
	
	<script src="resources/js/main.js"></script>
	<script src="resources/js/utils.js"></script>
	<script src="resources/js/projects.js"></script>
	<script src="resources/js/person.js"></script>
	<script src="resources/js/admin.js"></script>
    
    <style>
    	#copyright {
       		text-align: center;
			position: absolute;
			bottom: 0px;
			width: 100%;
    	}
    </style>
</head>
<body>
	<div class="top">
		<div class="user">
			<c:if test="${user==null}"><a href="login"><fmt:message key="label.login"/></a></c:if>
			<c:if test="${user!=null}"><span style="margin-right:10px;font-size:large">[<a href="profile">${user}</a>]</span><a href="action/logout" onclick="$.removeCookie('token')"><fmt:message key="label.logout"/></a></c:if>
		</div>
		<a href="."><img alt="GTA" src="resources/images/logo.png"></a>
		<div class="mainmenu">
			<a href="."><fmt:message key="label.home"/></a>
			<a href="proj"><fmt:message key="label.projects"/></a>
			<a href="diary"><fmt:message key="label.workdiary"/></a>
			<c:if test="${user!=null && userObj.sysAdmin}">
			<a href="admin"><fmt:message key="label.administrator"/></a>
			</c:if>
		</div>
	</div>
	<div class="content">
		<c:url var="innerurl" value="${base}/inner/${menu}"/>
		<c:import url="${innerurl}"/>
	</div>
	<div class="footer">
	
		<ul>
			<li><h3><fmt:message key="label.companyinfo"/></h3></li>
			<li><fmt:message key="label.about"/></li>
			<li><fmt:message key="label.contacts"/></li>
		</ul>
	
		<p id="copyright">© 2015 Gelicon JCC Ltd.</p>
	</div>
</body>
</fmt:bundle>
</html>