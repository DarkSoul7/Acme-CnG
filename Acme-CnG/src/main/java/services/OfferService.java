
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.OfferRepository;
import security.Authority;
import domain.Actor;
import domain.Customer;
import domain.Offer;
import form.OfferForm;

@Service
@Transactional
public class OfferService {

	// Managed repository

	@Autowired
	private OfferRepository			offerRepository;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;


	// Supported services

	public OfferService() {
		super();
	}

	public OfferForm create() {
		final OfferForm result = new OfferForm();
		result.setBanned(false);

		return result;
	}

	public Collection<Offer> findAll() {
		return this.offerRepository.findAll();
	}

	public Offer findOne(final int offerId) {
		return this.offerRepository.findOne(offerId);

	}

	public Offer save(final Offer offer) {
		Offer result;
		Assert.notNull(offer);

		result = this.offerRepository.save(offer);

		return result;
	}

	public void delete(final Offer offer) {
		Assert.notNull(offer);
		this.offerRepository.delete(offer);
	}

	// Other business methods

	public Collection<Offer> findOfferKeyWord(final String keyWord) {
		this.customerService.findByPrincipal();
		return this.offerRepository.findOfferKeyWord(keyWord);
	}

	public Collection<OfferForm> getOffersWithoutApplications() {
		Customer principal = customerService.findByPrincipal();

		return this.offerRepository.getOffersWithoutApplications(principal.getId());
	}

	public Collection<OfferForm> getOffersIAppliedOrMine() {
		Customer principal = customerService.findByPrincipal();

		return this.offerRepository.getOffersIAppliedOrMine(principal.getId());
	}

	public Collection<OfferForm> findAllForms() {
		administratorService.findByPrincipal();

		return this.offerRepository.findAllForms();
	}

	/***
	 * De ésta forma se evita la posibilidad de hackeo al cambiar el Id de la
	 * offer desde la vista
	 * 
	 * @param offerForm
	 * @param binding
	 * @return offer reconstruida
	 */
	public Offer reconstruct(final OfferForm offerForm, final BindingResult binding) {
		Assert.notNull(offerForm);
		final Customer customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);

		Offer result;

		if (offerForm.getId() != 0)
			try {

				result = this.findOne(offerForm.getId());
				Assert.notNull(result);
				Assert.isTrue(result.getCustomer().equals(customer));

				result.setTitle(offerForm.getTitle());
				result.setDescription(offerForm.getDescription());
				result.setDestinationPlace(offerForm.getDestinationPlace());
				result.setOriginPlace(offerForm.getOriginPlace());
				result.setMoment(offerForm.getMoment());

			} catch (final Throwable e) {
				result = null;
			}
		else {
			result = new Offer();
			result.setCustomer(customer);
			result.setTitle(offerForm.getTitle());
			result.setDescription(offerForm.getDescription());
			result.setDestinationPlace(offerForm.getDestinationPlace());
			result.setOriginPlace(offerForm.getOriginPlace());
			result.setMoment(offerForm.getMoment());

		}
		result.setBanned(false);
		return result;
	}

	public Collection<OfferForm> findOfferWithApplication() {
		Collection<OfferForm> result = new ArrayList<OfferForm>();
		Actor actor = actorService.findByPrincipal();
		Authority adminAuthority = new Authority();
		Authority customerAuthority = new Authority();

		adminAuthority.setAuthority(Authority.ADMINISTRATOR);
		customerAuthority.setAuthority(Authority.CUSTOMER);

		if (actor.getUserAccount().getAuthorities().contains(adminAuthority)) {
			result = this.findAllForms();
		} else if (actor.getUserAccount().getAuthorities().contains(customerAuthority)) {
			result = this.getOffersWithoutApplications();
			result.addAll(this.getOffersIAppliedOrMine());
		} else {
			throw new IllegalAccessError();
		}

		return result;
	}

	// DASHBOARD
	public double getOfferAvg() {
		return this.offerRepository.offerAvg();
	}

	public double avgOfferPerCustomer() {
		return this.offerRepository.avgOfferPerCustomer();
	}

	public OfferForm mapTo(final Offer offer) {
		OfferForm result = new OfferForm();
		result.setBanned(offer.getBanned());
		result.setDescription(offer.getDescription());
		result.setDestinationPlace(offer.getDestinationPlace());
		result.setId(offer.getId());
		result.setMoment(offer.getMoment());
		result.setOriginPlace(offer.getOriginPlace());
		result.setTitle(offer.getTitle());

		return result;
	}

}
