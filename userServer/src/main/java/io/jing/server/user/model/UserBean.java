package io.jing.server.user.model;

import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.util.Constant;
import io.jing.util.jdbc.core.util.db.annotation.Column;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author jingshouyan
 * @date 2018/4/19 10:22
 */
@Data@ToString(callSuper = true)
public class UserBean extends BaseBean {
    @Column(comment="用户昵称")
    String name;
    @Column(comment="昵称模糊匹配字段",length=20000)
    String name_match;
    @Column(comment="真实姓名")
    String realName;
    @Column(comment="用户类型 1.正常用户,2.机器人")
    Integer userType;
    @Column(comment="头像地址")
    String portraitURL;
    @Column(comment="原头像地址")
    String oriPortraitURL;
    @Column(comment="手机号码")
    String phoneNum;
    @Column(comment="邮箱")
    String email;
    @Column(comment="性别 1.男 2.女 3.保密")
    Integer sex;
    @Column(comment="使用状态 1.正常 2.未激活")
    Integer status;
    @Column(comment="存活状态 1.正常 2.锁定 3.冻")
    Integer survival;
    @Column(comment="地区")
    String area;
    @Column(comment="生日")
    String birthday;
    @Column(comment="密级")
    Integer securityLevel;
    @Column(comment="旧密级")
    Integer oldsecurityLevel;
    @Column(comment="手势密码")
    String gesturePwd;
    @Column(comment="扩展字段")
    String extend;
    @Column(comment="是否允许通过手机搜索: 1.允许 2.不允许")
    Integer allowPhoneSearch;
    @Column(comment="是否允许通过邮箱搜索: 1.允许 2.不允许")
    Integer allowEmailSearch;
    @Column(comment="是否开启手势密码登录: 1.开启 2.关闭")
    Integer allowGesturePwd;
    @Column(comment="是否显示密码轨迹: 1.是 2.否")
    Integer pwdTrajectory;
    @Column(comment="是否开启勿扰模式: 1.开启 2.不开启")
    Integer doNotDisturb;
    @Column(comment="勿扰时段开始")
    Integer doNotDisturbStart;
    @Column(comment="勿扰时段结束")
    Integer doNotDisturbEnd;
    @Column(comment="消息保存时长")
    Long messageSaveTime;
    @Column(comment="用户来源")
    String origin;
    @Column(comment="修订版本号")
    Long changeVersion;
    @Column(comment="客户端加密参数")
    String salt;
    @Column(comment="邀请人")
    String inviter;
    @Column(comment="消息内容模式: 1、通知源，隐藏内容  2、完全隐藏 , 默认开启模式1")
    Integer userMessageContentMode;

    @Column(comment="V标: 1:表示始终有声音提醒，2：表示始终无声音提醒 3:不始终提醒，默认1")
    Integer vipSetting;
    @Column(comment="@相关人提醒模式: 1:表示始终有声音提醒，2：表示始终无声音提醒 3:不始终提醒，默认1")
    Integer atSetting;
    @Column(comment="是否可以加好友: 1:不可以 2:需要验证 默认2")
    Integer addBuddySetting;
    @Column(comment="新消息提醒: 1:开启 2:关闭 默认1")
    Integer messageSetting;
    @Column(comment = "账号信息列表",json = true,length = Constant.VARCHAR_MAX_LENGTH)
    List<AccountBean> accountBeans;
    @Column(comment = "账号信息",json = true,length = Constant.VARCHAR_MAX_LENGTH)
    AccountBean accountBean;
    @Column(length = Constant.VARCHAR_MAX_LENGTH,json = true)
    Map<String,AccountBean> map;

    Date date;

}
