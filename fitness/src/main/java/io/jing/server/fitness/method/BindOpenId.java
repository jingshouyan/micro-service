package io.jing.server.fitness.method;

import io.jing.base.exception.MicroServiceException;
import io.jing.server.fitness.bean.BindInfo;
import io.jing.server.fitness.bean.ClubCardBean;
import io.jing.server.fitness.bean.CustomBean;
import io.jing.server.fitness.constant.FitnessCode;
import io.jing.server.fitness.dao.ClubCardDao;
import io.jing.server.fitness.dao.CustomDao;
import io.jing.server.method.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author jingshouyan
 * #date 2018/8/13 21:15
 */
@Component("fitness.bindOpenId")
public class BindOpenId implements Method<BindInfo> {

    @Autowired
    private CustomDao customDao;
    @Autowired
    private ClubCardDao clubCardDao;

    @Override
    public Object action(BindInfo bindInfo) {
        Optional<CustomBean> customBeanOptional = customDao.find(bindInfo.getId());
        CustomBean customBean = customBeanOptional.orElseThrow(() -> new MicroServiceException(FitnessCode.CUSTOM_NOT_FUND));
        if (!bindInfo.getContact().equals(customBean.getContact())){
            throw new MicroServiceException(FitnessCode.CONTACT_WRONG);
        }
        Optional<CustomBean> customBeanOptional2 = customDao.findByOpenId(bindInfo.getOpenId());
        if (customBeanOptional2.isPresent()){
            throw new MicroServiceException(FitnessCode.OPENID_IN_USE);
        }
        customBean.setOpenId(bindInfo.getOpenId());
        customBean.forUpdate();
        customDao.update(customBean);
        if (customBean.getCardId() != null && customBean.getCardId() != 0){
            Optional<ClubCardBean> clubCardBeanOptional =clubCardDao.find(customBean.getCardId());
            clubCardBeanOptional.ifPresent(customBean::setClubCard);
        }
        return customBean;
    }
}
