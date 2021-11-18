package glowa.glowabackendspring.repository.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import glowa.glowabackendspring.domain.QUser;
import glowa.glowabackendspring.dto.user.QUserSearchDto;
import glowa.glowabackendspring.dto.user.UserSearchDto;

import javax.persistence.EntityManager;
import java.util.List;

import static glowa.glowabackendspring.domain.QUser.*;

public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<UserSearchDto> search(String nickname, Long id) {
        return queryFactory
                .select(new QUserSearchDto(
                        user.id,
                        user.nickname,
                        user.password
                ))
                .from(user)
                .where(
                        user.nickname.contains(nickname).and(user.id.notIn(id))
                ).fetch();
    }
}
