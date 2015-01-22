<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${loc}"/>
<fmt:bundle basename="biz.gelicon.gta.server.i18n.Bundle">

<style>
	table#month-cal td li {
		font-size: medium;
		list-style: none;
	}
</style>

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
				<c:forEach var="data" items="${week.days}">
					<td id="day" dt="<fmt:formatDate value='${data.day}' pattern='dd.MM.yyyy'/>"><fmt:formatDate value="${data.day}" pattern="dd"/>
					<div class="hours"><fmt:formatNumber value="${data.hours}" maxFractionDigits="1" minFractionDigits="1"></fmt:formatNumber></div>
					<div class="activity" style="width:${data.activityWidth}px">&#160;</div>
					</td>
				</c:forEach>
    			
    			<fmt:formatDate var="dtStart" value='${week.days[0].day}' pattern='dd.MM.yyyy'/>
    			<fmt:formatDate var="dtEnd" value='${week.days[6].day}' pattern='dd.MM.yyyy'/>
				<td style="width:auto">
					<li>
						<a target="_blank" href="inner/report/r1?teamId=${teamId}&dateStart=${dtStart}&dateEnd=${dtEnd}"><fmt:message key="label.report"/></a>
					</li>
				<c:if test="${week.signAvailable and week.signNeeded}"> 
					<li><a class="signReport" date-sign="${dtEnd}" date-sig-begin="${dtStart}" href="#"><fmt:message key="label.signreport"/></a></li>
				</c:if>
				<c:if test="${week.signature!=null}">
					<fmt:message key="label.signed"/> <c:out value="${week.signature.user.name}"></c:out>, 
					<fmt:formatDate value='${week.signature.dtSignDay}' type="both" dateStyle="short" timeStyle="short"/>  
				</c:if>
				
				</td>   			
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
		
		$(".signReport").click(function(){
			var dtBegin = $(this).attr("date-sig-begin");
			var dtSign = $(this).attr("date-sign");
			
			$.ajax({
				type: "GET",
		        url: "inner/report/r1?teamId="+teamId+"&dateStart="+dtBegin+"&dateEnd="+dtSign+"&base64encode=1&signed=1",
		        success: function(dataSign){
		        	if(dataSign.error) {
		        		this.error({responseText:dataSign.message});
			        	return;
		        	};
					cades.signature(dataSign, function(sign,hash,data){
						if(sign) {
							$.ajax({
								type: "POST",
						        url: "inner/sign/put",
						        data: {
						        	teamId:teamId,
						        	date:dtSign,
						        	signature:sign,
						        	hash:hash,
						        	data:data
						        },
						        success: function(data){
						        	var obj = eval(data);
						        	if(obj.error) this.error({responseText:obj.message});else {
						        		if(data) {
						            		toastr["success"]("", "<fmt:message key='message.success'/>");
						            		refreshCalendar();
						        		} else {
						            		toastr["error"]("<fmt:message key='message.signcheckerror'/>", "<fmt:message key='message.error'/>");
						        		}
						        	}
						        },
						        error: function (request, status, error,data) {
						        	if($('#error-place').length==0) {
						            	toastr["error"](request.responseText, "<fmt:message key='message.error'/>");
						        	} else
						        	$.formUtils.showError(request.responseText);
						        }
						    });
						}
					});
		        },
		        error: function (request, status, error,data) {
		        	if($('#error-place').length==0) {
		            	toastr["error"](request.responseText, "<fmt:message key='message.error'/>");
		        	} else
		        	$.formUtils.showError(request.responseText);
		        }
			});
		});
		
	});

	function loadInfoOnDay(td) {
		var pId = $("#persons").val();
		var dt = $(td).attr("dt");
		$("#diary").load("inner/diary/day?personId="+pId+"&date="+dt+"&teamId="+teamId);
	}
	
</script>

</fmt:bundle>