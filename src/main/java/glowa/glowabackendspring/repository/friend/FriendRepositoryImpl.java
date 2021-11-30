package glowa.glowabackendspring.repository.friend;

import com.querydsl.jpa.impl.JPAQueryFactory;
import glowa.glowabackendspring.domain.Friend;
import glowa.glowabackendspring.domain.QFriend;
import glowa.glowabackendspring.domain.QUser;
import glowa.glowabackendspring.dto.user.QUserInfoDto;
import glowa.glowabackendspring.dto.user.UserInfoDto;

import javax.persistence.EntityManager;

import java.util.List;

import static glowa.glowabackendspring.domain.QFriend.*;
import static glowa.glowabackendspring.domain.QUser.*;

public class FriendRepositoryImpl implements FriendRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public FriendRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Friend friend(Long myId, Long friendId) {
        return queryFactory
                .selectFrom(friend1)
                .where(friend1.me.id.eq(myId).and(friend1.friend.id.eq(friendId)))
                .fetchOne();
    }

    @Override
    public List<UserInfoDto> friendList(Long myId) {
        return queryFactory
                .select(new QUserInfoDto(user.id, user.nickname, user.image))
                .from(friend1)
                .leftJoin(friend1.friend, user)
                .where(friend1.me.id.eq(myId))
                .fetch();
    }


}
