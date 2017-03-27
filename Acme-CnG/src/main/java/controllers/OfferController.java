
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
import services.CustomerService;
import services.OfferService;
import domain.Actor;
import domain.Announcement;
import domain.Offer;
import form.OfferForm;

@Controller
@RequestMapping("/offer")
public class OfferController extends AbstractController {

	@Autowired
	private OfferService		offerService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private AnnouncementService	announcementService;


	// Constructors -----------------------------------------------------------

	public OfferController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<OfferForm> offersForms = this.offerService.findOfferWithApplication();
		final Actor actor = this.actorService.findByPrincipal();
		result = new ModelAndView("offer/list");

		final Authority customerAuthority = new Authority();
		customerAuthority.setAuthority(Authority.CUSTOMER);
		int customerId = 0;
		if (actor.getUserAccount().getAuthorities().contains(customerAuthority))
			customerId = actor.getId();

		result.addObject("customerId", customerId);
		result.addObject("offersForms", offersForms);
		result.addObject("RequestURI", "offer/list.do");

		return result;
	}

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@Valid final int idOffer) {
		ModelAndView result;
		final Announcement announcement = this.announcementService.findOne(idOffer);
		this.announcementService.banAnnouncement(announcement);
		result = new ModelAndView("redirect:/offer/list.do");

		return result;
	}

	// Search by key word
	@RequestMapping(value = "/searchWord", method = RequestMethod.POST)
	public ModelAndView searchByKeyWord(@Valid final String word) {

		ModelAndView result;
		final Collection<OfferForm> offersForms = this.offerService.findOfferKeyWord(word);
		final Actor actor = this.actorService.findByPrincipal();
		result = new ModelAndView("offer/list");

		final Authority customerAuthority = new Authority();
		customerAuthority.setAuthority(Authority.CUSTOMER);
		int customerId = 0;
		if (actor.getUserAccount().getAuthorities().contains(customerAuthority))
			customerId = actor.getId();

		result.addObject("customerId", customerId);
		result.addObject("offersForms", offersForms);
		result.addObject("RequestURI", "offer/list.do");

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		final OfferForm offerForm = this.offerService.create();
		result = this.createEditModelAndView(offerForm);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final OfferForm offerForm, final BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Offer offer;

		offer = this.offerService.reconstruct(offerForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(offerForm);
		else
			try {
				if (offer.getMoment().before(new Date())) {
					offerForm.setMoment(null);
					throw new IllegalArgumentException("wrong moment");
				}
				this.offerService.save(offer);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				if (offerForm.getMoment() == null)
					result = this.createEditModelAndView(offerForm, "offer.commit.errorMoment");
				else
					result = this.createEditModelAndView(offerForm, "offer.commit.error");
			}

		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final OfferForm offerForm) {
		final ModelAndView result = this.createEditModelAndView(offerForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final OfferForm offerForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("offer/register");
		result.addObject("offerForm", offerForm);
		result.addObject("message", message);

		return result;
	}
}
