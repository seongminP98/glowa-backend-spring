package glowa.glowabackendspring.repository.friend;

import glowa.glowabackendspring.domain.Friend;
import glowa.glowabackendspring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long>, FriendRepositoryCustom {
    List<Friend> findAllByMe(User user);
    Optional<Friend> findOneByMeAndFriend(User me, User friend);
    long deleteByMeAndFriend(User me, User friend);
}
