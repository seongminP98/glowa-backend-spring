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

    @ManyToOne(fetch = FetchType.LAZY)
    private User master;

    private String name;
    private LocalDateTime date;
    private String place;

    @OneToMany(mappedBy = "schedule")
    private List<ScheduleManage> scheduleManages = new ArrayList<>();

    @OneToMany(mappedBy = "schedule")
    private List<InvSchedule> invSchedules = new ArrayList<>();

    public void changeMaster(User master) {
        this.master = master;
    }

    public Schedule(User master, String name, LocalDateTime date, String place) {
        this.master = master;
        this.name = name;
        this.date = date;
        this.place = place;
    }
}
