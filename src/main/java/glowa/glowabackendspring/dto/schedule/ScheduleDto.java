package glowa.glowabackendspring.dto.schedule;

import glowa.glowabackendspring.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ScheduleDto {
    private User master;
    private String name;
    private String place;
    private LocalDateTime date;

    public ScheduleDto(User master, String name, String place, LocalDateTime date) {
        this.master = master;
        this.name = name;
        this.place = place;
        this.date = date;
    }

    public ScheduleDto(String name, String place, LocalDateTime date) {
        this.name = name;
        this.place = place;
        this.date = date;
    }
}
