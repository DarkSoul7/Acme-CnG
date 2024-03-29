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

<form:form action="${requestURI}" modelAttribute="offerForm">

	<form:hidden path="banned"/>

	<fieldset>
		<acme:textbox code="offer.title" path="title" />
		<br />
		<acme:textbox code="offer.description" path="description" />
		<br />
		<acme:textbox code="offer.moment" path="moment" />
		<p><spring:message code="offer.formateDate"/><p/>
		<br />
		<fieldset>
			<legend><spring:message code="offer.originPlace"/></legend>
			<acme:textbox code="offer.originPlace.address"
				path="originPlace.address" />
			<br />
			<acme:textbox code="offer.originPlace.gpsCoordinates.latitude"
				path="originPlace.gpsCoordinates.latitude" />
			<br />
			<acme:textbox code="offer.originPlace.gpsCoordinates.longitude"
				path="originPlace.gpsCoordinates.longitude" />
			<br />
		</fieldset>
		<br />
		<fieldset>
			<legend><spring:message code="offer.destinationPlace"/></legend>
			<acme:textbox code="offer.destinationPlace.address" path="destinationPlace.address" />
			<br />
			<acme:textbox code="offer.destinationPlace.gpsCoordinates.latitude" path="destinationPlace.gpsCoordinates.latitude" />
			<br />
			<acme:textbox code="offer.destinationPlace.gpsCoordinates.longitude" path="destinationPlace.gpsCoordinates.longitude" />
			<br />
		</fieldset>
		<br />
	</fieldset>
	<br />

	<acme:submit code="offer.save" name="save" />

	<acme:cancel code="offer.cancel" url="welcome/index.do" />
</form:form>
