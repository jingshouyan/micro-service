package io.jing.server.sms.controller;

import com.google.common.collect.Maps;
import io.jing.server.sms.bean.PayOrder;
import io.jing.server.sms.constant.SmsConstant;
import io.jing.server.sms.dao.PayOrderDao;
import io.jing.util.jdbc.core.util.keygen.IdUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.Optional;

/**
 * @author jingshouyan
 * #date 2018/7/16 19:18
 */
@Controller
@RequestMapping("pay")
public class PayController implements SmsConstant {

    @Autowired
    private PayOrderDao payOrderDao;

    @RequestMapping("order")
    public String pay(
            @RequestParam String userId,
            @RequestParam String clientOrder,
            @RequestParam String subject,
            @RequestParam int money,
            @RequestParam String attachData,
            Map<String,Object> modal
            ){
        PayOrder payOrder = new PayOrder();
        payOrder.setUserId(userId);
        payOrder.setClientOrder(clientOrder);
        payOrder.setSubject(subject);
        payOrder.setAttachData(attachData);
        Optional<PayOrder> payOrderOptional = payOrderDao.userOrder(payOrder.getUserId(),payOrder.getClientOrder());
//        if(payOrderOptional.isPresent()){
//            return "error";
//        }
        payOrder.setPayId(PAY92_PAYID);
        payOrder.setPayChannel("alipay");
        payOrder.setMoney(1);
        payOrder.setOrderNumber(IdUtil.stringId());
        payOrder.forCreate();
        payOrder.setState(1);
        payOrderDao.insert(payOrder);
        Map<String,Object> data = payData(payOrder);
        modal.putAll(data);
        return "pay";
    }


    private Map<String,Object> payData(PayOrder payOrder){
        StringBuilder sb = new StringBuilder();
        Map<String,Object> map = Maps.newHashMap();
        map.put("Money",payOrder.getMoney());
        sb.append(payOrder.getMoney().toString());
        map.put("Notify_url",PAY92_NOTIFY_URL);
        sb.append(PAY92_NOTIFY_URL);
        map.put("Return_url",PAY92_RETURN_URL);
        sb.append(PAY92_RETURN_URL);
        map.put("Subject",payOrder.getSubject());
        sb.append(payOrder.getSubject());
        map.put("attachData",payOrder.getAttachData());
        sb.append(payOrder.getAttachData());
        map.put("orderNumber",payOrder.getOrderNumber());
        sb.append(payOrder.getOrderNumber());
        map.put("payChannel",payOrder.getPayChannel());
        sb.append(payOrder.getPayChannel());
        map.put("payId",PAY92_PAYID);
        sb.append(PAY92_PAYID);
        map.put("payKey",PAY92_PAYKEY);
        sb.append(PAY92_PAYKEY);
        String sign = DigestUtils.md5Hex(sb.toString());
        map.put("Sign",sign);
        return map;
    }

}
