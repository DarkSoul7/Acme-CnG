package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Announcement extends DomainEntity {

	// Attributes
	private String title;
	private String description;
	private Date moment;
	private Boolean banned;
	private Place originPlace;
	private Place destinationPlace;

	public Announcement() {
		super();
	}

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull
	@Temporal(value=TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	@NotNull
	public Boolean getBanned() {
		return banned;
	}

	public void setBanned(Boolean banned) {
		this.banned = banned;
	}
	
	@Valid
	@Embedded
	@AttributeOverrides({@AttributeOverride(name="address", column=@Column(name="originAddress")),
						@AttributeOverride(name="gpsCoordinates.latitude", column=@Column(name="originLatitude")),
						@AttributeOverride(name="gpsCoordinates.longitude", column=@Column(name="originLongitude"))})
	public Place getOriginPlace() {
		return originPlace;
	}

	public void setOriginPlace(Place originPlace) {
		this.originPlace = originPlace;
	}

	@Valid
	@Embedded
	@AttributeOverrides({@AttributeOverride(name="address", column=@Column(name="destinationAddress")),
						@AttributeOverride(name="gpsCoordinates.latitude", column=@Column(name="destinationLatitude")),
						@AttributeOverride(name="gpsCoordinates.longitude", column=@Column(name="destinationLongitude"))})
	public Place getDestinationPlace() {
		return destinationPlace;
	}

	public void setDestinationPlace(Place destinationPlace) {
		this.destinationPlace = destinationPlace;
	}

	

}
