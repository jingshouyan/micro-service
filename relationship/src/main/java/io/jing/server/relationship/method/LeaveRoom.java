package io.jing.server.relationship.method;

import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.db.helper.IdHelper;
import io.jing.server.method.Method;
import io.jing.server.relationship.bean.RoomLeave;
import io.jing.server.relationship.bean.RoomUserBean;
import io.jing.server.relationship.constant.RelationshipConstant;
import io.jing.server.relationship.dao.RoomUserDao;
import io.jing.server.relationship.helper.RoomHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author jingshouyan
 * #date 2018/6/16 20:36
 */
@Component("relationship.leaveRoom")
public class LeaveRoom implements Method<RoomLeave> {

    @Autowired
    private RoomUserDao roomUserDao;

    @Autowired
    private RoomHelper roomHelper;

    @Autowired
    private IdHelper idHelper;

    @Override
    public Object action(RoomLeave roomLeave) {
        String myId = ThreadLocalUtil.userId();
        RoomUserBean me = new RoomUserBean();
        me.setUserId(myId);
        me.setRoomId(roomLeave.getRoomId());
        Optional<RoomUserBean> roomUserBeanOptional = roomUserDao.find(me.genId());
        if(roomUserBeanOptional.isPresent()){
            me = roomUserBeanOptional.get();
            if(!me.deleted()){
                me.setRevisionUser(idHelper.genId(RelationshipConstant.ID_TYPE_ROOM_USER_REVISION));
                me.setRevisionRoom(idHelper.genId(RelationshipConstant.ID_TYPE_ROOM_REVISION));
                me.forDelete();
                roomUserDao.update(me);
                roomHelper.countRoomUser(roomLeave.getRoomId());
            }
        }
        return null;
    }
}
