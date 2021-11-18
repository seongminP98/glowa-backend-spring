package glowa.glowabackendspring.repository.user;

import glowa.glowabackendspring.dto.user.UserSearchDto;

import java.util.List;

public interface UserRepositoryCustom {
    List<UserSearchDto> search(String nickname, Long id);
}
