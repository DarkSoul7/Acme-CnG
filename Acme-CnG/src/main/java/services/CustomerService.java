
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Comment;
import domain.Customer;
import domain.Message;
import domain.Offer;
import domain.Request;
import form.CustomerForm;

@Service
@Transactional
public class CustomerService {

	//Managed repository

	@Autowired
	private CustomerRepository		customerRepository;

	@Autowired
	private AdministratorService	administratorService;


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

	public Customer save(final Customer customer) {
		Assert.notNull(customer);
		Customer result;

		result = this.customerRepository.save(customer);

		return result;
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

	public Customer reconstruct(final CustomerForm customerForm, final BindingResult binding) {
		Customer result;

		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hash = encoder.encodePassword(customerForm.getPassword(), null);

		result = new Customer();

		final Authority authority = new Authority();
		final UserAccount userAccount = new UserAccount();

		//Configuring authority & userAccount
		authority.setAuthority("CUSTOMER");
		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);

		final Collection<Offer> offers = new ArrayList<>();
		result.setOffers(offers);

		final Collection<Request> requests = new ArrayList<>();
		result.setRequests(requests);

		final Collection<Application> applications = new ArrayList<>();
		result.setApplications(applications);

		final Collection<Message> sentMessages = new ArrayList<>();
		result.setSentMessages(sentMessages);

		final Collection<Message> receivedMessages = new ArrayList<>();
		result.setReceivedMessages(receivedMessages);

		final Collection<Comment> comments = new ArrayList<>();
		result.setComments(comments);

		result.getUserAccount().setUsername(customerForm.getUsername());
		result.getUserAccount().setPassword(hash);

		result.setFullName(customerForm.getFullName());
		result.setEmail(customerForm.getEmail());
		result.setPhone(customerForm.getPhone());

		//Checking passwords and conditions
		if (!customerForm.getPassword().equals(customerForm.getRepeatPassword()))
			result.getUserAccount().setPassword(null);

		return result;
	}

	//DASHBOARD
	public Collection<Customer> getCustomerWithMoreAcceptedRequest() {
		Assert.notNull(this.administratorService.findByPrincipal());
		return this.customerRepository.getCustomerWithMoreAcceptedRequest();
	}

	public Collection<Customer> getCustomerWithMoreDeniedRequest() {
		Assert.notNull(this.administratorService.findByPrincipal());
		return this.customerRepository.getCustomerWithMoreDeniedRequest();
	}

}
