package glowa.glowabackendspring.repository.friend;

import glowa.glowabackendspring.domain.Friend;

public interface FriendRepositoryCustom {
    Friend friend(Long myId, Long friendId);
}
