package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Offer;
import repositories.OfferRepository;

@Service
@Transactional
public class OfferService {

	//Managed repository

	@Autowired
	private OfferRepository	offerRepository;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AdministratorService administratorService;
	
	//Supported services

	public OfferService() {
		super();
	}

	public Offer create() {
		Offer result= new Offer();
		
		result.setCustomer(customerService.findByPrincipal());
		
		return result;
	}

	public Collection<Offer> findAll() {
		return offerRepository.findAll();
	}

	public Offer findOne(int offerId) {
		return offerRepository.findOne(offerId);

	}

	public void save(Offer offer) {
		offerRepository.save(offer);
	}

	public void delete(Offer offer) {
		offerRepository.delete(offer);
	}

	//Other business methods
	
	public Collection<Offer> findOfferKeyWord(String keyWord){
		return offerRepository.findOfferKeyWord(keyWord);
	}
	
	
	//DASHBOARD
	public double ratioOfferVersusRequest(){
		Assert.notNull(administratorService.findByPrincipal());
		return offerRepository.ratioOfferVersusRequest();
	}
	
	public double avgOfferPerCustomer(){
		Assert.notNull(administratorService.findByPrincipal());
		return offerRepository.avgOfferPerCustomer();
	}

}