
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Banner;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class BannerServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private BannerService	bannerService;


	// Tests ------------------------------------------------------------------

	/***
	 * The manage of banner has been selected to pass the 10 use cases coverage test.
	 * The coverage is around:
	 * Create and save a banner: 3 use cases
	 * Edit a banner: 3 use cases
	 * Delete a banner: 4 use cases
	 */

	/***
	 * Create a banner
	 * Test cases:
	 * 1º Good test -> expected: banner registered
	 * 2º Bad test; A customer cannot register a banner -> expected: IllegalArgumentException
	 * 3º Bad test; An unauthenticated actor cannot register a banner -> expected: IllegalArgumentException
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
	 * Edit a banner
	 * Test cases:
	 * 1º Good test -> expected: banner edited
	 * 2º Bad test; A customer cannot edit a banner -> expected: IllegalArgumentException
	 * 3º Bad test; An unauthenticated actor cannot edit a banner -> expected: IllegalArgumentException
	 */

	@Test
	public void editBannerDriver() {
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
			this.editBannerTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void editBannerTemplate(final String principal, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			Banner banner = this.bannerService.findOne(90);

			banner.setActive(false);
			banner.setPicture("http://www.testing.es");

			banner = this.bannerService.save(banner);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);

	}

	/***
	 * Edit a banner
	 * Test cases:
	 * 1º Good test: deleting an inactive banner -> expected: banner deleted
	 * 2º Good test: deleting an active banner -> expected: banner deleted
	 * 3º Bad test; A customer cannot delete a banner -> expected: IllegalArgumentException
	 * 4º Bad test; An unauthenticated actor cannot delete a banner -> expected: IllegalArgumentException
	 */

	@Test
	public void deleteBannerDriver() {
		final Object testingData[][] = {
			//Actor, banner id, Expected exception
			{
				"admin", 90, null
			}, {
				"admin", 88, null
			}, {
				"customer", 92, IllegalArgumentException.class
			}, {
				null, 91, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteBannerTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void deleteBannerTemplate(final String principal, final int bannerId, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Banner banner = this.bannerService.findOne(bannerId);

			this.bannerService.delete(banner);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);

	}

	/***
	 * Active a banner
	 * Test cases:
	 * 1º Good test -> expected: banner activated
	 * 2º Bad test; A customer cannot active a banner -> expected: IllegalArgumentException
	 * 3º Bad test; An unauthenticated actor cannot active a banner -> expected: IllegalArgumentException
	 * 4º Bad test; An active banner cannot be re-active again -> expected: IllegalArgumentException
	 */
	@Test
	public void activeBannerDriver() {
		final Object testingData[][] = {
			//Actor, banner id, Expected exception
			{
				"admin", 89, null
			}, {
				"customer", 89, IllegalArgumentException.class
			}, {
				null, 89, IllegalArgumentException.class
			}, {
				"admin", 89, IllegalArgumentException.class
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
