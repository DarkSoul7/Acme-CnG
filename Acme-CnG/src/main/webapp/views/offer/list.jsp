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

<security:authorize access="hasRole('CUSTOMER')">
	<form action="offer/searchWord.do" method="post">

		<label> <spring:message code="offer.keyWord" />
		</label> <input type="text" name="word" /> <input type="submit"
			name="searchWord" value="<spring:message code="keyWord.filter"/>" />

	</form>
</security:authorize>

<display:table name="offersForms" id="row" requestURI="${RequestURI}"
	pagesize="5">

	<spring:message code="offer.title" var="title" />
	<display:column property="title" title="${title}" />

	<spring:message code="offer.description" var="description" />
	<display:column property="description" title="${description}" />

	<spring:message code="offer.moment" var="moment" />
	<display:column property="moment" title="${moment}" />

	<spring:message code="offer.originPlace.address"
		var="originPlaceAddress" />
	<display:column property="originPlace.address"
		title="${originPlaceAddress}" />

	<spring:message code="offer.originPlace.gpsCoordinates.latitude"
		var="originPlaceGPSLatitude" />
	<display:column property="originPlace.gpsCoordinates.latitude"
		title="${originPlaceGPSLatitude}" />

	<spring:message code="offer.originPlace.gpsCoordinates.longitude"
		var="originPlaceGPSLongitude" />
	<display:column property="originPlace.gpsCoordinates.latitude"
		title="${originPlaceGPSLongitude}" />

	<spring:message code="offer.destinationPlace.address"
		var="destinationPlaceAddress" />
	<display:column property="destinationPlace.address"
		title="${destinationPlaceAddress}" />

	<spring:message code="offer.destinationPlace.gpsCoordinates.latitude"
		var="destinationPlaceGPSLatitude" />
	<display:column property="destinationPlace.gpsCoordinates.latitude"
		title="${destinationPlaceGPSLatitude}" />

	<spring:message code="offer.destinationPlace.gpsCoordinates.longitude"
		var="destinationPlaceGPSLatitude" />
	<display:column property="destinationPlace.gpsCoordinates.longitude"
		title="${destinationPlaceGPSLatitude}" />

	<display:column>
		<acme:cancel url="comment/showComments.do?id=${row.id}"
			code="actor.showComment" />
	</display:column>

	<spring:message code="offer.status" var="status" />
	<display:column title="${status}">
		<jstl:if test="${!row.banned}">
			<spring:message code="offer.banned.false" var="no" />
			<jstl:out value="${no}"></jstl:out>
		</jstl:if>

		<jstl:if test="${row.banned}">
			<spring:message code="offer.banned.true" var="yes" />
			<jstl:out value="${yes}"></jstl:out>
		</jstl:if>

	</display:column>

	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<acme:cancel url="comment/createCommentOffer.do?offerId=${row.id}"
				code="actor.comment" />
		</display:column>

		<display:column>
			<jstl:if test="${!row.containsApplication}">
				<acme:cancel
					url="application/request.do?announcementId=${row.id}&announcementType=OFFER"
					code="offer.request" />
			</jstl:if>
		</display:column>

		<display:column>
			<jstl:if test="${row.customer.id == customerId}">
				<acme:cancel
					url="application/listByAnnouncement.do?announcementId=${row.id}&announcementType=OFFER"
					code="offer.application" />
			</jstl:if>
		</display:column>

	</security:authorize>

	<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column>
			<jstl:if test="${!row.banned}">
				<acme:cancel url="offer/ban.do?idOffer=${row.id}"
					code="offer.banned" />
			</jstl:if>
		</display:column>
	</security:authorize>

</display:table>
