package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ApplicationRepository;
import domain.Application;
import domain.Status;

@Service
@Transactional
public class ApplicationService {

	//Managed repository

	@Autowired
	private ApplicationRepository	applicationRepository;
	

	@Autowired
	private CustomerService customerService;
	//Supported services

	public ApplicationService() {
		super();
	}

	public Application create(int announcementId,String announcementType) {
		Application result = new Application();
		
		result.setStatus(Status.PENDING);
		result.setAnnouncementId(announcementId);
		result.setAnnouncementType(announcementType);
		result.setCustomer(customerService.findByPrincipal());
		
		return result;
	}

	public Collection<Application> findAll() {
		return applicationRepository.findAll();
	}

	public Application findOne(int applicationId) {
		return applicationRepository.findOne(applicationId);

	}

	public void save(Application application) {
		applicationRepository.save(application);
	}

	public void delete(Application application) {
		applicationRepository.delete(application);
	}

	//Other business methods

}