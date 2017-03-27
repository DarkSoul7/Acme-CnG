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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<display:table name="applications" id="row" requestURI="${RequestURI}"
	pagesize="5">

	<spring:message code="application.customer" var="customer" />
	<display:column property="customer.fullName" title="${customer}" />

	<spring:message code="application.status" var="status" />
	<display:column title="${status}">
		<jstl:if test="${row.status == 'PENDING' }">
			<spring:message code="application.status.pending" var="pending" />
			<jstl:out value="${pending}"></jstl:out>
		</jstl:if>
		<jstl:if test="${row.status == 'ACCEPTED' }">
			<spring:message code="application.status.accepted" var="accepted" />
			<jstl:out value="${accepted}"></jstl:out>
		</jstl:if>
		<jstl:if test="${row.status == 'DENIED' }">
			<spring:message code="application.status.denied" var="denied" />
			<jstl:out value="${denied}"></jstl:out>
		</jstl:if>
	</display:column>

	<spring:message code="application.announcementType" var="announcementType" />
	<display:column title="${announcementType}">
		<jstl:if test="${row.announcementType == 'OFFER' }">
			<spring:message code="application.offer" var="offer" />
			<jstl:out value="${offer}"></jstl:out>
		</jstl:if>
		<jstl:if test="${row.announcementType == 'REQUEST' }">
			<spring:message code="application.request" var="request" />
			<jstl:out value="${request}"></jstl:out>
		</jstl:if>
	</display:column>

	<display:column>
		<jstl:if test="${row.status == 'PENDING' || row.status == 'DENIED'}">
			<acme:cancel url="application/accepted.do?id=${row.id}"
				code="application.accept" />
		</jstl:if>
	</display:column>
	
	<display:column>
		<jstl:if test="${row.status == 'PENDING' || row.status == 'ACCEPTED'}">
			<acme:cancel url="application/denied.do?id=${row.id}"
				code="application.denied" />
		</jstl:if>
	</display:column>

</display:table>
