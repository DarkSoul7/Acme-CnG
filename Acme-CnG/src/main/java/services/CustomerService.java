
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Customer;
import form.CustomerForm;

@Service
@Transactional
public class CustomerService {

	//Managed repository

	@Autowired
	private CustomerRepository	customerRepository;


	//Supported services

	public CustomerService() {
		super();
	}

	public CustomerForm create() {
		final CustomerForm result = new CustomerForm();

		return result;
	}

	public Collection<Customer> findAll() {
		return this.customerRepository.findAll();
	}

	public Customer findOne(final int customerId) {
		return this.customerRepository.findOne(customerId);

	}

	public void save(final Customer customer) {
		Assert.notNull(customer);
		this.customerRepository.save(customer);
	}

	public void delete(final Customer customer) {
		this.customerRepository.delete(customer);
	}

	//Other business methods

	public Customer findByPrincipal() {
		Customer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount);
		Assert.notNull(result, "Any customer with userAccountId=" + userAccount.getId() + "has be found");

		return result;
	}

	public Customer findByUserAccount(final UserAccount userAccount) {
		Customer result;
		int userAccountId;

		userAccountId = userAccount.getId();
		result = this.customerRepository.findByUserAccountId(userAccountId);

		return result;
	}

	public Customer findByUserName(final String username) {
		Assert.notNull(username);
		Customer result;

		result = this.customerRepository.findByUserName(username);

		return result;
	}

	public CustomerForm convertToObjectForm(final Customer customer) {
		final CustomerForm result = new CustomerForm();

		if (customer.getId() != 0) {
			result.setName(customer.getName());
			result.setSurnames(customer.getSurnames());
			result.setEmail(customer.getEmail());
			result.setPhone(customer.getPhone());
		}
		return result;
	}

	/***
	 * Para evitar hacking se utiliza el método findByPrincipal. De ésta forma se evita que se edite el parámetro
	 * 'id' de customerForm
	 * 
	 * @param customerForm
	 * @return customer reconstruído
	 */
	public Customer reconstruct(final CustomerForm customerForm) {
		Customer result;

		//Try-catch para comprobar a través del metodo findByPrincipal si se trata de un registro o
		//de una edición de perfil
		try {
			//Se trata de una edición
			result = this.findByPrincipal();
			Assert.notNull(result);
			Assert.isTrue(customerForm.getId() == result.getId());

			result.setName(customerForm.getName());
			result.setEmail(customerForm.getEmail());
			result.setSurnames(customerForm.getSurnames());
			result.setPhone(customerForm.getPhone());

		} catch (final Throwable e) {
			//Se trata de un registro
			final Authority authority = new Authority();
			final UserAccount userAccount = new UserAccount();
			result = new Customer();

			//Configuring authority & userAccount
			authority.setAuthority("CUSTOMER");
			userAccount.addAuthority(authority);
			result.setUserAccount(userAccount);
		}
		return result;
	}
}
