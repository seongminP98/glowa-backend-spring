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

    public int add(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user, @RequestBody @Validated FavoritesRequest request) {
        favoritesService.add(new FavoritesInfoDto(request.restaurant, request.address, request.kind), user);
        return ResponseCode.OK;
    }

    public ListResponse list(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user) {
        return new ListResponse(ResponseCode.OK, favoritesService.favoritesList(user));
    }

    @Data
    static class FavoritesRequest{
        @NotEmpty(message = "식당 이름은 비어있을 수 없습니다.")
        private String restaurant;
        private String address;
        private String kind;
    }

    @Data
    @AllArgsConstructor
    static class ListResponse{
        private int conde;
        private List<Favorites> resultList;
    }
}
