package glowa.glowabackendspring.service;

import glowa.glowabackendspring.domain.Schedule;
import glowa.glowabackendspring.domain.ScheduleManage;
import glowa.glowabackendspring.domain.User;
import glowa.glowabackendspring.dto.schedule.ScheduleDto;
import glowa.glowabackendspring.exception.LoginException;
import glowa.glowabackendspring.exception.ScheduleException;
import glowa.glowabackendspring.repository.schedule.ScheduleRepository;
import glowa.glowabackendspring.repository.scheduleManage.ScheduleManageRepository;
import glowa.glowabackendspring.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final ScheduleManageRepository scheduleManageRepository;

    @Transactional
    public void make(ScheduleDto scheduleDto) {
        Optional<User> master = userRepository.findById(scheduleDto.getMaster().getId());
        if (master.isEmpty()) {
            throw new LoginException("잘못된 사용자입니다. 다시 로그인해주세요.");
        }

        Optional<Schedule> alreadySchedule = scheduleRepository.findOneByMasterAndName(master.get(), scheduleDto.getName());
        if (alreadySchedule.isPresent()) {
            throw new ScheduleException("일정 이름을 변경해 주세요.");
        }

        Schedule saveSchedule = scheduleRepository.save(new Schedule(master.get(), scheduleDto.getName(), scheduleDto.getDate(), scheduleDto.getPlace()));
        log.info("saveSchedule id = {}", saveSchedule.getId());
        scheduleManageRepository.save(new ScheduleManage(master.get(), saveSchedule));

    }
}
