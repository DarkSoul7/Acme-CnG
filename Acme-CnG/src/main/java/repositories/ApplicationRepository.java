
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select a from Application a where announcementId = ?1 and UPPER(announcementType) = ?2")
	public Collection<Application> findByAnnouncement(int idAnnouncement, String announcementType);

	//DashBoard C-3
	@Query("select count(a)*1.0/(select count(an) from Announcement an) from Application a")
	public double avgApplicationsPerAnnouncement();
}
