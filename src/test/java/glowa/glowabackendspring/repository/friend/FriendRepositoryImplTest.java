package glowa.glowabackendspring.repository.friend;

import com.querydsl.jpa.impl.JPAQueryFactory;
import glowa.glowabackendspring.domain.Friend;
import glowa.glowabackendspring.domain.User;
import glowa.glowabackendspring.dto.user.UserInfoDto;
import glowa.glowabackendspring.repository.reqFriend.ReqFriendRepository;
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
class FriendRepositoryImplTest {
    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;
    @Autowired
    FriendRepository friendRepository;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    public void friendList() {
        User userA = new User("test1", "눈큰올빼미", "1234");
        User userB = new User("test2", "작은호랑이", "12343456");
        User userC = new User("test3","무서운호랑이","1234156");
        userRepository.save(userA);
        userRepository.save(userB);
        userRepository.save(userC);

        Friend friend1 = new Friend(userA, userB);
        Friend friend2 = new Friend(userA, userC);
        Friend friend3 = new Friend(userC, userB);
        friendRepository.save(friend1);
        friendRepository.save(friend2);
        friendRepository.save(friend3);

        em.flush();
        em.clear();

        List<UserInfoDto> userInfoDtos = friendRepository.friendList(userA.getId());
        for (UserInfoDto userInfoDto : userInfoDtos) {
            System.out.println("userInfoDto.getNickname() = " + userInfoDto.getNickname());
        }
        assertThat(userInfoDtos.size()).isEqualTo(2);
    }
}