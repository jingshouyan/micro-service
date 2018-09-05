package io.jing.server.acl.method;

import com.google.common.collect.Sets;
import io.jing.base.bean.Empty;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.acl.constant.AclConstant;
import io.jing.server.acl.helper.AclHelper;
import io.jing.server.method.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Component("acl.myResource")
public class MyResource implements Method<Empty> {

    @Autowired
    private AclHelper aclHelper;

    @Override
    public Object action(Empty empty) {
        String myId = ThreadLocalUtil.userId();
        Set<Long> resourceIds = Sets.newHashSet();
        aclHelper.getUserRoleOpt(myId)
                .ifPresent(ur ->
                     ur.getRoleIds().stream()
                        .map(roleId -> aclHelper.getRoleOpt(roleId).orElse(null))
                             .filter(Objects::nonNull)
                             .forEach(role -> resourceIds.addAll(role.getResourceIds()))
                );
        return aclHelper.getResources().stream()
                .filter(resource ->
                    resource.getType() == AclConstant.RESOURCE_TYPE_PUB
                        ||resource.getType() == AclConstant.RESOURCE_TYPE_LOGIN
                        ||resourceIds.contains(AclConstant.ALL_RESOURCE_ID)
                        ||resourceIds.contains(resource.getId())
                ).collect(Collectors.toList());

    }
}
