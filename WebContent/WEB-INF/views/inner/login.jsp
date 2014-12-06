<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<form:form class="central" style="margin-left: -100px;margin-top:-78px;" method="POST" action="/action/login">
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
   <input type="submit" value="Submit"/>
   </td>
   </tr>
   </tfoot>
   </table>  
</form:form>
