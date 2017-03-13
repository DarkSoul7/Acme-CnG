
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	//Attributes
	private Status	status;
	private int		AnnouncementId;
	private String	announcementType;


	//Constructor
	public Application() {
		super();
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	public int getAnnouncementId() {
		return this.AnnouncementId;
	}

	public void setAnnouncementId(final int announcementId) {
		this.AnnouncementId = announcementId;
	}

	public String getAnnouncementType() {
		return this.announcementType;
	}

	public void setAnnouncementType(final String announcementType) {
		this.announcementType = announcementType;
	}


	//RellationShips
	private Customer	customer;


	@Valid
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

}
