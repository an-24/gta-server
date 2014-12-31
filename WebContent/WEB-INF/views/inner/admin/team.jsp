<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script>
  $(function() {
    initForm($("#frmTeam"),"inner/admin/teams/update",function(data){
    	toastr["success"](data.message, "Success");
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
   <table>
   <tr>
   	<td><form:label path="name">Team name<em>*</em></form:label></td>
   	<td><form:input path="name" data-validation="required" data-validation-error-msg="You must enter the team name"/></td>
   </tr>
   <tr>
   	<td><form:label path="active">Active</form:label></td>
   	<td><form:checkbox path="active"/></td>
   </tr>
   <tr>
   	<td><label>Date of start</label></td>
   	<td><input value="${team.createDate}" readonly type="text"/></td>
   </tr>
   <tr>
   	<td><form:label path="limit">Limit</form:label></td>
   	<td><form:input path="limit" data-validation="number" data-validation-optional="true"/></td>
   </tr>
   
   <tfoot>
   <tr>
   	<td colspan="2">
   		<input id="default" type="submit" value="Submit"/>
   	</td>
   </tr>
   </tfoot>
   </table>  
</form:form>
