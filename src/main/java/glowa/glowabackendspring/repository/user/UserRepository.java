package glowa.glowabackendspring.repository.user;

import glowa.glowabackendspring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByUserId(String userId);
    Optional<User> findByNickname(String nickname);
}
