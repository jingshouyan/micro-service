package io.jing.server.sms.method;

import io.jing.server.method.Method;
import io.jing.server.sms.bean.CodeSend;
import io.jing.server.sms.bean.SmsRecord;
import io.jing.server.sms.constant.SmsConstant;
import io.jing.server.sms.dao.SmsDao;
import io.jing.server.sms.util.netease.SmsResult;
import io.jing.server.sms.util.netease.SmsUtil;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/6/28 19:11
 */
@Component
public class SendCode implements Method<CodeSend> {

    @Autowired
    private SmsDao smsDao;

    @Override
    public Object action(CodeSend codeSend) {
        SmsRecord sms4Up = new SmsRecord();
        sms4Up.setCodeState(SmsConstant.CODE_STATE_NO);
        sms4Up.forUpdate();
        List<Compare> compares = CompareUtil.newInstance()
                .field("mobile").eq(codeSend.getMobile())
                .field("codeType").eq(codeSend.getCodeType())
                .field("codeState").eq(SmsConstant.CODE_STATE_OK)
                .compares();
        smsDao.update(sms4Up,compares);
        SmsRecord smsRecord = new SmsRecord();
        smsRecord.setMobile(codeSend.getMobile());
        smsRecord.setCodeType(codeSend.getCodeType());
        SmsResult result = SmsUtil.sendCode(codeSend.getMobile());
        smsRecord.setMsg(result.getMsg());
        smsRecord.setResultCode(result.getCode());
        smsRecord.setCode(result.getObj());
        smsRecord.setCodeState(SmsConstant.CODE_STATE_OK);
        smsRecord.setVerifyNum(0);
        smsRecord.setVerifyAt(0L);
        smsRecord.setExpireAt(System.currentTimeMillis()+SmsConstant.CODE_PERIOD);
        smsRecord.forCreate();
        smsDao.insert(smsRecord);
        return null;
    }
}
