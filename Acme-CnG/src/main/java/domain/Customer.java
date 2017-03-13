
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	//Attributes

	private CreditCard	creditCard;
	private double		totalfee;


	//Constructor

	public Customer() {
		super();
	}

	//Getter & setter

	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@Transient
	public double getTotalfee() {
		return this.totalfee;
	}

	public void setTotalfee(final double totalfee) {
		this.totalfee = totalfee;
	}


	//RellationShips

	private Collection<Offer>		offers;
	private Collection<Request>		requests;
	private Collection<Application>	applications;


	@Valid
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	public Collection<Offer> getOffers() {
		return this.offers;
	}

	public void setOffers(final Collection<Offer> offers) {
		this.offers = offers;
	}

	@Valid
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	public Collection<Request> getRequests() {
		return this.requests;
	}

	public void setRequests(final Collection<Request> requests) {
		this.requests = requests;
	}

	@Valid
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

}
