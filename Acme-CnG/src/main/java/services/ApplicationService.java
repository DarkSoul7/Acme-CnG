package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ApplicationRepository;
import domain.Application;

@Service
@Transactional
public class ApplicationService {

	//Managed repository

	@Autowired
	private ApplicationRepository	applicationRepository;

	//Supported services

	public ApplicationService() {
		super();
	}

	public Application create() {
		return null;
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