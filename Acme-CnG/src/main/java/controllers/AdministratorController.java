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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CustomerService;
import services.OfferService;
import services.RequestService;

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


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	//Dashboard C

	@RequestMapping(value="/dashboardC", method=RequestMethod.GET)
	public ModelAndView dashboardC(){
		final ModelAndView result;
		
		final double offerAvg = this.offerService.getOfferAvg();
		final double requestAvg = this.requestService.requestAvg();
		
		final double offersAvgPerCustomer = this.offerService.avgOfferPerCustomer();
		final double requestAvgPerCustomer = this.requestService.avgRequestPerCustomer();
		
		final double applicationAvg = this.applicationService.avgApplicationsPerAnnouncement();
		
		final double moreApplicationCustomer = this.customerService.
	}
}
