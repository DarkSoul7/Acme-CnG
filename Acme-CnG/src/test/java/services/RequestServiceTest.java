
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Place;
import domain.Request;
import form.RequestForm;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RequestServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private RequestService requestService;


	// Tests ------------------------------------------------------------------

	@Test
	public void createAndSavePositiveTest() {
		this.authenticate("customer1");

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
		Assert.notNull(request);
		Assert.isTrue(request.getId() != 0);
		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void createAndSaveNegativeTestA() {
		this.unauthenticate();
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
		Assert.notNull(request);
		Assert.isTrue(request.getId() != 0);
		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void createAndSaveNegativeTestB() {
		this.unauthenticate();
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
		Assert.notNull(request);
		Assert.isTrue(request.getId() != 0);
		this.unauthenticate();
	}
}
