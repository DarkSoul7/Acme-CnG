
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import domain.Customer;
import domain.Request;
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


	//Supported services

	public RequestService() {
		super();
	}

	public RequestForm create() {
		final RequestForm result = new RequestForm();

		return result;
	}

	public Collection<Request> findAll() {
		return this.requestRepository.findAll();
	}

	public Request findOne(final int requestId) {
		return this.requestRepository.findOne(requestId);

	}

	public void save(final Request request) {
		this.requestRepository.save(request);
	}

	public void delete(final Request request) {
		this.requestRepository.delete(request);
	}

	//Other business methods
	public Collection<Request> findRequestKeyWord(final String keyWord) {
		return this.requestRepository.findRequestKeyWord(keyWord);
	}


	@Autowired
	private Validator	validator;


	/***
	 * De �sta forma se evita el hacking modificando el id de la request desde la vista
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

				this.validator.validate(result, binding);

			} catch (final Throwable e) {
				result = null;
				this.validator.validate(result, binding);
			}
		else {
			result = new Request();
			result.setCustomer(customer);
			result.setTitle(requestForm.getTitle());
			result.setDescription(requestForm.getDescription());
			result.setDestinationPlace(requestForm.getDestinationPlace());
			result.setOriginPlace(requestForm.getOriginPlace());
			result.setMoment(requestForm.getMoment());

			this.validator.validate(result, binding);
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
