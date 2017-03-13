
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

	// Attributes
	private String	title;
	private Date	moment;
	private String	text;
	private int		stars;
	private int		commentableId;
	private String	commentableType;
	private boolean	banned;


	//Constructor
	public Comment() {
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

	@NotNull
	@Past
	@Temporal(value=TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Min(0)
	@Max(5)
	public int getStars() {
		return this.stars;
	}

	public void setStars(final int stars) {
		this.stars = stars;
	}

	public int getCommentableId() {
		return this.commentableId;
	}

	public void setCommentableId(final int commentableId) {
		this.commentableId = commentableId;
	}

	@NotBlank
	public String getCommentableType() {
		return this.commentableType;
	}

	public void setCommentableType(final String commentableType) {
		this.commentableType = commentableType;
	}

	public boolean isBanned() {
		return this.banned;
	}

	public void setBanned(final boolean banned) {
		this.banned = banned;
	}


	//RellationShips

	private Actor	author;


	@Valid
	@ManyToOne(optional = false)
	public Actor getAuthor() {
		return this.author;
	}

	public void setAuthor(final Actor author) {
		this.author = author;
	}

}
