package glowa.glowabackendspring.apiController;

import com.sun.istack.NotNull;
import glowa.glowabackendspring.exception.BindingException;
import glowa.glowabackendspring.exception.LoginException;
import glowa.glowabackendspring.exhandler.ErrorResult;
import glowa.glowabackendspring.service.UserService;
import glowa.glowabackendspring.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ErrorResult loginExHandler(LoginException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("409", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult bindingExHandler(BindingException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("400", e.getMessage());
    }

    @PostMapping("/join") //회원가입
    public CreateUserResponse saveUser(@RequestBody @Validated CreateUserRequest request, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new BindingException(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }

        String encodedPassword = passwordEncoder.encode(request.password);
        User user = new User(request.userId, request.nickname, encodedPassword);

        Long id = userService.join(user);

        return new CreateUserResponse(id, request.userId, request.nickname);
    }

    @Data
    static class CreateUserRequest {
        @NotEmpty(message = "아이디 값은 비어있을 수 없습니다.")
        private String userId;
        @NotEmpty(message = "패스워드 값은 비어있을 수 없습니다.")
        private String password;
        @NotEmpty(message = "닉네임 값은 비어있을 수 없습니다.")
        private String nickname;
    }

    @Data
    @AllArgsConstructor
    static class CreateUserResponse {
        private Long id;
        private String userId;
        private String nickname;
    }
}
