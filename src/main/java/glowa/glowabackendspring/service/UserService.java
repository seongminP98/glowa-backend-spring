package glowa.glowabackendspring.service;

import glowa.glowabackendspring.domain.User;
import glowa.glowabackendspring.dto.user.UserInfoDto;
import glowa.glowabackendspring.exception.ConflictException;
import glowa.glowabackendspring.exception.LoginException;
import glowa.glowabackendspring.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(User user) { //회원가입
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    public User login(String loginId, String password) {
        User user = userRepository.findByUserId(loginId).orElse(null);
        if(user == null) {
            throw new LoginException("잘못된 아이디입니다.");
        }
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new LoginException("잘못된 비밀번호입니다.");
        }
        return user;
    }

    public List<UserInfoDto> search(String nickname, Long userId) {
        return userRepository.search(nickname, userId);
    }

    private void validateDuplicateUser(User user) {
        Optional<User> findByUserId = userRepository.findByUserId(user.getUserId());
        if(findByUserId.isPresent()) {
            throw new ConflictException("이미 사용중인 아이디입니다.");
        }
        Optional<User> findByNickname = userRepository.findByNickname(user.getNickname());
        if(findByNickname.isPresent()) {
            throw new ConflictException("이미 사용중인 닉네임입니다.");
        }
    }

}
