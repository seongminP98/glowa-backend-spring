package glowa.glowabackendspring.repository.user;

import glowa.glowabackendspring.domain.User;
import glowa.glowabackendspring.dto.user.UserInfoDto;

import java.util.List;

public interface UserRepositoryCustom {
    List<UserInfoDto> search(String nickname, Long id);
    List<UserInfoDto> friendList(Long id);
    List<User> friendListUser(Long id);
}
