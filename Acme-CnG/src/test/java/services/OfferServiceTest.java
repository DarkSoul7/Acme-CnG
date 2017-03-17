
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Offer;
import domain.Place;
import form.OfferForm;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class OfferServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private OfferService offerService;


	// Tests ------------------------------------------------------------------

	@Test
	public void createAndSavePositiveTest() {
		this.authenticate("customer1");

		final OfferForm offerForm = this.offerService.create();
		final Place originPlace = new Place();
		final Place destinationPlace = new Place();
		originPlace.setAddress("CALLE 1");
		originPlace.setAddress("CALLE 2");

		offerForm.setDescription("Descripcion");
		offerForm.setTitle("TITULO");
		offerForm.setOriginPlace(originPlace);
		offerForm.setDestinationPlace(destinationPlace);

		Offer offer = this.offerService.reconstruct(offerForm, null);
		offer = this.offerService.save(offer);
		Assert.notNull(offer);
		Assert.isTrue(offer.getId() != 0);
		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void createAndSaveNegativeTestA() {
		this.unauthenticate();
		final OfferForm offerForm = this.offerService.create();
		final Place originPlace = new Place();
		final Place destinationPlace = new Place();
		originPlace.setAddress("CALLE 1");
		originPlace.setAddress("CALLE 2");

		offerForm.setDescription("Descripcion");
		offerForm.setTitle("TITULO");
		offerForm.setOriginPlace(originPlace);
		offerForm.setDestinationPlace(destinationPlace);

		Offer offer = this.offerService.reconstruct(offerForm, null);
		offer = this.offerService.save(offer);
		Assert.notNull(offer);
		Assert.isTrue(offer.getId() != 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createAndSaveNegativeTestB() {
		this.unauthenticate();
		final OfferForm offerForm = this.offerService.create();
		final Place originPlace = new Place();
		final Place destinationPlace = new Place();
		originPlace.setAddress("CALLE 1");
		originPlace.setAddress("CALLE 2");

		offerForm.setDescription("Descripcion");
		offerForm.setTitle("TITULO");
		offerForm.setOriginPlace(originPlace);
		offerForm.setDestinationPlace(destinationPlace);

		Offer offer = this.offerService.reconstruct(offerForm, null);
		offer = this.offerService.save(offer);
		Assert.notNull(offer);
		Assert.isTrue(offer.getId() != 0);
	}
}
