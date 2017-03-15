
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Customer;
import form.CustomerForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CustomerServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private CustomerService	customerService;


	// Tests ------------------------------------------------------------------

	@Test
	public void createAndReconstructAndSavePositiveTest() {
		this.unauthenticate();
		final CustomerForm customerForm = this.customerService.create();

		customerForm.setAcceptCondition(true);
		customerForm.setEmail("test@testing.com");
		customerForm.setFullName("Testing Test Test");
		customerForm.setPassword("testing");
		customerForm.setUsername("testing");
		customerForm.setRepeatPassword("testing");
		customerForm.setPhone("666666666");

		Customer customer = this.customerService.reconstruct(customerForm, null);

		customer = this.customerService.save(customer);
		Assert.isTrue(customer.getId() != 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createAndReconstructAndSaveNegativeTestA() {
		this.unauthenticate();
		final CustomerForm customerForm = this.customerService.create();

		customerForm.setAcceptCondition(true);
		customerForm.setEmail("test@testing.com");
		customerForm.setFullName("Testing Test Test");
		customerForm.setPassword("testing1");
		customerForm.setUsername("testing");
		customerForm.setRepeatPassword("testing2");
		customerForm.setPhone("666666666");

		Assert.isTrue(customerForm.getPassword().equals(customerForm.getRepeatPassword()));

		Customer customer = this.customerService.reconstruct(customerForm, null);

		customer = this.customerService.save(customer);
		Assert.isTrue(customer.getId() != 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createAndReconstructAndSaveNegativeTestB() {
		this.unauthenticate();
		final CustomerForm customerForm = this.customerService.create();

		customerForm.setAcceptCondition(false);
		customerForm.setEmail("test@testing.com");
		customerForm.setFullName("Testing Test Test");
		customerForm.setPassword("testing1");
		customerForm.setUsername("testing");
		customerForm.setRepeatPassword("testing2");
		customerForm.setPhone("666666666");

		Customer customer = this.customerService.reconstruct(customerForm, null);

		Assert.isTrue(customerForm.isAcceptCondition() == true);
		customer = this.customerService.save(customer);
		Assert.isTrue(customer.getId() != 0);
	}

	@Test
	public void findByPrincipalPositiveTest() {
		this.authenticate("customer1");
		final Customer customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);
		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void findByPrincipalNegativeTestA() {
		this.unauthenticate();
		final Customer customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);
	}

	@Test(expected = IllegalArgumentException.class)
	public void findByPrincipalNegativeTestB() {
		this.authenticate("usuarioErroneo");
		final Customer customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);
	}
}
