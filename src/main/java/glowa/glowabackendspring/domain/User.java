package glowa.glowabackendspring.entity;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String userId;
    @NotNull
    private String nickname;
    @NotNull
    private String password;
    private String image;

    public User(String userId, String nickname, String password) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
    }
}
