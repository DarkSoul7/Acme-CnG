package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Application;
import form.OfferForm;
import services.ApplicationService;

@Controller
@RequestMapping("/application")
public class ApplicationController extends AbstractController {

	@Autowired
	private ApplicationService applicationService;

	// Constructors -----------------------------------------------------------

	public ApplicationController() {
		super();
	}

	@RequestMapping(value = "/request", method = RequestMethod.GET)
	public ModelAndView register(@Valid int announcementId, @Valid String announcementType) {
		ModelAndView result;
		if ("OFFER".equals(announcementType.toUpperCase()) || "REQUEST".equals(announcementType.toUpperCase())) {
			Application application = applicationService.create(announcementId, announcementType);
			applicationService.save(application);
		}
		result = new ModelAndView("redirect:/offer/list.do");

		return result;
	}

}
