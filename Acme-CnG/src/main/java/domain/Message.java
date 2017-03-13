
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	//Attributes
	private String	title;
	private String	text;
	private Date	moment;
	private String	attachments;


	//Constructor
	public Message() {
		super();
	}

	//Getters and setters
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public String getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final String attachments) {
		this.attachments = attachments;
	}


	//RellationShips

	private Actor	sender;
	private Actor	addressee;


	@Valid
	@ManyToOne(optional = false)
	public Actor getSender() {
		return this.sender;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	@Valid
	@ManyToOne(optional = false)
	public Actor getAddressee() {
		return this.addressee;
	}

	public void setAddressee(final Actor addressee) {
		this.addressee = addressee;
	}

}
