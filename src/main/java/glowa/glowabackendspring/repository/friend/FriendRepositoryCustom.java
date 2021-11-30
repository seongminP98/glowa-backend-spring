package glowa.glowabackendspring.repository.friend;

import glowa.glowabackendspring.domain.Friend;
import glowa.glowabackendspring.dto.user.UserInfoDto;

import java.util.List;

public interface FriendRepositoryCustom {
    Friend friend(Long myId, Long friendId);
    List<UserInfoDto> friendList(Long myId);
}
