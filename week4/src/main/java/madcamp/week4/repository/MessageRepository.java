package madcamp.week4.repository;

import madcamp.week4.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByToIdUserId(Long userId);
    List<Message> findByToIdUserIdAndOrganizationOrganizationId(Long userId, Long organizationId);
}
