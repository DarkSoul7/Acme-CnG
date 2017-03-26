
package form;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import domain.Actor;

public class MessageForm {

	//Attributes
	private String	title;
	private String	text;
	private String	attachments;
	private Actor	receiver;
	private Integer	parentMessageId;


	//Constructor
	public MessageForm() {
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

	public String getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final String attachments) {
		this.attachments = attachments;
	}

	@Valid
	@NotNull
	public Actor getReceiver() {
		return this.receiver;
	}

	public void setReceiver(final Actor receiver) {
		this.receiver = receiver;
	}

	@Min(0)
	public Integer getParentMessageId() {
		return parentMessageId;
	}

	public void setParentMessageId(final Integer parentMessageId) {
		this.parentMessageId = parentMessageId;
	}

}
