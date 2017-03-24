
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import domain.Actor;
import domain.Application;
import domain.Customer;
import domain.Offer;
import form.OfferForm;
import repositories.OfferRepository;
import security.Authority;

@Service
@Transactional
public class OfferService {

	// Managed repository

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ActorService actorService;

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
		Collection<Offer> offers = this.findAll();

		Collection<String> authorities = new ArrayList<String>();

		for (Authority a : actor.getUserAccount().getAuthorities()) {
			authorities.add(a.getAuthority());
		}

		if (authorities.contains(Authority.ADMINISTRATOR)) {
			for (Offer o : offers) {
				OfferForm offerForm = new OfferForm();
				offerForm = mapTo(o);
				offerForm.setContainsApplication(true);
				result.add(offerForm);
			}
		} else if (authorities.contains(Authority.CUSTOMER)) {
			Customer customer = customerService.findByPrincipal();

			if (customer.getApplications().size() > 0) {
				for (Offer o : offers) {
					OfferForm offerForm = new OfferForm();
					Boolean find = false;
					for (Application a : customer.getApplications()) {
						if (o.getId() == a.getAnnouncementId()) {
							offerForm = mapTo(o);
							offerForm.setContainsApplication(true);
							find = true;

							if (o.getMoment().after(new Date())) {
								if (o.getBanned()) {
									if (o.getCustomer().getId() == customer.getId()) {
										result.add(offerForm);
									}
								} else {
									result.add(offerForm);
								}
							}

						}
					}
					if (!find) {
						if (o.getMoment().after(new Date())) {
							if (o.getBanned()) {
								if (o.getCustomer().getId() == customer.getId()) {
									offerForm = mapTo(o);
									offerForm.setContainsApplication(true);
									result.add(offerForm);
								}
							} else {
								offerForm = mapTo(o);
								offerForm.setContainsApplication(false);
								result.add(offerForm);
							}
						}

					}
				}
			} else {
				for (Offer o : offers) {
					OfferForm offerForm = new OfferForm();
					offerForm = mapTo(o);
					if (o.getMoment().after(new Date())) {
						if (o.getBanned()) {
							if (o.getCustomer().getId() == customer.getId()) {
								offerForm.setContainsApplication(true);
								result.add(offerForm);
							}
						}else{
							if (o.getCustomer().getId() == customer.getId()) {
								offerForm.setContainsApplication(true);
							}else{
								offerForm.setContainsApplication(false);
							}
							
							result.add(offerForm);
						}

					} else {
						if (o.getCustomer().getId() == customer.getId()) {
							offerForm.setContainsApplication(true);
							result.add(offerForm);
						}
					}
				}
			}
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

	public OfferForm mapTo(Offer offer) {
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
