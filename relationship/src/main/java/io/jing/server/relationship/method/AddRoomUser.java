package io.jing.server.relationship.method;

import com.google.common.collect.Lists;
import io.jing.base.exception.MicroServiceException;
import io.jing.base.util.code.Code;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.db.helper.IdHelper;
import io.jing.server.method.Method;
import io.jing.server.relationship.bean.RoomBean;
import io.jing.server.relationship.bean.RoomUser;
import io.jing.server.relationship.bean.RoomUserAdd;
import io.jing.server.relationship.bean.RoomUserBean;
import io.jing.server.relationship.constant.RelationshipConstant;
import io.jing.server.relationship.dao.RoomDao;
import io.jing.server.relationship.dao.RoomUserDao;
import io.jing.server.relationship.helper.RoomHelper;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import io.jing.util.jdbc.core.util.keygen.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jingshouyan
 * #date 2018/6/14 17:48
 */
@Component("relationship.addRoomUser")
public class AddRoomUser implements Method<RoomUserAdd> {

    @Autowired
    private RoomDao roomDao;
    @Autowired
    private RoomUserDao roomUserDao;
    @Autowired
    private RoomHelper roomHelper;
    @Autowired
    private IdHelper idHelper;

    @Override
    public Object action(RoomUserAdd roomUserAdd) {
        String myId = ThreadLocalUtil.userId();
        Map<String,RoomUser> map =  roomUserAdd.getRoomUsers().stream()
                //不能添加自己
                .filter(roomUser -> !myId.equals(roomUser.getUserId()))
                .collect(Collectors.toMap(
                        RoomUser::getUserId,
                        ru -> ru,
                        (ru1, ru2) -> ru1
                ));
        if(map.isEmpty()){
            throw new MicroServiceException(Code.BAD_REQUEST);
        }
        List<String> userIds = Lists.newArrayList(myId);
        userIds.addAll(map.keySet());
        List<Compare> compares = CompareUtil.newInstance()
                .field("userId").in(userIds)
                .field("roomId").eq(roomUserAdd.getRoomId())
                .compares();
        List<RoomUserBean> roomUserBeanList = roomUserDao.query(compares);
        RoomUserBean me = roomUserBeanList.stream().filter(rub -> myId.equals(rub.getUserId()))
                .findFirst().orElseThrow(() -> new MicroServiceException(Code.PERMISSION_DENIED));
        //任何人都可以邀请
        if(me.deleted()){
            throw new MicroServiceException(Code.PERMISSION_DENIED);
        }
        List<RoomUserBean> u4UnDel = Lists.newArrayList();
        long revisionRoom = idHelper.genId(RelationshipConstant.ID_TYPE_ROOM_REVISION);
        for (RoomUserBean r : roomUserBeanList){
            if(map.containsKey(r.getUserId())){
                if(r.deleted()){
                    RoomUser ru = map.get(r.getUserId());
                    r.setRevisionRoom(revisionRoom);
                    r.setRevisionUser(idHelper.genId(RelationshipConstant.ID_TYPE_ROOM_USER_REVISION));
                    r.setRemark(ru.getRemark());
                    r.setUserLevel(ru.getUserLevel());
                    u4UnDel.add(r);
                }
                map.remove(r.getUserId());
            }
        }
        if(!u4UnDel.isEmpty()){
            roomUserDao.batchUpdate(u4UnDel);
        }
        if(!map.isEmpty()){
            List<RoomUserBean> u4Insert = map.values().stream()
                    .map(r->{
                        RoomUserBean rub = new RoomUserBean();
                        rub.setRoomId(roomUserAdd.getRoomId());
                        rub.setUserId(r.getUserId());
                        rub.setRemark(r.getRemark());
                        rub.setUserLevel(r.getUserLevel());
                        rub.setRevisionUser(idHelper.genId(RelationshipConstant.ID_TYPE_ROOM_USER_REVISION));
                        rub.setRevisionRoom(revisionRoom);
                        rub.genId();
                        return rub;
                    }).collect(Collectors.toList());
            roomUserDao.batchInsert(u4Insert);
        }
        return roomHelper.countRoomUser(roomUserAdd.getRoomId());
    }
}
