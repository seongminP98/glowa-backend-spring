package glowa.glowabackendspring.repository.reqFriend;

import com.querydsl.jpa.impl.JPAQueryFactory;
import glowa.glowabackendspring.domain.QUser;
import glowa.glowabackendspring.domain.ReqFriends;
import glowa.glowabackendspring.dto.user.QUserInfoDto;
import glowa.glowabackendspring.dto.user.UserInfoDto;

import javax.persistence.EntityManager;

import java.util.List;

import static glowa.glowabackendspring.domain.QReqFriends.*;
import static glowa.glowabackendspring.domain.QUser.*;

public class ReqFriendRepositoryImpl implements ReqFriendRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ReqFriendRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

//    @Override
//    public ReqFriends alreadyReq(Long myId, Long friendId) {
//        return queryFactory
//                .selectFrom(reqFriends)
//                .where(
//                        reqFriends.me.id.eq(myId).and(reqFriends.reqFriend.id.eq(friendId))
//                ).fetchOne();
//    }

    @Override
    public ReqFriends checkSentReq(Long myId, Long friendId) {
        return queryFactory
                .selectFrom(reqFriends)
                .where(
                        reqFriends.me.id.eq(friendId).and(reqFriends.reqFriend.id.eq(myId))
                ).fetchOne();
    }

    @Override
    public List<UserInfoDto> reqList(Long myId) {
        return queryFactory
                .select(new QUserInfoDto(user.id, user.nickname, user.image))
                .from(reqFriends)
                .leftJoin(reqFriends.me, user)
                .where(reqFriends.reqFriend.id.eq(myId))
                .fetch();
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
