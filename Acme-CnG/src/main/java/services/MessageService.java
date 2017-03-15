package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		messageRepository.save(message);
	}

	public void delete(Message message) {
		messageRepository.delete(message);
	}

	//Other business methods
	
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