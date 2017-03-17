
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

	//Exchange messages with other actors.
	@Test
	public void exchangeMessagesPositiveTestA() {
		this.authenticate("customer1");
		final Actor receiver = this.actorService.findOne(48);
		final MessageForm messageForm = this.messageService.create();
		messageForm.setReceiver(receiver);
		messageForm.setAttachments("http://www.mega-full-1-link-ultraISO.es");
		messageForm.setText("Test");
		messageForm.setTitle("Testing");

		Message message = this.messageService.reconstruct(messageForm);
		message = this.messageService.save(message);
		Assert.isTrue(message.getId() != 0);
		this.unauthenticate();
	}

	//Sending message to himself
	@Test
	public void exchangeMessagesPositiveTestB() {
		this.authenticate("customer1");
		final Actor receiver = this.actorService.findByPrincipal();
		final MessageForm messageForm = this.messageService.create();
		messageForm.setReceiver(receiver);
		messageForm.setAttachments("http://www.mega-full-1-link-ultraISO.es");
		messageForm.setText("Test");
		messageForm.setTitle("Testing");

		Message message = this.messageService.reconstruct(messageForm);
		message = this.messageService.save(message);
		Assert.isTrue(message.getId() != 0);
		this.unauthenticate();
	}

	//Unauthenticated actor cannot send messages
	@Test(expected = IllegalArgumentException.class)
	public void exchangeMessagesNegativeTestA() {
		this.unauthenticate();
		final Actor receiver = this.actorService.findByPrincipal();
		final MessageForm messageForm = this.messageService.create();
		messageForm.setReceiver(receiver);
		messageForm.setAttachments("http://www.mega-full-1-link-ultraISO.es");
		messageForm.setText("Test");
		messageForm.setTitle("Testing");

		Message message = this.messageService.reconstruct(messageForm);
		message = this.messageService.save(message);
		Assert.isTrue(message.getId() != 0);
	}

	//A message cannot be null
	@Test(expected = NullPointerException.class)
	public void exchangeMessagesNegativeTestB() {
		this.authenticate("admin");
		final Actor receiver = this.actorService.findByPrincipal();
		final MessageForm messageForm = this.messageService.create();
		messageForm.setReceiver(receiver);
		messageForm.setAttachments("http://www.mega-full-1-link-ultraISO.es");
		messageForm.setText("Test");
		messageForm.setTitle("Testing");

		Message message = this.messageService.reconstruct(messageForm);
		message = null;
		message = this.messageService.save(message);
		Assert.isTrue(message.getId() != 0);
	}

	//The sender must be authenticated during the sending message process
	@Test(expected = IllegalArgumentException.class)
	public void exchangeMessagesNegativeTestC() {
		this.authenticate("admin");
		final Actor receiver = this.actorService.findByPrincipal();
		final MessageForm messageForm = this.messageService.create();
		messageForm.setReceiver(receiver);
		messageForm.setAttachments("http://www.mega-full-1-link-ultraISO.es");
		messageForm.setText("Test");
		messageForm.setTitle("Testing");

		Message message = this.messageService.reconstruct(messageForm);
		this.authenticate("customer2");
		message = this.messageService.save(message);
		Assert.isTrue(message.getId() != 0);
	}

	//Erase his or her messages, which requires previous confirmation.
	//The message owner try to delete his message
	@Test
	public void deleteMessagePositiveTestA() {
		this.authenticate("customer1");
		this.messageService.delete(53);
		final Message message = this.messageService.findOne(53);
		Assert.isTrue(message == null);
		this.unauthenticate();
	}

	//The message receiver try to delete his message
	@Test
	public void deleteMessagePositiveTestB() {
		this.authenticate("customer2");
		this.messageService.delete(56);
		final Message message = this.messageService.findOne(56);
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
		this.messageService.delete(52);
		final Message message = this.messageService.findOne(52);
		Assert.isTrue(message == null);

	}
}
