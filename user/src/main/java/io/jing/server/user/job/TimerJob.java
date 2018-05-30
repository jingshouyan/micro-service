package io.jing.server.user.job;

import com.google.common.collect.Lists;
import io.jing.base.util.json.JsonUtil;
import io.jing.server.user.model.AccountBean;
import io.jing.server.user.model.UserBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jingshouyan
 * @date 2018/4/25 23:04
 */
@Component
@Slf4j
public class TimerJob {

//    @Scheduled(fixedDelay = 5000)
    public void throwExp(){
        try{
            throw new RuntimeException("this is Runtime Exception!");
        }catch (Exception e){
            log.error("cache Exp",e);
        }
    }
//    @Scheduled(fixedDelay = 3000)
    public void json(){
        UserBean userBean = new UserBean();
        userBean.setName("张三");
        userBean.setUserType(1);
        AccountBean accountBean = new AccountBean();
        accountBean.setContactInfo("abcdef");
        accountBean.setStatus(1);
        userBean.setAccountBean(accountBean);
        List<AccountBean> accountBeans = Lists.newArrayList(accountBean);
        userBean.setAccountBeans(accountBeans);
        userBean.forCreate();
        AccountBean accountBean1 = new AccountBean();
        accountBean1.setContactInfo("wwww");
        accountBean1.setStatus(2);
        accountBean1.forCreate();
        accountBeans.add(accountBean1);
        String json = JsonUtil.toJsonString(userBean);
        log.info("json log :{}" ,json);
    }
}
