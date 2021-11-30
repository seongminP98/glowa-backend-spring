package glowa.glowabackendspring.repository.reqFriend;

import com.querydsl.jpa.impl.JPAQueryFactory;
import glowa.glowabackendspring.domain.ReqFriends;
import glowa.glowabackendspring.domain.User;
import glowa.glowabackendspring.dto.user.UserInfoDto;
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

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class ReqFriendRepositoryTest {
    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;
    @Autowired
    ReqFriendRepository reqFriendRepository;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    public void listReq() {
        User userA = new User("test1", "눈큰올빼미", "1234");
        User userB = new User("test2", "작은호랑이", "12343456");
        User userC = new User("test3","무서운호랑이","1234156");
        userRepository.save(userA);
        userRepository.save(userB);
        userRepository.save(userC);

        ReqFriends reqFriend1 = new ReqFriends(userA, userB);
        ReqFriends reqFriend2 = new ReqFriends(userC, userB);
        ReqFriends reqFriend3 = new ReqFriends(userA, userC);
        reqFriendRepository.save(reqFriend1);
        reqFriendRepository.save(reqFriend2);
        reqFriendRepository.save(reqFriend3);

        em.flush();
        em.clear();

        List<UserInfoDto> userInfoDtos = reqFriendRepository.reqList(userB.getId());
        for (UserInfoDto userInfoDto : userInfoDtos) {
            System.out.println(userInfoDto.getNickname());
        }
        assertThat(userInfoDtos.size()).isEqualTo(2);

    }

}