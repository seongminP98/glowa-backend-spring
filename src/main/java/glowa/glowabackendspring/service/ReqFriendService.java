package glowa.glowabackendspring.service;

import glowa.glowabackendspring.domain.Friend;
import glowa.glowabackendspring.domain.ReqFriends;
import glowa.glowabackendspring.domain.User;
import glowa.glowabackendspring.exception.AddFriendException;
import glowa.glowabackendspring.repository.friend.FriendRepository;
import glowa.glowabackendspring.repository.reqFriend.ReqFriendRepository;
import glowa.glowabackendspring.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReqFriendService {

    private final ReqFriendRepository reqFriendRepository;
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public ReqFriends addFriend(User me, Long friendId) {
        Optional<User> friend = userRepository.findById(friendId);
        if(friend.isEmpty()) {
            throw new AddFriendException("잘못된 친구요청입니다.");
        }

        Optional<ReqFriends> alreadyReq = reqFriendRepository.findOneByMeAndReqFriend(me, friend.get());
        if(alreadyReq.isPresent()) {
            throw new AddFriendException("이미 친구요청을 보냈습니다.");
        }

        Optional<ReqFriends> receiveReq = reqFriendRepository.findOneByMeAndReqFriend(friend.get(), me);
        if(receiveReq.isPresent()) {
            throw new AddFriendException("상대한테 친구요청을 받은 상태입니다.");
        }

        Optional<Friend> alreadyFriend = friendRepository.findOneByMeAndFriend(me, friend.get());
        if(alreadyFriend.isPresent()) {
            throw new AddFriendException("이미 친구입니다.");
        }

        return reqFriendRepository.save(new ReqFriends(me, friend.get()));
    }
}
