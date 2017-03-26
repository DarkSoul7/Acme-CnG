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

<display:table name="requests" id="row" requestURI="${RequestURI}"
	pagesize="5">

	<spring:message code="request.title" var="title" />
	<display:column property="title" title="${title}" />

	<spring:message code="request.description" var="description" />
	<display:column property="description" title="${description}" />
	
	<spring:message code="request.moment" var="moment" />
	<display:column property="moment" title="${moment}" />
	
	<spring:message code="request.originPlace.address" var="originPlaceAddress" />
	<display:column property="originPlace.address" title="${originPlaceAddress}" />
	
	<spring:message code="request.originPlace.gpsCoordinates.latitude" var="originPlaceGPSLatitude" />
	<display:column property="originPlace.gpsCoordinates.latitude" title="${originPlaceGPSLatitude}" />
	
	<spring:message code="request.originPlace.gpsCoordinates.longitude" var="originPlaceGPSLongitude" />
	<display:column property="originPlace.gpsCoordinates.latitude" title="${originPlaceGPSLongitude}" />
	
	<spring:message code="request.destinationPlace.address" var="destinationPlaceAddress" />
	<display:column property="destinationPlace.address" title="${destinationPlaceAddress}" />
	
	<spring:message code="request.destinationPlace.gpsCoordinates.latitude" var="destinationPlaceGPSLatitude" />
	<display:column property="destinationPlace.gpsCoordinates.latitude" title="${destinationPlaceGPSLatitude}" />
	
	<spring:message code="request.destinationPlace.gpsCoordinates.longitude" var="destinationPlaceGPSLatitude" />
	<display:column property="destinationPlace.gpsCoordinates.longitude" title="${destinationPlaceGPSLatitude}" />

	<display:column>
		<acme:cancel url="comment/showComments.do?id=${row.id}" code="actor.showComment"/>
	</display:column>
	
	<display:column>
		<acme:cancel url="comment/createCommentRequest.do?requestId=${row.id}" code="actor.comment"/>
	</display:column>
	
</display:table>
