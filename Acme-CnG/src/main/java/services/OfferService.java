package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.OfferRepository;
import domain.Offer;

@Service
@Transactional
public class OfferService {

	//Managed repository

	@Autowired
	private OfferRepository	offerRepository;

	//Supported services

	public OfferService() {
		super();
	}

	public Offer create() {
		return null;
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

}