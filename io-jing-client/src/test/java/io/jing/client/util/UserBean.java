package io.jing.client.util;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @author jingshouyan
 * @date 2018/4/19 10:22
 */
@Data
public class UserBean {
    String name = "name reg";
    String name_match = "name_match reg";
    String realName = "realName reg";
    Integer userType = 250;
    String portraitURL = "portraitURL reg";
    String oriPortraitURL = "oriPortraitURL reg";
    String phoneNum = "phoneNum reg";
    String email = "email reg";
    Integer sex = 250;
    Integer status = 250;
    Integer survival = 250;
    String area = "area reg";
    String birthday = "birthday reg";
    Integer securityLevel = 250;
    Integer oldsecurityLevel = 250;
    String gesturePwd = "gesturePwd reg";
    String extend = "extend reg";
    Integer allowPhoneSearch = 250;
    Integer allowEmailSearch = 250;
    Integer allowGesturePwd = 250;
    Integer pwdTrajectory = 250;
    Integer doNotDisturb = 250;
    Integer doNotDisturbStart = 250;
    Integer doNotDisturbEnd = 250;
    Long messageSaveTime = 10000L;
    String origin = "origin reg";
    Long changeVersion = 10000L;
    String salt = "salt reg";
    String inviter = "inviter reg";
    Integer userMessageContentMode = 250;

    Integer vipSetting = 250;
    Integer atSetting = 250;
    Integer addBuddySetting = 250;
    Integer messageSetting = 250;
    List<AccountBean> accountBeans = Lists.newArrayList();
}
