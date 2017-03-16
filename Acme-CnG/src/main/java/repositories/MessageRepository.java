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
	 * N�mero m�nimo de mensajes enviados por actores
	 **/
	@Query("select min(a.sentMessages.size)from Actor a")
	public Integer getMinimumNumberOfSentMessagesPerActor();
	
	/**
	 * N�mero m�ximo de mensajes enviados por actores
	 **/
	@Query("select max(a.sentMessages.size) from Actor a")
	public Integer getMaximumNumberOfSentMessagesPerActor();
	
	/**
	 * N�mero medio de mensajes enviados por actores
	 **/
	@Query("select avg(a.sentMessages.size) from Actor a")
	public Integer getAverageNumberOfSentMessagesPerActor();
	
	/**
	 * N�mero m�nimo de mensajes recibidos por actores
	 **/
	@Query("select min(a.receivedMessages.size)from Actor a")
	public Integer getMinimumNumberOfReceivedMessagesPerActor();
	
	/**
	 * N�mero m�ximo de mensajes recibidos por actores
	 **/
	@Query("select max(a.receivedMessages.size) from Actor a")
	public Integer getMaximumNumberOfReceivedMessagesPerActor();
	
	/**
	 * N�mero medio de mensajes recibidos por actores
	 **/
	@Query("select avg(a.receivedMessages.size) from Actor a")
	public Integer getAverageNumberOfReceivedMessagesPerActor();
	
	/**
	 * Actores con m�s mensajes enviados
	 **/
	@Query("select a from Actor a where a.sentMessages.size = (select max(a2.sentMessages.size) from Actor a2)")
	public Collection<Actor> getActorsWhoHaveSentMoreMessages();
	
	/**
	 * Actores con m�s mensajes recibidos
	 **/
	@Query("select a from Actor a where a.receivedMessages.size = (select max(a2.receivedMessages.size) from Actor a2)")
	public Collection<Actor> getActorsWhoHaveReceivedMoreMessages();
}