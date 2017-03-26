
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Offer;
import domain.Place;
import form.OfferForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class OfferServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private OfferService	offerService;


	// Tests ------------------------------------------------------------------

	/***
	 * Post an offer
	 * Testing cases:
	 * 1º Good register -> expected: the offer posted
	 * 2º Unauthenticated customer -> expected: IllegalArgumentException
	 */
	@Test
	public void offerCreateDriver() {
		final Object testingData[][] = {

			//customer, expected exception
			{
				"customer1", null
			}, {
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createOfferTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void createOfferTemplate(final String principal, final Class<?> expectedException) {

		Class<?> caught = null;

		try {
			this.authenticate(principal);

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
			Assert.isTrue(offer.getId() != 0);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Find offers by keyWord
	 * Testing cases:
	 * 1º Good search -> expected: collection of offers found
	 * 2º Bad search; A customer must be authenticated -> expected: IllegalArgumentException
	 */
	@Test
	public void searchOfferByKeyWordDriver() {
		final Object testingData[][] = {

			//customer, expected exception
			{
				"customer1", null
			}, {
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.searchOfferByKeyWordTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void searchOfferByKeyWordTemplate(final String principal, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			final Collection<OfferForm> offers = this.offerService.findOfferKeyWord("viaje");
			Assert.notNull(offers);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);
	}

}
