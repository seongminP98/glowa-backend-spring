package glowa.glowabackendspring.service;

import glowa.glowabackendspring.domain.Friend;
import glowa.glowabackendspring.domain.ReqFriends;
import glowa.glowabackendspring.domain.User;
import glowa.glowabackendspring.dto.user.UserInfoDto;
import glowa.glowabackendspring.exception.FriendException;
import glowa.glowabackendspring.repository.friend.FriendRepository;
import glowa.glowabackendspring.repository.reqFriend.ReqFriendRepository;
import glowa.glowabackendspring.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReqFriendService {

    private final ReqFriendRepository reqFriendRepository;
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReqFriends addFriend(User me, Long friendId) {
        Optional<User> friend = userRepository.findById(friendId);
        if(friend.isEmpty()) {
            throw new FriendException("잘못된 친구요청입니다.");
        }

        Optional<ReqFriends> alreadyReq = reqFriendRepository.findOneByMeAndReqFriend(me, friend.get());
        if(alreadyReq.isPresent()) {
            throw new FriendException("이미 친구요청을 보냈습니다.");
        }

        Optional<ReqFriends> receiveReq = reqFriendRepository.findOneByMeAndReqFriend(friend.get(), me);
        if(receiveReq.isPresent()) {
            throw new FriendException("상대한테 친구요청을 받은 상태입니다.");
        }

        Optional<Friend> alreadyFriend = friendRepository.findOneByMeAndFriend(me, friend.get());
        if(alreadyFriend.isPresent()) {
            throw new FriendException("이미 친구입니다.");
        }

        return reqFriendRepository.save(new ReqFriends(me, friend.get()));
    }

    public List<UserInfoDto> reqList(User me) {
        List<UserInfoDto> friendReqList = reqFriendRepository.reqList(me.getId());
        if(friendReqList.size() == 0) {
            throw new FriendException("받은 친구요청이 없습니다.");
        }

        return friendReqList;
    }

    @Transactional
    public void acceptReq(User me, Long reqId) {
        Optional<User> reqFriend = userRepository.findById(reqId);
        Optional<User> userMe = userRepository.findById(me.getId());
        if (reqFriend.isEmpty() || userMe.isEmpty()) {
            throw new FriendException("잘못된 요청입니다.");
        }
        Friend friend = new Friend(userMe.get(), reqFriend.get());
        friendRepository.save(friend);

        reqFriendRepository.delete(reqId, me.getId());
    }

    @Transactional
    public long reject(User me, Long reqId) {
        Optional<User> reqFriend = userRepository.findById(reqId);
        Optional<User> userMe = userRepository.findById(me.getId());
        if (reqFriend.isEmpty() || userMe.isEmpty()) {
            throw new FriendException("잘못된 요청입니다.");
        }

        return reqFriendRepository.delete(reqId, me.getId());
    }
}
