package repositories;

import java.util.Collection;

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
	
	//DASHBOARD C4
	@Query("select c from Customer c where (select count(a) from Application a where a.status=1 and a.customer.id=c.id) >= all (select count(a) from Application a where a.status=1 group by a.customer order by count(a) desc)")
	public Collection<Customer> getCustomerWithMoreAcceptedRequest();
	
	//DASHBOARD C5
	@Query("select c from Customer c where (select count(a) from Application a where a.status=2 and a.customer.id=c.id) >= all (select count(a) from Application a where a.status=2 group by a.customer order by count(a) desc)")
	public Collection<Customer> getCustomerWithMoreDeniedRequest();
}