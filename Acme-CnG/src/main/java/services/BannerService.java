package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.BannerRepository;
import domain.Banner;

@Service
@Transactional
public class BannerService {

	//Managed repository

	@Autowired
	private BannerRepository	bannerRepository;

	//Supported services

	public BannerService() {
		super();
	}

	public Banner create() {
		return null;
	}

	public Collection<Banner> findAll() {
		return bannerRepository.findAll();
	}

	public Banner findOne(int bannerId) {
		return bannerRepository.findOne(bannerId);

	}

	public void save(Banner banner) {
		bannerRepository.save(banner);
	}

	public void delete(Banner banner) {
		bannerRepository.delete(banner);
	}

	//Other business methods

}