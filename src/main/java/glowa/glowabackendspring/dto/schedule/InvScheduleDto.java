package glowa.glowabackendspring.dto.schedule;

import glowa.glowabackendspring.dto.user.UserInfoDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class InvScheduleDto {
    private Long id;
    private Long masterId;
    private String scheduleName;
    private LocalDateTime date;
    private String place;
    private String masterNickname;
    private List<UserInfoDto> users;

    public InvScheduleDto(Long id, Long masterId, String scheduleName, LocalDateTime date, String place, String masterNickname, List<UserInfoDto> users) {
        this.id = id;
        this.masterId = masterId;
        this.scheduleName = scheduleName;
        this.date = date;
        this.place = place;
        this.masterNickname = masterNickname;
        this.users = users;
    }
}
