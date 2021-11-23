package glowa.glowabackendspring.repository.schedule;

import glowa.glowabackendspring.domain.Schedule;
import glowa.glowabackendspring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {

    Optional<Schedule> findOneByMasterAndName(User master, String name);
    Optional<Schedule> findOneById(Long id);
    Optional<Schedule> findOneByIdAndMaster(Long id, User master); //내가 마스터인지 확인



}
