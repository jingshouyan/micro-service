package io.jing.server.acl.bean;

import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.util.db.annotation.Column;
import io.jing.util.jdbc.core.util.db.annotation.Key;
import io.jing.util.jdbc.core.util.db.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jingshouyan
 * #date 2018/8/30 23:54
 */
@Getter
@Setter
@ToString(callSuper = true)
@Table(comment = "资源信息")
public class ResourceBean extends BaseBean {
    @Key
    private Long id;
    @Column(comment = "资源名称")
    private String name;
    @Column(comment = "资源编码")
    private String code;
    @Column(comment = "说明")
    private String description;
    @Column(comment = "http method: GET/POST/PUT/DELETE etc.")
    private String method;
    @Column(comment = "uri路径")
    private String uri;
    @Column(comment = "资源类型 1 公开,2 需登录,3 需授权")
    private Integer type;
    @Column(comment = "状态 1 有效，2 失效")
    private Integer state;
    @Column(comment = "调用后退出")
    private Boolean logout;
}

