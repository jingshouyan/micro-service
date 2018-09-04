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

    @Value("${server.context-path:}")
    private String contextPath;

    private static String DOUBLE_SLASH = "//";

    @Autowired
    private AclHelper aclHelper;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception{
        try{
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
            ResourceBean resource = aclHelper.getResource(method,uri);
            if(resource == null
                    || STATE_ENABLE.equals(resource.getState())
                    || null == resource.getType()
                    ){
                throw new MicroServiceException(AclCode.NOT_FOUND_RESOURCE);
            }
            int rType = resource.getType();
            if(RESOURCE_TYPE_PUB != resource.getType()){
                Token token = aclHelper.getToken(ticket);
                ThreadLocalUtil.setToken(token);
                if(RESOURCE_TYPE_LOGIN !=resource.getType()){
                    boolean canActive = aclHelper.canActive(token.getUserId(),resource.getId());
                    if(!canActive){
                        throw new MicroServiceException(AclCode.PERMISSION_DENIED);
                    }
                }
            }
            return super.preHandle(request, response, handler);
        }catch (Exception e){
            rspErr(e,response);
            return false;
        }
    }

    private void rspErr(Exception e,HttpServletResponse response) throws IOException {
        Rsp rsp;
        if(e instanceof MicroServiceException){
            rsp = RspUtil.error((MicroServiceException)e);
        }else {
            rsp = RspUtil.error(Code.SERVER_ERROR,e);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(JsonUtil.toJsonString(rsp));
    }

}
