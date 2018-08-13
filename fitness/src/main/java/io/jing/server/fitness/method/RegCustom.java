package io.jing.server.fitness.method;

import io.jing.base.exception.MicroServiceException;
import io.jing.server.fitness.bean.CustomBean;
import io.jing.server.fitness.bean.CustomInfo;
import io.jing.server.fitness.constant.FitnessCode;
import io.jing.server.fitness.dao.CustomDao;
import io.jing.server.method.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author jingshouyan
 * #date 2018/8/13 21:45
 */
@Component("fitness.regCustom")
public class RegCustom implements Method<CustomInfo> {

    @Autowired
    private CustomDao customDao;
    @Override
    public Object action(CustomInfo customInfo) {
        Optional<CustomBean> customBeanOptional2 = customDao.findByOpenId(customInfo.getOpenId());
        if (customBeanOptional2.isPresent()){
            throw new MicroServiceException(FitnessCode.OPENID_IN_USE);
        }

        return null;
    }
}
