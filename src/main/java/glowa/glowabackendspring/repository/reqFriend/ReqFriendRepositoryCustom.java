package glowa.glowabackendspring.repository.reqFriend;

import glowa.glowabackendspring.domain.ReqFriends;

import java.util.List;

public interface ReqFriendRepositoryCustom {
    ReqFriends alreadyReq(Long myId, Long friendId); //이미 친구요청 보냈는지 확인
    ReqFriends checkSentReq(Long myId, Long friendId); //나한테 친구요청을 보낸 상태인지 확인
    List<ReqFriends> listReq(Long myId); //받은 친구요청 목록
    long delete(Long myId, Long friendId);
}
