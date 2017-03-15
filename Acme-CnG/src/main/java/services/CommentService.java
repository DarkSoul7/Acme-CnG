
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Actor;
import domain.Administrator;
import domain.Comment;

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

		final Comment result = new Comment();
		result.setAuthor(actor);

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

	public Comment save(final Comment comment) {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(comment);
		Comment result;

		if (actor.getUserAccount().getAuthorities().iterator().next().equals("ADMINISTRATOR"))
			result = this.save(comment);
		else {
			Assert.isTrue(comment.getAuthor().equals(actor));
			Assert.isTrue(comment.getCommentableId() != actor.getId());
			comment.setMoment(new Date(System.currentTimeMillis() - 1000));
			comment.setBanned(false);
			result = this.commentRepository.save(comment);
		}

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

//	public Collection<Actor> actorsWhoHavePostThe10PercentMessages() {
//		return this.commentRepository.actorsWhoHavePostThe10PercentMessages();
//	}
}
