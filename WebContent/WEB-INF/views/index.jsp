<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Gelicon Team Application</title>
    <link type="text/css" rel="stylesheet" href="resources/css/main.css">
    <link rel="shortcut icon" href="resources/images/favicon.png" type="image/png">
</head>
<body>
	<div class="top">
		<div class="user">
			<c:if test="${user==null}"><a href="login">Log in</a></c:if>
			<c:if test="${user!=null}"><span style="margin-right:20px">${user.name}</span><a href="logout">Log out</a></c:if>
		</div>
		<a href="."><img alt="GTA" src="resources/images/logo.png"></a>
		<div class="mainmenu">
			<a href=".">Home</a>
			<a href="proj">Projects</a>
		</div>
	</div>
	<div class="content">
		<c:choose>
			<c:when test="${user!=null}"><c:url var="innerurl" value="${base}/inner/${menu}"/></c:when>
			<c:when test="${user==null}">
				<c:if test="${menu!='home'}"><c:url var="innerurl" value="${base}/inner/login"/></c:if>
				<c:if test="${menu=='home'}"><c:url var="innerurl" value="${base}/inner/home"/></c:if>
			</c:when>
		</c:choose>
		<c:import url="${innerurl}"/>
	</div>
	<div class="footer">
	</div>
</body>
</html>