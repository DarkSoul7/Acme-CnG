package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Announcement extends DomainEntity {

	// Attributes
	private String title;
	private String description;
	private Date moment;
	private Boolean banned;

	public Announcement() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	public Boolean getBanned() {
		return banned;
	}

	public void setBanned(Boolean banned) {
		this.banned = banned;
	}

	//Relationship
	private Place originPlace;
	private Place destinationPlace;
	
	public Place getOriginPlace() {
		return originPlace;
	}

	public void setOriginPlace(Place originPlace) {
		this.originPlace = originPlace;
	}

	public Place getDestinationPlace() {
		return destinationPlace;
	}

	public void setDestinationPlace(Place destinationPlace) {
		this.destinationPlace = destinationPlace;
	}
	
	

}
