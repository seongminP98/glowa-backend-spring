package glowa.glowabackendspring.service;

import glowa.glowabackendspring.domain.Friend;
import glowa.glowabackendspring.domain.User;
import glowa.glowabackendspring.dto.user.UserInfoDto;
import glowa.glowabackendspring.exception.LoginException;
import glowa.glowabackendspring.exception.UserException;
import glowa.glowabackendspring.repository.friend.FriendRepository;
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
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public List<UserInfoDto> getList(User user) { //친구목록 가져오기
        Optional<User> me = userRepository.findById(user.getId());
        if(me.isPresent()) {
            return userRepository.friendList(me.get().getId());
        } else {
            throw new LoginException("로그인 에러. 다시 로그인 해주세요.");
        }
    }

    public long delete(User me, User friend) {
        Optional<User> user1 = userRepository.findById(me.getId());
        Optional<User> user2 = userRepository.findById(friend.getId());
        if(user1.isPresent() && user2.isPresent()) {
            long result1 = friendRepository.deleteByMeAndFriend(user1.get(), user2.get());
            long result2 = friendRepository.deleteByMeAndFriend(user2.get(), user1.get());
            return result1 + result2;
        } else {
            throw new UserException("잘못된 유저입니다.");
        }
    }

}
