<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<fmt:setLocale value="${loc}" />
<fmt:bundle basename="biz.gelicon.gta.server.i18n.Bundle">

<head>
	<style>
		body {
			font-family:Tahoma;
			color:black;
		}
	
		h1 {
			font-size:18pt;
			padding-bottom:20px;
		}
		h2 {
			padding-top:12px;
		}
		p {
			padding-bottom:6px;
		}
		
		table {
			margin-top:18px;
			table-layout: fixed;
			width:100%;
			border: 1px #ccc;
		}
		
		th {
			text-align:center;
			border:1px #000;
		}
		td {
			border:1px #ccc;
		}
		
		.right {
			text-align: right;
		}
		
		.sign {
			margin-top:28px;
			text-align: right;
		}
		
	</style>

</head>

<body>

<h1><fmt:message key="label.worked"/></h1>
<p><fmt:message key="label.onperiod"/> <fmt:formatDate value="${dtStart}" dateStyle="short"/> - <fmt:formatDate value="${dtEnd}" dateStyle="short"/>
</p>
<p><fmt:message key="label.team"/>: ${teamName}</p>
<table cellpadding="4" cellspacing="0">
	<tr>
		<th width="280" rowspan="2"><fmt:message key="label.member"/></th>
		<th width="180" rowspan="2"><fmt:message key="label.post"/></th>
		<th width="80" rowspan="2"><fmt:message key="label.worked"/> (<fmt:message key="label.hours"/>)</th>
		<th colspan="2"><fmt:message key="label.activity"/></th>
	</tr>
	<tr>
		<th>(<fmt:message key="label.scores"/>)</th>
		<th>(%)</th>
	</tr>
    <c:set var="sumHours" value="${0}"/>
	<tbody>
		<c:forEach var="wo" items="${data}">
			<tr>
		  		<td>${wo.member}</td>
		  		<td>${wo.post}</td>
		  		<td class="right"><fmt:formatNumber value="${wo.hours}" maxFractionDigits="1" minFractionDigits="1"/></td>
		  		<td class="right"><fmt:formatNumber value="${wo.activityScore}" maxFractionDigits="1" minFractionDigits="1"/></td>
		  		<td class="right"><fmt:formatNumber value="${wo.activityPercent}" maxFractionDigits="1" minFractionDigits="1"/></td>
			</tr>
			<c:set var="sumHours" value="${sumHours + wo.hours}"/>
		</c:forEach>
	</tbody>
	<tfoot>
		<tr>
	  		<th class="right" colspan="3"><fmt:formatNumber value="${sumHours}" maxFractionDigits="1" minFractionDigits="1"/></th>
	  		<th colspan="2"></th>
		</tr>
	</tfoot>
</table>
<h2><fmt:message key="label.byposition"/></h2>
<table cellpadding="4" cellspacing="0">
	<tr>
		<th width="300" rowspan="2"><fmt:message key="label.post"/></th>
		<th width="100" rowspan="2"><fmt:message key="label.worked"/> (<fmt:message key="label.hours"/>)</th>
		<th width="200" colspan="2"><fmt:message key="label.activity"/></th>
	</tr>
	<tr>
		<th>(<fmt:message key="label.scores"/>)</th>
		<th>(%)</th>
	</tr>
    <c:set var="sumHours" value="${0}"/>
	<tbody>
		<c:forEach var="wo" items="${dataByPosition}">
			<tr>
		  		<td>${wo.post}</td>
		  		<td class="right"><fmt:formatNumber value="${wo.hours}" maxFractionDigits="1" minFractionDigits="1"/></td>
		  		<td class="right"><fmt:formatNumber value="${wo.activityScore}" maxFractionDigits="1" minFractionDigits="1"/></td>
		  		<td class="right"><fmt:formatNumber value="${wo.activityPercent}" maxFractionDigits="1" minFractionDigits="1"/></td>
			</tr>
			<c:set var="sumHours" value="${sumHours + wo.hours}"/>
		</c:forEach>
	</tbody>
	<tfoot>
		<tr>
	  		<th class="right" colspan="2"><fmt:formatNumber value="${sumHours}" maxFractionDigits="1" minFractionDigits="1"/></th>
	  		<th colspan="2"></th>
		</tr>
	</tfoot>
</table>

<p class="sign">
 <fmt:message key="label.signnote"/>
</p>
<p class="right"> _____________________/____________________</p>
<p class="right">(<fmt:message key="label.sign"/>)</p>
<c:set var="now" value="<%=new java.util.Date()%>"></c:set>
<p class="right"><fmt:formatDate value="${now}" dateStyle="short"/></p>

</body>
</fmt:bundle>
</html>