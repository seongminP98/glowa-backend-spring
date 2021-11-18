package glowa.glowabackendspring.dto.user;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class UserSearchDto {
    private Long id;
    private String nickname;
    private String image;

    @QueryProjection
    public UserSearchDto(Long id, String nickname, String image) {
        this.id = id;
        this.nickname = nickname;
        this.image = image;
    }
}
