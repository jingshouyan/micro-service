package io.jing.server.impl;

import io.jing.base.bean.Empty;
import io.jing.server.acl.bean.ResourceBean;
import io.jing.server.acl.method.MyResource;
import io.jing.server.thrift.ResourceT;
import io.jing.server.thrift.ResponseT;
import io.jing.server.thrift.acl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service@Slf4j
public class AclImpl implements acl.Iface {

    @Autowired
    private MyResource resource;

    @Override
    public ResponseT myResource() {
        long start = System.currentTimeMillis();
        List<ResourceBean> resourceBeans = (List<ResourceBean> ) resource.action(new Empty());
        ResponseT r = new ResponseT();
        r.setCode(0);
        r.setMessage("success");
        List<ResourceT> resourceTS = resourceBeans.stream().map(rb-> {
            ResourceT rt = new ResourceT();
            BeanUtils.copyProperties(rb,rt);
            return rt;
        }).collect(Collectors.toList());
        r.setResources(resourceTS);
        log.info("result: {}" ,r);
        long end  = System.currentTimeMillis();
        log.info("use {} ms",end-start);
        return r;
    }
}
