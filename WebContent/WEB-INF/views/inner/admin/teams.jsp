<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${loc}"/>
<fmt:bundle basename="biz.gelicon.gta.server.i18n.Bundle">

<script type="text/javascript">
$(function() {
	$("#teams-table").dataTable({
		pageLength:6,
		lengthChange:false,
		info: false
	});
	$("#addTeam").click(function(){
		openTeam();
	});
});
</script>

<button id="addTeam"><fmt:message key="label.add"/></button>
<table id="teams-table">
	<thead>
		<tr>
			<th><fmt:message key="label.name"/></th>
			<th><fmt:message key="label.manager"/></th>
			<th><fmt:message key="label.active"/></th>
			<th><fmt:message key="label.startdate"/></th>
			<th><fmt:message key="label.limit"/></th>
			<th><fmt:message key="label.membercount"/></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="t" items="${teams}">
			<tr>
		  		<td><a onclick="openTeam(${t.id});return false;" href="#${t.id}">${t.name}</a></td>
		  		<td>${t.manager.nic}</td>
		  		<td>${t.active}</td>
		  		<td><fmt:formatDate value='${t.createDate}' dateStyle='short'/></td>
		  		<td>${t.limit}</td>
		  		<td>${t.workerCount}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

</fmt:bundle>
