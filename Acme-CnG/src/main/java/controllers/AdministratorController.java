/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CommentService;
import services.CustomerService;
import services.MessageService;
import services.OfferService;
import services.RequestService;
import domain.Actor;
import domain.Customer;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	//Required services

	@Autowired
	private OfferService		offerService;

	@Autowired
	private RequestService		requestService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private CommentService		comentService;

	@Autowired
	private MessageService		messageService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	//Dashboard C

	@RequestMapping(value = "/dashboardC", method = RequestMethod.GET)
	public ModelAndView dashboardC() {
		final ModelAndView result;

		final double offerAvg = this.offerService.getOfferAvg();
		final double requestAvg = this.requestService.requestAvg();

		final double offersAvgPerCustomer = this.offerService.avgOfferPerCustomer();
		final double requestAvgPerCustomer = this.requestService.avgRequestPerCustomer();

		final double applicationAvg = this.applicationService.avgApplicationsPerAnnouncement();

		final Collection<Customer> moreApplicationAcceptedCustomer = this.customerService.getCustomerWithMoreAcceptedApplications();

		final Collection<Customer> moreApplicationDeniedCustomer = this.customerService.getCustomerWithMoreDeniedApplications();

		result = new ModelAndView("administrator/dashboardC");
		result.addObject("offerAvg", offerAvg);
		result.addObject("requestAvg", requestAvg);
		result.addObject("offersAvgPerCustomer", offersAvgPerCustomer);
		result.addObject("requestAvgPerCustomer", requestAvgPerCustomer);
		result.addObject("applicationAvg", applicationAvg);
		result.addObject("moreApplicationAcceptedCustomer", moreApplicationAcceptedCustomer);
		result.addObject("moreApplicationDeniedCustomer", moreApplicationDeniedCustomer);
		result.addObject("RequestURI", "administrator/dashboardC.do");

		return result;
	}

	@RequestMapping(value = "/dashboardB", method = RequestMethod.GET)
	public ModelAndView dashboardB() {
		ModelAndView result;

		final double commentsPerActor = this.comentService.commentsPerActor();
		final double commentsPerOffer = this.comentService.commentsPerOffer();
		final double commentsPerRequest = this.comentService.commentsPerRequest();

		final double avgCommentsPerActor = this.comentService.avgCommentsPerActor();

		final Collection<Actor> actorsWhoHavePostThe10PercentMessages = this.comentService.actorsWhoHavePostThe10PercentMessages();

		result = new ModelAndView("administrator/dashboardB");

		result.addObject("commentsPerActor", commentsPerActor);
		result.addObject("commentsPerOffer", commentsPerOffer);
		result.addObject("commentsPerRequest", commentsPerRequest);
		result.addObject("avgCommentsPerActor", avgCommentsPerActor);
		result.addObject("actorsWhoHavePostThe10PercentMessages", actorsWhoHavePostThe10PercentMessages);

		result.addObject("RequestURI", "administrator/dashboardB.do");

		return result;
	}

	@RequestMapping(value = "/dashboardA", method = RequestMethod.GET)
	public ModelAndView dashboardA() {
		ModelAndView result;

		final int getMinimumNumberOfSentMessagesPerActor = this.messageService.getMinimumNumberOfSentMessagesPerActor();
		final int getMaximumNumberOfSentMessagesPerActor = this.messageService.getMaximumNumberOfSentMessagesPerActor();
		final double getAverageNumberOfSentMessagesPerActor = this.messageService.getAverageNumberOfSentMessagesPerActor();

		final int getMinimumNumberOfReceivedMessagesPerActor = this.messageService.getMinimumNumberOfReceivedMessagesPerActor();
		final int getMaximumNumberOfReceivedMessagesPerActor = this.messageService.getMaximumNumberOfReceivedMessagesPerActor();
		final double getAverageNumberOfReceivedMessagesPerActor = this.messageService.getAverageNumberOfReceivedMessagesPerActor();

		final Collection<Actor> getActorsWhoHaveSentMoreMessages = this.messageService.getActorsWhoHaveSentMoreMessages();

		final Collection<Actor> getActorsWhoHaveReceivedMoreMessages = this.messageService.getActorsWhoHaveReceivedMoreMessages();

		result = new ModelAndView("administrator/dashboardA");
		result.addObject("getMinimumNumberOfSentMessagesPerActor", getMinimumNumberOfSentMessagesPerActor);
		result.addObject("getMaximumNumberOfSentMessagesPerActor", getMaximumNumberOfSentMessagesPerActor);
		result.addObject("getAverageNumberOfSentMessagesPerActor", getAverageNumberOfSentMessagesPerActor);
		result.addObject("getMinimumNumberOfReceivedMessagesPerActor", getMinimumNumberOfReceivedMessagesPerActor);
		result.addObject("getMaximumNumberOfReceivedMessagesPerActor", getMaximumNumberOfReceivedMessagesPerActor);
		result.addObject("getAverageNumberOfReceivedMessagesPerActor", getAverageNumberOfReceivedMessagesPerActor);
		result.addObject("getActorsWhoHaveSentMoreMessages", getActorsWhoHaveSentMoreMessages);
		result.addObject("getActorsWhoHaveReceivedMoreMessages", getActorsWhoHaveReceivedMoreMessages);
		result.addObject("RequestURI", "administrator/dashboardA.do");

		return result;
	}
}
