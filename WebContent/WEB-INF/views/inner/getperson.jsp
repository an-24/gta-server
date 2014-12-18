<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<form:form id="frmPerson" style="width:auto" method="POST">
   <table>
   <tr>
   	<td><form:label path="user">User name<em>*</em></form:label></td>
   	<td><form:input path="user"/></td>
   	<td><form:label path="nic">Nic name<em>*</em></form:label></td>
   	<td><form:input path="nic"/></td>
   </tr>
   <tr>
   	<td><form:label path="post">Post (from reference book)</form:label></td>
   	<td><form:input path="post"/></td>
   	<td><form:label path="postName">Post name<em>*</em></form:label></td>
   	<td><form:input path="postName"/></td>
   </tr>
   <tr>
   <td><form:label path="limit">Limit (in hours per week)</form:label></td>
   <td><form:input path="limit"/></td>
   </tr>
   <tfoot>
   <tr>
   
   <c:if test="${person.manager}">
   	<td colspan="4">
   		<input id="default" onclick="submitPerson();return false;" type="button" value="Submit"/>
   	</td>
   </c:if>
   
   </tr>
   </tfoot>
   </table>  
</form:form>
