
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Message;
import form.MessageForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
//@TransactionConfiguration(defaultRollback = false)
public class MessageServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	// Tests ------------------------------------------------------------------

	/***
	 * Test cases:
	 * 1º Good test: actor sending a message to other actor -> expected: message exchanged
	 * 2º Good test: actor sending a message to himself -> expected: message exchanged
	 * 3º Bad test: an unauthenticated actor cannot exchange messages -> expected: IllegalArgumentException
	 */
	@Test
	public void exchangeMessagesDriver() {
		final Object testingData[][] = {
			//current actor, receiver id, Expected exception
			{
				"admin", 58, null
			}, {
				"customer1", 55, null
			}, {
				null, 58, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.exchangeMessagesTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void exchangeMessagesTemplate(final String principal, final int receiverId, final Class<?> expectedException) {

		Class<?> caught = null;
		try {
			this.authenticate(principal);
			final Actor receiver = this.actorService.findOne(receiverId);
			final MessageForm messageForm = this.messageService.create();
			messageForm.setReceiver(receiver);
			messageForm.setAttachments("http://www.mega-full-1-link-ultraISO.es");
			messageForm.setText("Test");
			messageForm.setTitle("Testing");

			Message message = this.messageService.reconstruct(messageForm);
			message = this.messageService.save(message);
			Assert.isTrue(message.getId() != 0);
			this.unauthenticate();

		} catch (final Throwable e) {
			caught = e.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Test cases:
	 * 1º Good test: actor list his received message and reply to them -> expected: reply done
	 * 2º Bad test: an unauthenticated actor cannot exchange messages -> expected: IllegalArgumentException
	 */
	@Test
	public void listMessagesAndReplyDriver() {
		final Object testingData[][] = {
			//current actor(sender), receiver id, message to reply id, Expected exception
			{
				"customer1", 54, 63, null
			}, {
				null, 55, 62, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.listMessagesAndReplyTemplate((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void listMessagesAndReplyTemplate(final String principal, final int receiverId, final int messageId, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final MessageForm replyForm = this.messageService.create();
			replyForm.setParentMessageId(messageId);
			final Actor actor = this.actorService.findOne(receiverId);
			replyForm.setReceiver(actor);
			replyForm.setText("Testing");
			replyForm.setTitle("Test");

			final Message reply = this.messageService.reconstruct(replyForm);
			this.messageService.save(reply);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Test cases:
	 * 1º Good test: actor list his received message and reply to them -> expected: forward done
	 * 2º Good test: actor list his received message and forward to himself -> expected: forward done
	 * 3º Bad test: an unauthenticated actor cannot exchange messages -> expected: IllegalArgumentException
	 */
	@Test
	public void listMessagesAndForwardDriver() {
		final Object testingData[][] = {
			//current actor(sender), receiver id, message to forward id, Expected exception
			{
				"customer1", 54, 63, null
			}, {
				"customer1", 55, 63, null
			}, {
				null, 55, 62, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.listMessagesAndForwardTemplate((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void listMessagesAndForwardTemplate(final String principal, final int receiverId, final int messageId, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Actor receiver = this.actorService.findOne(receiverId);
			final Message message = this.messageService.findOne(messageId);
			final Message forwardMessage = this.messageService.cloneMessage(message);
			forwardMessage.setReceiver(receiver);
			this.messageService.save(forwardMessage);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Test cases:
	 * 1º Good test -> expected: message deleted
	 * 2º Bad test: an actor who is not the message owner cannot delete it -> expected: IllegalArgumentException
	 * 3º Bad test: an unauthenticated actor cannot exchange messages -> expected: IllegalArgumentException
	 */
	@Test
	public void eraseMessagesDriver() {
		final Object testingData[][] = {
			//current actor(sender),  message id, Expected exception
			{
				"customer1", 64, null
			}, {
				"customer1", 62, IllegalArgumentException.class
			}, {
				null, 64, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.eraseMessagesTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void eraseMessagesTemplate(final String principal, final int messageId, final Class<?> expectedException) {
		Class<?> caught = null;

		try {

			this.authenticate(principal);
			this.messageService.delete(messageId);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);
	}

}
