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

<form:form action="${requestURI}" modelAttribute="messageForm">
	<form:hidden path="parentMessageId"/>
	
	<acme:textbox code="message.title" path="title" />
	<br/>
	<acme:textarea code="message.text" path="text" />
	<br/>
	<acme:textarea code="message.attachments" path="attachments" />
	
	<spring:message code="message.attachmentsHelp" var="attachmentsHelp" />
	<jstl:out value="(${attachmentsHelp})"></jstl:out>
	<br/><br/>
	<acme:select items="${actors}" itemLabel="fullName" code="message.receiver" path="receiver" />
		
	<br/>
	
	<jstl:if test="${true}">
		<acme:submit code="message.send" name="save"/>
	
		<acme:cancel code="message.cancel" url="welcome/index.do"/>
	</jstl:if>
		
</form:form>
