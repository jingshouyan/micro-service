package io.jing.server.iface;

import io.jing.base.bean.Req;
import io.jing.base.bean.ReqAndRsp;
import io.jing.base.bean.Rsp;
import io.jing.base.bean.Token;
import io.jing.base.exception.MicroServiceException;
import io.jing.base.thrift.MicroService;
import io.jing.base.thrift.ReqBean;
import io.jing.base.thrift.RspBean;
import io.jing.base.thrift.TokenBean;
import io.jing.base.util.code.Code;
import io.jing.base.util.event.EventBusUtil;
import io.jing.base.util.json.JsonUtil;
import io.jing.base.util.rsp.RspUtil;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.method.Method;
import io.jing.server.method.factory.MethodFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
/**
 * @author jingshouyan
 * @date 2018/4/15 0:04
 */
@Slf4j
@Service
public class MicroServiceImpl implements MicroService.Iface{
    @Override
    public RspBean call(TokenBean token, ReqBean req) {
        ThreadLocalUtil.setTraceId(token.getTraceId());
        Token t = new Token(token);
        Req r = new Req(req);
        Rsp rsp =  run(t,r);
        ThreadLocalUtil.removeTrace();
        return rsp.rspBean();
    }

    @Override
    public void send(TokenBean token, ReqBean req){
        call(token,req);
    }

    private Rsp run(Token token,Req req){
        ReqAndRsp reqAndRsp = new ReqAndRsp();
        reqAndRsp.setTraceId(ThreadLocalUtil.getTrace().getTraceId());
        String methodName = req.getMethod();
        long start = System.currentTimeMillis();
        log.info("call [{}] start.",methodName);
        log.info("call [{}] token: {}",methodName,token);
        log.info("call [{}] req: {}",methodName,req);
        Rsp rsp;
        try{
            ThreadLocalUtil.setToken(token);
            Method method = MethodFactory.getMethod(methodName);
            Class<?> clazz = method.getClazz();
            Object obj;
            try {
                obj = JsonUtil.toBean(req.getParam(), clazz);
            }catch (Exception e){
                throw new MicroServiceException(Code.JSON_PARSE_ERROR,e);
            }
            @SuppressWarnings("unchecked")
            Object result = method.actionWithValidate(obj);
            rsp = RspUtil.success(result);
        }catch (MicroServiceException e){
            rsp = RspUtil.error(e.getCode(),e);
        }catch (Exception e){
            log.error("call [{}] error.",methodName,e);
            rsp = RspUtil.error(Code.SERVER_ERROR,e);
        }finally {
            ThreadLocalUtil.removeToken();
        }
        long end = System.currentTimeMillis();
        long cost = end - start;
        reqAndRsp.setToken(token);
        reqAndRsp.setReq(req);
        reqAndRsp.setRsp(rsp);
        reqAndRsp.setStartAt(start);
        reqAndRsp.setCost(cost);
        EventBusUtil.DEFAULT.post(reqAndRsp);
        log.info("call [{}] end. {}",methodName,rsp);
        log.info("call [{}] use {} ms",methodName,cost);
        return rsp;
    }
}
