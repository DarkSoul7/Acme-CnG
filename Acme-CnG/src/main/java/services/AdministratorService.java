package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	//Managed repository

	@Autowired
	private AdministratorRepository	administratorRepository;

	//Supported services

	public AdministratorService() {
		super();
	}

	public Administrator create() {
		return null;
	}

	public Collection<Administrator> findAll() {
		return administratorRepository.findAll();
	}

	public Administrator findOne(int administratorId) {
		return administratorRepository.findOne(administratorId);

	}

	public void save(Administrator administrator) {
		administratorRepository.save(administrator);
	}

	public void delete(Administrator administrator) {
		administratorRepository.delete(administrator);
	}

	//Other business methods
	
	public Administrator findByPrincipal() {
		Administrator result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount);
		Assert.notNull(result, "Any administrator with userAccountId=" + userAccount.getId() + "has be found");

		return result;
	}

	public Administrator findByUserAccount(UserAccount userAccount) {
		Administrator result;
		int userAccountId;

		userAccountId = userAccount.getId();
		result = administratorRepository.findByUserAccountId(userAccountId);

		return result;
	}

	public Administrator findByUserName(String username) {
		Assert.notNull(username);
		Administrator result;

		result = administratorRepository.findByUserName(username);

		return result;
	}

}