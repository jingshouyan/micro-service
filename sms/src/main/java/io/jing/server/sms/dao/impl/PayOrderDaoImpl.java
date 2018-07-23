package io.jing.server.sms.dao.impl;

import io.jing.server.sms.bean.PayOrder;
import io.jing.server.sms.dao.PayOrderDao;
import io.jing.util.jdbc.core.dao.impl.BaseDaoImpl;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author jingshouyan
 * #date 2018/7/16 19:19
 */
@Repository
public class PayOrderDaoImpl extends BaseDaoImpl<PayOrder> implements PayOrderDao {

    @Override
    public Optional<PayOrder> userOrder(String userId, String clientOrder) {
        List<Compare> compares = CompareUtil.newInstance()
                .field("userId").eq(userId)
                .field("clientOrder").eq(clientOrder)
                .compares();
        List<PayOrder> payOrderList = query(compares);
        if(payOrderList.isEmpty()){
            return Optional.empty();
        }else {
            return Optional.of(payOrderList.get(0));
        }

    }
}
