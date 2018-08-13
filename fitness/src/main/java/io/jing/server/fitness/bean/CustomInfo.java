package io.jing.server.fitness.bean;

import io.jing.util.jdbc.core.util.db.annotation.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * @author jingshouyan
 * #date 2018/8/13 21:46
 */
@Getter@Setter
public class CustomInfo {

    @Column(comment = "微信 openId")
    private String openId;
    @Column(comment = "客户姓名")
    private String name;
    @Column(comment = "联系方式")
    private String contact;
    @Column(comment = "身高")
    private Double height;
    @Column(comment = "体重")
    private Double weight;
    @Column(comment = "出生年 yyyy")
    private String birthyear;
    @Column(comment = "生日 MMdd")
    private String birthday;
}
