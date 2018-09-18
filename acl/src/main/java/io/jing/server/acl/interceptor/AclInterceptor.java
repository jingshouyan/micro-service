package io.jing.server.acl.interceptor;

import io.jing.base.bean.Rsp;
import io.jing.base.bean.Token;
import io.jing.base.exception.MicroServiceException;
import io.jing.base.util.code.Code;
import io.jing.base.util.json.JsonUtil;
import io.jing.base.util.rsp.RspUtil;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.acl.bean.ResourceBean;
import io.jing.server.acl.constant.AclCode;
import io.jing.server.acl.constant.AclConstant;
import io.jing.server.acl.helper.AclHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class AclInterceptor extends HandlerInterceptorAdapter implements AclConstant {

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    private static String DOUBLE_SLASH = "//";

    @Autowired
    private AclHelper aclHelper;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception{
        String traceId = request.getHeader("Trace-Id");
        if(null == traceId) {
            traceId = Long.toHexString(System.nanoTime());
        }
        ThreadLocalUtil.setTraceId(traceId);
        String ticket = request.getHeader("Ticket");
        String method = request.getMethod();
        String uri = request.getRequestURI();
        uri = uri.substring(contextPath.length());
        if (uri.startsWith(DOUBLE_SLASH)){
            uri = uri.substring(1);
        }
        ResourceBean resource = aclHelper.getResourceOpt(method,uri)
                .orElseThrow(() -> new MicroServiceException(AclCode.NOT_FOUND_RESOURCE));
        int rType = resource.getType();
        if(RESOURCE_TYPE_PUB != rType){
            Token token = aclHelper.getToken(ticket);
            ThreadLocalUtil.setToken(token);
            if(RESOURCE_TYPE_LOGIN !=rType){
                boolean canActive = aclHelper.canActive(token.getUserId(),resource.getId());
                if(!canActive){
                    throw new MicroServiceException(AclCode.PERMISSION_DENIED);
                }
            }
            if(Boolean.TRUE.equals(resource.getLogout())){
                aclHelper.removeToken(token);
            }
        }
        return super.preHandle(request, response, handler);

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex){
        ThreadLocalUtil.removeToken();
        ThreadLocalUtil.removeTrace();
    }

}
