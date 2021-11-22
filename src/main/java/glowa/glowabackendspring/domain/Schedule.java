package glowa.glowabackendspring.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long master;
    private String name;
    private LocalDateTime date;
    private String place;

    @OneToMany(mappedBy = "schedule")
    private List<ScheduleManage> scheduleManages = new ArrayList<>();

    public Schedule(Long master, String name, LocalDateTime date, String place) {
        this.master = master;
        this.name = name;
        this.date = date;
        this.place = place;
    }
}
