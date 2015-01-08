<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${loc}"/>
<fmt:bundle basename="biz.gelicon.gta.server.i18n.Bundle">

<style>
 #select-project {
	width: 300px;
	left: 50%;
	margin-left: -150px;
	min-height: 300px;
	top: 100px;
	position: relative;
	background-color: white;
	border-radius: 5px;
	padding:5px;
 }
 #select-project h2 {
 	margin-left: 10px;
 }
</style>

<script>
  $(function() {
    $("#plist").selectable({
    	selected : function(event, ui) {
    		var teamId = $(ui.selected).attr("team-id");
   			location.href = "diary/"+teamId;
    	}
    });
  }); 
</script>

<div id="select-project" style="white-space:nowrap;">
<h2><fmt:message key='label.selectproject'/></h2>
<ol id="plist" class="list-view">
	<c:forEach var="t" items="${teams}">
  		<li class="ui-widget-content" team-id="${t.id}" id="li-team-${t.id}">${t.name}</li>
	</c:forEach>
</ol>
</div>

</fmt:bundle>