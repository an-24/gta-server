<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${loc}" />
<fmt:bundle basename="biz.gelicon.gta.server.i18n.Bundle">


<form:form id="frmlogin" class="central ui-widget" style="margin-left: -200px;width:400px;top:260px;position:absolute;">
   <table>
   <tr>
   	<th colspan="2"><h2><fmt:message key='label.login'/></h2></th>
   </tr>
   <tr><td colspan="2"><div style="text-align: center" id="error-place" class="form-error"></div></td></tr>
   <tr>
   <td><form:label path="name"><fmt:message key='label.username'/></form:label></td>
   	<fmt:message var="tmp" key='message.username'/>
   <td><form:input path="name" data-validation="required" data-validation-error-msg="${tmp}"/></td>
   </tr>
   <tr>
   <td><form:label path="password"><fmt:message key='label.youpassword'/></form:label></td>
   <td><form:password path="password"/></td>
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

<script type="text/javascript">
	
	$(function() {
		initForm($('#frmlogin'),"action/login",function(data){
	    	$.cookie("token", data.token);
	    	$.cookie("userId", data.userId);
	    	$.cookie("userName", data.userName);
	    });
	});

</script>

</fmt:bundle>