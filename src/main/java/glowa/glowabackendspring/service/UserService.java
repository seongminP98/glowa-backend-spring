package glowa.glowabackendspring.service;

import glowa.glowabackendspring.entity.User;
import glowa.glowabackendspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long join(User user) { //회원가입
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    public List<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public List<User> findByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    private void validateDuplicateUser(User user) {
        List<User> findByUserId = userRepository.findByUserId(user.getUserId());
        if(!findByUserId.isEmpty()) {
            throw new IllegalStateException("이미 사용중인 아이디입니다.");
        }
        List<User> findByNickname = userRepository.findByNickname(user.getNickname());
        if(!findByNickname.isEmpty()) {
            throw new IllegalStateException("이미 사용중인 닉네임입니다.");
        }
    }

}
