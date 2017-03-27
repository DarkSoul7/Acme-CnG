
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

	//Erase his or her messages, which requires previous confirmation.
	//The message owner try to delete his message
	@Test
	public void deleteMessagePositiveTestA() {
		this.authenticate("customer1");
		this.messageService.delete(57);
		final Message message = this.messageService.findOne(53);
		Assert.isTrue(message == null);
		this.unauthenticate();
	}

	//The message receiver try to delete his message
	@Test
	public void deleteMessagePositiveTestB() {
		this.authenticate("customer2");
		this.messageService.delete(58);
		final Message message = this.messageService.findOne(58);
		Assert.isTrue(message == null);
		this.unauthenticate();
	}

	//An unauthenticated actor trying to remove a message
	@Test(expected = IllegalArgumentException.class)
	public void deleteMessageNegativeTestA() {
		this.unauthenticate();
		this.messageService.delete(53);
		final Message message = this.messageService.findOne(53);
		Assert.isTrue(message == null);

	}

	//An authenticated actor trying to remove a message that is not yours (sender or receiver)
	@Test(expected = IllegalArgumentException.class)
	public void deleteMessageNegativeTestB() {
		this.authenticate("customer3");
		this.messageService.delete(57);
		final Message message = this.messageService.findOne(52);
		Assert.isTrue(message == null);

	}
}
