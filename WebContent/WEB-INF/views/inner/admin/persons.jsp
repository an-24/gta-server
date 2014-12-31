<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script type="text/javascript">
$(function() {
	$("#person-table").dataTable({
		pageLength:6,
		lengthChange:false,
		info: false
	});
	$("#addPerson").click(function(){
		openAdminPerson();
	});
});
</script>


<button id="addPerson">Add</button>
<table id="person-table" class="display" cellspacing="0">
	<thead>
		<tr>
			<th>Nic</th>
			<th>User</th>
			<th>Team</th>
			<th>Post</th>
			<th>Limit</th>
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
