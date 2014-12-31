<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
    	toastr["success"](data.message, "Success");
    	selectSelectableElement($("#admin-menu"),$("#admin-menu li[href='inner/admin/teams']"));
    });
    $("#frmTeam #cancel").click(function() {
    	selectSelectableElement($("#admin-menu"),$("#admin-menu li[href='inner/admin/teams']"));
    });
  });
</script>


<c:if test="${team.mode==1}"><h2>New team</h2></c:if>
<c:if test="${team.mode==2}"><h2>Edited by '${team.name}'</h2></c:if>

<div id='error-place' class='form-error'/>

<form:form id="frmTeam" style="width:auto" commandName="team">
   <form:input path="mode" type="hidden"/>
   <form:input path="id" type="hidden"/>
   <form:input path="manager.id" type="hidden"/>
   <table>
   <tr>
   	<td><form:label path="name">Team name<em>*</em></form:label></td>
   	<td><form:input path="name" data-validation="required" data-validation-error-msg="You must enter the team name"/></td>
   	<td><label>Date of start</label></td>
   	<td><input value="${team.createDate}" readonly type="text"/></td>
   </tr>
   <tr>
   	<td><form:label path="active">Active</form:label></td>
   	<td><form:checkbox path="active"/></td>
   </tr>
   <tr>
   	<td><form:label path="limit">Limit</form:label></td>
   	<td><form:input path="limit" data-validation="number" data-validation-optional="true"/></td>
   </tr>
   <tr>
	<td colspan="4" style="text-align:left">Project manager<hr></td>
   </tr>
   <tr>
   	<td><form:label path="manager.user.name">User<em>*</em></form:label></td>
   	<td><form:input id="user-name" path="manager.user.name"/>
   	    <form:input id="user-id" path="manager.user.id" type="hidden" data-validation="key" data-validation-error-msg="You must select a user"/></td>
   	<td><form:label path="manager.nic">Nic name<em>*</em></form:label></td>
   	<td><form:input id="nic" path="manager.nic" data-validation="required" data-validation-error-msg="You must enter the nic name"/></td>
   </tr>
   
   <tfoot>
   <tr>
   	<td colspan="4">
   		<input id="cancel" type="button" value="Cancel"/>
   		<input id="default" type="submit" value="Submit"/>
   	</td>
   </tr>
   </tfoot>
   </table>  
</form:form>
