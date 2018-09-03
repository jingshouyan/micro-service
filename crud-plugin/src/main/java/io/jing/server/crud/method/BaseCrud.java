package io.jing.server.crud.method;

import com.google.common.base.Preconditions;
import io.jing.server.crud.constant.CrudConstant;
import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public abstract class BaseCrud implements CrudConstant {

    @Autowired
    private ApplicationContext ctx;

    protected BaseDao<BaseBean> dao(String beanName){
        String daoImplName = beanName + "DaoImpl";
        BaseDao<BaseBean> dao = ctx.getBean(daoImplName,BaseDao.class);
        Preconditions.checkNotNull(dao, beanName + "DaoImpl not found.");
        return dao;
    }
}
