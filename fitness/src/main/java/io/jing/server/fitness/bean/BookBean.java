package io.jing.server.fitness.bean;

import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.util.db.annotation.Column;
import io.jing.util.jdbc.core.util.db.annotation.Ignore;
import io.jing.util.jdbc.core.util.db.annotation.Key;
import io.jing.util.jdbc.core.util.db.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jingshouyan
 * #date 2018/8/13 18:02
 */
@Getter
@Setter
@ToString(callSuper = true)
@Table(comment = "预约信息")
public class BookBean extends BaseBean {

    @Key
    private Long id;
    @Column(comment = "客户id")
    private Long customId;
    @Column(comment = "课程id")
    private Long lessonId;
    @Ignore
    private CustomBean custom;
    @Ignore
    private LessonBean lesson;
}
