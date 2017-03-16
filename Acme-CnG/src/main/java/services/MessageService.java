package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Message;

@Service
@Transactional
public class MessageService {

	//Managed repository

	@Autowired
	private MessageRepository	messageRepository;

	//Supported services
	
	@Autowired
	ActorService actorService;

	//Constructor
	
	public MessageService() {
		super();
	}

	public Message create() {
		return null;
	}

	public Collection<Message> findAll() {
		return messageRepository.findAll();
	}

	public Message findOne(int messageId) {
		return messageRepository.findOne(messageId);

	}

	public void save(Message message) {
		Actor principal = actorService.findByPrincipal();
		Assert.isTrue(principal.getId() == message.getSender().getId());
		
		messageRepository.save(message);
	}

	public void delete(Message message) {
		Actor principal = actorService.findByPrincipal();
		Assert.isTrue(principal.getId() == message.getReceiver().getId() || principal.getId() == message.getSender().getId());
		
		messageRepository.delete(message);
	}
	
	public void delete(int messageId) {
		Message message = messageRepository.findOne(messageId);
		
		this.delete(message);
	}

	//Other business methods
	
	public Collection<Message> findAllSentByPrincipal() {
		Actor principal = actorService.findByPrincipal();
		Collection<Message> result = null;
		
		result = messageRepository.findAllSentByActor(principal.getId());
		
		return result;
	}
	
	public Collection<Message> findAllReceivedByPrincipal() {
		Actor principal = actorService.findByPrincipal();
		Collection<Message> result = null;
		
		result = messageRepository.findAllReceivedByActor(principal.getId());
		
		return result;
	}
	
	// Dashboard
	
	public Integer getMinimumNumberOfSentMessagesPerActor() {
		Integer result = null;
		
		result = messageRepository.getMinimumNumberOfSentMessagesPerActor();
		
		return result;
	}
	
	public Integer getMaximumNumberOfSentMessagesPerActor() {
		Integer result = null;
		
		result = messageRepository.getMaximumNumberOfSentMessagesPerActor();
		
		return result;
	}
	
	public Integer getAverageNumberOfSentMessagesPerActor() {
		Integer result = null;
		
		result = messageRepository.getAverageNumberOfSentMessagesPerActor();
		
		return result;
	}
	
	public Integer getMinimumNumberOfReceivedMessagesPerActor() {
		Integer result = null;
		
		result = messageRepository.getMinimumNumberOfReceivedMessagesPerActor();
		
		return result;
	}
	
	public Integer getMaximumNumberOfReceivedMessagesPerActor() {
		Integer result = null;
		
		result = messageRepository.getMaximumNumberOfReceivedMessagesPerActor();
		
		return result;
	}
	
	public Integer getAverageNumberOfReceivedMessagesPerActor() {
		Integer result = null;
		
		result = messageRepository.getAverageNumberOfReceivedMessagesPerActor();
		
		return result;
	}
	
	public Collection<Actor> getActorsWhoHaveSentMoreMessages() {
		Collection<Actor> result = null;
		
		result = messageRepository.getActorsWhoHaveSentMoreMessages();
		
		return result;
	}

	public Collection<Actor> getActorsWhoHaveReceivedMoreMessages() {
		Collection<Actor> result = null;
		
		result = messageRepository.getActorsWhoHaveReceivedMoreMessages();
		
		return result;
	}

}