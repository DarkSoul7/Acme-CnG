
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Request;
import form.RequestForm;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

//	@Query("select r from Request r where r.title like %?1% or r.description like %?1% or r.originPlace.address like %?1%")
//	public Collection<Request> findRequestKeyWord(String keyWord);
	
	@Query("select new form.RequestForm(r,'false') from Request r where (r.title like %?2% or r.description like %?2% or r.originPlace.address like %?2% or r.destinationPlace.address like %?2%) and r.customer.id != ?1 and r.moment >= CURRENT_DATE and r.banned = false and not exists (select a from Application a where a.customer.id = ?1 and a.announcementId = r.id)")
	public Collection<RequestForm> findRequestKeyWordWithoutApplications(int customerId, String keyWord);
	
	@Query("select new form.RequestForm(r,'true') from Request r where (r.title like %?2% or r.description like %?2% or r.originPlace.address like %?2% or r.destinationPlace.address like %?2%) and r.customer.id = ?1 or exists (select a from Application a where a.customer.id = ?1 and a.announcementId = r.id)")
	public Collection<RequestForm> findRequestKeyWordIAppliedOrMine(int customerId, String keyWord);
	
	@Query("select new form.RequestForm(r,'false') from Request r where r.customer.id != ?1 and r.moment >= CURRENT_DATE and r.banned = false and not exists (select a from Application a where a.customer.id = ?1 and a.announcementId = r.id)")
	public Collection<RequestForm> getRequestsWithoutApplications(int customerId);

	@Query("select new form.RequestForm(r,'true') from Request r where r.customer.id = ?1 or exists (select a from Application a where a.customer.id = ?1 and a.announcementId = r.id)")
	public Collection<RequestForm> getRequestsIAppliedOrMine(int customerId);

	@Query("select new form.RequestForm(r,'false') from Request r)")
	public Collection<RequestForm> findAllForms();

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
