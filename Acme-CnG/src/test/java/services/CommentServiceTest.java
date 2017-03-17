
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Comment;
import domain.Customer;
import domain.Offer;
import domain.Request;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
//@TransactionConfiguration(defaultRollback = false)
public class CommentServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private CommentService	commentService;

	@Autowired
	private CustomerService	customerService;

	@Autowired
	private OfferService	offerService;

	@Autowired
	private RequestService	requestService;


	// Tests ------------------------------------------------------------------

	//Post a comment on commentable entity. (Actor, offer or request)
	@Test
	public void doCommentPositiveTestA() {
		this.authenticate("customer1");
		final Customer customer2 = this.customerService.findOne(48);
		Comment comment = this.commentService.create(customer2.getId(), customer2.getClass().getSimpleName());
		comment.setStars(5);
		comment.setText("Testing");
		comment.setTitle("Test");
		comment = this.commentService.save(comment);
		Assert.isTrue(comment.getId() != 0);
		this.unauthenticate();
	}

	@Test
	public void doCommentPositiveTestB() {
		this.authenticate("admin");
		final Customer customer2 = this.customerService.findOne(48);
		Comment comment = this.commentService.create(customer2.getId(), customer2.getClass().getSimpleName());
		comment.setStars(5);
		comment.setText("Testing");
		comment.setTitle("Test");
		comment = this.commentService.save(comment);
		Assert.isTrue(comment.getId() != 0);
		this.unauthenticate();
	}

	@Test
	public void doCommentPositiveTestC() {
		this.authenticate("customer1");
		final Offer offer = this.offerService.findOne(46);
		Comment comment = this.commentService.create(offer.getId(), offer.getClass().getSimpleName());
		comment.setStars(5);
		comment.setText("Testing");
		comment.setTitle("Test");
		comment = this.commentService.save(comment);
		Assert.isTrue(comment.getId() != 0);
		this.unauthenticate();
	}

	@Test
	public void doCommentPositiveTestD() {
		this.authenticate("customer2");
		final Request request = this.requestService.findOne(69);
		Comment comment = this.commentService.create(request.getId(), request.getClass().getSimpleName());
		comment.setStars(5);
		comment.setText("Testing");
		comment.setTitle("Test");
		comment = this.commentService.save(comment);
		Assert.isTrue(comment.getId() != 0);
		this.unauthenticate();
	}

	//Unauthenticated actor cannot post a comment 
	@Test(expected = IllegalArgumentException.class)
	public void doCommentNegativeTestA() {
		this.unauthenticate();
		final Request request = this.requestService.findOne(69);
		Comment comment = this.commentService.create(request.getId(), request.getClass().getSimpleName());
		comment.setStars(5);
		comment.setText("Testing");
		comment.setTitle("Test");
		comment = this.commentService.save(comment);
		Assert.isTrue(comment.getId() != 0);
	}

	//An actor cannot post comments to himself
	@Test(expected = IllegalArgumentException.class)
	public void doCommentNegativeTestB() {
		this.authenticate("customer1");
		final Customer customer = this.customerService.findOne(45);
		Comment comment = this.commentService.create(customer.getId(), customer.getClass().getSimpleName());
		comment.setStars(5);
		comment.setText("Testing");
		comment.setTitle("Test");
		comment = this.commentService.save(comment);
		Assert.isTrue(comment.getId() != 0);
		this.unauthenticate();
	}

	//Ban a comment that he or she finds inappropriate. Such comments must not be displayed 
	//to a general audience, only to the administrators and the actor who posted it.

	@Test
	public void banCommentPositiveTestA() {
		this.authenticate("admin");
		final Comment comment = this.commentService.findOne(57);
		this.commentService.banComment(comment);
		this.unauthenticate();
	}

	//Unauthenticated actor cannot ban a comment
	@Test(expected = IllegalArgumentException.class)
	public void banCommentNegativeTestA() {
		this.unauthenticate();
		final Comment comment = this.commentService.findOne(57);
		this.commentService.banComment(comment);
	}

	//A customer cannot ban a comment
	@Test(expected = IllegalArgumentException.class)
	public void banCommentNegativeTestB() {
		this.authenticate("customer");
		final Comment comment = this.commentService.findOne(57);
		this.commentService.banComment(comment);
		this.unauthenticate();
	}

	//A banned comment cannot be banned again
	@Test(expected = IllegalArgumentException.class)
	public void banCommentNegativeTestC() {
		this.authenticate("admin");
		final Comment comment = this.commentService.findOne(58);
		this.commentService.banComment(comment);
		this.unauthenticate();
	}
}
