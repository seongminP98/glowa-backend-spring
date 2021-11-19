package glowa.glowabackendspring.repository.user;

import glowa.glowabackendspring.dto.user.UserInfoDto;

import java.util.List;

public interface UserRepositoryCustom {
    List<UserInfoDto> search(String nickname, Long id);
}
