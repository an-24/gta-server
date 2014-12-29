<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<c:if test="${user.mode==1}"><h2>New user</h2></c:if>
<c:if test="${user.mode==2}"><h2>Edited by '${user.name}'</h2></c:if>

