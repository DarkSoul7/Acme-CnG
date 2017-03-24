/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;
import domain.Banner;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	//Used services

	@Autowired
	private BannerService	bannerService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "John Doe") final String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		final Banner banner = this.bannerService.getActiveBanner();

		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("moment", moment);
		result.addObject("banner", banner.getPicture());

		return result;
	}

	@RequestMapping(value = "/cookies")
	public ModelAndView cookies() {

		ModelAndView result;

		result = new ModelAndView("welcome/cookies");
		result.addObject("backURI", "/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/conditions")
	public ModelAndView conditions() {
		ModelAndView result;

		result = new ModelAndView("welcome/conditions");
		result.addObject("backURI", "/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/eraseMe")
	public ModelAndView eraseMe() {
		ModelAndView result;

		result = new ModelAndView("welcome/eraseMe");
		result.addObject("backURI", "/welcome/index.do");

		return result;
	}
}
