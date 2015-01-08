<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${loc}"/>
<fmt:bundle basename="biz.gelicon.gta.server.i18n.Bundle">

<style>
	#backToData {
		float:left;
		margin-left: 20px;
	}
	
	h3#date {
		text-align: center;
	}
	
	#hours-place {
		position: absolute;
		overflow-y: scroll;
		bottom: 10px;
		top: 42px;
		left: 20px;
		right: 20px; 
	}
	
	#hours-place table td {
		height:50px;
		padding: 5px;
	}
	
	#hours-place table td ol {
		list-style-type: none;
		font-size:medium;
	}	
}
	
</style>

<script type="text/javascript">
	$("#total").text("<fmt:formatNumber value="${total.worktime}" maxFractionDigits="1" minFractionDigits="1"/>");

	$(function() {
		$("#backToData").click(function() {
			loadInfo();
		});

		$("a#image").fancybox({
			transitionIn: 'none',
			transitionOut: 'none'
		});
	});
</script>


<button id="backToData"><fmt:message key='label.back'/></button>
<h3 id="date"><fmt:formatDate value="${date}" dateStyle="short"/></h3>

<div id="hours-place">
<table>
	<thead>
		<tr>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="hour" items="${hours}">
			<tr>
				<td>${hour.info}</td>
				<td>
					<c:if test="${hour.worktime!=null}">
					 <ol>
						<li><fmt:message key='label.worked'/>: <fmt:formatNumber value="${hour.worktime}" maxFractionDigits="1" minFractionDigits="1"></fmt:formatNumber></li>
						<li><fmt:message key='label.activity'/>: <fmt:formatNumber value="${hour.activity}" maxFractionDigits="1" minFractionDigits="1"></fmt:formatNumber>
								(<fmt:formatNumber value="${hour.activityPercent}" maxFractionDigits="1" minFractionDigits="1"></fmt:formatNumber>%)
						<div style="font-size: x-small;">(keys pressed: ${hour.keyDown},
						mouse clicked: ${hour.mouseClick},
						mouse moved: ${hour.mouseMove})</div></li>
					 </ol>	
					</c:if>
				</td>
				<c:forEach var="ss" items="${hour.screenshots}">
					<td><a rel="imagegroup" id="image" href="${ss.fullImageUrl}"><img alt="screenshot" src="${ss.url}"></a></td>
				</c:forEach>
			</tr> 
   		</c:forEach>   			
	</tbody>
</table>
</div>

</fmt:bundle>


