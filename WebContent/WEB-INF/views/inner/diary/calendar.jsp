<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${loc}"/>
<fmt:bundle basename="biz.gelicon.gta.server.i18n.Bundle">

<style>
	#diary table#month-cal {
		width:100%;
		height:100%;
		text-align: center;
		border-collapse: collapse;
	}
	#diary table#month-cal td {
		vertical-align: top;
		font-size: x-small;
		width: 120px;
	}
	#diary table#month-cal th {
		padding-bottom: 20px;
	}
	#diary table#month-cal td .hours {
		padding-top: 11px;
		font-size: large;	 
	}
	
	#diary table#month-cal tbody td:hover {
		cursor:default;
		color:#660000;
		background-color: silver;
	}
	
	#diary table#month-cal td .activity {
		position: relative;
		height: 10px;
		background-color: #00A000;
		margin-left: 16px;
		margin-right: 16px;
		width:0px;
	}
	
	#diary-container .header button {
		float:left;
	}
	
	.headertext {
		margin-left:20px;
		float:left;
		position:relative;
		top:6px
	}
	
	.team-name {
		float: left;
		max-width: 200px;
		overflow: hidden;
		text-overflow: ellipsis;
	}

</style>

<script type="text/javascript">

var teamId = <c:out value="${team.id}"/>;

$(function() {
	$("#persons").multiselect({
		   multiple: false,
		   header: "<fmt:message key='label.select-member'/>",
		   noneSelectedText: "-",
		   style:"width:200px",
		   selectedList: 1,
		   click: function(event, ui) {
			   loadInfo(ui.value);
		   }
	});
	$("#months").multiselect({
		   multiple: false,
		   header: "<fmt:message key='label.select-month'/>",
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
	$("#diary").load("inner/diary/data?personId="+pId+"&monthId="+mId+"&teamId="+teamId);
}

</script>

<div id="diary-container" class="ui-widget">
	<div class="header" style="white-space: nowrap;margin-bottom:10px;padding-left: 20px;">
		<h2 class="team-name">${team.name}</h2>
		<div class="headertext"><fmt:message key='label.member'/>:</div>
 		<select id="persons">
			<c:forEach var="p" items="${team.persons}"> 
 			    <option value="${p.id}" ${currentPersonId == p.id ? 'selected' : ''}><c:out value="${p.nic}"/></option>
      		</c:forEach>   			
 		</select>
		<div class="headertext"><fmt:message key='label.month'/>:</div>
 		<select id="months">
			<c:forEach var="m" items="${months}"> 
 			    <option value="${m.id}" ${currentMonthId == m.id ? 'selected' : ''}><c:out value="${m.fullName}"/></option>
      		</c:forEach>   			
 		</select>
		<div class="headertext"><fmt:message key='label.total'/>:</div>
		<div id="total" class="headertext"></div>
	</div>
	<div id="diary">
	</div>
</div>
</fmt:bundle>