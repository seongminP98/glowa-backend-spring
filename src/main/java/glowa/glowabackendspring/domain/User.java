package glowa.glowabackendspring.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
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

    @OneToMany(mappedBy = "friend")
    private List<Friend> friends = new ArrayList<>();

    @OneToMany(mappedBy = "reqFriend")
    private List<ReqFriends> reqFriends = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ScheduleManage> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Favorites> favorites = new ArrayList<>();

    @OneToMany(mappedBy = "master")
    private List<Schedule> masterSchedules = new ArrayList<>();

    public User(String userId, String nickname, String password) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
    }
}
