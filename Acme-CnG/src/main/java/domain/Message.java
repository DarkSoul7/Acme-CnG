
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	//Attributes
	private String	title;
	private String	text;
	private Date	moment;
	private String	attachments;
	private Boolean	original;


	//Constructor
	public Message() {
		super();
	}

	//Getters and setters

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Past
	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
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

	@NotNull
	public Boolean getOriginal() {
		return original;
	}

	public void setOriginal(final Boolean original) {
		this.original = original;
	}


	//RellationShips

	private Actor	sender;
	private Actor	receiver;
	private Message	childMessage;
	private Message	parentMessage;


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
	public Actor getReceiver() {
		return this.receiver;
	}

	public void setReceiver(final Actor receiver) {
		this.receiver = receiver;
	}

	@Valid
	@OneToOne(mappedBy = "parentMessage", optional = true)
	public Message getChildMessage() {
		return childMessage;
	}

	public void setChildMessage(final Message childMessage) {
		this.childMessage = childMessage;
	}

	@Valid
	@OneToOne(optional = true)
	public Message getParentMessage() {
		return parentMessage;
	}

	public void setParentMessage(final Message parentMessage) {
		this.parentMessage = parentMessage;
	}


	{

	}

}
