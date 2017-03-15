
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.OfferRepository;
import domain.Customer;
import domain.Offer;
import form.OfferForm;

@Service
@Transactional
public class OfferService {

	//Managed repository

	@Autowired
	private OfferRepository			offerRepository;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private AdministratorService	administratorService;


	//Supported services

	public OfferService() {
		super();
	}

	public OfferForm create() {
		final OfferForm result = new OfferForm();

		return result;
	}

	public Collection<Offer> findAll() {
		return this.offerRepository.findAll();
	}

	public Offer findOne(final int offerId) {
		return this.offerRepository.findOne(offerId);

	}

	public void save(final Offer offer) {
		this.offerRepository.save(offer);
	}

	public void delete(final Offer offer) {
		this.offerRepository.delete(offer);
	}

	//Other business methods

	public Collection<Offer> findOfferKeyWord(final String keyWord) {
		return this.offerRepository.findOfferKeyWord(keyWord);
	}

	/***
	 * De ésta forma se evita la posibilidad de hackeo al cambiar el Id de la offer desde la vista
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

		return result;
	}

	//DASHBOARD
	public double getOfferAvg() {
		return this.offerRepository.offerAvg();
	}

	public double avgOfferPerCustomer() {
		return this.offerRepository.avgOfferPerCustomer();
	}

}
