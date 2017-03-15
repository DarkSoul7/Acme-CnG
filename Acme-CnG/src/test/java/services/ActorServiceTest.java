
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ActorServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ActorService	actorService;


	// Tests ------------------------------------------------------------------

	@Test
	public void findByPrincipalPositiveTest() {
		this.authenticate("admin");
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void findByPrincipalNegativeTestA() {
		this.unauthenticate();
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
	}

	@Test(expected = IllegalArgumentException.class)
	public void findByPrincipalNegativeTestB() {
		this.authenticate("usuarioErroneo");
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
	}
}
