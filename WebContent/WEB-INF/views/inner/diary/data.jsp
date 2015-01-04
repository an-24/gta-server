<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:forEach var="week" items="${data}">
	<tr> 
		<c:forEach var="data" items="${week}">
			<td><fmt:formatDate value="${data.day}" pattern="dd"/></td>
		</c:forEach>   			
	</tr>
</c:forEach>   			
