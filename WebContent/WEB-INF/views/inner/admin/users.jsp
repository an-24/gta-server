<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script type="text/javascript">
$(function() {
	$("#users-table").dataTable({
		pageLength:8,
		lengthChange:false,
		info: false
	});
	$("#addUser").click(function(){
		openUser();
	});
});
</script>

<button id="addUser">Add</button>
<table id="users-table">
	<thead>
		<tr>
			<th>Name</th>
			<th>e-mail</th>
			<th>Members of team</th>
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