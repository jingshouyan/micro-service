package io.jing.server.message.helper;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.jing.base.exception.MicroServiceException;
import io.jing.base.util.thread.ExecUtil;
import io.jing.server.message.bean.Message;
import io.jing.server.message.constant.MessageCode;
import io.jing.server.message.constant.MessageConstant;
import io.jing.server.message.helper.related.Related;
import io.jing.server.message.helper.related.RoomRelated;
import io.jing.server.message.helper.related.UserRelated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Component
public class RelatedProxy implements Related,MessageConstant {

    @Autowired
    private UserRelated userRelated;

    @Autowired
    private RoomRelated roomRelated;

    private final ExecutorService exec = new ThreadPoolExecutor(1, 10,
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1024),
            new ThreadFactoryBuilder().setNameFormat("related-pool-%d").build(),
            new ThreadPoolExecutor.AbortPolicy()
    );

    @Override
    public void actionBatch(Message message, Consumer<List<String>> consumer) {

        Related related = null;

        switch (message.getTargetType()){
            case MESSAGE_TARGET_TYPE_USER:
                related = userRelated;
                break;
            case MESSAGE_TARGET_TYPE_ROOM:
                related = roomRelated;
                break;
            default:
                throw new MicroServiceException(MessageCode.UNSUPPORTED_TARGET_TYPE);
        }
        Related r = related;

        ExecUtil.exec(exec,()->{
            r.actionBatch(message,userIds -> {
                userIds = filterRelated(message,userIds);
                if(!userIds.isEmpty()){
                    consumer.accept(userIds);
                }
            });
        });


    }
}
