package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.MessageRepository;
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

}