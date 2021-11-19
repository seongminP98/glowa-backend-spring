package glowa.glowabackendspring.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import glowa.glowabackendspring.domain.QUser;
import glowa.glowabackendspring.domain.User;
import glowa.glowabackendspring.dto.user.UserSearchDto;
import glowa.glowabackendspring.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;
    @Autowired
    UserRepository userRepository;

    @Test
    public void testEntity() {
        queryFactory = new JPAQueryFactory(em);

        User userA = new User("test1","눈큰올빼미","1234");
        User userB = new User("test2","작은호랑이","12343456");
        User userC = new User("test3","무서운호랑이","1234156");
        User userD = new User("test4","뚱뚱한고양이","1234356");
        em.persist(userA);
        em.persist(userB);
        em.persist(userC);
        em.persist(userD);

        em.flush();
        em.clear();

        QUser user = QUser.user;
        List<User> result = queryFactory
                .selectFrom(user)
                .fetch();

        for (User u : result) {
            System.out.println("user = " + u);
        }
        Optional<User> userById = userRepository.findById(result.get(0).getId());
        userById.ifPresent(value -> assertThat(value.getId()).isEqualTo(1));

        List<User> all = userRepository.findAll();
        Optional<User> userByNickname = userRepository.findByNickname(result.get(0).getNickname());
        userByNickname.ifPresent(value -> assertThat(value.getNickname()).isEqualTo("눈큰올빼미"));

        for (User user1 : all) {
            System.out.println("user1.getNickname() = " + user1.getNickname());
        }
        List<UserSearchDto> search = userRepository.search("두꺼비", 2L);
        List<UserSearchDto> search2 = userRepository.search("호랑이", 2L);
        assertThat(search).size().isEqualTo(0);
        assertThat(search2).size().isEqualTo(1);
    }
}