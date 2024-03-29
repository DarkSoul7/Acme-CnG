<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:choose>
	<jstl:when test="${cookie.language.value == 'en'}">
		<fmt:formatDate value="${mes.moment}" pattern="MM/dd/yyyy HH:mm" var="moment" />
	</jstl:when>
	<jstl:otherwise>
		<fmt:formatDate value="${mes.moment}" pattern="dd/MM/yyyy HH:mm" var="moment" />
	</jstl:otherwise>
</jstl:choose>

<h2><jstl:out value="${mes.title}"></jstl:out></h2>
<fieldset>

	<b><spring:message code="message.sender" />:</b>
	<jstl:out value="${mes.sender.fullName}"></jstl:out>
	<br/><br/>
	
	<b><spring:message code="message.receiver" />:</b>
	<jstl:out value="${mes.receiver.fullName}"></jstl:out>
	<br/><br/>
	
	<jstl:out value="${moment}"></jstl:out>
	<br/><br/>
	
	<jstl:if test="${mes.attachments != ''}">
		<b><spring:message code="message.attachments" /></b>
		<jstl:forEach items="${fn:split(mes.attachments, ',')}" var="attachment">
			<a href="${attachment}">${attachment}</a>
			<br/>
		</jstl:forEach>
	</jstl:if>
	
</fieldset>

<br/>
<textarea disabled="disabled" style="width:50vw; height:20vh; color:black; background-color:white;"><jstl:out value="${mes.text}"></jstl:out></textarea>
<br/>

<jstl:if test="${mes != null}">
	<jstl:if test="${mes.childMessage == null}">
		<input type="button" name="replyButton"
				value="<spring:message code="message.reply" />"
				onclick="javascript: window.location.replace('message/reply.do?messageId=${mes.id}')" />
	</jstl:if>
	
	<input type="button" name="forwardButton"
			value="<spring:message code="message.forward" />"
			onclick="javascript: window.location.replace('message/forward.do?messageId=${mes.id}')" />
	
	<jstl:if test="${mes.parentMessage != null}">
		<input type="button" name="parentMessageButton"
				value="<spring:message code="message.parentMessage" />"
				onclick="javascript: window.location.replace('message/showMessage.do?messageId=${mes.parentMessage.id}')" />
	</jstl:if>
	
	<jstl:if test="${mes.childMessage != null}">
		<input type="button" name="childMessageButton"
				value="<spring:message code="message.childMessage" />"
				onclick="javascript: window.location.replace('message/showMessage.do?messageId=${mes.childMessage.id}')" />
	</jstl:if>
</jstl:if>