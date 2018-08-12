package io.jing.server.fitness.bean;

import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.util.db.annotation.Column;
import io.jing.util.jdbc.core.util.db.annotation.Key;
import io.jing.util.jdbc.core.util.db.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jingshouyan
 * #date 2018/8/7 21:53
 */
@Getter
@Setter
@ToString(callSuper = true)
@Table(comment = "课程信息")
public class LessonBean extends BaseBean {

    @Key
    private Long id;
    @Column("课程名称")
    private String name;
    @Column("课程开始时间")
    private String startAt;
    @Column("课程时常，分钟")
    private Integer duration;
    @Column("课程人数")
    private Integer pCount;
    @Column("课程申请人数")
    private Integer applyCount;
    @Column("课程地点")
    private String location;
    @Column("课程类型")
    private String type;

}
