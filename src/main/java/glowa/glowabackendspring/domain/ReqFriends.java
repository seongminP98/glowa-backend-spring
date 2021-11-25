package glowa.glowabackendspring.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReqFriends extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "me")
    private User me;

    @ManyToOne
    @JoinColumn(name = "req_friend")
    private User reqFriend;

    public ReqFriends(User me, User reqFriend) {
        this.me = me;
        this.reqFriend = reqFriend;
    }
}
