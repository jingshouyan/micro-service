package io.jing.server.fitness.method;

import io.jing.base.exception.MicroServiceException;
import io.jing.server.db.helper.IdHelper;
import io.jing.server.fitness.bean.CustomBean;
import io.jing.server.fitness.bean.CustomInfo;
import io.jing.server.fitness.constant.FitnessCode;
import io.jing.server.fitness.constant.FitnessConstant;
import io.jing.server.fitness.dao.CustomDao;
import io.jing.server.method.Method;
import org.springframework.beans.BeanUtils;
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
    private IdHelper idHelper;
    @Autowired
    private CustomDao customDao;
    @Override
    public Object action(CustomInfo customInfo) {
        Optional<CustomBean> customBeanOptional2 = customDao.findByOpenId(customInfo.getOpenId());
        //openId 已经被使用.
        if (customBeanOptional2.isPresent()){
            throw new MicroServiceException(FitnessCode.OPENID_IN_USE);
        }
        CustomBean customBean = new CustomBean();
        BeanUtils.copyProperties(customInfo,customBean);
        customBean.setId(idHelper.genId(FitnessConstant.ID_TYPE_CUSTOM));
        customBean.forCreate();
        customDao.insert(customBean);
        return customBean;
    }
}
