package glowa.glowabackendspring.repository.scheduleManage;

import glowa.glowabackendspring.domain.ScheduleManage;
import glowa.glowabackendspring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ScheduleManageRepository extends JpaRepository<ScheduleManage, Long> {
    List<ScheduleManage> findAllByUser(User user);
}
