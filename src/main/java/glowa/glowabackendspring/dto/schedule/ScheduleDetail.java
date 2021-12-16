package glowa.glowabackendspring.dto.schedule;

import glowa.glowabackendspring.dto.user.UserInfoDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ScheduleDetail {
    private Long id;
    private Long masterId;
    private String name;
    private String place;
    private LocalDateTime date;
    private String masterNickname;
    private List<UserInfoDto> member;

    public ScheduleDetail(Long id, Long masterId, String name, String place, LocalDateTime date, String masterNickname, List<UserInfoDto> member) {
        this.id = id;
        this.masterId = masterId;
        this.name = name;
        this.place = place;
        this.date = date;
        this.masterNickname = masterNickname;
        this.member = member;
    }
}
