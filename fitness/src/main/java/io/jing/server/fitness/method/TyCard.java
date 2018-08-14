package io.jing.server.fitness.method;

import io.jing.base.exception.MicroServiceException;
import io.jing.server.fitness.bean.ClubCardBean;
import io.jing.server.fitness.bean.CustomBean;
import io.jing.server.fitness.bean.TyQ;
import io.jing.server.fitness.constant.FitnessCode;
import io.jing.server.fitness.constant.FitnessConstant;
import io.jing.server.fitness.dao.ClubCardDao;
import io.jing.server.fitness.dao.CustomDao;
import io.jing.server.method.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component("fitness.tyCard")
public class TyCard implements Method<TyQ> {

    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
    @Autowired
    private ClubCardDao clubcardDao;
    @Autowired
    private CustomDao customDao;

    @Override
    public Object action(TyQ tyQ) {
        Optional<CustomBean> customBeanOptional = customDao.find(tyQ.getCustomId());
        CustomBean customBean = customBeanOptional
                .orElseThrow(() -> new MicroServiceException(FitnessCode.CUSTOM_NOT_FUND));
        if(customBean.getCardId()!= null && customBean.getCardId()>0){
            throw new MicroServiceException(FitnessCode.NO_RIGHT_TO_DO);
        }
        Optional<ClubCardBean> clubCardBeanOptional = clubcardDao.find(FitnessConstant.TY_CARD_ID);
        ClubCardBean tyCard = clubCardBeanOptional
                .orElseThrow(() -> new MicroServiceException(FitnessCode.CARD_NOT_FUND));
        LocalDate today = LocalDate.now();
        LocalDate date = today.plusDays(tyCard.getDays());
        String dateStr = date.format(df);
        customBean.setCardId(tyCard.getId());
        customBean.setUsableCount(tyCard.getCount());
        customBean.setCardPeriod(dateStr);
        customBean.setClubCard(tyCard);
        customBean.forUpdate();
        customDao.update(customBean);
        return customBean;
    }
}
