package glowa.glowabackendspring.apiController;

import glowa.glowabackendspring.domain.User;
import glowa.glowabackendspring.dto.schedule.InvScheduleDto;
import glowa.glowabackendspring.dto.schedule.ScheduleDto;
import glowa.glowabackendspring.exception.LoginException;
import glowa.glowabackendspring.exception.ScheduleException;
import glowa.glowabackendspring.exception.UserException;
import glowa.glowabackendspring.exhandler.ErrorResult;
import glowa.glowabackendspring.payload.ResponseCode;
import glowa.glowabackendspring.service.InvScheduleService;
import glowa.glowabackendspring.service.ScheduleService;
import glowa.glowabackendspring.session.SessionConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("schedule")
public class ScheduleApiController {

    private ScheduleService scheduleService;
    private InvScheduleService invScheduleService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult loginExHandler(LoginException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult(ResponseCode.CLIENT_ERROR, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult scheduleExHandler(ScheduleException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult(ResponseCode.CLIENT_ERROR, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult userExHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult(ResponseCode.CLIENT_ERROR, e.getMessage());
    }

    @PostMapping("/make")
    public int make(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user, MakeRequest request) {
        if (user == null) {
            throw new LoginException("로그인 되어있지 않음");
        }
        scheduleService.make(new ScheduleDto(user, request.name, request.place, request.date));
        return ResponseCode.OK;
    }

    @PostMapping("/invite")
    public int invite(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user, ScheduleRequest request) {
        if (user == null) {
            throw new LoginException("로그인 되어있지 않음");
        }
        invScheduleService.invite(user, request.friendId, request.scheduleId);
        return ResponseCode.OK;
    }

    @PostMapping("/accept")
    public int accept(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user, ScheduleRequest request) {
        if (user == null) {
            throw new LoginException("로그인 되어있지 않음");
        }
        invScheduleService.accept(request.scheduleId, request.friendId, user);
        return ResponseCode.OK;
    }

    @PostMapping("/reject")
    public int reject(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user, ScheduleRequest request) {
        if (user == null) {
            throw new LoginException("로그인 되어있지 않음");
        }
        invScheduleService.reject(request.scheduleId, request.friendId, user);
        return ResponseCode.OK;
    }

    @GetMapping("/invite/list")
    public InviteListResponse invScheduleList(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user) {
        if (user == null) {
            throw new LoginException("로그인 되어있지 않음");
        }

        return new InviteListResponse(ResponseCode.OK, invScheduleService.list(user));
    }

    @PatchMapping("/")
    public int modify(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user, ModifyRequest request) {
        if (user == null) {
            throw new LoginException("로그인 되어있지 않음");
        }

        scheduleService.modify(request.scheduleId, new ScheduleDto(request.name, request.place, request.date), user);
        return ResponseCode.OK;
    }

    @PostMapping("/transfer")
    public int transfer(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user, ScheduleRequest request) {
        if (user == null) {
            throw new LoginException("로그인 되어있지 않음");
        }

        scheduleService.transferMaster(user, request.scheduleId, request.friendId);
        return ResponseCode.OK;
    }

    @Data
    static class MakeRequest {
        @NotEmpty(message = "스케줄 이름은 비어있을 수 없습니다.")
        private String name;
        private String place;
        private LocalDateTime date;
    }

    @Data
    static class ScheduleRequest {
        @NotEmpty(message = "스케줄 id 값은 비어있을 수 없습니다.")
        private Long scheduleId;
        @NotEmpty(message = "친구 id 값은 비어있을 수 없습니다.")
        private Long friendId;
    }

    @Data
    @AllArgsConstructor
    static class InviteListResponse {
        private int code;
        private List<InvScheduleDto> invScheduleList;
    }

    @Data
    static class ModifyRequest {
        @NotEmpty(message = "스케줄 id 값은 비어있을 수 없습니다.")
        private Long scheduleId;
        private String name;
        private LocalDateTime date;
        private String place;
    }
}
