<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<display:table name="comments" id="row" requestURI="${RequestURI}"
	pagesize="5">

	<spring:message code="comment.title" var="title" />
	<display:column property="title" title="${title}" />

	<spring:message code="comment.text" var="text" />
	<display:column property="text" title="${text}" />
	
	<spring:message code="comment.stars" var="stars" />
	<display:column property="stars" title="${stars}" />
	
	<spring:message code="comment.status" var="nameColumn" />
	<display:column title="${nameColumn}">
		<jstl:if test="${!row.banned}">
			<spring:message code="comment.no" var="ban" />
			<jstl:out value="${ban}"></jstl:out>
		</jstl:if>
		<jstl:if test="${row.banned}">
			<spring:message code="comment.yes" var="ban" />
			<jstl:out value="${ban}"></jstl:out>
		</jstl:if>
	</display:column>
	<display:column>
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<jstl:if test="${!row.banned}">
				<acme:cancel url="comment/ban.do?commentId=${row.id}" code="comment.ban"/>
			</jstl:if>
		</security:authorize>
	</display:column>
</display:table>
