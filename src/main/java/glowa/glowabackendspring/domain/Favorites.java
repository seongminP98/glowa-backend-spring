package glowa.glowabackendspring.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorites extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_id")
    private User user;

    private String restaurant;
    private String address;
    private String kind;

    public Favorites(User user, String restaurant, String address, String kind) {
        this.user = user;
        this.restaurant = restaurant;
        this.address = address;
        this.kind = kind;
    }
}
