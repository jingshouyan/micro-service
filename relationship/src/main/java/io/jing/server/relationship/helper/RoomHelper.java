package io.jing.server.relationship.helper;

import io.jing.server.db.helper.IdHelper;
import io.jing.server.relationship.bean.RoomBean;
import io.jing.server.relationship.bean.RoomUserBean;
import io.jing.server.relationship.constant.RelationshipConstant;
import io.jing.server.relationship.dao.RoomDao;
import io.jing.server.relationship.dao.RoomUserDao;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import io.jing.util.jdbc.core.util.keygen.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/6/16 22:28
 */
@Component
public class RoomHelper {

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private RoomUserDao roomUserDao;

    @Autowired
    private IdHelper idHelper;

    public int countRoomUser(String roomId){
        long revisionRoom = idHelper.genId(RelationshipConstant.ID_TYPE_ROOM_REVISION);;
        List<Compare> compareList = CompareUtil.newInstance()
                .field("roomId").eq(roomId)
                .field("deletedAt").eq(-1)
                .compares();
        RoomUserBean r = new RoomUserBean();
        r.setRevisionRoom(revisionRoom);
        r.forUpdate();
        int count = roomUserDao.update(r,compareList);
        RoomBean roomBean = new RoomBean();
        roomBean.setId(roomId);
        roomBean.setUserCount(count);
        roomBean.setRevision(revisionRoom);
        roomBean.forUpdate();
        roomDao.update(roomBean);
        return count;
    }
}
