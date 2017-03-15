
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;
import domain.Offer;

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
	private OfferService		offerService;


	// Tests ------------------------------------------------------------------

	@Test
	public void createAndSavePositiveTest() {
		this.authenticate("customer1");
		final Offer offer = this.offerService.findOne(46);

		Application application = this.applicationService.create(offer.getId(), offer.getClass().getSimpleName());
		application = this.applicationService.save(application);
		Assert.notNull(application);
		Assert.isTrue(application.getId() != 0);
		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void createAndSaveNegativeTestA() {
		this.authenticate("customer1");
		final Offer offer = this.offerService.findOne(46);

		Application application = this.applicationService.create(offer.getId(), offer.getClass().getSimpleName());
		this.authenticate("customer2");

		application = this.applicationService.save(application);
		Assert.notNull(application);
		Assert.isTrue(application.getId() != 0);
		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void createAndSaveNegativeTestB() {
		this.unauthenticate();
		final Offer offer = this.offerService.findOne(46);
		Application application = this.applicationService.create(offer.getId(), offer.getClass().getSimpleName());
		application = this.applicationService.save(application);
		Assert.notNull(application);
		Assert.isTrue(application.getId() != 0);
		this.unauthenticate();
	}
}
