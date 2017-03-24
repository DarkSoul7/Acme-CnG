
package form;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Place;

public class OfferForm {

	//Constructor
	public OfferForm() {
		super();
	}


	//Attributes
	private int		id;
	private String	title;
	private String	description;
	private Date	moment;
	private Boolean	banned;
	private Place	originPlace;
	private Place	destinationPlace;
	private Boolean containsApplication;


	//Getter & setter

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotNull
	public Boolean getBanned() {
		return this.banned;
	}
	public void setBanned(final Boolean banned) {
		this.banned = banned;
	}

	@Valid
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "address", column = @Column(name = "originAddress")), @AttributeOverride(name = "gpsCoordinates.latitude", column = @Column(name = "originLatitude")),
		@AttributeOverride(name = "gpsCoordinates.longitude", column = @Column(name = "originLongitude"))
	})
	public Place getOriginPlace() {
		return this.originPlace;
	}
	public void setOriginPlace(final Place originPlace) {
		this.originPlace = originPlace;
	}

	@Valid
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "address", column = @Column(name = "destinationAddress")), @AttributeOverride(name = "gpsCoordinates.latitude", column = @Column(name = "destinationLatitude")),
		@AttributeOverride(name = "gpsCoordinates.longitude", column = @Column(name = "destinationLongitude"))
	})
	public Place getDestinationPlace() {
		return this.destinationPlace;
	}
	public void setDestinationPlace(final Place destinationPlace) {
		this.destinationPlace = destinationPlace;
	}

	public Boolean getContainsApplication() {
		return containsApplication;
	}

	public void setContainsApplication(Boolean containsApplication) {
		this.containsApplication = containsApplication;
	}
	
	

}
