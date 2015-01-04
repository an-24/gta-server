<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<style>
	#diary table {
		width:100%;
	}	 
</style>

<script type="text/javascript">

$(function() {
	$("#persons").multiselect({
		   multiple: false,
		   header: "Select member",
		   noneSelectedText: "-",
		   style:"width:200px",
		   selectedList: 1,
		   click: function(event, ui) {
			   loadInfo(ui.value);
		   }
	});
	$("#months").multiselect({
		   multiple: false,
		   header: "Select month",
		   noneSelectedText: "-",
		   style:"width:200px",
		   selectedList: 1,
		   click: function(event, ui) {
			   loadInfo(undefined,ui.value);
		   }
	});
	
	loadInfo();
});

function loadInfo(selPersonId,selMonthId) {
	var pId = selPersonId || $("#persons").val();
	var mId = selMonthId || $("#months").val();
	$("#diary table tbody").load("inner/diary/data?personId="+pId+"&monthId="+mId);
}

</script>

<div id="diary-container" class="ui-widget">
	<div style="white-space: nowrap;margin-bottom:10px;">
		<h2 style="float:left;margin-right:60px;">${team.name}</h2>
		<span>Member:</span>
 		<select id="persons">
			<c:forEach var="p" items="${team.persons}"> 
 			    <option value="${p.id}" ${currentPersonId == p.id ? 'selected' : ''}><c:out value="${p.nic}"/></option>
      		</c:forEach>   			
 		</select>
		<span style="margin-left:20px;">Month:</span>
 		<select id="months">
			<c:forEach var="m" items="${months}"> 
 			    <option value="${m.id}" ${currentMonthId == m.id ? 'selected' : ''}><c:out value="${m.fullName}"/></option>
      		</c:forEach>   			
 		</select>
	</div>
	<div id="diary" style="white-space: nowrap;height: 100%;">
		<table>
			<thead>
				<tr>
				<c:forEach var="day" items="${axis}"> 
		    		<th><c:out value="${day}"/></th>
   				</c:forEach>   			
	    		<th>&#160;&#160;&#160;</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>

</div>