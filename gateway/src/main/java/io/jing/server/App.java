package io.jing.server;

import io.jing.server.iface.MicroServiceImpl;
import io.jing.server.zk.Register;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author jingshouyan
 * #date 2018/6/16 22:54
 */
@SpringBootApplication
@Slf4j
@EnableScheduling
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class App {
    public static void main(String[] args) {
        Register.run(()->SpringApplication.run(App.class, args).getBean(MicroServiceImpl.class));
    }
}
