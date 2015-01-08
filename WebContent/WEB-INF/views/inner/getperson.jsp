<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${loc}"/>
<fmt:bundle basename="biz.gelicon.gta.server.i18n.Bundle">

<script>
  $(function() {
    $("#post").multiselect({
    	   multiple: false,
    	   header: "<fmt:message key='label.select-post'/>",
    	   noneSelectedText: "-",
    	   style:"width:192px",
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
    	toastr["success"](data.message, "<fmt:message key='message.success'/>");
    	refreshProjectSheet(1);
    });
    
    $("#backbutton").val("<fmt:message key='label.back'/>");
    
  });
</script>

<style>
	#frmPerson {
		width:auto;
		white-space: normal;
		padding-left:0px;
		padding-right:0px;
	}
	
	#frmPerson table{
		table-layout: fixed;
	}
	
	#frmPerson table td input{
		width:100%;
	}
	
	#frmPerson table td input[type='submit'],
	#frmPerson table td input[type='checkbox']{
		width:auto;
	}
	

</style>

<form:form id="frmPerson">
   <form:input path="mode" type="hidden"/>
   <input name="teamId" type="hidden" value="${teamId}"/>
   <form:input path="id" type="hidden"/>
   <table>
   <colgroup>
   		<col/>
   		<col width="170px"/>
   		<col/>
   		<col width="150px"/>
   </colgroup>
   <tr>
   	<td><form:label path="user.name"><fmt:message key='label.username'/><em>*</em></form:label></td>
   	<td><form:input id="user-name" path="user.name"/>
   		<fmt:message var="tmp" key='message.selectuser'/>
   	    <form:input id="user-id" path="user.id" type="hidden" data-validation="key" data-validation-error-msg="${tmp}"/></td>
   	<td><form:label path="nic"><fmt:message key='label.nic'/><em>*</em></form:label></td>
   	<fmt:message var="tmp" key='message.nic'/>
   	<td><form:input path="nic" data-validation="required" data-validation-error-msg="${tmp}"/></td>
   </tr>
   <tr>
   	<td><form:label path="post"><fmt:message key='label.post'/></form:label></td>
   	<td>
   			<!--<form:input path="post"/>-->
   			<select id="post" name="post">
   			    <option value="-1">-empty-</option>
				<c:forEach var="p" items="${posts}"> 
   			    	<option value="${p.id}" ${command.post == p.id ? 'selected' : ''}><c:out value="${p.name}"/></option>
        		</c:forEach>   			
   			</select>
   	</td>
   	<td><form:label path="postName"><fmt:message key='label.postname'/><em>*</em></form:label></td>
   	<fmt:message var="tmp" key='message.postname'/>
   	<td><form:input path="postName" id="postName" data-validation="required" data-validation-error-msg="${tmp}"/></td>
   </tr>
   <tr>
   <td><form:label path="limit"><fmt:message key='label.limit'/></form:label></td>
   <td><form:input path="limit" data-validation="number" data-validation-optional="true"/></td>
   <td><form:label path="internal"><fmt:message key='label.internal'/></form:label></td>
   <td><form:checkbox path="internal"/></td>
   </tr>
   <tfoot>
   <tr>
   
   <c:if test="${manager}">
   	<td colspan="4">
   		<input id="default" type="submit" value="<fmt:message key='label.submit'/>"/>
   	</td>
   </c:if>
   
   </tr>
   </tfoot>
   </table>  
</form:form>

</fmt:bundle>