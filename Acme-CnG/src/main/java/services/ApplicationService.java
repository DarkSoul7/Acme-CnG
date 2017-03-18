
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Announcement;
import domain.Application;
import domain.Customer;
import domain.Offer;
import domain.Request;
import domain.Status;

@Service
@Transactional
public class ApplicationService {

	//Managed repository

	@Autowired
	private ApplicationRepository	applicationRepository;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private AnnouncementService		announcementService;

	@Autowired
	private OfferService			offerService;

	@Autowired
	private RequestService			requestService;


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

	public Application save(final Application application) {
		Assert.notNull(application);
		final Customer customer = this.customerService.findByPrincipal();
		Request request = null;
		Offer offer = null;

		//No se permite una aplicación de un customer a su propia oferta/petición 
		if (application.getId() == 0)
			if (application.getAnnouncementType().equals("Offer")) {
				offer = this.offerService.findOne(application.getAnnouncementId());
				Assert.isTrue(!offer.getCustomer().equals(customer));
			} else {
				request = this.requestService.findOne(application.getAnnouncementId());
				Assert.notNull(request);
				Assert.isTrue(!request.getCustomer().equals(customer));
			}
		Application result;
		result = this.applicationRepository.save(application);
		return result;
	}

	public void delete(final Application application) {
		this.applicationRepository.delete(application);
	}

	//Other business methods

	public void acceptApplication(final Application application) {
		Assert.notNull(application);
		final Announcement announcement = this.announcementService.findOne(application.getAnnouncementId());
		Assert.notNull(announcement);
		final Customer customer = this.customerService.findByPrincipal();
		Assert.isTrue(application.getCustomer().equals(customer));
		application.setStatus(Status.ACCEPTED);

		this.save(application);
	}

	public void denyApplication(final Application application) {
		Assert.notNull(application);
		final Announcement announcement = this.announcementService.findOne(application.getAnnouncementId());
		Assert.notNull(announcement);
		final Customer customer = this.customerService.findByPrincipal();
		Assert.isTrue(application.getCustomer().equals(customer));
		application.setStatus(Status.DENIED);

		this.save(application);
	}
}
