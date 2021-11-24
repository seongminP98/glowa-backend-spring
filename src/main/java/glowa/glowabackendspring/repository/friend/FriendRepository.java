package glowa.glowabackendspring.repository.friend;

import glowa.glowabackendspring.domain.Friend;
import glowa.glowabackendspring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long>, FriendRepositoryCustom {
    List<Friend> findAllByMe(User user);
}
