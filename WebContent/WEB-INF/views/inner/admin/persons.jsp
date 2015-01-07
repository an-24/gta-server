<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${loc}"/>
<fmt:bundle basename="biz.gelicon.gta.server.i18n.Bundle">

<script type="text/javascript">
$(function() {
	$("#person-table").dataTable({
		pageLength:4,
		lengthChange:false,
		info: false
	});
	$("#addPerson").click(function(){
		openAdminPerson();
	});
});
</script>

<button id="addPerson"><fmt:message key="label.add"/></button>
<table id="person-table" class="display">
	<thead>
		<tr>
			<th><fmt:message key="label.nic"/></th>
			<th><fmt:message key="label.username"/></th>
			<th><fmt:message key="label.team"/></th>
			<th><fmt:message key="label.post"/></th>
			<th><fmt:message key="label.limit"/></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="p" items="${persons}">
			<tr>
		  		<td><a onclick="openAdminPerson(${p.id});return false;" href="#${p.id}">${p.nic}</a></td>
		  		<td>${p.user.name}</td>
		  		<td>${p.team.name}</td>
		  		<td>${p.postName}</td>
		  		<td>${p.limit}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

</fmt:bundle>