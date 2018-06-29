package io.jing.server.sms.dao.impl;

import io.jing.server.sms.bean.SmsRecord;
import io.jing.server.sms.dao.SmsDao;
import io.jing.util.jdbc.core.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * @author jingshouyan
 * #date 2018/6/28 18:54
 */
@Repository
public class SmsDaoImpl extends BaseDaoImpl<SmsRecord> implements SmsDao {
}
