package io.jing.server.relationship.method;

import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.method.Method;
import io.jing.server.relationship.bean.QueryParam;
import io.jing.server.relationship.bean.RoomBean;
import io.jing.server.relationship.bean.RoomUserBean;
import io.jing.server.relationship.dao.RoomDao;
import io.jing.server.relationship.dao.RoomUserDao;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import io.jing.util.jdbc.core.util.db.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author jingshouyan
 * #date 2018/6/15 11:23
 */
@Component
public class ListRoom implements Method<QueryParam> {

    @Autowired
    private RoomDao roomDao;
    @Autowired
    private RoomUserDao roomUserDao;

    @Override
    public Object action(QueryParam queryParam) {
        String myId = ThreadLocalUtil.userId();
        List<Compare> compares = CompareUtil.newInstance()
                .field("userId").eq(myId)
                .field("revisionRoom").gt(queryParam.getRevision())
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
        if (!roomUserBeanList.isEmpty()){
            List<String> roomIds = roomUserBeanList.stream()
                    .filter(r -> !r.deleted())
                    .map(RoomUserBean::getRoomId)
                    .collect(Collectors.toList());
            List<Compare> cl = CompareUtil.newInstance()
                    .field("id").in(roomIds)
                    .compares();
            List<RoomBean> roomBeanList = roomDao.query(cl);
            Map<String,RoomBean> roomBeanMap = roomBeanList.stream()
                    .collect(Collectors.toMap(RoomBean::getId,r ->r,(r1,r2)->r1));
            roomUserBeanList.forEach(ru->{
                RoomBean r = roomBeanMap.get(ru.getRoomId());
                ru.setRoom(r);
            });
        }
        return roomUserBeanList;
    }
}
