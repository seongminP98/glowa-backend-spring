package glowa.glowabackendspring.repository.invSchedule;

import com.querydsl.jpa.impl.JPAQueryFactory;
import glowa.glowabackendspring.domain.InvSchedule;
import glowa.glowabackendspring.domain.Schedule;
import glowa.glowabackendspring.domain.User;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class InvScheduleRepositoryTest {
    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    InvScheduleRepository invScheduleRepository;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    public void findOneByScheduleAndMeAndFriend() {
        User userA = new User("test1", "눈큰올빼미", "1234");
        User userB = new User("test2", "작은호랑이", "12343456");
        User userC = new User("test3","무서운호랑이","1234156");
        userRepository.save(userA);
        userRepository.save(userB);
        userRepository.save(userC);

        Schedule schedule1 = new Schedule(userA, "점심약속", LocalDateTime.now(), "집 앞");
        Schedule schedule2 = new Schedule(userB, "저녁약속", LocalDateTime.now(), "역 앞");
        Schedule schedule3 = new Schedule(userC, "점심약속", LocalDateTime.now(), "집");
        scheduleRepository.save(schedule1);
        scheduleRepository.save(schedule2);
        scheduleRepository.save(schedule3);

        InvSchedule invSchedule1 = new InvSchedule(userA, userB, schedule1);
        InvSchedule invSchedule2 = new InvSchedule(userA, userC, schedule1);
        InvSchedule invSchedule3 = new InvSchedule(userC, userB, schedule3);
        invScheduleRepository.save(invSchedule1);
        invScheduleRepository.save(invSchedule2);
        invScheduleRepository.save(invSchedule3);

        em.flush();
        em.clear();


        Optional<User> user1 = userRepository.findById(1L);
        Optional<User> user2 = userRepository.findById(2L);
        Optional<User> user3 = userRepository.findById(3L);

        Optional<Schedule> schedule11 = scheduleRepository.findById(1L);
        Optional<Schedule> schedule22 = scheduleRepository.findById(2L);
        Optional<Schedule> schedule33 = scheduleRepository.findById(3L);

        Optional<InvSchedule> invSchedule11 = invScheduleRepository.findOneByScheduleAndMeAndFriend(schedule11.get(), user1.get(), user2.get());
        Optional<InvSchedule> invSchedule22 = invScheduleRepository.findOneByScheduleAndMeAndFriend(schedule11.get(), user1.get(), user3.get());
        Optional<InvSchedule> invSchedule33 = invScheduleRepository.findOneByScheduleAndMeAndFriend(schedule33.get(), user3.get(), user2.get());
        System.out.println("invSchedule11 = " + invSchedule11.get().getId());
        System.out.println("invSchedule22 = " + invSchedule22.get().getId());
        System.out.println("invSchedule33 = " + invSchedule33.get().getId());
        invSchedule11.ifPresent(invSchedule -> assertThat(invSchedule.getId()).isEqualTo(1));
        invSchedule22.ifPresent(invSchedule -> assertThat(invSchedule.getId()).isEqualTo(2));
        invSchedule33.ifPresent(invSchedule -> assertThat(invSchedule.getId()).isEqualTo(3));
    }

    @Test
    public void findAllByFriend() {
        User userA = new User("test1", "눈큰올빼미", "1234");
        User userB = new User("test2", "작은호랑이", "12343456");
        User userC = new User("test3","무서운호랑이","1234156");
        userRepository.save(userA);
        userRepository.save(userB);
        userRepository.save(userC);

        Schedule schedule1 = new Schedule(userA, "점심약속", LocalDateTime.now(), "집 앞");
        Schedule schedule2 = new Schedule(userB, "저녁약속", LocalDateTime.now(), "역 앞");
        Schedule schedule3 = new Schedule(userC, "점심약속", LocalDateTime.now(), "집");
        scheduleRepository.save(schedule1);
        scheduleRepository.save(schedule2);
        scheduleRepository.save(schedule3);

        InvSchedule invSchedule1 = new InvSchedule(userA, userB, schedule1);
        InvSchedule invSchedule3 = new InvSchedule(userC, userB, schedule3);
        invScheduleRepository.save(invSchedule1);
        invScheduleRepository.save(invSchedule3);

        em.flush();
        em.clear();

        User user = userRepository.findByUserId("test2").get();

        List<InvSchedule> result = invScheduleRepository.findAllByFriend(user);
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void deleteByScheduleAndMeAndFriend() {
        User userA = new User("test1", "눈큰올빼미", "1234");
        User userB = new User("test2", "작은호랑이", "12343456");
        User userC = new User("test3","무서운호랑이","1234156");
        userRepository.save(userA);
        userRepository.save(userB);
        userRepository.save(userC);

        Schedule schedule1 = new Schedule(userA, "점심약속", LocalDateTime.now(), "집 앞");
        Schedule schedule2 = new Schedule(userB, "저녁약속", LocalDateTime.now(), "역 앞");
        Schedule schedule3 = new Schedule(userC, "점심약속", LocalDateTime.now(), "집");
        scheduleRepository.save(schedule1);
        scheduleRepository.save(schedule2);
        scheduleRepository.save(schedule3);

        InvSchedule invSchedule1 = new InvSchedule(userA, userB, schedule1);
        InvSchedule invSchedule3 = new InvSchedule(userC, userB, schedule3);
        invScheduleRepository.save(invSchedule1);
        invScheduleRepository.save(invSchedule3);

        em.flush();
        em.clear();


        long l = invScheduleRepository.deleteByScheduleAndMeAndFriend(schedule1, userA, userB);
        assertThat(l).isEqualTo(1);


    }

}