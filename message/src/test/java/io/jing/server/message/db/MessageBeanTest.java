package io.jing.server.message.db;

import io.jing.server.App;
import io.jing.server.message.bean.Message;
import io.jing.server.message.bean.MessageBean;
import io.jing.server.message.dao.MessageDao;
import io.jing.server.message.util.MessageConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/6/5 16:50
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class MessageBeanTest {

    @Autowired
    private MessageDao messageDao;

    @Test
    public void query(){
        List<MessageBean> messageBeanList = messageDao.query(null);
        for (MessageBean m : messageBeanList) {
            Message message = MessageConverter.toMessage(m);
            System.out.println(message);
        }
    }
}
