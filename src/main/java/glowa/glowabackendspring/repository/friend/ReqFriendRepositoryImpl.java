package glowa.glowabackendspring.repository.friend;

import com.querydsl.jpa.impl.JPAQueryFactory;
import glowa.glowabackendspring.domain.QReqFriends;
import glowa.glowabackendspring.domain.ReqFriends;

import javax.persistence.EntityManager;

import java.util.List;

import static glowa.glowabackendspring.domain.QReqFriends.*;

public class ReqFriendRepositoryImpl implements ReqFriendRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ReqFriendRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public ReqFriends alreadyReq(Long myId, Long friendId) {
        return queryFactory
                .selectFrom(reqFriends)
                .where(
                        reqFriends.me.id.eq(myId).and(reqFriends.reqFriend.id.eq(friendId))
                ).fetchOne();
    }

    @Override
    public ReqFriends checkSentReq(Long myId, Long friendId) {
        return queryFactory
                .selectFrom(reqFriends)
                .where(
                        reqFriends.me.id.eq(friendId).and(reqFriends.reqFriend.id.eq(myId))
                ).fetchOne();
    }

    @Override
    public List<ReqFriends> listReq(Long myId) {
        return queryFactory
                .selectFrom(reqFriends)
                .where(
                        reqFriends.reqFriend.id.eq(myId)
                ).fetch();
    }

    @Override
    public long delete(Long myId, Long friendId) {
        return queryFactory
                .delete(reqFriends)
                .where(
                       reqFriends.me.id.eq(friendId).and(reqFriends.reqFriend.id.eq(myId))
                ).execute();
    }
}
