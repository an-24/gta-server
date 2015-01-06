<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<table id="month-cal">
	<thead>
		<tr>
		<c:forEach var="day" items="${axis}"> 
			<th><c:out value="${day}"/></th>
   		</c:forEach>   			
	    <th>&#160;&#160;&#160;</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="week" items="${data}">
			<tr> 
				<c:forEach var="data" items="${week}">
					<td id="day" dt="<fmt:formatDate value='${data.day}' pattern='dd.MM.yyyy'/>"><fmt:formatDate value="${data.day}" pattern="dd"/>
					<div class="hours"><fmt:formatNumber value="${data.hours}" maxFractionDigits="1" minFractionDigits="1"></fmt:formatNumber></div>
					<div class="activity" style="width:${data.activityWidth}px">&#160;</div>
					</td>
				</c:forEach>
				<td style="width:auto"></td>   			
			</tr>
		</c:forEach>   			
	</tbody>
</table>



<script type="text/javascript">
	$("#total").text("<fmt:formatNumber value="${total.hours}" maxFractionDigits="1" minFractionDigits="1"/>");
	
	$(function() {
		$("#diary tbody td#day").click(function(){
			loadInfoOnDay(this);
		});
	});

	function loadInfoOnDay(td) {
		var pId = $("#persons").val();
		var dt = $(td).attr("dt");
		$("#diary").load("inner/diary/day?personId="+pId+"&date="+dt+"&teamId="+teamId);
	}
	
</script>
