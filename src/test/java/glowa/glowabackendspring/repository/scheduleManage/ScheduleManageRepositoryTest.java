package glowa.glowabackendspring.repository.scheduleManage;

import com.querydsl.jpa.impl.JPAQueryFactory;
import glowa.glowabackendspring.domain.Schedule;
import glowa.glowabackendspring.domain.ScheduleManage;
import glowa.glowabackendspring.domain.User;
import glowa.glowabackendspring.repository.invSchedule.InvScheduleRepository;
import glowa.glowabackendspring.repository.schedule.ScheduleRepository;
import glowa.glowabackendspring.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class ScheduleManageRepositoryTest {
    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ScheduleManageRepository scheduleManageRepository;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    public void findAllByUser() {
        User userA = new User("test1", "눈큰올빼미", "1234");
        User userB = new User("test2", "작은호랑이", "12343456");
        User userC = new User("test3","무서운호랑이","1234156");
        User userD = new User("test4","퉁퉁한소","13156");
        userRepository.save(userA);
        userRepository.save(userB);
        userRepository.save(userC);
        userRepository.save(userD);

        Schedule schedule1 = new Schedule(userA, "점심약속", LocalDateTime.now(), "집 앞");
        Schedule schedule2 = new Schedule(userB, "저녁약속", LocalDateTime.now(), "역 앞");
        Schedule schedule3 = new Schedule(userC, "점심약속", LocalDateTime.now(), "집");
        scheduleRepository.save(schedule1);
        scheduleRepository.save(schedule2);
        scheduleRepository.save(schedule3);

        ScheduleManage scheduleManage1 = new ScheduleManage(userA, schedule1);
        ScheduleManage scheduleManage2 = new ScheduleManage(userA, schedule2);
        ScheduleManage scheduleManage3 = new ScheduleManage(userA, schedule3);
        ScheduleManage scheduleManage4 = new ScheduleManage(userB, schedule2);
        ScheduleManage scheduleManage5 = new ScheduleManage(userC, schedule3);
        scheduleManageRepository.save(scheduleManage1);
        scheduleManageRepository.save(scheduleManage2);
        scheduleManageRepository.save(scheduleManage3);
        scheduleManageRepository.save(scheduleManage4);
        scheduleManageRepository.save(scheduleManage5);

        em.flush();
        em.clear();

        List<ScheduleManage> schedulesA = scheduleManageRepository.findAllByUser(userA);
        List<ScheduleManage> schedulesB = scheduleManageRepository.findAllByUser(userB);
        List<ScheduleManage> schedulesD = scheduleManageRepository.findAllByUser(userD);
        assertThat(schedulesA.size()).isEqualTo(3);
        assertThat(schedulesB.size()).isEqualTo(1);
        assertThat(schedulesD.size()).isEqualTo(0);
    }
}