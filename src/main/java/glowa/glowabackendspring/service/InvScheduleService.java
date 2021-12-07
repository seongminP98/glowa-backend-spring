package glowa.glowabackendspring.service;

import glowa.glowabackendspring.domain.InvSchedule;
import glowa.glowabackendspring.domain.Schedule;
import glowa.glowabackendspring.domain.ScheduleManage;
import glowa.glowabackendspring.domain.User;
import glowa.glowabackendspring.dto.schedule.InvScheduleDto;
import glowa.glowabackendspring.dto.user.UserInfoDto;
import glowa.glowabackendspring.exception.LoginException;
import glowa.glowabackendspring.exception.ScheduleException;
import glowa.glowabackendspring.exception.UserException;
import glowa.glowabackendspring.repository.invSchedule.InvScheduleRepository;
import glowa.glowabackendspring.repository.schedule.ScheduleRepository;
import glowa.glowabackendspring.repository.scheduleManage.ScheduleManageRepository;
import glowa.glowabackendspring.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InvScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final ScheduleManageRepository scheduleManageRepository;
    private final InvScheduleRepository invScheduleRepository;

    @Transactional
    public Long invite(User me, Long friendId, Long ScheduleId) {
        Optional<User> userMe = userRepository.findById(me.getId());
        if (userMe.isEmpty()) {
            throw new LoginException("잘못된 사용자입니다. 다시 로그인해주세요.");
        }

        Optional<User> friend = userRepository.findById(friendId);
        if (friend.isEmpty()) {
            throw new UserException("잘못된 사용자입니다.");
        }

        Optional<Schedule> schedule = scheduleRepository.findById(ScheduleId);
        if (schedule.isEmpty()) {
            throw new ScheduleException("잘못된 스케줄입니다.");
        }

        Optional<InvSchedule> alreadyInv
                = invScheduleRepository.findOneByScheduleAndMeAndFriend(schedule.get(), userMe.get(), friend.get());
        if (alreadyInv.isPresent()) {
            throw new ScheduleException("이미 이 일정에 초대를 했습니다.");
        }

        List<ScheduleManage> friendScheduleList = scheduleManageRepository.findAllByUser(friend.get());
        for (ScheduleManage friendSchedule : friendScheduleList) {
            if (friendSchedule.getSchedule().getId().equals(ScheduleId)) {
                throw new ScheduleException("이미 일정에 있습니다.");
            }
        }

        Optional<Schedule> check = scheduleRepository.findOneByIdAndMaster(ScheduleId, userMe.get()); //권한체크
        if (check.isEmpty()) {
            throw new UserException("이 일정에 대한 초대 권한이 없습니다.");
        }

        InvSchedule invSchedule = invScheduleRepository.save(new InvSchedule(userMe.get(), friend.get(), schedule.get()));
        return invSchedule.getId();

    }

    public List<InvScheduleDto> list(User me) {
        Optional<User> userMe = userRepository.findById(me.getId());
        if (userMe.isEmpty()) {
            throw new LoginException("잘못된 사용자입니다. 다시 로그인해주세요.");
        }

        List<InvSchedule> invScheduleList = invScheduleRepository.findAllByFriend(userMe.get()); //초대받은 스케줄 목록
        List<InvScheduleDto> resultList = new ArrayList<>();
        for (InvSchedule invSchedule : invScheduleList) {
            List<UserInfoDto> users = new ArrayList<>();
            List<ScheduleManage> schedules = scheduleManageRepository.findAllBySchedule(invSchedule.getSchedule());
            for (ScheduleManage schedule : schedules) {
                UserInfoDto user = new UserInfoDto(schedule.getUser().getId(), schedule.getUser().getNickname(), schedule.getUser().getImage());
                users.add(user);
            }
            resultList.add(new InvScheduleDto(invSchedule.getSchedule().getId(), invSchedule.getMe().getId(), invSchedule.getSchedule().getName(),
                    invSchedule.getSchedule().getDate(), invSchedule.getSchedule().getPlace(), invSchedule.getMe().getNickname(), users));
        }

        return resultList;

    }
}
