package glowa.glowabackendspring.apiController;

import glowa.glowabackendspring.domain.Favorites;
import glowa.glowabackendspring.domain.User;
import glowa.glowabackendspring.dto.favorites.FavoritesInfoDto;
import glowa.glowabackendspring.exception.FavoritesException;
import glowa.glowabackendspring.exception.LoginException;
import glowa.glowabackendspring.exhandler.ErrorResult;
import glowa.glowabackendspring.payload.ResponseCode;
import glowa.glowabackendspring.service.FavoritesService;
import glowa.glowabackendspring.session.SessionConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("favorites")
public class FavoritesApiController {

    private final FavoritesService favoritesService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult loginExHandler(LoginException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult(ResponseCode.CLIENT_ERROR, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult favoritesExHandler(FavoritesException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult(ResponseCode.CLIENT_ERROR, e.getMessage());
    }

    @PostMapping("/add")
    public int add(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user, @RequestBody @Validated FavoritesRequest request) {
        if (user == null) {
            throw new LoginException("로그인 되어있지 않음");
        }
        favoritesService.add(new FavoritesInfoDto(request.restaurant, request.address, request.kind), user);
        return ResponseCode.OK;
    }

    @GetMapping("/list")
    public ListResponse list(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user) {
        if (user == null) {
            throw new LoginException("로그인 되어있지 않음");
        }
        return new ListResponse(ResponseCode.OK, favoritesService.favoritesList(user));
    }

    @DeleteMapping
    public int delete(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user, @RequestParam("favoritesId") @Validated DeleteRequest request) {
        if (user == null) {
            throw new LoginException("로그인 되어있지 않음");
        }
        favoritesService.delete(user, request.favoritesId);
        return ResponseCode.OK;
    }

    @Data
    static class FavoritesRequest {
        @NotEmpty(message = "식당 이름은 비어있을 수 없습니다.")
        private String restaurant;
        private String address;
        private String kind;
    }

    @Data
    @AllArgsConstructor
    static class ListResponse {
        private int conde;
        private List<Favorites> resultList;
    }

    @Data
    static class DeleteRequest {
        @NotEmpty(message = "id값은 비어있을 수 없습니다.")
        private Long favoritesId;
    }
}
