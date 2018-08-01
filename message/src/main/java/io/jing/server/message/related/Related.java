package io.jing.server.message.related;

import io.jing.server.message.bean.Message;

import java.util.List;
import java.util.function.Function;

public interface Related {

    void r(Message message, Function<List<String>,Boolean> function);
}
