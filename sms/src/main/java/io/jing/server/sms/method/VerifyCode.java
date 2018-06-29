package io.jing.server.sms.method;

import io.jing.base.exception.MicroServiceException;
import io.jing.base.thrift.MicroService;
import io.jing.server.method.Method;
import io.jing.server.sms.bean.CodeVerify;
import io.jing.server.sms.bean.SmsRecord;
import io.jing.server.sms.constant.SmsCode;
import io.jing.server.sms.constant.SmsConstant;
import io.jing.server.sms.dao.SmsDao;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import io.jing.util.jdbc.core.util.db.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/6/28 19:29
 */
@Component
public class VerifyCode implements Method<CodeVerify> {

    @Autowired
    private SmsDao smsDao;
    @Override
    public Object action(CodeVerify codeVerify) {
        List<Compare> compares = CompareUtil.newInstance()
                .field("mobile").eq(codeVerify.getMobile())
                .field("codeType").eq(codeVerify.getCodeType())
                .field("code").eq(codeVerify.getCode())
                .field("codeState").eq(SmsConstant.CODE_STATE_OK)
                .compares();
        Page<SmsRecord> page = new Page<>();
        page.addOrderBy("createdAt",false);
        List<SmsRecord> records = smsDao.queryLimit(compares,page);
        if(records.isEmpty()){
            throw new MicroServiceException(SmsCode.CODE_INVALID);
        }
        SmsRecord record = records.get(0);
        if(record.getExpireAt()<System.currentTimeMillis()){
            throw new MicroServiceException(SmsCode.CODE_EXPIRE);
        }
        record.setVerifyAt(System.currentTimeMillis());
        int verifyNum = record.getVerifyNum() == null ? 0 : record.getVerifyNum();
        record.setVerifyNum(verifyNum+1);
        record.forUpdate();
        smsDao.update(record);
        return null;
    }
}
