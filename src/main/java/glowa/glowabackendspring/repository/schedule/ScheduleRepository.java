package glowa.glowabackendspring.repository.schedule;

import glowa.glowabackendspring.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {

    Optional<Schedule> findOneByMasterAndName(Long master, String name);
    Optional<Schedule> findOneById(Long id);
    Optional<Schedule> findOneByIdAndMaster(Long id, Long master); //내가 마스터인지 확인



}
