<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<script>
  $(function() {
    $("#project-list" ).selectable();
  });
</script>

<ol id="project-list" class="list-view">
  <li class="ui-widget-content">Item 1</li>
</ol>