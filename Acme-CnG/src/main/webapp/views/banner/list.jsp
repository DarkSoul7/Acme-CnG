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

<display:table name="banners" id="row" requestURI="${RequestURI}"
	pagesize="5">

	<spring:message code="banner.picture" var="picture" />
	<display:column title="${picture}">
		<img src="${row.picture}" style="width:250px; height:250px" alt="Banner" />
	</display:column>

	<spring:message code="banner.active" var="active" />
	<display:column title="${active}">
		<jstl:if test="${row.active == true}">
			<spring:message code="banner.trueActive" var="trueActive" />
			<jstl:out value="${trueActive}"/>
		</jstl:if>
		<jstl:if test="${row.active == false}">
			<spring:message code="banner.falseActive" var="falseActive" />
			<jstl:out value="${falseActive}"/>
		</jstl:if>
	</display:column>
	
	<display:column>
		<acme:cancel url="banner/edit.do?bannerId=${row.id}" code="banner.edit"/>
	</display:column>
	
	<display:column>
		<acme:cancel url="banner/delete.do?bannerId=${row.id}" code="banner.delete"/>
	</display:column>
	
</display:table>
