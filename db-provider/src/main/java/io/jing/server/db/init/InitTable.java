package io.jing.server.db.init;

import io.jing.base.util.config.ConfigSettings;
import io.jing.server.constant.ServerConstant;
import io.jing.server.db.helper.IdHelper;
import io.jing.util.jdbc.core.dao.BaseDao;
import io.jing.util.jdbc.core.util.keygen.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author jingshouyan
 * #date 2018/7/13 10:17
 */
@Component
@Slf4j
public class InitTable{

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private IdHelper idHelper;

    @PostConstruct
    public void init(){
        long flag = ServerConstant.DS_INIT;
        log.info("init tables,flag = {}",flag);
        if(flag == 1){
            createTable();
        }
        if(flag == 2){
            dropTable();
            createTable();
        }

        IdUtil.setKeyGenerator(idHelper);
    }

    private void createTable() {
        Map<String, BaseDao> map = ctx.getBeansOfType(BaseDao.class);
        for (BaseDao dao : map.values()) {
            log.info("dao[{}] create table",dao);
            dao.createTable();
        }
    }

    private void dropTable() {
        Map<String, BaseDao> map = ctx.getBeansOfType(BaseDao.class);
        for (BaseDao dao : map.values()) {
            log.info("dao[{}] drop table",dao);
            dao.dropTable();
        }
    }

}
