
package controllers;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.AnnouncementService;
import services.RequestService;
import domain.Actor;
import domain.Announcement;
import domain.Request;
import form.RequestForm;

@Controller
@RequestMapping("/request")
public class RequestController extends AbstractController {

	@Autowired
	private RequestService		requestService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private AnnouncementService	announcementService;


	// Constructors -----------------------------------------------------------

	public RequestController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<RequestForm> requestForms = this.requestService.findRequestWithApplication();
		final Actor actor = this.actorService.findByPrincipal();
		result = new ModelAndView("request/list");

		final Authority customerAuthority = new Authority();
		customerAuthority.setAuthority(Authority.CUSTOMER);
		int customerId = 0;
		if (actor.getUserAccount().getAuthorities().contains(customerAuthority))
			customerId = actor.getId();

		result.addObject("customerId", customerId);
		result.addObject("requestForms", requestForms);
		result.addObject("RequestURI", "request/list.do");

		return result;
	}

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@Valid final int idRequest) {
		ModelAndView result;
		final Announcement announcement = this.announcementService.findOne(idRequest);
		this.announcementService.banAnnouncement(announcement);
		result = new ModelAndView("redirect:/request/list.do");

		return result;
	}

	// Search by key word
	@RequestMapping(value = "/searchWord", method = RequestMethod.POST)
	public ModelAndView searchByKeyWord(@Valid final String word) {

		ModelAndView result;
		final Collection<RequestForm> requestForms = this.requestService.findRequestKeyWord(word);
		final Actor actor = this.actorService.findByPrincipal();
		result = new ModelAndView("request/list");

		final Authority customerAuthority = new Authority();
		customerAuthority.setAuthority(Authority.CUSTOMER);
		int customerId = 0;
		if (actor.getUserAccount().getAuthorities().contains(customerAuthority))
			customerId = actor.getId();

		result.addObject("customerId", customerId);
		result.addObject("requestForms", requestForms);
		result.addObject("RequestURI", "request/list.do");

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		final RequestForm requestForm = this.requestService.create();
		result = this.createEditModelAndView(requestForm);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final RequestForm requestForm, final BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Request request;

		request = this.requestService.reconstruct(requestForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(requestForm);
		else
			try {
				if (request.getMoment().before(new Date())) {
					requestForm.setMoment(null);
					throw new IllegalArgumentException("wrong moment");
				}

				this.requestService.save(request);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				if (requestForm.getMoment() == null)
					result = this.createEditModelAndView(requestForm, "request.commit.errorMoment");
				else
					result = this.createEditModelAndView(requestForm, "request.commit.error");
			}

		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final RequestForm requestForm) {
		final ModelAndView result = this.createEditModelAndView(requestForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final RequestForm requestForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("request/register");
		result.addObject("requestForm", requestForm);
		result.addObject("message", message);

		return result;
	}
}
