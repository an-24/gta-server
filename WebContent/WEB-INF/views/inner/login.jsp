<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<form:form id="frmlogin" class="central" style="margin-left: -150px;top:260px;position:absolute;">
   <table>
   <tr>
   	<th colspan="2"><h2>Log in</h2></th>
   </tr>
   <tr><td colspan="2"><div style="text-align: center" id="error-place" class="form-error"></div></td></tr>
   <tr>
   <td><form:label path="name">Name</form:label></td>
   <td><form:input path="name" data-validation="required" data-validation-error-msg="You must enter the user name"/></td>
   </tr>
   <tr>
   <td><form:label path="password">Password</form:label></td>
   <td><form:password path="password"/></td>
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

<script type="text/javascript">
	
	$(function() {
		initForm($('#frmlogin'),"action/login",function(data){
	    	$.cookie("token", data.token);
	    	$.cookie("userId", data.userId);
	    	$.cookie("userName", data.userName);
	    });
	});

</script>