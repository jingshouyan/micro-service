package io.jing.server.relationship.method;

import io.jing.base.bean.Empty;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.method.Method;
import io.jing.server.relationship.bean.RoomLeave;
import io.jing.server.relationship.bean.RoomUserBean;
import io.jing.server.relationship.dao.RoomUserDao;
import io.jing.server.relationship.helper.RoomHelper;
import io.jing.util.jdbc.core.util.keygen.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author jingshouyan
 * #date 2018/6/16 20:36
 */
@Component
public class LeaveRoom implements Method<RoomLeave> {

    @Autowired
    private RoomUserDao roomUserDao;

    @Autowired
    private RoomHelper roomHelper;

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
                me.setRevisionUser(IdUtil.longId());
                me.setRevisionRoom(IdUtil.longId());
                me.forDelete();
                roomUserDao.update(me);
                roomHelper.countRoomUser(roomLeave.getRoomId());
            }
        }
        return null;
    }
}
