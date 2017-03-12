package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

	// Attributes
	private String title;
	private Date moment;
	private String text;
	private int stars;
	private int commentableId;
	private String commentableType;
	private boolean banned;

	//Constructor
	public Comment() {
		super();
	}

	//Getters and setters
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public int getCommentableId() {
		return commentableId;
	}

	public void setCommentableId(int commentableId) {
		this.commentableId = commentableId;
	}

	public String getCommentableType() {
		return commentableType;
	}

	public void setCommentableType(String commentableType) {
		this.commentableType = commentableType;
	}

	public boolean isBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
	}
	
	

}
