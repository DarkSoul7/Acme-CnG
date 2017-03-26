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

<fieldset>
	<legend>
		<b><spring:message
				code="dashboard.sentMessagesStatistics" /></b>
	</legend>
	<spring:message code="dashboard.minimum" />
	<jstl:out value="${getMinimumNumberOfSentMessagesPerActor}"></jstl:out>
	<br/>
	<spring:message code="dashboard.avg" />
	<jstl:out value="${getAverageNumberOfSentMessagesPerActor}"></jstl:out>
	<br/>
	<spring:message code="dashboard.maximum" />
	<jstl:out value="${getMaximumNumberOfSentMessagesPerActor}"></jstl:out>
</fieldset>
<br/>
<fieldset>
	<legend>
		<b><spring:message
				code="dashboard.receivedMessagesStatistics" /></b>
	</legend>
	<spring:message code="dashboard.minimum" />
	<jstl:out value="${getMinimumNumberOfReceivedMessagesPerActor}"></jstl:out>
	<br/>
	<spring:message code="dashboard.avg" />
	<jstl:out value="${getAverageNumberOfReceivedMessagesPerActor}"></jstl:out>
	<br/>
	<spring:message code="dashboard.maximum" />
	<jstl:out value="${getMaximumNumberOfReceivedMessagesPerActor}"></jstl:out>
</fieldset>
<br/>
<fieldset>
	<legend>
		<b><spring:message
				code="dashboard.actorsWithMoreMessagesSent" /></b>
	</legend>
	
	<display:table name="getActorsWhoHaveSentMoreMessages" id="row" requestURI="${RequestURI}"
	pagesize="5">

	<spring:message code="customer.username" var="username" />
	<display:column property="userAccount.username" title="${username}" />

	<spring:message code="customer.fullName" var="fullName" />
	<display:column property="fullName" title="${fullName}" />

	<spring:message code="customer.phone" var="phone" />
	<display:column property="phone" title="${phone}" />
	
	<spring:message code="customer.email" var="email" />
	<display:column property="email" title="${email}" />
	</display:table>
</fieldset>
<br/>
<fieldset>
	<legend>
		<b><spring:message
				code="dashboard.actorsWithMoreMessagesReceived" /></b>
	</legend>
	
	<display:table name="getActorsWhoHaveReceivedMoreMessages" id="row" requestURI="${RequestURI}"
	pagesize="5">

	<spring:message code="customer.username" var="username" />
	<display:column property="userAccount.username" title="${username}" />

	<spring:message code="customer.fullName" var="fullName" />
	<display:column property="fullName" title="${fullName}" />

	<spring:message code="customer.phone" var="phone" />
	<display:column property="phone" title="${phone}" />
	
	<spring:message code="customer.email" var="email" />
	<display:column property="email" title="${email}" />
	</display:table>
</fieldset>