package madcamp.week4.repository;

import madcamp.week4.model.Organization;
import madcamp.week4.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
