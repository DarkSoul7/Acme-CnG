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

<form:form action="${requestURI}" modelAttribute="requestForm">

	<form:hidden path="banned"/>

	<fieldset>
		<acme:textbox code="request.title" path="title" />
		<br />
		<acme:textbox code="request.description" path="description" />
		<br />
		<acme:textbox code="request.moment" path="moment" />
		<p><spring:message code="request.formateDate"/><p/>
		<br />
		<fieldset>
			<legend><spring:message code="request.originPlace"/></legend>
			<acme:textbox code="request.originPlace.address"
				path="originPlace.address" />
			<br />
			<acme:textbox code="request.originPlace.gpsCoordinates.latitude"
				path="originPlace.gpsCoordinates.latitude" />
			<br />
			<acme:textbox code="request.originPlace.gpsCoordinates.longitude"
				path="originPlace.gpsCoordinates.longitude" />
			<br />
		</fieldset>
		<br />
		<fieldset>
			<legend><spring:message code="request.destinationPlace"/></legend>
			<acme:textbox code="request.destinationPlace.address" path="destinationPlace.address" />
			<br />
			<acme:textbox code="request.destinationPlace.gpsCoordinates.latitude" path="destinationPlace.gpsCoordinates.latitude" />
			<br />
			<acme:textbox code="request.destinationPlace.gpsCoordinates.longitude" path="destinationPlace.gpsCoordinates.longitude" />
			<br />
		</fieldset>
		<br />
	</fieldset>
	<br />

	<acme:submit code="request.save" name="save" />

	<acme:cancel code="request.cancel" url="welcome/index.do" />
</form:form>
