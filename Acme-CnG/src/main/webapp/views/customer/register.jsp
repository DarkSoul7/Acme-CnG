<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestURI}" modelAttribute="customerForm">
	
	<fieldset >
		<legend><spring:message code="customer.userAccountDetails"/></legend>
			<br/>
		<acme:textbox code="customer.username" path="username" />
			<br/>
		<acme:password code="customer.password" path="password" />
			<br/>
		<acme:password code="customer.repeatPassword" path="repeatPassword" />
	</fieldset>
	<br/>
	<fieldset >
		<legend><spring:message code="customer.personalDataDetails"/></legend>
			<br/>
		<acme:textbox code="customer.fullName" path="fullName" />
			<br/>
		<acme:textbox code="customer.phone" path="phone" />
			<br/>
		<acme:textbox code="customer.email" path="email" />
			<br/>
		<acme:checkbox code="customer.acceptCondition" path="acceptCondition"/>
		
	</fieldset>
	<br/>
		
		<acme:submit code="customer.save" name="save"/>
		
		<acme:cancel code="customer.cancel" url="welcome/index.do"/>
		
		
</form:form>
