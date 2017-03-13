package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
	@Query("select a from Customer a where a.userAccount.id = ?1")
	Customer findByUserAccountId(int id);
	
	@Query("select a from Customer a where a.userAccount.username = ?1")
	Customer findByUserName(String username);
}