<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${loc}" />
<fmt:bundle basename="biz.gelicon.gta.server.i18n.Bundle">

<style>
	form {
		padding-top:0px;
		padding-bottom:0px;
	}

	#profile-content {
		top: 20px;
		background-color: white;
		position: relative;
		border-radius: 5px;
		min-height: 402px;
		padding: 20px;
		overflow: hidden;
		left: 50%;
		width: 700px;
		margin-left: -350px;
	}
	td#td-tz-id button span.ui-label-text{
		text-overflow: ellipsis;
		width: 420px;
		overflow: hidden;
	}

</style>


<script>
  $(function() {
		 $("#locale").multiselect({
		   	   multiple: false,
		   	   header: "<fmt:message key='label.select-locale'/>",
		   	   noneSelectedText: "-",
		   	   style:"width:190px",
		   	   selectedList: 1
		});
		 $("#timeZoneId").multiselect({
		   	   multiple: false,
		   	   header: "<fmt:message key='label.select-tz'/>",
		   	   noneSelectedText: "-",
		   	   style:"width:490px",
		   	   selectedList: 1
		});
	  
		initMultiPartForm($("#frmUser"),"inner/admin/users/update",function(data) {
        	toastr["success"](data.message, "<fmt:message key='message.success'/>");
    	});
		
		$("#activeSertificateName").click(function () {
		    $("#activeSertificate").trigger('click');
		});
		$("#activeSertificate").change(function () {
			$("#activeSertificateName").val(this.value);
	    });
  });
</script>

<div id="profile-content" class="ui-widget">

<c:if test="${user.mode==2}"><h2><fmt:message key="label.profile"/> '${user.name}'</h2></c:if>

<div id='error-place' class='form-error'></div> 

<form:form id="frmUser" style="width:auto" commandName="user" enctype="multipart/form-data">
   <form:input path="mode" type="hidden"/>
   <form:input path="id" type="hidden"/>
   <table>
   <tr>
   	<td><form:label path="name"><fmt:message key="label.username"/><em>*</em></form:label></td>
   	<fmt:message var="tmp" key='message.username'/>
   	<td><form:input id="user-name" path="name" data-validation="required" data-validation-error-msg="${tmp}"/></td>
   </tr>
   <tr>
   	<td><form:label path="email">E-mail</form:label></td>
   	<fmt:message var="tmp" key='message.email'/>
   	<td><form:input path="email" data-validation="email" data-validation-error-msg="${tmp}" data-validation-optional="true"/></td>
   </tr>
   <tr>
   	<td><form:label path="locale"><fmt:message key="label.lang"/></form:label></td>
   	<td>
		<!--<form:input path="locale"/>-->
   		<select id="locale" name="locale">
   		    <option value="">-default-</option>
			<c:forEach var="l" items="${locales}"> 
   		    	<option value="${l.id}" ${user.locale == l.id ? 'selected' : ''}><c:out value="${l.name}"/></option>
        	</c:forEach>   			
   		</select>
   	</td>
   </tr>
   <tr>
   	<td><form:label path="timeZoneId"><fmt:message key="label.tz"/></form:label></td>
   	<td id="td-tz-id">
		<!--<form:input path="locale"/>-->
   		<select id="timeZoneId" name="timeZoneId">
   		    <option value="">-empty-</option>
			<c:forEach var="tz" items="${timezones}"> 
   		    	<option value="${tz.id}" ${user.timeZoneId == tz.id ? 'selected' : ''}><c:out value="${tz.name}"/></option>
        	</c:forEach>   			
   		</select>
   	</td>
   </tr>
   
   <tr>
   	<td><form:label path="activeSertificate"><fmt:message key="label.cert"/></form:label></td>
   	<td><input name="activeSertificateName" id="activeSertificateName" readonly value="${user.activeSertificateName}" type="text"/>
   	<form:input id="activeSertificate" path="activeSertificate" type="file" accept="application/x-x509-ca-cert" style="display:none"/></td>
   </tr>
   
   <c:if test="${user.mode==2}">
   <tr>
   	<td colspan="2" style="text-align: left;">
   		<fmt:message key="label.changepassword"/>
   		<hr>
   	</td>
   </tr>
   </c:if>
   <tr>
   	<c:if test="${user.mode==2}">
   		<td><label><fmt:message key="label.newpassword"/></label></td>
   		<fmt:message var="tmp" key='message.incorrectpassword'/>
   		<td><input name="pswd_confirmation" value="" type="password" autocomplete="off" data-validation="length" data-validation-length="min8" data-validation-optional="true" data-validation-error-msg="${tmp}"/></td>
   	</c:if>
   	<c:if test="${user.mode==1}">
   		<td><label><fmt:message key="label.youpassword"/></label></td>
   		<fmt:message var="tmp" key='message.incorrectpassword'/>
   		<td><input name="pswd_confirmation" value="" type="password" autocomplete="off" data-validation="length" data-validation-length="min8" data-validation-error-msg="${tmp}"/></td>
   	</c:if>
   </tr>
   <tr>
   	<td><label><fmt:message key="label.confirmpassword"/></label></td>
   	<fmt:message var="tmp" key='message.noconfirmpassword'/>
   	<td><input name="pswd" value="" type="password"  autocomplete="off" data-validation="confirmation" data-validation-error-msg="${tmp}"/></td>
   </tr>
   
   <tfoot>
   <tr>
   	<td colspan="2">
   		<input id="default" type="submit" value="<fmt:message key='label.submit'/>"/>
   	</td>
   </tr>
   </tfoot>
   </table>  
</form:form>

</div>

</fmt:bundle>