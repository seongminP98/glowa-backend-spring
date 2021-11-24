package glowa.glowabackendspring.repository.invSchedule;

import glowa.glowabackendspring.domain.InvSchedule;
import glowa.glowabackendspring.domain.Schedule;
import glowa.glowabackendspring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvScheduleRepository extends JpaRepository<InvSchedule, Long> {
    Optional<InvSchedule> findOneByScheduleAndMeAndFriend(Schedule schedule, User me, User friend);
    List<InvSchedule> findAllByFriend(User friend); //내가 초대받은 스케줄 목록
    long deleteByScheduleAndMeAndFriend(Schedule schedule, User me, User friend);

}
