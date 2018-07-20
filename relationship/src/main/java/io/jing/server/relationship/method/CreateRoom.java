package io.jing.server.relationship.method;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.db.helper.IdHelper;
import io.jing.server.method.Method;
import io.jing.server.relationship.bean.RoomBean;
import io.jing.server.relationship.bean.RoomCreate;
import io.jing.server.relationship.bean.RoomUser;
import io.jing.server.relationship.bean.RoomUserBean;
import io.jing.server.relationship.constant.RelationshipConstant;
import io.jing.server.relationship.dao.RoomDao;
import io.jing.server.relationship.dao.RoomUserDao;
import io.jing.util.jdbc.core.util.keygen.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author jingshouyan
 * #date 2018/6/14 15:30
 */
@Component
public class CreateRoom implements Method<RoomCreate> {

    @Autowired
    private RoomDao roomDao;
    @Autowired
    private RoomUserDao roomUserDao;

    @Autowired
    private IdHelper idHelper;

    @Override
    public Object action(RoomCreate roomCreate) {
        String myId = ThreadLocalUtil.userId();
        RoomBean roomBean = new RoomBean();
        roomBean.setId(idHelper.genIdStr(RelationshipConstant.ID_TYPE_ROOM));
        roomBean.setName(roomCreate.getName());
        roomBean.setIcon(roomCreate.getIcon());
        roomBean.setRevision(IdUtil.longId());
        roomBean.forCreate();

        Set<String> uSet = Sets.newHashSet(myId);
        List<RoomUserBean> roomUserBeanList = Lists.newArrayList();
        RoomUserBean owner = new RoomUserBean();
        owner.setUserId(myId);
        owner.setRemark("");
        owner.setRoomId(roomBean.getId());
        owner.setUserLevel(RelationshipConstant.ROOM_LEVEL_OWNER);
        owner.setRevisionUser(IdUtil.longId());
        owner.setRevisionRoom(IdUtil.longId());
        owner.forCreate();
        for (RoomUser roomUser : roomCreate.getRoomUsers()){
            if(uSet.contains(roomUser.getUserId())){
                continue;
            }
            uSet.add(roomUser.getUserId());
            RoomUserBean roomUserBean = new RoomUserBean();
            roomUserBean.setUserId(roomUser.getUserId());
            roomUserBean.setRemark(roomUser.getRemark());
            roomUserBean.setRoomId(roomBean.getId());
            roomUserBean.setUserLevel(roomUser.getUserLevel());
            roomUserBean.setRevisionUser(IdUtil.longId());
            roomUserBean.setRevisionRoom(roomBean.getRevision());
            roomUserBean.genId();
            roomUserBean.forCreate();
            roomUserBeanList.add(roomUserBean);
        }
        roomBean.setUserCount(roomUserBeanList.size());
        roomDao.insert(roomBean);
        roomUserDao.batchInsert(roomUserBeanList);
        return roomBean;
    }
}
