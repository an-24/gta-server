<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${loc}"/>
<fmt:bundle basename="biz.gelicon.gta.server.i18n.Bundle">

<script type="text/javascript">
$(function() {
	$("#users-table").dataTable({
		pageLength:6,
		lengthChange:false,
		info: false
	});
	$("#addUser").click(function(){
		openUser();
	});
});
</script>

<button id="addUser"><fmt:message key="label.add"/></button>
<table id="users-table">
	<thead>
		<tr>
			<th><fmt:message key="label.name"/></th>
			<th>e-mail</th>
			<th><fmt:message key="label.members"/></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="u" items="${users}">
			<tr>
		  		<td><a onclick="openUser(${u.id});return false;" href="#${u.id}">${u.name}</a></td>
		  		<td>${u.email}</td>
		  		<td>${u.members}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

</fmt:bundle>