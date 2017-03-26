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
import domain.Offer;
import form.OfferForm;
import security.Authority;
import services.ActorService;
import services.AnnouncementService;
import services.CustomerService;
import services.OfferService;

@Controller
@RequestMapping("/offer")
public class OfferController extends AbstractController {

	@Autowired
	private OfferService offerService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private AnnouncementService announcementService;

	// Constructors -----------------------------------------------------------

	public OfferController() {
		super();
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<OfferForm> offersForms = offerService.findOfferWithApplication();
		Actor actor = actorService.findByPrincipal();
		result = new ModelAndView("offer/list");
		
		Authority customerAuthority = new Authority();
		customerAuthority.setAuthority(Authority.CUSTOMER);
		int customerId = 0;
		if (actor.getUserAccount().getAuthorities().contains(customerAuthority)) {
			customerId = actor.getId();
		}
		
		result.addObject("customerId", customerId);
		result.addObject("offersForms", offersForms);
		result.addObject("RequestURI", "offer/list.do");

		return result;
	}
	
	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@Valid int idOffer) {
		ModelAndView result;
		Announcement announcement = announcementService.findOne(idOffer);
		announcementService.banAnnouncement(announcement);
		result = new ModelAndView("redirect:/offer/list.do");

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		OfferForm offerForm = offerService.create();
		result = createEditModelAndView(offerForm);

		return result;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid OfferForm offerForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Offer offer;

		offer = offerService.reconstruct(offerForm, binding);
		if (binding.hasErrors()) {
			result = createEditModelAndView(offerForm);

		} else {
			try {
				offerService.save(offer);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(offerForm, "offer.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(OfferForm offerForm) {
		ModelAndView result = createEditModelAndView(offerForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(OfferForm offerForm, String message) {
		ModelAndView result;

		result = new ModelAndView("offer/register");
		result.addObject("offerForm", offerForm);
		result.addObject("message", message);

		return result;
	}
}
