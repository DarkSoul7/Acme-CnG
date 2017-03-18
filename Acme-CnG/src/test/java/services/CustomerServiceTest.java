
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

	/***
	 * Register a customer.
	 * Testing cases:
	 * 1º Good register -> expected: customer registered
	 * 2º Password != repeatPassword -> expected: IllegalArgumentException
	 * 3º User does not accept use condition -> expected: IllegalArgumentException
	 */
	@Test
	public void registerCustomerDriver() {
		final Object testingData[][] = {
			//repeatPassword, conditions, expectedException
			{
				"testing", true, null
			}, {
				"exception", true, IllegalArgumentException.class
			}, {
				"testing", false, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.registerCustomerTemplate((String) testingData[i][0], (boolean) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void registerCustomerTemplate(final String repeatPassword, final boolean conditions, final Class<?> expectedException) {

		Class<?> caught = null;

		try {
			this.unauthenticate();
			final CustomerForm customerForm = this.customerService.create();

			customerForm.setAcceptCondition(conditions);
			customerForm.setEmail("test@testing.com");
			customerForm.setFullName("Testing Test Test");
			customerForm.setPassword("testing");
			customerForm.setUsername("testing");
			customerForm.setRepeatPassword(repeatPassword);
			customerForm.setPhone("666666666");

			Customer customer = this.customerService.reconstruct(customerForm, null);

			customer = this.customerService.save(customer);
			Assert.isTrue(customerForm.isAcceptCondition() == true);
			Assert.isTrue(customerForm.getPassword().equals(customerForm.getRepeatPassword()));
			Assert.isTrue(customer.getId() != 0);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

}
