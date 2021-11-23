package glowa.glowabackendspring.repository.schedule;

import com.querydsl.jpa.impl.JPAQueryFactory;
import glowa.glowabackendspring.domain.Schedule;
import glowa.glowabackendspring.domain.User;
import glowa.glowabackendspring.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
public class ScheduleRepositoryTest {
    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);

        User userA = new User("test1", "눈큰올빼미", "1234");
        User userB = new User("test2", "작은호랑이", "12343456");
        em.persist(userA);
        em.persist(userB);

        Schedule schedule1 = new Schedule(userA, "점심약속", LocalDateTime.now(), "집 앞");
        Schedule schedule2 = new Schedule(userA, "저녁약속", LocalDateTime.now(), "역 앞");
        Schedule schedule3 = new Schedule(userB, "점심약속", LocalDateTime.now(), "집");

        em.persist(schedule1);
        em.persist(schedule2);
        em.persist(schedule3);
    }

    @Test
    public void findOneByMasterAndName() {
        Optional<User> user = userRepository.findById(1L);
        user.flatMap(u -> scheduleRepository.findOneByMasterAndName(u, "점심약속")).ifPresent(value -> assertThat(value.getName()).isEqualTo("점심약속"));
    }

    @Test
    public void findOneById() {
        Optional<Schedule> result = scheduleRepository.findOneById(2L);
        result.ifPresent(value -> assertThat(value.getMaster().getId()).isEqualTo(1));
        result.ifPresent(value -> assertThat(value.getName()).isEqualTo("저녁약속"));
    }

    @Test
    public void findOneByIdAndMaster() {
        Optional<User> user = userRepository.findById(1L);
        user.flatMap(u -> scheduleRepository.findOneByIdAndMaster(2L, u)).ifPresent(value -> assertThat(value.getName()).isEqualTo("저녁약속"));
        user.flatMap(u -> scheduleRepository.findOneByIdAndMaster(2L, u)).ifPresent(value -> assertThat(value.getMaster().getId()).isEqualTo(1));

    }
}
