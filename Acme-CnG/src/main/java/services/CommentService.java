
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import domain.Actor;
import domain.Administrator;
import domain.Comment;
import form.CommentForm;
import repositories.CommentRepository;

@Service
@Transactional
public class CommentService {

	//Managed repository

	@Autowired
	private CommentRepository		commentRepository;

	//Supported services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;


	//Constructor

	public CommentService() {
		super();
	}

	public Comment create(final int commentableId, final String commentableType) {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(commentableId != actor.getId());

		final Comment result = new Comment();

		result.setCommentableId(commentableId);
		result.setCommentableType(commentableType);

		return result;
	}

	public Collection<Comment> findAll() {
		return this.commentRepository.findAll();
	}

	public Comment findOne(final int commentId) {
		return this.commentRepository.findOne(commentId);

	}

	public Collection<Comment> commentsOfObject(final int id, final int authorId) {
		return this.commentRepository.commentsOfObject(id, authorId);
	}

	public Collection<Comment> commentsAll(final int id) {
		return this.commentRepository.commentsAll(id);
	}

	public Comment save(final Comment comment) {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(comment);
		Comment result;

		if (comment.getId() != 0 && actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMINISTRATOR"))
			result = comment;
		else {
			comment.setAuthor(actor);
			Assert.isTrue(comment.getAuthor().equals(actor));
			Assert.isTrue(comment.getCommentableId() != actor.getId());
			comment.setMoment(new Date(System.currentTimeMillis() - 1000));
			comment.setBanned(false);

		}

		result = this.commentRepository.save(comment);
		return result;
	}
	public void delete(final Comment comment) {

		this.commentRepository.delete(comment);
	}

	//Other business methods

	public void banComment(final Comment comment) {
		Assert.notNull(comment);
		final Administrator administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);
		Assert.isTrue(comment.isBanned() == false);

		comment.setBanned(true);
		this.save(comment);
	}

	//Dashboard

	public double commentsPerActor() {
		return this.commentRepository.commentsPerActor();
	}

	public double commentsPerOffer() {
		return this.commentRepository.commentsPerOffer();
	}

	public double commentsPerRequest() {
		return this.commentRepository.commentsPerRequest();
	}

	public double avgCommentsPerActor() {
		return this.commentRepository.avgCommentsPerActor();
	}

	public Collection<Actor> actorsWhoHavePostThe10PercentMessages() {
		return this.commentRepository.actorsWhoHavePostThe10PercentMessages();
	}

	public Comment reconstruct(final CommentForm commentForm, final BindingResult binding) {
		Assert.notNull(commentForm);

		final Comment result = new Comment();
		result.setStars(commentForm.getStars());
		result.setCommentableId(commentForm.getCommentableId());
		result.setCommentableType(commentForm.getCommentableType());
		result.setText(commentForm.getText());
		result.setTitle(commentForm.getTitle());

		return result;
	}

}
