
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
import domain.Place;
import domain.Request;
import form.RequestForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RequestServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private RequestService	requestService;


	// Tests ------------------------------------------------------------------

	/***
	 * Post an request
	 * Testing cases:
	 * 1º Good register -> expected: the request posted
	 * 2º Unauthenticated customer -> expected: IllegalArgumentException
	 */
	@Test
	public void RequestCreateDriver() {
		final Object testingData[][] = {
			{
				"customer1", null
			}, {
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createRequestDriver((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void createRequestDriver(final String principal, final Class<?> expectedException) {

		Class<?> caught = null;

		try {
			this.authenticate(principal);

			final RequestForm requestForm = this.requestService.create();
			final Place originPlace = new Place();
			final Place destinationPlace = new Place();
			originPlace.setAddress("CALLE 1");
			originPlace.setAddress("CALLE 2");

			requestForm.setDescription("Descripcion");
			requestForm.setTitle("TITULO");
			requestForm.setOriginPlace(originPlace);
			requestForm.setDestinationPlace(destinationPlace);

			Request request = this.requestService.reconstruct(requestForm, null);
			request = this.requestService.save(request);
			Assert.isTrue(request.getId() != 0);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Find requests by keyWord
	 * Testing cases:
	 * 1º Good search -> expected: collection of requests found
	 * 2º Bad search; A customer must be authenticated -> expected: IllegalArgumentException
	 */
	@Test
	public void searchRequestByKeyWordDriver() {
		final Object testingData[][] = {

			//customer, expected exception
			{
				"customer1", null
			}, {
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.searchRequestByKeyWordTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void searchRequestByKeyWordTemplate(final String principal, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			final Collection<RequestForm> request = this.requestService.findRequestKeyWord("viaje");
			Assert.notNull(request);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);
	}
}
