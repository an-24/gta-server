<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${loc}"/>
<fmt:bundle basename="biz.gelicon.gta.server.i18n.Bundle">

<script>
  $(function() {
    $("#admin-menu").selectable({
    	selected : function(event, ui) {
    		var url = $(ui.selected).attr("href");
    		$("#admin-content").load(url);
    	}
    });
  }); 
</script>

<ol id="admin-menu" class="list-view">
	<li class="ui-widget-content" href="inner/admin/users"><fmt:message key="label.users"/></li>
	<li class="ui-widget-content" href="inner/admin/teams"><fmt:message key="label.teams"/></li>
	<li class="ui-widget-content" href="inner/admin/persons"><fmt:message key="label.members"/></li>
</ol>

<div id="admin-content" class="ui-widget"></div>

</fmt:bundle>