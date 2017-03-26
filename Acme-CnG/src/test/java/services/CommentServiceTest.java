
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

	/***
	 * Post a comment on another actor, on an offer, or a request.
	 * Test cases:
	 * 1º Good test; A customer comment on other customer. expected -> comment persisted
	 * 2º Good test; A administrator comment on other customer. expected -> comment persisted
	 * 3º Good test; A customer comment an offer. expected -> comment persisted
	 * 4º Good test; A customer comment a request customer. expected -> comment persisted
	 * 5º Bad test; An unauthenticated actor cannot post comments. expected -> IllegalArgumentException
	 * 5º Bad test; An authenticated actor cannot post comments to himself. expected -> IllegalArgumentException
	 */
	@Test
	public void postCommentDriver() {
		final Object testingData[][] = {
			//Actor, commentable id, commentable type, expected exception
			{
				//Comment of customer
				"customer1", 53, "Customer", null
			}, {
				//Comment on customer
				"admin", 53, "Customer", null
			}, { //Comment on offer
				"customer1", 51, "Offer", null
			}, {
				//Comment on request
				"customer2", 74, "Request", null
			}, {
				null, 50, "Customer", IllegalArgumentException.class
			}, {
				"customer1", 50, "Customer", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.postCommentTemplate((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void postCommentTemplate(final String principal, final int commentableId, final String commentableType, final Class<?> expectedException) {

		Class<?> caught = null;

		try {
			this.authenticate(principal);
			Comment comment = null;

			if (commentableType.equals("Customer")) {
				final Customer customer2 = this.customerService.findOne(commentableId);
				comment = this.commentService.create(customer2.getId(), commentableType);
			} else if (commentableType.equals("Offer")) {
				final Offer offer = this.offerService.findOne(commentableId);
				comment = this.commentService.create(offer.getId(), commentableType);
			} else if (commentableType.equals("Request")) {
				final Request request = this.requestService.findOne(commentableId);
				comment = this.commentService.create(request.getId(), commentableType);
			}

			comment.setStars(5);
			comment.setText("Testing");
			comment.setTitle("Test");
			comment = this.commentService.save(comment);
			Assert.isTrue(comment.getId() != 0);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);

	}

	/***
	 * Ban a comment
	 * Test cases:
	 * 1º Good test; A logged Administrator ban a comment -> expected: comment banned
	 * 2º Bad test; An unauthenticated actor cannot ban a comment -> expected: IllegalArgumentException
	 * 3º Bad test; A customer cannot ban a comment -> expected: IllegalArgumentException
	 * 4º Bad test; A banned comment cannot be re-banned -> expected: IllegalArgumentException
	 */
	@Test
	public void banCommentDriver() {
		final Object testingData[][] = {
			//Actor, comment id, expected exception
			{
				"admin", 62, null
			}, {
				null, 62, IllegalArgumentException.class
			}, {
				"customer1", 66, IllegalArgumentException.class
			}, {
				"admin", 63, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.banCommentTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void banCommentTemplate(final String principal, final int commentId, final Class<?> expectedException) {

		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Comment comment = this.commentService.findOne(commentId);
			this.commentService.banComment(comment);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

}
