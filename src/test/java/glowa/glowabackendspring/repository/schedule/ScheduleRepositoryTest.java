package glowa.glowabackendspring.repository.schedule;

import com.querydsl.jpa.impl.JPAQueryFactory;
import glowa.glowabackendspring.domain.Schedule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

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

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);

        Schedule schedule1 = new Schedule(1L, "점심약속", LocalDateTime.now(), "집 앞");
        Schedule schedule2 = new Schedule(1L, "저녁약속", LocalDateTime.now(), "역 앞");
        Schedule schedule3 = new Schedule(2L, "점심약속", LocalDateTime.now(), "집");

        em.persist(schedule1);
        em.persist(schedule2);
        em.persist(schedule3);
    }

    @Test
    public void findByMasterAndName() {
        Schedule result = scheduleRepository.findOneByMasterAndName(1L, "점심약속");
        System.out.println("result = " + result);
        assertThat(result.getName()).isEqualTo("점심약속");

    }

    @Test
    public void findOneById() {
        Schedule result = scheduleRepository.findOneById(2L);
        System.out.println("result = " + result);
        assertThat(result.getMaster()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("저녁약속");
    }

    @Test
    public void findOneByIdAndMaster() {
        Schedule result = scheduleRepository.findOneByIdAndMaster(2L, 1L);
        System.out.println("result = " + result);
        assertThat(result.getMaster()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("저녁약속");
    }
}
