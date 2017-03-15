
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Administrator;
import domain.Announcement;
import domain.Offer;
import domain.Request;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AnnouncementService		announcementService;

	@Autowired
	private OfferService			offerService;

	@Autowired
	private RequestService			requestService;


	// Tests ------------------------------------------------------------------

	@Test
	public void findByPrincipalPositiveTest() {
		this.authenticate("admin");
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void findByPrincipalNegativeTestA() {
		this.unauthenticate();
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
	}

	@Test(expected = IllegalArgumentException.class)
	public void findByPrincipalNegativeTestB() {
		this.authenticate("customer1");
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
	}

	//Check from Announcement super class
	@Test
	public void banAnnouncementPositiveTest() {
		final Announcement announcement = this.announcementService.findOne(46);
		this.authenticate("admin");
		this.administratorService.banAnnouncement(announcement);
		Assert.isTrue(announcement.getBanned() == true);
		this.unauthenticate();
	}

	//Check from Offer class
	@Test
	public void banOfferPositiveTest() {
		final Offer offer = this.offerService.findOne(46);
		this.authenticate("admin");
		this.administratorService.banAnnouncement(offer);
		Assert.isTrue(offer.getBanned() == true);
		this.unauthenticate();
	}

	//Check from Request class
	@Test
	public void banRequestPositiveTest() {
		final Request request = this.requestService.findOne(69);
		this.authenticate("admin");
		this.administratorService.banAnnouncement(request);
		Assert.isTrue(request.getBanned() == true);
		this.unauthenticate();
	}

	//Check from Announcement super class
	@Test(expected = IllegalArgumentException.class)
	public void banAnnouncementNegativeTestA() {
		this.unauthenticate();
		final Announcement announcement = this.announcementService.findOne(46);
		this.administratorService.banAnnouncement(announcement);
		Assert.isTrue(announcement.getBanned() == true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void banAnnouncementNegativeTestB() {
		this.authenticate("admin");
		final Announcement announcement = this.announcementService.findOne(47);
		this.administratorService.banAnnouncement(announcement);
		Assert.isTrue(announcement.getBanned() == true);
		this.unauthenticate();
	}

	//Check from Offer class
	@Test(expected = IllegalArgumentException.class)
	public void banOfferNegativeTestA() {
		this.unauthenticate();
		final Offer offer = this.offerService.findOne(46);
		this.administratorService.banAnnouncement(offer);
		Assert.isTrue(offer.getBanned() == true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void banOfferNegativeTestB() {
		this.authenticate("admin");
		final Offer offer = this.offerService.findOne(47);
		this.administratorService.banAnnouncement(offer);
		Assert.isTrue(offer.getBanned() == true);
		this.unauthenticate();
	}

	//Check from Request class
	@Test(expected = IllegalArgumentException.class)
	public void banRequestNegativeTestA() {
		this.unauthenticate();
		final Request request = this.requestService.findOne(69);
		this.administratorService.banAnnouncement(request);
		Assert.isTrue(request.getBanned() == true);
		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void banRequestNegativeTestB() {
		this.authenticate("admin");
		final Request request = this.requestService.findOne(71);
		this.administratorService.banAnnouncement(request);
		Assert.isTrue(request.getBanned() == true);
		this.unauthenticate();
	}
}
