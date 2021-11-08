package glowa.glowabackendspring.apiController;

import glowa.glowabackendspring.exception.BindingException;
import glowa.glowabackendspring.exception.ConflictException;
import glowa.glowabackendspring.exception.LoginException;
import glowa.glowabackendspring.exhandler.ErrorResult;
import glowa.glowabackendspring.service.UserService;
import glowa.glowabackendspring.domain.User;
import glowa.glowabackendspring.session.SessionConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult loginExHandler(LoginException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("400", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult bindingExHandler(BindingException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("400", e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ErrorResult bindingExHandler(ConflictException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("409", e.getMessage());
    }

    @PostMapping("/join") //회원가입
    public CreateUserResponse saveUser(@RequestBody @Validated CreateUserRequest request, BindingResult bindingResult) {
        bindingResultException(bindingResult);

        String encodedPassword = passwordEncoder.encode(request.password);
        User user = new User(request.userId, request.nickname, encodedPassword);

        Long id = userService.join(user);

        return new CreateUserResponse(id, request.userId, request.nickname);
    }

    @PostMapping("/auth/login")
    public LoginUserResponse login(@RequestBody @Validated LoginUserRequest request, BindingResult bindingResult, HttpServletRequest req) {
        bindingResultException(bindingResult);
        User user = userService.login(request.userId, request.password);

        HttpSession session = req.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, user);

        return new LoginUserResponse(user.getId(), user.getNickname());
    }

    @GetMapping("/auth/login")
    public LoginUserResponse login(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user){
        if(user == null) {
            throw new LoginException("로그인 되어있지 않음");
        }

        return new LoginUserResponse(user.getId(), user.getNickname());
    }

    @GetMapping("/auth/logout")
    public String logout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "200";
    }

    private void bindingResultException(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BindingException(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
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

    @Data
    static class LoginUserRequest {
        @NotEmpty(message = "아이디 값은 비어있을 수 없습니다.")
        private String userId;
        @NotEmpty(message = "패스워드 값은 비어있을 수 없습니다.")
        private String password;
    }

    @Data
    @AllArgsConstructor
    static class LoginUserResponse {
        private Long id;
        private String nickname;
    }
}
