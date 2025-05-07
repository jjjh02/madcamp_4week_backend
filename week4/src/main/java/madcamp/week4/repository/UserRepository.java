package madcamp.week4.repository;

import madcamp.week4.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUserName(String userName);
    Optional<User> findByUserId(Long userId);

    Optional<User> findByRefreshToken(String refreshToken);
}
