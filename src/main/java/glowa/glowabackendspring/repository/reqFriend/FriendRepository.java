package glowa.glowabackendspring.repository.reqFriend;

import glowa.glowabackendspring.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
