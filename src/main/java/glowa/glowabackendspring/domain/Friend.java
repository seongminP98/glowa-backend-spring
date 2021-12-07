package glowa.glowabackendspring.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "me")
    private User me;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend")
    private User friend;

    public Friend(User me, User friend) {
        this.me = me;
        this.friend = friend;
    }

}
