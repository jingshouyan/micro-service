package io.jing.server.db.helper;

import com.google.common.collect.Maps;
import io.jing.server.db.bean.IdBean;
import io.jing.server.db.dao.IdDao;
import io.jing.util.jdbc.core.util.keygen.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class IdHelper implements KeyGenerator {
    @Autowired
    private IdDao idDao;

    private static final long STEP = 1000L;

    private static final long INIT_ID = 10000L;

    private static final Map<String,Long> MAP = Maps.newConcurrentMap();

    public long genId(String type){
        synchronized (type.intern()){
            Long id = MAP.get(type);
            if(null == id){
                Optional<IdBean> idBeanOptional = idDao.find(type);
                if(idBeanOptional.isPresent()){
                    IdBean idBean = idBeanOptional.get();
                    id = idBean.getSeed();
                    idBean.setSeed(id + STEP*2);
                    idBean.forUpdate();
                    idDao.update(idBean);
                }else{
                    id = INIT_ID;
                    IdBean idBean = new IdBean();
                    idBean.setType(type);
                    idBean.setSeed(id + STEP*2);
                    idBean.forCreate();
                    idDao.insert(idBean);
                }
            }
            id++;
            MAP.put(type,id);
            if(id % STEP == 0){
                IdBean idBean = new IdBean();
                idBean.setType(type);
                idBean.setSeed(id + STEP*2);
                idBean.forUpdate();
                idDao.update(idBean);
            }
            return id;
        }
    }
    public String genIdStr(String type){
        return String.valueOf(genId(type));
    }
}
