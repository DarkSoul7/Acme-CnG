
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Offer;
import form.OfferForm;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {

//	@Query("select o from Offer o where o.title like %?1% or o.description like %?1% or o.originPlace.address like %?1%")
//	public Collection<Offer> findOfferKeyWord(String keyWord);
	
	@Query("select new form.OfferForm(o,'false') from Offer o where (o.title like %?2% or o.description like %?2% or o.originPlace.address like %?2% or o.destinationPlace.address like %?2%) and o.customer.id != ?1 and o.moment >= CURRENT_DATE and o.banned = false and not exists (select a from Application a where a.customer.id = ?1 and a.announcementId = o.id)")
	public Collection<OfferForm> findOfferKeyWordWithoutApplications(int customerId, String keyWord);

	@Query("select new form.OfferForm(o,'true') from Offer o where (o.title like %?2% or o.description like %?2% or o.originPlace.address like %?2% or o.destinationPlace.address like %?2%) and o.customer.id = ?1 or exists (select a from Application a where a.customer.id = ?1 and a.announcementId = o.id)")
	public Collection<OfferForm> findOfferKeyWordIAppliedOrMine(int customerId, String keyWord);
	
	@Query("select new form.OfferForm(o,'false') from Offer o where o.customer.id != ?1 and o.moment >= CURRENT_DATE and o.banned = false and not exists (select a from Application a where a.customer.id = ?1 and a.announcementId = o.id)")
	public Collection<OfferForm> getOffersWithoutApplications(int customerId);

	@Query("select new form.OfferForm(o,'true') from Offer o where o.customer.id = ?1 or exists (select a from Application a where a.customer.id = ?1 and a.announcementId = o.id)")
	public Collection<OfferForm> getOffersIAppliedOrMine(int customerId);

	@Query("select new form.OfferForm(o,'false') from Offer o)")
	public Collection<OfferForm> findAllForms();

	//DASHBOARD C1
	@Query("select count(o)*1.0/(select count(a) from Announcement a) from Offer o")
	public double offerAvg();

	//DASHBOARD C2
	@Query("select avg(c.offers.size) from Customer c")
	public double avgOfferPerCustomer();

	//DASHBOARD C3
	@Query("select 1.0*count(a)/(select count(o) from Offer o) from Application a")
	public double avgApplicationPerOffer();

}
