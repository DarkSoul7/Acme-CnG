package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Announcement;
import domain.Request;
import form.RequestForm;
import security.Authority;
import services.ActorService;
import services.AnnouncementService;
import services.RequestService;

@Controller
@RequestMapping("/request")
public class RequestController extends AbstractController{

	@Autowired
	private RequestService requestService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private AnnouncementService announcementService;

	// Constructors -----------------------------------------------------------

	public RequestController() {
		super();
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<RequestForm> requestForms = requestService.findRequestWithApplication();
		Actor actor = actorService.findByPrincipal();
		result = new ModelAndView("request/list");
		
		Authority customerAuthority = new Authority();
		customerAuthority.setAuthority(Authority.CUSTOMER);
		int customerId = 0;
		if (actor.getUserAccount().getAuthorities().contains(customerAuthority)) {
			customerId = actor.getId();
		}
		
		result.addObject("customerId", customerId);
		result.addObject("requestForms", requestForms);
		result.addObject("RequestURI", "request/list.do");

		return result;
	}
	
	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@Valid int idRequest) {
		ModelAndView result;
		Announcement announcement = announcementService.findOne(idRequest);
		announcementService.banAnnouncement(announcement);
		result = new ModelAndView("redirect:/request/list.do");

		return result;
	}
	

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		RequestForm requestForm = requestService.create();
		result = createEditModelAndView(requestForm);

		return result;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid RequestForm requestForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Request request;

		request = requestService.reconstruct(requestForm, binding);
		if (binding.hasErrors()) {
			result = createEditModelAndView(requestForm);

		} else {
			try {
				requestService.save(request);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(requestForm, "request.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(RequestForm requestForm) {
		ModelAndView result = createEditModelAndView(requestForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(RequestForm requestForm, String message) {
		ModelAndView result;

		result = new ModelAndView("request/register");
		result.addObject("requestForm", requestForm);
		result.addObject("message", message);

		return result;
	}
}
