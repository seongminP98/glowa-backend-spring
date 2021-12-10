package glowa.glowabackendspring.service;

import glowa.glowabackendspring.domain.Schedule;
import glowa.glowabackendspring.domain.ScheduleManage;
import glowa.glowabackendspring.domain.User;
import glowa.glowabackendspring.dto.schedule.ScheduleDto;
import glowa.glowabackendspring.exception.LoginException;
import glowa.glowabackendspring.exception.ScheduleException;
import glowa.glowabackendspring.exception.UserException;
import glowa.glowabackendspring.repository.schedule.ScheduleRepository;
import glowa.glowabackendspring.repository.scheduleManage.ScheduleManageRepository;
import glowa.glowabackendspring.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public void delete(Long scheduleId, User me) {
        Optional<Schedule> schedule = scheduleRepository.findOneById(scheduleId);
        if (schedule.isEmpty()) {
            throw new ScheduleException("잘못된 일정입니다. 다시 확인해주세요.");
        }

        Optional<User> userMe = userRepository.findById(me.getId());
        if (userMe.isEmpty()) {
            throw new UserException("잘못된 사용자입니다. 다시 로그인해주세요.");
        }

        if (!schedule.get().getMaster().getId().equals(userMe.get().getId())) { //엔티티 동등성 비교 확인하기.
            throw new UserException("이 일정을 삭제할 권한이 없습니다.");
        }

        scheduleRepository.delete(schedule.get()); //scheduleManage 에서 어떻게 변하는지 확인.
    }

    public Long exit(Long scheduleId, User me) {
        Optional<Schedule> schedule = scheduleRepository.findOneById(scheduleId);
        if (schedule.isEmpty()) {
            throw new ScheduleException("잘못된 일정입니다. 다시 확인해주세요.");
        }

        Optional<User> userMe = userRepository.findById(me.getId());
        if (userMe.isEmpty()) {
            throw new UserException("잘못된 사용자입니다. 다시 로그인해주세요.");
        }

        if (schedule.get().getMaster().getId().equals(userMe.get().getId())) {
            throw new UserException("일정권한 양도 후 가능합니다.");
        }

        return scheduleManageRepository.deleteByUserAndSchedule(userMe.get(), schedule.get());
    }

    public void transferMaster(User me, Long scheduleId, Long friendId) {
        Optional<Schedule> schedule = scheduleRepository.findOneById(scheduleId);
        if (schedule.isEmpty()) {
            throw new ScheduleException("잘못된 일정입니다. 다시 확인해주세요.");
        }

        Optional<User> userMe = userRepository.findById(me.getId());
        if (userMe.isEmpty()) {
            throw new UserException("잘못된 사용자입니다. 다시 로그인해주세요.");
        }

        Optional<User> friend = userRepository.findById(friendId);
        if (friend.isEmpty()) {
            throw new UserException("잘못된 사용자입니다. 다시 확인해주세요.");
        }

        if (!schedule.get().getMaster().getId().equals(userMe.get().getId())) {
            throw new UserException("이 일정에 대한 권한이 없습니다.");
        }

        if (userMe.get().getId().equals(friendId)) {
            throw new UserException("자기 자신한테는 권한을 넘길 수 없습니다.");
        }

        boolean check = false;
        List<ScheduleManage> scheduleManages = scheduleManageRepository.findAllBySchedule(schedule.get());
        for (ScheduleManage scheduleManage : scheduleManages) {
            if (scheduleManage.getUser().getId().equals(friendId)) {
                check = true;
                break;
            }
        }

        if (!check) {
            throw new UserException("먼저 이 일정에 초대해주세요.");
        }

        schedule.get().changeMaster(friend.get());

    }

    public Long kick(User me, Long scheduleId, Long targetId) {
        Optional<Schedule> schedule = scheduleRepository.findOneById(scheduleId);
        if (schedule.isEmpty()) {
            throw new ScheduleException("잘못된 일정입니다. 다시 확인해주세요.");
        }

        Optional<User> userMe = userRepository.findById(me.getId());
        if (userMe.isEmpty()) {
            throw new UserException("잘못된 사용자입니다. 다시 로그인해주세요.");
        }

        Optional<User> target = userRepository.findById(targetId);
        if (target.isEmpty()) {
            throw new UserException("잘못된 사용자입니다. 다시 확인해주세요.");
        }

        if (!schedule.get().getMaster().getId().equals(userMe.get().getId())) {
            throw new UserException("이 일정에 대한 권한이 없습니다.");
        }

        if (me.getId().equals(targetId)) {
            throw new UserException("자기 자신은 추방할 수 없습니다.");
        }

        return scheduleManageRepository.deleteByUserAndSchedule(target.get(), schedule.get());
    }
}
