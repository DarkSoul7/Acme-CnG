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

<display:table name="actors" id="row" requestURI="${RequestURI}"
	pagesize="5">

	<spring:message code="actor.fullName" var="fullName" />
	<display:column property="fullName" title="${fullName}" />

	<spring:message code="actor.phone" var="phone" />
	<display:column property="phone" title="${phone}" />
	
	<spring:message code="actor.email" var="email" />
	<display:column property="email" title="${email}" />
	
	<display:column>
		<acme:cancel url="comment/showComments.do?id=${row.id}" code="actor.showComment"/>
	</display:column>
	
	<display:column>
		<jstl:if test="${row.id != actorId}">
			<acme:cancel url="comment/createCommentActor.do?actorId=${row.id}" code="actor.comment"/>
		</jstl:if>
	</display:column>
	


</display:table>
