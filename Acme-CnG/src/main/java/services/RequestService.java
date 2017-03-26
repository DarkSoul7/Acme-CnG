
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.RequestRepository;
import security.Authority;
import domain.Actor;
import domain.Customer;
import domain.Request;
import form.OfferForm;
import form.RequestForm;

@Service
@Transactional
public class RequestService {

	//Managed repository

	@Autowired
	private RequestRepository		requestRepository;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private AdministratorService	administratorService;
	
	@Autowired
	private ActorService			actorService;


	//Supported services

	public RequestService() {
		super();
	}

	public RequestForm create() {
		final RequestForm result = new RequestForm();
		result.setBanned(false);

		return result;
	}

	public Collection<Request> findAll() {
		return this.requestRepository.findAll();
	}

	public Request findOne(final int requestId) {
		return this.requestRepository.findOne(requestId);

	}

	public Request save(final Request request) {
		Assert.notNull(request);
		Request result;

		result = this.requestRepository.save(request);

		return result;
	}

	public void delete(final Request request) {
		Assert.notNull(request);
		this.requestRepository.delete(request);
	}

	//Other business methods
	public Collection<RequestForm> findRequestKeyWord(final String keyWord) {
		Collection<RequestForm> result = new ArrayList<RequestForm>();
		Actor actor = actorService.findByPrincipal();
		Authority customerAuthority = new Authority();

		customerAuthority.setAuthority(Authority.CUSTOMER);

		if (actor.getUserAccount().getAuthorities().contains(customerAuthority)) {
			result = this.findRequestKeyWordWithoutApplications(keyWord);
			result.addAll(this.findRequestKeyWordIAppliedOrMine(keyWord));
		} else {
			throw new IllegalAccessError();
		}

		return result;
	}
	
	public Collection<RequestForm> findRequestKeyWordWithoutApplications(String keyWord) {
		Customer principal = customerService.findByPrincipal();

		return this.requestRepository.findRequestKeyWordWithoutApplications(principal.getId(), keyWord);
	}

	public Collection<RequestForm> findRequestKeyWordIAppliedOrMine(String keyWord) {
		Customer principal = customerService.findByPrincipal();

		return this.requestRepository.findRequestKeyWordIAppliedOrMine(principal.getId(), keyWord);
	}
	
	public Collection<RequestForm> getRequestsWithoutApplications() {
		Customer principal = customerService.findByPrincipal();

		return this.requestRepository.getRequestsWithoutApplications(principal.getId());
	}

	public Collection<RequestForm> getRequestsIAppliedOrMine() {
		Customer principal = customerService.findByPrincipal();

		return this.requestRepository.getRequestsIAppliedOrMine(principal.getId());
	}

	public Collection<RequestForm> findAllForms() {
		administratorService.findByPrincipal();

		return this.requestRepository.findAllForms();
	}

	/***
	 * De ésta forma se evita el hacking modificando el id de la request desde la vista
	 * 
	 * @param requestForm
	 * @param binding
	 * @return request reconstruida
	 */
	public Request reconstruct(final RequestForm requestForm, final BindingResult binding) {
		Assert.notNull(requestForm);
		final Customer customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);

		Request result;

		if (requestForm.getId() != 0)
			try {

				result = this.findOne(requestForm.getId());
				Assert.notNull(result);
				Assert.isTrue(result.getCustomer().equals(customer));

				result.setTitle(requestForm.getTitle());
				result.setDescription(requestForm.getDescription());
				result.setDestinationPlace(requestForm.getDestinationPlace());
				result.setOriginPlace(requestForm.getOriginPlace());
				result.setMoment(requestForm.getMoment());

			} catch (final Throwable e) {
				result = null;
			}
		else {
			result = new Request();
			result.setCustomer(customer);
			result.setTitle(requestForm.getTitle());
			result.setDescription(requestForm.getDescription());
			result.setDestinationPlace(requestForm.getDestinationPlace());
			result.setOriginPlace(requestForm.getOriginPlace());
			result.setMoment(requestForm.getMoment());

		}

		result.setBanned(false);
		return result;
	}
	
	public Collection<RequestForm> findRequestWithApplication() {
		Collection<RequestForm> result = new ArrayList<RequestForm>();
		Actor actor = actorService.findByPrincipal();
		Authority adminAuthority = new Authority();
		Authority customerAuthority = new Authority();

		adminAuthority.setAuthority(Authority.ADMINISTRATOR);
		customerAuthority.setAuthority(Authority.CUSTOMER);

		if (actor.getUserAccount().getAuthorities().contains(adminAuthority)) {
			result = this.findAllForms();
		} else if (actor.getUserAccount().getAuthorities().contains(customerAuthority)) {
			result = this.getRequestsWithoutApplications();
			result.addAll(this.getRequestsIAppliedOrMine());
		} else {
			throw new IllegalAccessError();
		}

		return result;
	}

	//Dashboard
	public double avgRequestPerCustomer() {
		return this.requestRepository.avgRequestPerCustomer();
	}

	public double requestAvg() {
		return this.requestRepository.avgOfRequest();
	}
}
