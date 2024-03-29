
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = "email")
})
public class Actor extends DomainEntity {

	// Attributes
	private String	fullName;
	private String	phone;
	private String	email;


	// Constructor
	public Actor() {
		super();
	}

	//Getter & setter

	
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@NotBlank
	@Pattern(regexp = "((\\+|00)\\d{2,4}(\\s)?)?\\d{9,13}")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPhone() {
		return this.phone;
	}


	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@NotBlank
	@Email
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}


	//RelationShips

	private UserAccount			userAccount;
	private Collection<Message>	sentMessages;
	private Collection<Message>	receivedMessages;
	private Collection<Comment>	comments;


	@Valid
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@Valid
	@OneToMany(mappedBy = "sender")
	public Collection<Message> getSentMessages() {
		return this.sentMessages;
	}

	public void setSentMessages(final Collection<Message> sentMessages) {
		this.sentMessages = sentMessages;
	}

	@Valid
	@OneToMany(mappedBy = "receiver")
	public Collection<Message> getReceivedMessages() {
		return this.receivedMessages;
	}

	public void setReceivedMessages(final Collection<Message> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}

	@Valid
	@OneToMany(mappedBy = "author")
	public Collection<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}

}
