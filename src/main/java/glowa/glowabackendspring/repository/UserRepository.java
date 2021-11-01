package glowa.glowabackendspring.repository;

import glowa.glowabackendspring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUserId(String userId);
    List<User> findByNickname(String nickname);
}
