package glowa.glowabackendspring.repository.favorites;

import com.querydsl.jpa.impl.JPAQueryFactory;
import glowa.glowabackendspring.domain.Favorites;
import glowa.glowabackendspring.domain.User;
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
class FavoritesRepositoryTest {
    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;
    @Autowired
    FavoritesRepository favoritesRepository;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);

        User userA = new User("test1", "눈큰올빼미", "1234");
        User userB = new User("test2", "작은호랑이", "12343456");
        em.persist(userA);
        em.persist(userB);

        Favorites favorites1 = new Favorites(userA, "삼겹살집", "천호동", "고기");
        Favorites favorites2 = new Favorites(userA, "롯데리아", "천호동 55", "햄버거");
        Favorites favorites3 = new Favorites(userB, "초밥집", "종로3가 22", "일식");
        Favorites favorites4 = new Favorites(userB, "돈까스", "아차산역 21", "일식");
        em.persist(favorites1);
        em.persist(favorites2);
        em.persist(favorites3);
        em.persist(favorites4);
    }

    @Test
    public void findOneByUserAndAddress() {
        Optional<User> userA = userRepository.findById(1L);
        Optional<User> userB = userRepository.findById(2L);

        userA.flatMap(user -> favoritesRepository.findOneByUserAndRestaurantAndAddress(user, "롯데리아", "천호동 55"))
                .ifPresent(favorites -> assertThat(favorites.getId()).isEqualTo(2));

        userB.flatMap(user -> favoritesRepository.findOneByUserAndRestaurantAndAddress(user, "돈까스","아차산역 21"))
                .ifPresent(favorites -> assertThat(favorites.getId()).isEqualTo(4));
    }

    @Test
    public void findAllByUser() {
        User user = userRepository.findById(1L).get();
        List<Favorites> result = favoritesRepository.findAllByUser(user);
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
    }

}