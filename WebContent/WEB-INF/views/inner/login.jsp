<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<form:form id="frmlogin" class="central" style="margin-left: -150px;margin-top:-78px;" method="POST">
   <table>
   <tr>
   	<th colspan="2"><h2>Log in</h2></th>
   </tr>
   <tr>
   <td><form:label path="name">Name</form:label></td>
   <td><form:input path="name"/></td>
   </tr>
   <tr>
   <td><form:label path="password">Password</form:label></td>
   <td><form:password path="password"/></td>
   </tr>
   <tfoot>
   <tr>
   <td colspan="2">
   <input id="default" onclick="submitLogin();return false;" type="button" value="Submit"/>
   </td>
   </tr>
   </tfoot>
   </table>  
</form:form>

<script type="text/javascript">
	var form = $('#frmlogin');
	initForm(form);
    function submitLogin() {
        submitFunc("action/login","../web/",form,function(data){
        	$.cookie("token", data);
        	//alert($.cookie("user"));
        });
    };
</script>