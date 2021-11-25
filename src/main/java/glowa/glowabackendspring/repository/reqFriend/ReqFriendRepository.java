package glowa.glowabackendspring.repository.reqFriend;

import glowa.glowabackendspring.domain.ReqFriends;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReqFriendRepository extends JpaRepository<ReqFriends, Long>, ReqFriendRepositoryCustom {
    List<ReqFriends> findAllById(Long id);
    Optional<ReqFriends> findOneByMeAndReqFriend(Long myId, Long friendId);
}
