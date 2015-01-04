<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
        source: "inner/person/getusers",
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
    initForm($("#frmPerson"),"inner/person/update",function(data){
    	toastr["success"](data.message, "Success");
    	refreshProjectSheet(1);
    });
    
  });
</script>

<form:form id="frmPerson" style="width:auto">
   <form:input path="mode" type="hidden"/>
   <input name="teamId" type="hidden" value="${teamId}"/>
   <form:input path="id" type="hidden"/>
   <table>
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
   			    	<option value="${p.id}" ${command.post == p.id ? 'selected' : ''}><c:out value="${p.name}"/></option>
        		</c:forEach>   			
   			</select>
   	</td>
   	<td><form:label path="postName">Post name<em>*</em></form:label></td>
   	<td><form:input path="postName" id="postName" data-validation="required" data-validation-error-msg="You must enter the post name"/></td>
   </tr>
   <tr>
   <td><form:label path="limit">Limit</form:label></td>
   <td><form:input path="limit" data-validation="number" data-validation-optional="true"/></td>
   </tr>
   <tfoot>
   <tr>
   
   <c:if test="${manager}">
   	<td colspan="4">
   		<input id="default" type="submit" value="Submit"/>
   	</td>
   </c:if>
   
   </tr>
   </tfoot>
   </table>  
</form:form>
