package io.jing.server.fitness.init;

import io.jing.server.fitness.bean.ClubCardBean;
import io.jing.server.fitness.constant.FitnessConstant;
import io.jing.server.fitness.dao.ClubCardDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class InitData implements CommandLineRunner {

    @Autowired
    private ClubCardDao clubCardDao;

    @Override
    public void run(String... strings) {
        long tyId = FitnessConstant.TY_CARD_ID;
        Optional<ClubCardBean> clubCardBeanOptional = clubCardDao.find(tyId);
        if(!clubCardBeanOptional.isPresent()){
            ClubCardBean card = new ClubCardBean();
            card.setId(tyId);
            card.setCount(1);
            card.setDays(7);
            card.setName("体验卡");
            card.setType("体验卡");
            card.setNote("体验卡");
            card.forCreate();
            log.info("体验卡不存在,初始化. {}",card);
            clubCardDao.insert(card);
        }else {
            log.info("体验卡存在. {}",clubCardBeanOptional.get());
        }
    }
}
