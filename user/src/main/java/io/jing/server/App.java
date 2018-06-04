package io.jing.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author jingshouyan
 * @date 2018/4/16 23:51
 */
@SpringBootApplication
@Slf4j
@EnableScheduling
public class App {
    public static void main(String[] args) {
//        Register.run(()->SpringApplication.run(App.class, args).getBean(MicroServiceImpl.class));
        SpringApplication.run(App.class, args);
    }
}
