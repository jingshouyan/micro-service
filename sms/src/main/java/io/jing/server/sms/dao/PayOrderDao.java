package io.jing.server.sms.dao;

import io.jing.server.sms.bean.PayOrder;
import io.jing.util.jdbc.core.dao.BaseDao;

import java.util.Optional;

/**
 * @author jingshouyan
 * #date 2018/6/28 18:54
 */
public interface PayOrderDao extends BaseDao<PayOrder> {
    Optional<PayOrder> userOrder(String userId,String clientOrder);
}
