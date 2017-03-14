
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select r from Request r where r.title like %?1% or r.description like %?1% or r.originPlace.address like %?1%")
	public Collection<Request> findRequestKeyWord(String keyWord);

	//DASHBOARD C1
	@Query("select count(r)*1.0/(select count(a) from Announcement a) from Request r")
	public double avgOfRequest();

	//DASHBOARD C2
	@Query("select avg(c.requests.size) from Customer c")
	public double avgRequestPerCustomer();

	//DASHBOARD C3
	@Query("select 1.0*count(a)/(select count(r) from Request r) from Application a")
	public double avgApplicationPerRequest();
}
