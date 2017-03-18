
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
	 * Active a banner
	 * Test cases:
	 * 1º Good test -> expected: banner activated
	 * 2º Bad test; A customer cannot active a banner -> expected: IllegalArgumentException
	 * 3º Bad test; An unauthenticated actor cannot active a banner -> expected: IllegalArgumentException
	 * 3º Bad test; An active banner cannot be re-active again -> expected: IllegalArgumentException
	 */
	@Test
	public void activeBannerDriver() {
		final Object testingData[][] = {
			//Actor, banner id, Expected exception
			{
				"admin", 74, null
			}, {
				"customer", 74, IllegalArgumentException.class
			}, {
				null, 74, IllegalArgumentException.class
			}, {
				"admin", 73, IllegalArgumentException.class
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

	@Test(expected = IllegalArgumentException.class)
	public void activeBannerNegativeTestA() {
		this.unauthenticate();
		final Banner banner = this.bannerService.findOne(74);
		this.bannerService.activeBanner(banner);
		Assert.isTrue(banner.getActive() == true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void activeBannerNegativeTestB() {
		this.authenticate("customer1");
		final Banner banner = this.bannerService.findOne(74);
		this.bannerService.activeBanner(banner);
		Assert.isTrue(banner.getActive() == true);
		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void activeBannerNegativeTestC() {
		this.authenticate("admin");
		final Banner banner = this.bannerService.findOne(100);
		this.bannerService.activeBanner(banner);
		Assert.isTrue(banner.getActive() == true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void activeBannerNegativeTestD() {
		this.authenticate("admin");
		final Banner banner = this.bannerService.findOne(73);
		this.bannerService.activeBanner(banner);
		Assert.isTrue(banner.getActive() == true);
		this.unauthenticate();
	}
}
