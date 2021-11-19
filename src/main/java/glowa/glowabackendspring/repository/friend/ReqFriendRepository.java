package glowa.glowabackendspring.repository.friend;

import glowa.glowabackendspring.domain.ReqFriends;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReqFriendRepository extends JpaRepository<ReqFriends, Long>, ReqFriendRepositoryCustom {
    List<ReqFriends> findAllById(Long id);
}
