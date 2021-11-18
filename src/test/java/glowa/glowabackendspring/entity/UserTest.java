package glowa.glowabackendspring.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import glowa.glowabackendspring.domain.QUser;
import glowa.glowabackendspring.domain.User;
import glowa.glowabackendspring.repository.UserRepository;
import org.assertj.core.api.Assertions;
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

        User userA = new User("test1","닉네임1","1234");
        User userB = new User("test2","닉네임2","123456");
        em.persist(userA);
        em.persist(userB);

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
        userByNickname.ifPresent(value -> assertThat(value.getNickname()).isEqualTo("닉네임1"));

        for (User user1 : all) {
            System.out.println("user1.getNickname() = " + user1.getNickname());
        }
    }
}