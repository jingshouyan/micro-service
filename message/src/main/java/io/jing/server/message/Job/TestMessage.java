package io.jing.server.message.Job;

import io.jing.base.bean.Token;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.message.bean.Message;
import io.jing.server.message.bean.Text;
import io.jing.server.message.method.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

/**
 * @author jingshouyan
 * #date 2018/6/6 19:53
 */
//@Component
@Slf4j
public class TestMessage implements CommandLineRunner {

    @Autowired
    private SendMessage sendMessage;

    private long i = 0;

    @Override
    public void run(String... strings) {
        new Thread(()->{
            while(true){
                try{
                    task();
                    Thread.sleep(100);
                }catch (Exception e){
                    log.error("",e);
                }
            }
        }).start();
    }

    public void task(){
            i++;
            Token token = new Token();
            token.setUserId("user200");
            token.setTicket("user200");
            token.setClientType(1);
            ThreadLocalUtil.setToken(token);
            Message message = new Message();
            message.setTargetId("user600");
            message.setTargetType("user");
            message.setMessageType("text");
            Text text = new Text();
            text.setContent(""+i);
            message.setText(text);
            message.setFlag(0);
            message.setSentAt(System.currentTimeMillis());
            sendMessage.action(message);
    }
}
