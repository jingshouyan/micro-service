package io.jing.server.fitness.method;

import io.jing.base.exception.MicroServiceException;
import io.jing.server.fitness.bean.ClubCardBean;
import io.jing.server.fitness.bean.CustomBean;
import io.jing.server.fitness.bean.WechatInfo;
import io.jing.server.fitness.constant.FitnessCode;
import io.jing.server.fitness.dao.ClubCardDao;
import io.jing.server.fitness.dao.CustomDao;
import io.jing.server.method.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author jingshouyan
 * #date 2018/8/13 18:13
 */
@Component("fitness.getCustom")
public class GetCustom implements Method<WechatInfo> {

    @Autowired
    private CustomDao customDao;
    @Autowired
    private ClubCardDao clubCardDao;

    @Override
    public Object action(WechatInfo wechatInfo) {
        Optional<CustomBean> customBeanOptional = customDao.findByOpenId(wechatInfo.getOpenId());
        CustomBean customBean = customBeanOptional.orElseThrow(() -> new MicroServiceException(FitnessCode.CUSTOM_NOT_FUND));
        if (customBean.getCardId() != null && customBean.getCardId() != 0){
            Optional<ClubCardBean> clubCardBeanOptional =clubCardDao.find(customBean.getCardId());
            clubCardBeanOptional.ifPresent(customBean::setClubCard);
        }
        return customBean;
    }
}
