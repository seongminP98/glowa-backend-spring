package glowa.glowabackendspring.apiController;

import glowa.glowabackendspring.service.UserService;
import glowa.glowabackendspring.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public CreateUserResponse saveUser(@RequestBody @Validated CreateUserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.password);
        User user = new User(request.userId, request.nickname, encodedPassword);

        Long id = userService.join(user);

        CreateUserResponse response = new CreateUserResponse(id, request.userId, request.nickname);
        return response;
//        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/join/id")
    public Boolean existUserId(@RequestBody @Validated existUserIdRequest request) {
        List<User> findByUserId = userService.findByUserId(request.getUserId());
        return findByUserId.isEmpty();
    }

    @PostMapping("/join/nickname")
    public Boolean existNickname(@RequestBody existNicknameRequest request) {
        List<User> findByNickname = userService.findByNickname(request.getNickname());
        return findByNickname.isEmpty();
    }

    @Data
    static class existUserIdRequest {
        private String userId;
    }

    @Data
    static class existNicknameRequest {
        private String nickname;
    }

    @Data
    static class CreateUserRequest {
        private String userId;
        private String password;
        private String nickname;
    }

    @Data
    static class CreateUserResponse {
        private Long id;
        private String userId;
        private String nickname;

        public CreateUserResponse(Long id, String userId, String nickname) {
            this.id = id;
            this.userId = userId;
            this.nickname = nickname;
        }
    }
}
