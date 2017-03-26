
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Banner;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BannerServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private BannerService	bannerService;


	// Tests ------------------------------------------------------------------

	/***
	 * Create a banner
	 * Test cases:
	 * 1� Good test -> expected: banner registered
	 * 2� Bad test; A customer cannot register a banner -> expected: IllegalArgumentException
	 * 3� Bad test; An unauthenticated actor cannot register a banner -> expected: IllegalArgumentException
	 */
	@Test
	public void createBannerDriver() {
		final Object testingData[][] = {
			//Actor, Expected exception
			{
				"admin", null
			}, {
				"customer", IllegalArgumentException.class
			}, {
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createBannerTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void createBannerTemplate(final String principal, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			Banner banner = this.bannerService.create();

			banner.setActive(false);
			banner.setPicture("http://www.testing.es");

			banner = this.bannerService.save(banner);
			Assert.isTrue(banner.getId() != 0);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);

	}

	/***
	 * Active a banner
	 * Test cases:
	 * 1� Good test -> expected: banner activated
	 * 2� Bad test; A customer cannot active a banner -> expected: IllegalArgumentException
	 * 3� Bad test; An unauthenticated actor cannot active a banner -> expected: IllegalArgumentException
	 * 3� Bad test; An active banner cannot be re-active again -> expected: IllegalArgumentException
	 */
	@Test
	public void activeBannerDriver() {
		final Object testingData[][] = {
			//Actor, banner id, Expected exception
			{
				"admin", 79, null
			}, {
				"customer", 79, IllegalArgumentException.class
			}, {
				null, 79, IllegalArgumentException.class
			}, {
				"admin", 78, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.activeBannerTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void activeBannerTemplate(final String principal, final int bannerId, final Class<?> expectedException) {

		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Banner banner = this.bannerService.findOne(bannerId);
			this.bannerService.activeBanner(banner);
			Assert.isTrue(banner.getActive() == true);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);
	}

}
