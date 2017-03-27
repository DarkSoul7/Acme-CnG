
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
import domain.Application;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private AnnouncementService	announcementService;


	// Tests ------------------------------------------------------------------

	/***
	 * Apply for an offer or request.
	 * Testing cases:
	 * 1º Good register -> expected: application for offer registered
	 * 2º Good register -> expected: application for request registered
	 * 3º Bad register; A customer cannot apply for his himself offer -> expected: IllegalArgumentException
	 * 4º Bad register; A customer cannot apply for his himself request -> expected: IllegalArgumentException
	 * 5º Bad register; A customer must be authenticated to register an application for an offer -> expected: IllegalArgumentException
	 * 6º Bad register; A customer must be authenticated to register an application for a request -> expected: IllegalArgumentException
	 */
	@Test
	public void registerApplicationDriver() {
		final Object testingData[][] = {
			//customer, announcement id, announcement type, expected exception
			{
				"customer2", 56, "Offer", null
			}, {
				"customer2", 84, "Request", null
			}, {
				"customer1", 56, "Offer", IllegalArgumentException.class
			}, {
				"customer1", 84, "Request", IllegalArgumentException.class
			}, {
				null, 56, "Offer", IllegalArgumentException.class
			}, {
				null, 84, "Request", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createApplicationTemplate((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void createApplicationTemplate(final String principal, final int announcementId, final String announcementType, final Class<?> expectedException) {

		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Announcement announcement = this.announcementService.findOne(announcementId);

			Application application = this.applicationService.create(announcement.getId(), announcementType);
			application = this.applicationService.save(application);
			Assert.isTrue(application.getId() != 0);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);

	}

}
