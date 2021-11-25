package glowa.glowabackendspring.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import glowa.glowabackendspring.domain.User;
import glowa.glowabackendspring.dto.user.UserInfoDto;
import glowa.glowabackendspring.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@Rollback
class UserServiceTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;
    @Autowired
    UserService userService;

    @Test
    public void search() {
        User userA = new User("test1","눈큰올빼미","1234");
        User userB = new User("test2","작은호랑이","12343456");
        User userC = new User("test3","무서운호랑이","1234156");
        User userD = new User("test4","뚱뚱한고양이","1234356");
        em.persist(userA);
        em.persist(userB);
        em.persist(userC);
        em.persist(userD);

        List<UserInfoDto> result = userService.search("호랑이", userA);
        for (UserInfoDto userInfoDto : result) {
            System.out.println("userInfoDto.getNickname() = " + userInfoDto.getNickname());
        }
    }

}