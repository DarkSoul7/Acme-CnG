
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	//Attributes
	private Status	status;
	private int		announcementId;
	private String	announcementType;


	//Constructor
	public Application() {
		super();
	}

	@NotNull
	public Status getStatus() {
		return this.status;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	@Min(1)
	public int getAnnouncementId() {
		return this.announcementId;
	}

	public void setAnnouncementId(final int announcementId) {
		this.announcementId = announcementId;
	}

	@NotBlank
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
