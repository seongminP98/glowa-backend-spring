package glowa.glowabackendspring.repository.user;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import glowa.glowabackendspring.domain.User;
import glowa.glowabackendspring.dto.user.QUserInfoDto;
import glowa.glowabackendspring.dto.user.UserInfoDto;

import javax.persistence.EntityManager;
import java.util.List;

import static glowa.glowabackendspring.domain.QFriend.*;
import static glowa.glowabackendspring.domain.QUser.*;

public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<UserInfoDto> search(String nickname, Long id) {
        return queryFactory
                .select(new QUserInfoDto(
                        user.id,
                        user.nickname,
                        user.image
                ))
                .from(user)
                .where(
                        user.nickname.contains(nickname).and(user.id.notIn(id))
                ).fetch();
    }

    @Override
    public List<UserInfoDto> friendList(Long id) {
        return queryFactory
                .select(new QUserInfoDto(user.id, user.nickname, user.image))
                .from(user)
                .leftJoin(user.friends, friend1)
                .where(friend1.me.id.eq(id))
                .fetch();
    }

    @Override
    public List<User> friendListUser(Long id) {
        return queryFactory
                .selectFrom(user)
                .leftJoin(user.friends, friend1).fetchJoin()
                .where(friend1.me.id.eq(id))
                .fetch();
    }

}
