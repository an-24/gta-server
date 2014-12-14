<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<script>
  $(function() {
    $("#project-list").selectable({
    	selected : function(event, ui) {
    		$("#tabs").css("display","block");
    		$("#team-propertys h2").text("Project: "+ui.selected.innerHTML);
    		var teamId = $(ui.selected).attr("team-id");
    		loadProjectSheet(teamId,$.cookie("token"));
    	    $("#person-list").selectable({
    	    	selected : function(event, ui) {
    	    		
    	    	}
    	    });
    	}
    });
    $("#tabs").tabs({
    	active:0
    });
  });
</script>

<ol id="project-list" class="list-view">
	<c:forEach var="t" items="${teams}">
  		<li class="ui-widget-content" team-id="${t.id}" id="li-team-${t.id}">${t.name}</li>
	</c:forEach>
</ol>

<div id="team-propertys">
	<h2></h2>
	<div id="tabs" style="display:none">
		<ul>
			<li><a href="#tabs-1">Common</a></li>
			<li><a href="#tabs-2">Team</a></li>
		</ul>
		<div id="tabs-1">
		   <table style="width:100%;">
		   	<tr>
		   		<td style="width:400px;"><label>Project starting date</label></td>
		   		<td><input id="createDate" readonly type="text"/></td>
		   	</tr>
		   	<tr>
		   		<td><label>Weekly limit</label></td>
		   		<td><input id="limit" readonly type="text"/></td>
		   	</tr>
		   	<tr>
		   		<td colspan="2">Statistics<hr></td>
		   	</tr>
		   	<tr>
		   		<td><label>Worked per day</label></td>
		   		<td id="workedOfDay">17<span> hours</span></td>
		   	</tr>
		   	<tr>
		   		<td><label>Worked per week</label></td>
		   		<td id="workedOfWeek">62<span> hours</span></td>
		   	</tr>
		   	<tr>
		   		<td><label>Worked per month</label></td>
		   		<td id="workedOfMonth">340<span> hours</span></td>
		   	</tr>
		   	<tr>
		   		<td><label>Worked from the beginning of the project</label></td>
		   		<td id="workedOfBeginProject">340<span> hours</span></td>
		   	</tr>
		   </table>  
  		</div>
  		<div id="tabs-2">
			<ol id="person-list" class="list-view"></ol>
  		</div>
	</div>
</div>