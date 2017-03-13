package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Customer;

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
		return null;
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

}