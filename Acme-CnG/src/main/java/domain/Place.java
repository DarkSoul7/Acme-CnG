package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class Place {

	//Attributes
	private String address;
	private GpsCoordinates gpsCoordinates;
	
	//Constructor
	public Place() {
		super();
	}

	//Getters and setters
	
	@NotBlank
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public GpsCoordinates getGpsCoordinates() {
		return gpsCoordinates;
	}

	public void setGpsCoordinates(GpsCoordinates gpsCoordinates) {
		this.gpsCoordinates = gpsCoordinates;
	}
	
}
