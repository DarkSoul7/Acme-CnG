<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<display:table name="messages" id="row" requestURI="${requestURI}" pagesize="5">

	<spring:message code="message.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="message.moment" var="description" />
	<display:column title="${description}" >
		<jstl:choose>
			<jstl:when test="${cookie.language.value == 'en'}">
				<fmt:formatDate value="${row.moment}" pattern="MM/dd/yyyy HH:mm" />
			</jstl:when>
			<jstl:otherwise>
				<fmt:formatDate value="${row.moment}" pattern="dd/MM/yyyy HH:mm" />
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<spring:message code="message.sender" var="sender" />
	<display:column title="${sender}">
		<jstl:out value="${row.sender.fullName} (${row.sender.userAccount.username})" />
	</display:column>
	
	<spring:message code="message.receiver" var="receiver" />
	<display:column title="${receiver}">
		<jstl:out value="${row.receiver.fullName} (${row.receiver.userAccount.username})" />
	</display:column>
	
	<display:column>
		<input type="button" name="message.showDetailsButton"
				value="<spring:message code="message.showDetails" />"
				onclick="javascript: window.location.replace('message/showMessage.do?messageId=${row.id}')" />
	</display:column>
	
	<display:column>
		<jstl:if test="${row.childMessage == null}">
			<input type="button" name="message.deleteButton"
					value="<spring:message code="message.delete" />"
					onclick="javascript: confirmDeletion('${cookie.language.value}', 'message/delete.do?messageId=${row.id}&url=${requestURI}')" />
		</jstl:if>
	</display:column>
	
</display:table>