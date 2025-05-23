package madcamp.week4.repository;

import madcamp.week4.model.User;
import madcamp.week4.model.UserOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserOrganizationRepository extends JpaRepository<UserOrganization, Long> {
    Optional<UserOrganization> findByUserUserIdAndOrganizationOrganizationId(Long userId, Long organizationId);
    Optional<List<UserOrganization>> findByUserUserId(Long userId);
}
