
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
	 * 
	 * Test cases:
	 * 1º Good test -> expected: announcement(Offer) banned
	 * 2º Good test -> expected: announcement(Request) banned
	 * 3º Bad test; A customer cannot ban an announcement; expected IllegalArgumentException
	 * 4º Bad test; An unauthenticated actor cannot ban an announcement; expected IllegalArgumentException
	 * 5º Bad test; A banned announcement(Offer) cannot be re-banned ; expected IllegalArgumentException
	 * 6º Bad test; A banned announcement(Request) cannot be re-banned ; expected IllegalArgumentException
	 */

	@Test
	public void banAnnouncementDriver() {
		final Object testingData[][] = {
			//Logged actor, announcement id, expected exception
			{
				"admin", 56, null
			}, {
				"admin", 84, null
			}, {
				"customer", 56, IllegalArgumentException.class
			}, {
				null, 84, IllegalArgumentException.class
			}, {
				"admin", 57, IllegalArgumentException.class
			}, {
				"admin", 86, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.banAnnoucenmentTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void banAnnoucenmentTemplate(final String principal, final int announcementId, final Class<?> expectedException) {

		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Announcement announcement = this.announcementService.findOne(announcementId);
			this.announcementService.banAnnouncement(announcement);
			Assert.isTrue(announcement.getBanned() == true);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);
	}

}
