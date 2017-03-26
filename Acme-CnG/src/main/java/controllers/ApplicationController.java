package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Application;
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

	@RequestMapping(value = "/listByAnnouncement", method = RequestMethod.GET)
	public ModelAndView listByAnnouncement(@Valid int announcementId, @Valid String announcementType) {
		ModelAndView result;
		if ("OFFER".equals(announcementType.toUpperCase()) || "REQUEST".equals(announcementType.toUpperCase())) {
			Collection<Application> applications = applicationService.findByAnnouncement(announcementId,
					announcementType);
			result = new ModelAndView("application/list");
			result.addObject("applications", applications);
		} else {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	@RequestMapping(value = "/accepted", method = RequestMethod.GET)
	public ModelAndView accepted(@Valid int id) {
		ModelAndView result;
		Application application = applicationService.findOne(id);
		applicationService.acceptApplication(application);
		result = new ModelAndView("redirect:/application/listByAnnouncement.do?announcementId="+application.getAnnouncementId()
		+"&announcementType="+application.getAnnouncementType());

		return result;
	}
	
	@RequestMapping(value = "/denied", method = RequestMethod.GET)
	public ModelAndView denied(@Valid int id) {
		ModelAndView result;
		Application application = applicationService.findOne(id);
		applicationService.denyApplication(application);
		result = new ModelAndView("redirect:/application/listByAnnouncement.do?announcementId="+application.getAnnouncementId()
		+"&announcementType="+application.getAnnouncementType());
		return result;
	}

}
