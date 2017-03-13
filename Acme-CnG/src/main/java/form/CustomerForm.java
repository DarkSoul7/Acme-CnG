
package form;

import javax.persistence.Column;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

public class CustomerForm {

	//Constructor
	public CustomerForm() {
		super();
	}


	//Attributes
	private String	username;
	private String	password;
	private String	repeatPassword;
	private boolean	acceptCondition;
	private String	name;
	private String	surnames;
	private String	phone;
	private String	email;


	//Getter & setter
	@Size(min = 5, max = 32)
	@Column(unique = true)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getUsername() {
		return this.username;
	}
	public void setUsername(final String username) {
		this.username = username;
	}

	@Size(min = 5, max = 32)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPassword() {
		return this.password;
	}
	public void setPassword(final String password) {
		this.password = password;
	}

	@Size(min = 5, max = 32)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getRepeatPassword() {
		return this.repeatPassword;
	}
	public void setRepeatPassword(final String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	@AssertTrue
	public boolean isAcceptCondition() {
		return this.acceptCondition;
	}
	public void setAcceptCondition(final boolean acceptCondition) {
		this.acceptCondition = acceptCondition;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public String getSurnames() {
		return this.surnames;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public void setSurnames(final String surnames) {
		this.surnames = surnames;
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

}
