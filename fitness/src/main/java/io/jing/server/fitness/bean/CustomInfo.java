package io.jing.server.fitness.bean;

import io.jing.util.jdbc.core.util.db.annotation.Column;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author jingshouyan
 * #date 2018/8/13 21:46
 */
@Getter@Setter
public class CustomInfo {

    @NonNull
    @Column(comment = "微信 openId")
    private String openId;
    @NonNull
    @Column(comment = "客户姓名")
    private String name;
    @NonNull
    @Column(comment = "联系方式")
    private String contact;
    @Column(comment = "身高")
    private Double height = 180.0;
    @Column(comment = "体重")
    private Double weight = 50.0;
    @Column(comment = "出生年 yyyy")
    private String birthyear = "1990";
    @Column(comment = "生日 MMdd")
    private String birthday="1212";
}
