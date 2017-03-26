package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer>{
	
	@Query("select a from Application a where announcementId = ?1 and UPPER(announcementType) = ?2")
	public Collection<Application> findByAnnouncement(int idAnnouncement, String announcementType);
}