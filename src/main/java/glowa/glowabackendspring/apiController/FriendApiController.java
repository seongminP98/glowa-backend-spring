package glowa.glowabackendspring.apiController;

import glowa.glowabackendspring.domain.ReqFriends;
import glowa.glowabackendspring.domain.User;
import glowa.glowabackendspring.exception.LoginException;
import glowa.glowabackendspring.payload.ResponseCode;
import glowa.glowabackendspring.service.FriendService;
import glowa.glowabackendspring.service.ReqFriendService;
import glowa.glowabackendspring.session.SessionConst;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("friend")
public class FriendApiController {

    private final FriendService friendService;
    private final ReqFriendService reqFriendService;

    @PostMapping("/add")
    public int addFriend(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user, @RequestBody @Validated AddFriendRequest request) {
        if(user == null) {
            throw new LoginException("로그인 되어있지 않음");
        }
        reqFriendService.addFriend(user, request.friendId);
        return ResponseCode.OK;
    }

    @Data
    static class AddFriendRequest{
        @NotEmpty(message = "친구 요청하는 아이디 값은 비어있을 수 없습니다.")
        private Long friendId;
    }



}
