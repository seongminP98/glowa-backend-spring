package glowa.glowabackendspring.repository.friend;

import com.querydsl.jpa.impl.JPAQueryFactory;
import glowa.glowabackendspring.domain.Friend;
import glowa.glowabackendspring.domain.QFriend;

import javax.persistence.EntityManager;

import static glowa.glowabackendspring.domain.QFriend.*;

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
}
