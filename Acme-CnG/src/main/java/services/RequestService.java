package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.RequestRepository;
import domain.Request;

@Service
@Transactional
public class RequestService {

	//Managed repository

	@Autowired
	private RequestRepository	requestRepository;

	//Supported services

	public RequestService() {
		super();
	}

	public Request create() {
		return null;
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

}