<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${loc}" />
<fmt:bundle basename="biz.gelicon.gta.server.i18n.Bundle">

<style>
	#addPerson {
		position: absolute;
		top:70px;
		z-index:200;
	}
	
	#frmTeam td{
		text-align:left;
	}
	
	#tabs-1,#tabs-2,#tabs-3 {
		overflow: hidden;
	}
	
	#workDiary {
		display:none;
		float: right;
		top: -26pt;
		position: relative;
	}
</style>

<script>
  $(function() {
    $("#project-list").selectable({
    	selected : function(event, ui) {
    		$("#tabs").css("display","block");
    		$("#team-propertys h2").text("<fmt:message key='label.project'/>: "+ui.selected.innerHTML);
    		$('#workDiary').css("display","block");
    		var teamId = $(ui.selected).attr("team-id");
    		loadProjectSheet(teamId,$.cookie("token"));
    	}
    });
    $("#tabs").tabs({
    	active:0
    });
	$("#addPerson").click(function(){
		openPerson();
	});
	
    initForm($("#frmTeam"),"inner/team/update",function(data){
    	toastr["success"](data.message, "<fmt:message key='message.success'/>");
    	refreshProjectSheet(0);
    });
	$("#workDiary").click(function(){
		location.href = "diary/"
			+$("#project-list li.ui-selected").attr("team-id");
	});
  });
  
</script>

<div style="white-space:nowrap;">

<ol id="project-list" class="list-view" style="width: 300px;">
	<c:forEach var="t" items="${teams}">
  		<li class="ui-widget-content" team-id="${t.id}" id="li-team-${t.id}">${t.name}</li>
	</c:forEach>
</ol>

<div id="team-propertys">
	<h2></h2><button id="workDiary" class="ui-widget"><fmt:message key='label.workdiary'/></button>
	<div id="tabs" style="display:none">
		<ul>
			<li><a href="#tabs-1"><fmt:message key='label.common'/></a></li>
			<li><a href="#tabs-2"><fmt:message key='label.members'/></a></li>
		</ul>
		<div id="tabs-1">
		   <form id="frmTeam" style="width:100%;">
		   <input id="id" name="id" type="hidden"/>
		   <table style="width:100%;">
		   	<tr>
		   		<td style="width:400px;"><label><fmt:message key='label.startdate'/></label></td>
		   		<td><input id="createDate" readonly type="text"/></td>
		   	</tr>
		   	<tr>
		   		<td><label><fmt:message key='label.limit'/></label></td>
		   		<td><input id="limit" name="limit" data-validation="number" data-validation-optional="true" readonly type="text"/></td>
		   	</tr>
		   	<tr>
		   		<td colspan="2"><fmt:message key='label.statistics'/><hr></td>
		   	</tr>
		   	<tr>
		   		<td><label><fmt:message key='label.worked-per-day'/></label></td>
		   		<td id="workedOfDay">17<span> hours</span></td>
		   	</tr>
		   	<tr>
		   		<td><label><fmt:message key='label.worked-per-week'/></label></td>
		   		<td id="workedOfWeek">62<span> hours</span></td>
		   	</tr>
		   	<tr>
		   		<td><label><fmt:message key='label.worked-per-month'/></label></td>
		   		<td id="workedOfMonth">340<span> hours</span></td>
		   	</tr>
		   	<tr>
		   		<td><label><fmt:message key='label.worked-from-begin'/></label></td>
		   		<td id="workedOfBeginProject">340<span> hours</span></td>
		   	</tr>
		   	<tfoot>
		   		<tr>
		   			<td id="updTeamBtn" style="text-align: right;" colspan="2"><input value="<fmt:message key='label.update'/>" type="submit"/></td>
		   		</tr>
		   	</tfoot>
		   </table>
		   </form>  
  		</div>
  		<div id="tabs-2">
  			<button id="addPerson"><fmt:message key='label.add'/></button>
			<table id="person-list" class="display">
				<thead>
					<tr>
						<th><fmt:message key='label.name'/></th>
						<th><fmt:message key='label.post'/></th>
						<th><fmt:message key='label.limit'/></th>
					</tr>
				</thead>
			</table>
  		</div>
	</div>
</div>

</div>
</fmt:bundle>