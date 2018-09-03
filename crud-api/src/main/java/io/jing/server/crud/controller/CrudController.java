package io.jing.server.crud.controller;

import io.jing.base.bean.Req;
import io.jing.base.bean.Token;
import io.jing.client.util.ClientUtil;
import io.jing.server.crud.bean.C;
import io.jing.server.crud.bean.D;
import io.jing.server.crud.bean.R;
import io.jing.server.crud.bean.U;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author jingshouyan
 * #date 2018/9/3 22:36
 */
@RestController
@Slf4j
public class CrudController {

    @RequestMapping("{service}/{bean}/insert")
    public Object insert(@PathVariable String service,
                         @PathVariable String bean,
                         @RequestParam String type,
                         @RequestBody String data){
        Token token = new Token();
        C c= new C();
        c.setBean(bean);
        c.setType(type);
        c.setData(data);
        Req req = Req.builder().service(service).method("insert").paramObj(c).build();
        return ClientUtil.call(token,req);
    }

    @RequestMapping("{service}/{bean}/update")
    public Object update(@PathVariable String service,
                         @PathVariable String bean,
                         @RequestParam String type,
                         @RequestBody String data){
        Token token = new Token();
        U u= new U();
        u.setBean(bean);
        u.setType(type);
        u.setData(data);
        Req req = Req.builder().service(service).method("update").paramObj(u).build();
        return ClientUtil.call(token,req);
    }

    @RequestMapping("{service}/{bean}/query")
    public Object query(@PathVariable String service,
                         @PathVariable String bean,
                         @RequestParam String type,
                         @RequestBody R r){
        Token token = new Token();
        r.setBean(bean);
        r.setType(type);
        Req req = Req.builder().service(service).method("query").paramObj(r).build();
        return ClientUtil.call(token,req);
    }

    @RequestMapping("{service}/{bean}/delete")
    public Object delete(@PathVariable String service,
                         @PathVariable String bean,
                         @RequestParam String type,
                         @RequestBody D d){
        Token token = new Token();
        d.setBean(bean);
        d.setType(type);
        Req req = Req.builder().service(service).method("delete").paramObj(d).build();
        return ClientUtil.call(token,req);
    }
}
