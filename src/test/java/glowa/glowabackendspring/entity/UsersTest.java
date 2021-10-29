package glowa.glowabackendspring.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class UsersTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @Test
    public void testEntity() {
        queryFactory = new JPAQueryFactory(em);

        Users userA = new Users("test1","닉네임1","1234");
        Users userB = new Users("test2","닉네임2","123456");
        em.persist(userA);
        em.persist(userB);

        em.flush();
        em.clear();

        QUsers user = QUsers.users;
        List<Users> result = queryFactory
                .selectFrom(user)
                .fetch();

        for (Users users : result) {
            System.out.println("users = " + users);
        }
    }


}