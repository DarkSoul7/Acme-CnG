
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Announcement;
import domain.Offer;
import domain.Request;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
//@TransactionConfiguration(defaultRollback = false)
public class AnnouncementServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private AnnouncementService	announcementService;

	@Autowired
	private OfferService		offerService;

	@Autowired
	private RequestService		requestService;


	// Tests ------------------------------------------------------------------

	/***
	 * Ban an offer or a request that he or she finds inappropriate.
	 * Such offers and re-quests must not be displayed to a general audience, only
	 * to the administrators and the customer who posted it.
	 */

	//Trying ban an announcement

	//Ban an offer

	@Test
	public void banOfferPositiveTest() {
		this.authenticate("admin");
		final Offer offer = this.offerService.findOne(46);
		this.announcementService.banAnnouncement(offer);
		Assert.isTrue(offer.getBanned() == true);
		this.unauthenticate();
	}

	//Ban a request

	@Test
	public void banRequestPositiveTest() {
		this.authenticate("admin");
		final Request request = this.requestService.findOne(69);
		this.announcementService.banAnnouncement(request);
		Assert.isTrue(request.getBanned() == true);
		this.unauthenticate();
	}

	//From superClass

	@Test
	public void banAnnouncementPositiveTest() {
		this.authenticate("admin");
		final Announcement announcement = this.announcementService.findOne(72);
		this.announcementService.banAnnouncement(announcement);
		Assert.isTrue(announcement.getBanned() == true);
		this.unauthenticate();
	}

	// Check an unauthenticated actor cannot ban an announcement
	@Test(expected = IllegalArgumentException.class)
	public void banAnnouncementNegativeTest() {
		this.unauthenticate();
		final Announcement announcement = this.announcementService.findOne(72);
		this.announcementService.banAnnouncement(announcement);
		Assert.isTrue(announcement.getBanned() == true);
	}

	// Check an unauthenticated actor cannot ban a request
	@Test(expected = IllegalArgumentException.class)
	public void banRequestNegativeTest() {
		this.unauthenticate();
		final Request request = this.requestService.findOne(72);
		this.announcementService.banAnnouncement(request);
		Assert.isTrue(request.getBanned() == true);
	}

	// Check an unauthenticated actor cannot ban an offer
	@Test(expected = IllegalArgumentException.class)
	public void banOfferNegativeTest() {
		this.unauthenticate();
		final Offer offer = this.offerService.findOne(49);
		this.announcementService.banAnnouncement(offer);
		Assert.isTrue(offer.getBanned() == true);
	}

	// A banned announcement cannot be re-banned
	@Test(expected = IllegalArgumentException.class)
	public void banAnnouncementNegativeTestB() {
		this.authenticate("admin");
		final Announcement announcement = this.announcementService.findOne(47);
		this.announcementService.banAnnouncement(announcement);
		Assert.isTrue(announcement.getBanned() == true);
		this.unauthenticate();
	}
}
