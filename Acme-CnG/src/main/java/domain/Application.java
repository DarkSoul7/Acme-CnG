package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity{

	//Attributes
	private Status status;
	private int AnnouncementId;
	private String announcementType;
	
	//Constructor
	public Application() {
		super();
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getAnnouncementId() {
		return AnnouncementId;
	}

	public void setAnnouncementId(int announcementId) {
		AnnouncementId = announcementId;
	}

	public String getAnnouncementType() {
		return announcementType;
	}

	public void setAnnouncementType(String announcementType) {
		this.announcementType = announcementType;
	}
	
	
}
