
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AnnouncementRepository;
import domain.Announcement;

@Service
@Transactional
public class AnnouncementService {

	//Managed repository
	@Autowired
	private AnnouncementRepository	announcementRepository;

	//Supported services

	@Autowired
	private OfferService			offerService;

	@Autowired
	private RequestService			requestService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private AdministratorService	administratorService;


	//Constructor
	public AnnouncementService() {
		super();
	}

	//Simple CRUD methods

	public Announcement create() {
		final Announcement result = new Announcement();

		return result;
	}

	public Collection<Announcement> findAll() {
		return this.announcementRepository.findAll();
	}

	public Announcement findOne(final int announcementId) {
		return this.announcementRepository.findOne(announcementId);
	}

	public void save(final Announcement announcement) {
		Assert.notNull(announcement);
		this.announcementRepository.save(announcement);
	}

	public void delete(final Announcement announcement) {
		Assert.notNull(announcement);
		this.announcementRepository.delete(announcement);
	}

	//Other business methods

	public void banAnnouncement(final Announcement announcement) {
		this.administratorService.findByPrincipal();
		Assert.notNull(announcement);
		Assert.isTrue(announcement.getBanned() == false);

		announcement.setBanned(true);
		this.save(announcement);
	}
}
