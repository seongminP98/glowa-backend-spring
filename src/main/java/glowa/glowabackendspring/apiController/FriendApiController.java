package glowa.glowabackendspring.apiController;

import glowa.glowabackendspring.domain.ReqFriends;
import glowa.glowabackendspring.domain.User;
import glowa.glowabackendspring.dto.user.UserInfoDto;
import glowa.glowabackendspring.exception.FriendException;
import glowa.glowabackendspring.exception.LoginException;
import glowa.glowabackendspring.exception.UserException;
import glowa.glowabackendspring.exhandler.ErrorResult;
import glowa.glowabackendspring.payload.ResponseCode;
import glowa.glowabackendspring.service.FriendService;
import glowa.glowabackendspring.service.ReqFriendService;
import glowa.glowabackendspring.session.SessionConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("friend")
public class FriendApiController {

    private final FriendService friendService;
    private final ReqFriendService reqFriendService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult loginExHandler(LoginException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult(ResponseCode.CLIENT_ERROR, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult friendExHandler(FriendException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult(ResponseCode.CLIENT_ERROR, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult userExHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult(ResponseCode.CLIENT_ERROR, e.getMessage());
    }

    @PostMapping("/add")
    public int addFriend(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user, @RequestBody @Validated FriendRequest request) {
        if (user == null) {
            throw new LoginException("로그인 되어있지 않음");
        }
        reqFriendService.addFriend(user, request.friendId);
        return ResponseCode.OK;
    }

    @GetMapping("/req/list")
    public ListResponse requestFriendList(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user) {
        if (user == null) {
            throw new LoginException("로그인 되어있지 않음");
        }

        return new ListResponse(ResponseCode.OK, reqFriendService.reqList(user));
    }

    @PostMapping("/accept")
    public int accept(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user, @RequestBody @Validated FriendRequest request) {
        if (user == null) {
            throw new LoginException("로그인 되어있지 않음");
        }

        reqFriendService.acceptReq(user, request.friendId);
        return ResponseCode.OK;
    }

    @Data
    static class FriendRequest {
        @NotEmpty(message = "친구 아이디 값은 비어있을 수 없습니다.")
        private Long friendId;
    }

    @Data
    @AllArgsConstructor
    static class ListResponse {
        int code;
        List<UserInfoDto> userInfoList;
    }


}
