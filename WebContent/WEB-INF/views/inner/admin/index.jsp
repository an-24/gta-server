<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
	<li class="ui-widget-content" href="inner/admin/users">Users</li>
	<li class="ui-widget-content" href="inner/admin/teams">Teams</li>
	<li class="ui-widget-content" href="inner/admin/persons">Members</li>
</ol>

<div id="admin-content" class="ui-widget"></div>