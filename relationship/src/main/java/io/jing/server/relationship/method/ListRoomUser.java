package io.jing.server.relationship.method;

import io.jing.base.exception.MicroServiceException;
import io.jing.base.util.code.Code;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.method.Method;
import io.jing.server.relationship.bean.QueryParam;
import io.jing.server.relationship.bean.RoomUserBean;
import io.jing.server.relationship.dao.RoomUserDao;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import io.jing.util.jdbc.core.util.db.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author jingshouyan
 * #date 2018/6/16 15:18
 */
@Component("relationship.listRoomUser")
public class ListRoomUser implements Method<QueryParam> {

    @Autowired
    private RoomUserDao roomUserDao;

    @Override
    public Object action(QueryParam queryParam) {
        String myId = ThreadLocalUtil.userId();
        RoomUserBean tmp = new RoomUserBean();
        tmp.setRoomId(queryParam.getId());
        tmp.setUserId(myId);
        String id = tmp.genId();
        Optional<RoomUserBean> me = roomUserDao.find(id);
        //只有 room 内成员才能使用此接口
        if(me.isPresent()&&!me.get().deleted()){
            List<Compare> compares = CompareUtil.newInstance()
                    .field("roomId").eq(queryParam.getId())
                    .field("revisionUser").gt(queryParam.getRevision())
                    .compares();
            if(!queryParam.isContainDel()){
                Compare c = new Compare();
                c.setField("deletedAt");
                c.setEq(-1);
                compares.add(c);
            }
            Page<RoomUserBean> page = new Page<>();
            page.setPageSize(queryParam.getSize());
            page.addOrderBy("revisionRoom");
            List<RoomUserBean> roomUserBeanList = roomUserDao.queryLimit(compares,page);
            return roomUserBeanList;
        }
        throw new MicroServiceException(Code.PERMISSION_DENIED);
    }
}
