<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<style>
#profile-content {
	top: 20px;
	background-color: white;
	position: relative;
	border-radius: 5px;
	min-height: 402px;
	padding: 20px;
	overflow: hidden;
	left: 50%;
	width: 500px;
	margin-left: -250px;
}

</style>


<script>
  $(function() {
    initForm($("#frmUser"),"inner/admin/users/update",function(data){
    	toastr["success"](data.message, "Success");
    });
    $("#frmUser #cancel").click(function() {
    });
    
  });
</script>

<div id="profile-content" class="ui-widget">

<c:if test="${user.mode==2}"><h2>Profile '${user.name}'</h2></c:if>

<div id='error-place' class='form-error'></div> 

<form:form id="frmUser" style="width:auto" commandName="user">
   <form:input path="mode" type="hidden"/>
   <form:input path="id" type="hidden"/>
   <table>
   <tr>
   	<td><form:label path="name">User name<em>*</em></form:label></td>
   	<td><form:input id="user-name" path="name" data-validation="required" data-validation-error-msg="You must enter the user name"/></td>
   </tr>
   <tr>
   	<td><form:label path="email">E-mail</form:label></td>
   	<td><form:input path="email" data-validation="email" data-validation-error-msg="Error in entering of e-mail" data-validation-optional="true"/></td>
   </tr>
   <c:if test="${user.mode==2}">
   <tr>
   	<td colspan="2" style="text-align: left;">
   		Change password
   		<hr>
   	</td>
   </tr>
   </c:if>
   <tr>
   	<c:if test="${user.mode==2}">
   		<td><label>New password</label></td>
   		<td><input name="pswd_confirmation" value="" type="password" autocomplete="off" data-validation="length" data-validation-length="min8" data-validation-optional="true" data-validation-error-msg="The password is shorter than 8 characters"/></td>
   	</c:if>
   	<c:if test="${user.mode==1}">
   		<td><label>You password</label></td>
   		<td><input name="pswd_confirmation" value="" type="password" autocomplete="off" data-validation="length" data-validation-length="min8" data-validation-error-msg="The password is shorter than 8 characters"/></td>
   	</c:if>
   </tr>
   <tr>
   	<td><label>Confirm password</label></td>
   	<td><input name="pswd" value="" type="password"  autocomplete="off" data-validation="confirmation" data-validation-error-msg="Password could not be confirmed"/></td>
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

</div>