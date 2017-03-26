
package services;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Message;
import form.MessageForm;

@Service
@Transactional
public class MessageService {

	//Managed repository

	@Autowired
	private MessageRepository	messageRepository;

	//Supported services

	@Autowired
	ActorService				actorService;


	//Constructor

	public MessageService() {
		super();
	}

	public MessageForm create() {
		this.actorService.findByPrincipal();
		final MessageForm messageForm = new MessageForm();
 
		return messageForm;
	}

	// Este método no debe usarse nunca 
	public Collection<Message> findAll() {
		throw new IllegalArgumentException();
	}

	public Message findOne(final int messageId) {
		Message result;
		Actor principal;

		principal = actorService.findByPrincipal();
		result = this.messageRepository.findOne(messageId);

		if (result.getOriginal()) {
			Assert.isTrue(principal.getId() == result.getSender().getId());
		} else {
			Assert.isTrue(principal.getId() == result.getReceiver().getId());
		}

		return result;

	}

	public Message save(final Message message) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal.getId() == message.getSender().getId());
		Assert.notNull(message);
		Message result;
		Message copiedMessage;

		copiedMessage = this.cloneMessage(message);

		if (message.getParentMessage() != null) {
			if (message.getParentMessage().getOriginal()) {
				Assert.isTrue(principal.getId() == message.getParentMessage().getSender().getId());
			} else {
				Assert.isTrue(principal.getId() == message.getParentMessage().getReceiver().getId());
			}
		}

		result = this.messageRepository.save(message);
		this.messageRepository.save(copiedMessage);

		return result;
	}

	public void delete(final Message message) {
		final Actor principal = this.actorService.findByPrincipal();
		if (message.getOriginal()) {
			Assert.isTrue(principal.getId() == message.getSender().getId());
		} else {
			Assert.isTrue(principal.getId() == message.getReceiver().getId());
		}

		this.messageRepository.delete(message);
	}

	public void delete(final int messageId) {
		Message message = this.messageRepository.findOne(messageId);

		this.delete(message);
	}

	//Other business methods

	public Collection<Message> findAllSentByPrincipal() {
		final Actor principal = this.actorService.findByPrincipal();
		Collection<Message> result = null;

		result = this.messageRepository.findAllSentByActor(principal.getId());

		return result;
	}

	public Collection<Message> findAllReceivedByPrincipal() {
		final Actor principal = this.actorService.findByPrincipal();
		Collection<Message> result = null;

		result = this.messageRepository.findAllReceivedByActor(principal.getId());

		return result;
	}

	public Message cloneMessage(final Message message) {
		Message result = new Message();

		result.setTitle(message.getTitle());
		result.setText(message.getText());
		result.setAttachments(message.getAttachments());
		result.setMoment(message.getMoment());
		result.setOriginal(false);
		result.setSender(message.getSender());
		result.setReceiver(message.getReceiver());

		return result;
	}

	// Dashboard

	public Integer getMinimumNumberOfSentMessagesPerActor() {
		Integer result = null;

		result = this.messageRepository.getMinimumNumberOfSentMessagesPerActor();

		return result;
	}

	public Integer getMaximumNumberOfSentMessagesPerActor() {
		Integer result = null;

		result = this.messageRepository.getMaximumNumberOfSentMessagesPerActor();

		return result;
	}

	public Integer getAverageNumberOfSentMessagesPerActor() {
		Integer result = null;

		result = this.messageRepository.getAverageNumberOfSentMessagesPerActor();

		return result;
	}

	public Integer getMinimumNumberOfReceivedMessagesPerActor() {
		Integer result = null;

		result = this.messageRepository.getMinimumNumberOfReceivedMessagesPerActor();

		return result;
	}

	public Integer getMaximumNumberOfReceivedMessagesPerActor() {
		Integer result = null;

		result = this.messageRepository.getMaximumNumberOfReceivedMessagesPerActor();

		return result;
	}

	public Integer getAverageNumberOfReceivedMessagesPerActor() {
		Integer result = null;

		result = this.messageRepository.getAverageNumberOfReceivedMessagesPerActor();

		return result;
	}

	public Collection<Actor> getActorsWhoHaveSentMoreMessages() {
		Collection<Actor> result = null;

		result = this.messageRepository.getActorsWhoHaveSentMoreMessages();

		return result;
	}

	public Collection<Actor> getActorsWhoHaveReceivedMoreMessages() {
		Collection<Actor> result = null;

		result = this.messageRepository.getActorsWhoHaveReceivedMoreMessages();

		return result;
	}

	// MessageForm

	public MessageForm toFormObject(final Message message, final Boolean isReply) {
		final MessageForm result = this.create();
		String start;
		final String delimiter = System.getProperty("line.separator");

		if (isReply) {
			start = "RE: ";
			result.setReceiver(message.getSender());
		} else
			start = "FWD: ";
		result.setTitle(start + message.getTitle());
		result.setText(message.getText());
		result.setAttachments(StringUtils.replace(message.getAttachments(), ",", delimiter));

		return result;
	}
	public Message reconstruct(final MessageForm messageForm) {
		Message result;
		Actor principal;
		String attachments;
		Message parentMessage;

		principal = this.actorService.findByPrincipal();
		result = new Message();
		if (StringUtils.isNotBlank(messageForm.getAttachments()))
			attachments = this.compruebaEnlaces(messageForm.getAttachments());
		else
			attachments = "";

		result.setTitle(messageForm.getTitle());
		result.setText(messageForm.getText());
		result.setReceiver(messageForm.getReceiver());

		result.setSender(principal);
		result.setMoment(new Date());
		result.setAttachments(attachments);
		result.setId(0);
		result.setVersion(0);
		result.setOriginal(true);

		if (messageForm.getParentMessageId() != null) {
			parentMessage = this.findOne(messageForm.getParentMessageId());
			result.setParentMessage(parentMessage);
		}

		return result;
	}
	private String compruebaEnlaces(final String attachments) {
		String result;
		String delimiter = System.getProperty("line.separator");
		String[] aattachments = StringUtils.split(attachments, delimiter);
		String[] schemes = {
			"http", "https"
		};
		UrlValidator urlValidator = new UrlValidator(schemes);

		for (String attachment : aattachments)
			if (!urlValidator.isValid(attachment))
				throw new IllegalArgumentException();

		result = StringUtils.join(aattachments, ",");

		return result;
	}
}
