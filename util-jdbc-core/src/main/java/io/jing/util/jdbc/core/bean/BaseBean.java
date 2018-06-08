package io.jing.util.jdbc.core.bean;

import io.jing.util.jdbc.core.util.Constant;
import io.jing.util.jdbc.core.util.db.annotation.Column;
import io.jing.util.jdbc.core.util.db.annotation.Key;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 数据实体抽象类
 * @author jingshouyan
 * @date 2018/4/14 17:25
 */
@Getter@Setter@ToString
public abstract class BaseBean implements Serializable{

    @Column(order = 1001,comment = "创建时间")
    private Long createdAt;
    @Column(order = 1002,comment = "修改时间")
    private Long updatedAt;
    @Column(order = 1003,comment = "删除时间")
    private Long deletedAt;


    public void forCreate() {
        long now = System.currentTimeMillis();
        createdAt = now;
        updatedAt = now;
        deletedAt = -1L;
    }

    public void forUpdate() {
        long now = System.currentTimeMillis();
        createdAt = null;
        updatedAt = now;
        deletedAt = null;
    }

    public void forDelete() {
        long now = System.currentTimeMillis();
        createdAt = null;
        updatedAt = now;
        deletedAt = now;
    }

    public void forUndelete() {
        long now = System.currentTimeMillis();
        createdAt = null;
        updatedAt = now;
        deletedAt = -1L;
    }


    public boolean deleted() {
        return deletedAt != null && !deletedAt.equals(-1L);
    }

    public static boolean deleted(BaseBean bean) {
        return bean == null || bean.deleted();
    }

}
