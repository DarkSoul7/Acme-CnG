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
				code="dashboard.commentsPerCommentable" /></b>
	</legend>
	<spring:message code="dashboard.commentsPerActor" />
	<jstl:out value="${commentsPerActor}"></jstl:out>
	<br/>
	<spring:message code="dashboard.commentsPerOffer" />
	<jstl:out value="${commentsPerOffer}"></jstl:out>
	<br/>
	<spring:message code="dashboard.commentsPerRequest" />
	<jstl:out value="${commentsPerRequest}"></jstl:out>
</fieldset>
<br/>
<fieldset>
	<legend>
		<b><spring:message
				code="dashboard.avgCommentsPerActor" /></b>
	</legend>
	<spring:message code="dashboard.avg" />
	<jstl:out value="${avgCommentsPerActor}"></jstl:out>
</fieldset>
<br/>
<fieldset>
	<legend>
		<b><spring:message
				code="dashboard.actorsWhoHavePostThe10PercentMessages" /></b>
	</legend>
	
	<display:table name="actorsWhoHavePostThe10PercentMessages" id="row" requestURI="${RequestURI}"
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
