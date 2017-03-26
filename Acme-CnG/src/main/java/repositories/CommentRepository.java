
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("select c from Comment c where c.commentableId=?1 and (c.author.id=?2 or c.banned=false)")
	public Collection<Comment> commentsOfObject(int id, int idActor);

	@Query("select c from Comment c where c.commentableId=?1")
	public Collection<Comment> commentsAll(int id);

	//Dashboard

	//C1
	//	@Query("")
	//B1 a)
	// Average number of comments per actor
	@Query("select count(c)*1.0/(select count(a) from Actor a) from Comment c where c.commentableType = 'Customer' or c.commentableType = 'Administrator'")
	public double commentsPerActor();

	//B1 b)
	// Average number of comments per offer
	@Query("select count(c)*1.0/(select count(o) from Offer o) from Comment c where c.commentableType = 'Offer'")
	public double commentsPerOffer();

	//B1 c)
	// Average number of comments per request
	@Query("select count(c)*1.0/(select count(r) from Request r) from Comment c where c.commentableType = 'Request'")
	public double commentsPerRequest();

	//B2
	// Average number of comments posted by administrators and customers
	@Query("select avg(a.comments.size) from Actor a")
	public double avgCommentsPerActor();

	//B3
	// The actors who have posted ±10% the average number of comments per actor
	@Query("select a from Actor a where a.comments.size >= (select avg(a.comments.size)-(avg(a.comments.size)*0.1) from Actor a) and a.comments.size <= (select avg(a2.comments.size)+(avg(a2.comments.size)*0.1) from Actor a2)")
	public Collection<Actor> actorsWhoHavePostThe10PercentMessages();
}
