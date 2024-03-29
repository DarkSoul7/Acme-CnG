<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="scripts/functions.js"></script>

<div>
	<a href="/Acme-CnG"><img src="images/logo.png" alt="Acme-CnG., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a href="banner/list.do"><spring:message code="master.page.list.banner" /></a></li>
			<li><a href="offer/list.do"><spring:message code="master.page.customer.announcement.offer.list" /></a></li>
			<li><a href="request/list.do"><spring:message code="master.page.customer.announcement.request.list" /></a></li>
			<li><a class="fNiv"><spring:message code="master.page.administrator.dashboard" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/dashboardC.do"><spring:message code="master.page.administrator.dashboardC" /></a></li>
					<li><a href="administrator/dashboardB.do"><spring:message code="master.page.administrator.dashboardB" /></a></li>
					<li><a href="administrator/dashboardA.do"><spring:message code="master.page.administrator.dashboardA" /></a></li>
				</ul>
			</li>
		</security:authorize>

		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message code="master.page.customer.announcement.offer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="offer/register.do"><spring:message code="master.page.customer.announcement.offer.create" /></a></li>
					<li><a href="offer/list.do"><spring:message code="master.page.customer.announcement.offer.list" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.customer.announcement.request" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="request/register.do"><spring:message code="master.page.customer.announcement.request.create" /></a></li>
					<li><a href="request/list.do"><spring:message code="master.page.customer.announcement.request.list" /></a></li>
				</ul>
			</li>
		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/register.do"><spring:message code="master.page.customer.register" /></a></li>
				</ul>
			</li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="actor/list.do"><spring:message code="master.page.list.actor" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.messagingSystem" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="message/send.do"><spring:message code="master.page.messagingSystem.send" /></a></li>
					<li><a href="message/sentMessages.do"><spring:message code="master.page.messagingSystem.sentMessages" /></a></li>
					<li><a href="message/receivedMessages.do"><spring:message code="master.page.messagingSystem.receivedMessages" /></a></li>
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message code="master.page.profile" />(<security:authentication property="principal.username" />)</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

<jstl:if
	test="${cookie.language.value == null || (cookie.language.value != 'en' && cookie.language.value != 'es')}">
	<script type="text/javascript">
		window.location.replace('welcome/index.do?language=en');
	</script>
</jstl:if>