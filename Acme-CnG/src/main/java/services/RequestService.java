package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Request;
import repositories.RequestRepository;

@Service
@Transactional
public class RequestService {

	//Managed repository

	@Autowired
	private RequestRepository	requestRepository;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AdministratorService administratorService;

	//Supported services

	public RequestService() {
		super();
	}

	public Request create() {
		Request result= new Request();
		
		result.setCustomer(customerService.findByPrincipal());
		
		return result;
	}

	public Collection<Request> findAll() {
		return requestRepository.findAll();
	}

	public Request findOne(int requestId) {
		return requestRepository.findOne(requestId);

	}

	public void save(Request request) {
		requestRepository.save(request);
	}

	public void delete(Request request) {
		requestRepository.delete(request);
	}

	//Other business methods
	public Collection<Request> findRequestKeyWord(String keyWord){
		return requestRepository.findRequestKeyWord(keyWord);
	}
	
	//Dashboard
	public double avgRequestPerCustomer(){
		Assert.notNull(administratorService.findByPrincipal());
		return requestRepository.avgRequestPerCustomer();
	}
}