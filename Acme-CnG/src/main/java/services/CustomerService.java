package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import domain.Application;
import domain.Comment;
import domain.Customer;
import domain.Message;
import domain.Offer;
import domain.Request;
import form.CustomerForm;
import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

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

	public Customer create() {
		Customer result = new Customer();

		Authority authority = new Authority();
		UserAccount userAccount = new UserAccount();

		//Configuring authority & userAccount
		authority.setAuthority("CUSTOMER");
		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);

		Collection<Offer> offers = new ArrayList<>();
		result.setOffers(offers);

		Collection<Request> requests = new ArrayList<>();
		result.setRequests(requests);
		
		Collection<Application> applications = new ArrayList<>();
		result.setApplications(applications);
		
		Collection<Message> sentMessages = new ArrayList<>();
		result.setSentMessages(sentMessages);
		
		Collection<Message> receivedMessages = new ArrayList<>();
		result.setReceivedMessages(receivedMessages);
		
		Collection<Comment> comments = new ArrayList<>();
		result.setComments(comments);
		
		return result;
	}

	public Collection<Customer> findAll() {
		return customerRepository.findAll();
	}

	public Customer findOne(int customerId) {
		return customerRepository.findOne(customerId);

	}

	public void save(Customer customer) {
		customerRepository.save(customer);
	}

	public void delete(Customer customer) {
		customerRepository.delete(customer);
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

	public Customer findByUserAccount(UserAccount userAccount) {
		Customer result;
		int userAccountId;

		userAccountId = userAccount.getId();
		result = customerRepository.findByUserAccountId(userAccountId);

		return result;
	}

	public Customer findByUserName(String username) {
		Assert.notNull(username);
		Customer result;

		result = customerRepository.findByUserName(username);

		return result;
	}
	
	public Customer reconstruct(CustomerForm customerForm, BindingResult binding) {
		Customer result;

		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String hash = encoder.encodePassword(customerForm.getPassword(), null);

		result = new Customer();

		Authority authority = new Authority();
		UserAccount userAccount = new UserAccount();

		//Configuring authority & userAccount
		authority.setAuthority("CUSTOMER");
		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);

		Collection<Offer> offers = new ArrayList<>();
		result.setOffers(offers);

		Collection<Request> requests = new ArrayList<>();
		result.setRequests(requests);
		
		Collection<Application> applications = new ArrayList<>();
		result.setApplications(applications);
		
		Collection<Message> sentMessages = new ArrayList<>();
		result.setSentMessages(sentMessages);
		
		Collection<Message> receivedMessages = new ArrayList<>();
		result.setReceivedMessages(receivedMessages);
		
		Collection<Comment> comments = new ArrayList<>();
		result.setComments(comments);

		result.getUserAccount().setUsername(customerForm.getUsername());
		result.getUserAccount().setPassword(hash);

		result.setName(customerForm.getName());
		result.setSurnames(customerForm.getSurname());
		result.setEmail(customerForm.getEmail());
		result.setPhone(customerForm.getPhone());

		//Checking passwords and conditions
		if (!customerForm.getPassword().equals(customerForm.getRepeatPassword())) {
			result.getUserAccount().setPassword(null);
		}


		return result;
	}


}