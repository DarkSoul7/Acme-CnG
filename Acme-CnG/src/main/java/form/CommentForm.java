
package form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

public class CommentForm {

	//Constructor
	public CommentForm() {
		super();
	}


	private String	title;
	private String	text;
	private int		stars;
	private int		commentableId;
	private String	commentableType;


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

	public String getCommentableType() {
		return this.commentableType;
	}

	public void setCommentableType(final String commentableType) {
		this.commentableType = commentableType;
	}

}
