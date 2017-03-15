
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

	@Test
	public void createAndSavePositiveTest() {
		this.authenticate("admin");
		Banner banner = this.bannerService.create();

		banner.setActive(false);
		banner.setPicture("http://www.testing.es");

		banner = this.bannerService.save(banner);
		Assert.isTrue(banner.getId() != 0);

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void createAndSaveNegativeTestA() {
		this.unauthenticate();
		Banner banner = this.bannerService.create();

		banner.setActive(false);
		banner.setPicture("http://www.testing.es");

		banner = this.bannerService.save(banner);
		Assert.isTrue(banner.getId() != 0);

	}

	@Test(expected = IllegalArgumentException.class)
	public void createAndSaveNegativeTestB() {
		this.authenticate("customer1");
		Banner banner = this.bannerService.create();

		banner.setActive(false);
		banner.setPicture("http://www.testing.es");

		banner = this.bannerService.save(banner);
		Assert.isTrue(banner.getId() != 0);
		this.unauthenticate();
	}

	@Test
	public void activeBannerPositiveTest() {
		this.authenticate("admin");
		final Banner banner = this.bannerService.findOne(74);
		this.bannerService.activeBanner(banner);
		Assert.isTrue(banner.getActive() == true);
		this.unauthenticate();
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
