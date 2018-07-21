package io.jing.server.relationship.constant;

import io.jing.base.util.config.ConfigSettings;
import io.jing.server.constant.ServerConstant;

/**
 * @author jingshouyan
 * #date 2018/6/14 15:01
 */
public interface RelationshipConstant extends ServerConstant {
    String DS_DRIVER = ConfigSettings.get("datasource.driver").get();
    String DS_USERNAME = ConfigSettings.get("datasource.username").get();
    String DS_PASSWORD = ConfigSettings.get("datasource.password").get();
    String DS_URL= ConfigSettings.get("datasource.url").get();

    int ROOM_LEVEL_OWNER = 3;
    int ROOM_LEVEL_ADMIN = 2;
    int ROOM_LEVEL_NORMAL = 1;

    String ID_TYPE_ROOM = "room";
    String ID_TYPE_CONTACT_REVISION = "contact_revision";
    String ID_TYPE_ROOM_REVISION = "room_revision";
    String ID_TYPE_ROOM_USER_REVISION = "room_user_revision";
}
