
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BannerRepository;
import domain.Administrator;
import domain.Banner;

@Service
@Transactional
public class BannerService {

	//Managed repository

	@Autowired
	private BannerRepository		bannerRepository;

	//Supported services

	@Autowired
	private AdministratorService	administratorService;


	public BannerService() {
		super();
	}

	public Banner create() {
		final Administrator administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);
		final Banner result = new Banner();

		return result;
	}

	public Collection<Banner> findAll() {
		return this.bannerRepository.findAll();
	}

	public Banner findOne(final int bannerId) {
		return this.bannerRepository.findOne(bannerId);

	}

	public void save(final Banner banner) {
		final Administrator administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);

		this.bannerRepository.save(banner);
	}

	public void delete(final Banner banner) {
		this.bannerRepository.delete(banner);
	}

	//Other business methods

}
