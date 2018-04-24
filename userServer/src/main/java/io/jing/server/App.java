package io.jing.server;

import io.jing.server.iface.MicroServiceImpl;
import io.jing.server.zk.Register;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jingshouyan
 * @date 2018/4/16 23:51
 */
@SpringBootApplication
@Slf4j
public class App {
    public static void main(String[] args) {
        Register.run(()->SpringApplication.run(App.class, args).getBean(MicroServiceImpl.class));
    }
}
