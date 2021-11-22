package glowa.glowabackendspring.repository.schedule;

import glowa.glowabackendspring.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {

    Schedule findOneByMasterAndName(Long master, String name);
    Schedule findOneById(Long id);
    Schedule findOneByIdAndMaster(Long id, Long master); //내가 마스터인지 확인


}
