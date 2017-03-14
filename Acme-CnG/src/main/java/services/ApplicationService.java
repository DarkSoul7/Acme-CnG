
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Application;
import domain.Customer;
import domain.Status;

@Service
@Transactional
public class ApplicationService {

	//Managed repository

	@Autowired
	private ApplicationRepository	applicationRepository;

	@Autowired
	private CustomerService			customerService;


	//Supported services

	public ApplicationService() {
		super();
	}

	public Application create(final int announcementId, final String announcementType) {
		final Application result = new Application();
		final Customer customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);

		result.setStatus(Status.PENDING);
		result.setAnnouncementId(announcementId);
		result.setAnnouncementType(announcementType);
		result.setCustomer(customer);

		return result;
	}

	public Collection<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	public Application findOne(final int applicationId) {
		return this.applicationRepository.findOne(applicationId);

	}

	public void save(final Application application) {
		Assert.notNull(application);
		final Customer customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);

		this.applicationRepository.save(application);
	}

	public void delete(final Application application) {
		this.applicationRepository.delete(application);
	}

	//Other business methods

}
