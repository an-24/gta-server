<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${loc}"/>
<fmt:bundle basename="biz.gelicon.gta.server.i18n.Bundle">


<script>

$("#user-name").autocomplete({
    source: "inner/admin/getusers",
    minLength: 2,
    select: function(event, ui) {
    	$("#user-id").val(ui.item.id);
    },
    change: function(event, ui) {
    	if(ui.item!=null) {
        	var nic = $("#nic").val();
        	if(nic=="") $("#nic").val(ui.item.label);
        	$("#user-id").val(ui.item.id);
    	} else {
        	$("#user-id").val("");
    	}
    }
});

  $(function() {
    initForm($("#frmTeam"),"inner/admin/teams/update",function(data){
    	toastr["success"](data.message, "<fmt:message key='message.success'/>");
    	selectSelectableElement($("#admin-menu"),$("#admin-menu li[href='inner/admin/teams']"));
    });
    $("#frmTeam #cancel").click(function() {
    	selectSelectableElement($("#admin-menu"),$("#admin-menu li[href='inner/admin/teams']"));
    });
  });
</script>

<c:if test="${team.mode==1}"><h2><fmt:message key="label.newteam"/></h2></c:if>
<c:if test="${team.mode==2}"><h2><fmt:message key="label.editby"/> '${team.name}'</h2></c:if>

<div id='error-place' class='form-error'></div>

<form:form id="frmTeam" style="width:auto" commandName="team">
   <form:input path="mode" type="hidden"/>
   <form:input path="id" type="hidden"/>
   <form:input path="manager.id" type="hidden"/>
   <table>
   <tr>
   	<td><form:label path="name"><fmt:message key="label.teamname"/><em>*</em></form:label></td>
   	<fmt:message var="tmp" key='message.teamname'/>
   	<td><form:input path="name" data-validation="required" data-validation-error-msg="${tmp}"/></td>
   	<td><label><fmt:message key="label.startdate"/></label></td>
   	<td><input value="<fmt:formatDate value='${team.createDate}' dateStyle='short'/>" readonly type="text"/></td>
   </tr>
   <tr>
   	<td><form:label path="active"><fmt:message key="label.active"/></form:label></td>
   	<td><form:checkbox path="active"/></td>
   </tr>
   <tr>
   	<td><form:label path="limit"><fmt:message key="label.limit"/></form:label></td>
   	<td><form:input path="limit" data-validation="number" data-validation-optional="true"/></td>
   </tr>
   <tr>
	<td colspan="4" style="text-align:left"><fmt:message key="label.pm"/><hr></td>
   </tr>
   <tr>
   	<td><form:label path="manager.user.name"><fmt:message key="label.username"/><em>*</em></form:label></td>
   	<td><form:input id="user-name" path="manager.user.name"/>
   		<fmt:message var="tmp" key='message.selectuser'/>
   	    <form:input id="user-id" path="manager.user.id" type="hidden" data-validation="key" data-validation-error-msg="${tmp}"/></td>
   	<td><form:label path="manager.nic"><fmt:message key="label.nic"/><em>*</em></form:label></td>
   	<fmt:message var="tmp" key='message.nic'/>
   	<td><form:input id="nic" path="manager.nic" data-validation="required" data-validation-error-msg="${tmp}"/></td>
   </tr>
   
   <tfoot>
   <tr>
   	<td colspan="4">
   		<input id="cancel" type="button" value="<fmt:message key='label.cancel'/>"/>
   		<input id="default" type="submit" value="<fmt:message key='label.submit'/>"/>
   	</td>
   </tr>
   </tfoot>
   </table>  
</form:form>

</fmt:bundle>
