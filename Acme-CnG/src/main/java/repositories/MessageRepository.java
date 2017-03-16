package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
	
	@Query("select m from Message m where m.sender.id = ?1")
	public Collection<Message> findAllSentByActor(int actorId);
	
	@Query("select m from Message m where m.receiver.id = ?1")
	public Collection<Message> findAllReceivedByActor(int actorId);
	
	// Dashboard
	
	/**
	 * Número mínimo de mensajes enviados por actores
	 **/
	@Query("select min(a.sentMessages.size)from Actor a")
	public Integer getMinimumNumberOfSentMessagesPerActor();
	
	/**
	 * Número máximo de mensajes enviados por actores
	 **/
	@Query("select max(a.sentMessages.size) from Actor a")
	public Integer getMaximumNumberOfSentMessagesPerActor();
	
	/**
	 * Número medio de mensajes enviados por actores
	 **/
	@Query("select avg(a.sentMessages.size) from Actor a")
	public Integer getAverageNumberOfSentMessagesPerActor();
	
	/**
	 * Número mínimo de mensajes recibidos por actores
	 **/
	@Query("select min(a.receivedMessages.size)from Actor a")
	public Integer getMinimumNumberOfReceivedMessagesPerActor();
	
	/**
	 * Número máximo de mensajes recibidos por actores
	 **/
	@Query("select max(a.receivedMessages.size) from Actor a")
	public Integer getMaximumNumberOfReceivedMessagesPerActor();
	
	/**
	 * Número medio de mensajes recibidos por actores
	 **/
	@Query("select avg(a.receivedMessages.size) from Actor a")
	public Integer getAverageNumberOfReceivedMessagesPerActor();
	
	/**
	 * Actores con más mensajes enviados
	 **/
	@Query("select a from Actor a where a.sentMessages.size = (select max(a2.sentMessages.size) from Actor a2)")
	public Collection<Actor> getActorsWhoHaveSentMoreMessages();
	
	/**
	 * Actores con más mensajes recibidos
	 **/
	@Query("select a from Actor a where a.receivedMessages.size = (select max(a2.receivedMessages.size) from Actor a2)")
	public Collection<Actor> getActorsWhoHaveReceivedMoreMessages();
}