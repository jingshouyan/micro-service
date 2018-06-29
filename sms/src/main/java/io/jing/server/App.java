package io.jing.server;

import io.jing.server.sms.constant.SmsConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author jingshouyan
 * #date 2018/6/28 19:59
 */
@SpringBootApplication
@Slf4j
@EnableScheduling
public class App implements SmsConstant {
    public static void main(String[] args) {
        System.setProperty("SERVER_NAME",THRIFT_SERVER_NAME);
        System.setProperty("LOG_ROOT_PATH",LOG_ROOT_PATH);
        System.setProperty("LOG_LEVEL",LOG_LEVEL);
        System.setProperty("LOG_REF",LOG_REF);
        SpringApplication.run(App.class, args);
    }
}