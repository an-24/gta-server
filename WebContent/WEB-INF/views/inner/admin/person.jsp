<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<style>
#team-name {
	width:100%;
}
</style>

<script>
  $(function() {
    $("#post").multiselect({
    	   multiple: false,
    	   header: "Select post",
    	   noneSelectedText: "-",
    	   style:"width:100%",
    	   selectedList: 1,
    	   click: function(event, ui) {
    		   var opt = $(this).find("option[value='"+ui.value+"']");
    		   $("#postName").val(opt[0].innerHTML);
    	   }
    	});
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
    $("#team-name").autocomplete({
        source: "inner/admin/getteams",
        minLength: 2,
        select: function(event, ui) {
        	$("#team-id").val(ui.item.id);
        },
        change: function(event, ui) {
        	if(ui.item!=null) {
            	$("#team-id").val(ui.item.id);
        	} else {
            	$("#team-id").val("");
        	}
        }
    });
    initForm($("#frmPerson"),"inner/admin/persons/update",function(data){
    	toastr["success"](data.message, "Success");
    	selectSelectableElement($("#admin-menu"),$("#admin-menu li[href='inner/admin/persons']"));
    });
    $("#frmPerson #cancel").click(function() {
    	selectSelectableElement($("#admin-menu"),$("#admin-menu li[href='inner/admin/persons']"));
    });
    
  });
</script>

<c:if test="${person.mode==1}"><h2>New member</h2></c:if>
<c:if test="${person.mode==2}"><h2>Edited by '${person.nic}'</h2></c:if>

<div id='error-place' class='form-error'></div>

<form:form id="frmPerson" style="width:auto" commandName="person">
   <form:input path="mode" type="hidden"/>
   <form:input path="id" type="hidden"/>
   <table>
   <tr>
    <td><form:label path="team.name">Team<em>*</em></form:label></td>
   	<td colspan="2">
   	  <div style="padding-right: 73px;">
   		<form:input id="team-name" path="team.name"/>
   	    <form:input id="team-id" path="team.id" type="hidden" data-validation="key" data-validation-error-msg="You must select a team"/>
   	  </div>  
    </td>
   <tr>
   	<td><form:label path="user.name">User<em>*</em></form:label></td>
   	<td><form:input id="user-name" path="user.name"/>
   	    <form:input id="user-id" path="user.id" type="hidden" data-validation="key" data-validation-error-msg="You must select a user"/></td>
   	<td><form:label path="nic">Nic name<em>*</em></form:label></td>
   	<td><form:input path="nic" data-validation="required" data-validation-error-msg="You must enter the nic name"/></td>
   </tr>
   <tr>
   	<td><form:label path="post">Post</form:label></td>
   	<td>
   			<!--<form:input path="post"/>-->
   			<select id="post" name="post">
   			    <option value="-1">-empty-</option>
				<c:forEach var="p" items="${posts}"> 
   			    	<option value="${p.id}" ${person.post == p.id ? 'selected' : ''}><c:out value="${p.name}"/></option>
        		</c:forEach>   			
   			</select>
   	</td>
   	<td><form:label path="postName">Post name<em>*</em></form:label></td>
   	<td><form:input path="postName" id="postName" data-validation="required" data-validation-error-msg="You must enter the post name"/></td>
   </tr>
   <tr>
   <td><form:label path="limit">Limit</form:label></td>
   <td><form:input path="limit" data-validation="number" data-validation-optional="true"/></td>
   <td><form:label path="internal">Internal</form:label></td>
   <td><form:checkbox path="internal"/></td>
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
