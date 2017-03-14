package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer>{
	
	@Query("select o from Offer o where o.title like %?1% or o.description like %?1% or o.originPlace.address like %?1%")
	public Collection<Offer> findOfferKeyWord(String keyWord);
	
	//DASHBOARD C1
	@Query("select distinct(select count(o) from Offer o)*1.0/(select count(r) from Request r) from Offer o2")
	public double ratioOfferVersusRequest();
	
	//DASHBOARD C2
	@Query("select avg(c.offers.size) from Customer c")
	public double avgOfferPerCustomer();
	
	//DASHBOARD C3
	@Query("select 1.0*count(a)/(select count(o) from Offer o) from Application a")
	public double avgApplicationPerOffer();
	
	
	
}