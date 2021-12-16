package glowa.glowabackendspring.repository.scheduleManage;

import glowa.glowabackendspring.domain.Schedule;
import glowa.glowabackendspring.domain.ScheduleManage;
import glowa.glowabackendspring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ScheduleManageRepository extends JpaRepository<ScheduleManage, Long> {
    List<ScheduleManage> findAllByUser(User user);
    List<ScheduleManage> findAllBySchedule(Schedule schedule);
    long deleteByUserAndSchedule(User user, Schedule schedule);
    Optional<ScheduleManage> findByUserAndSchedule(User user, Schedule schedule);
}
