package io.jing.server.fitness.bean;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ListBookingQ {

    private long customId;
    private int page = 1;
    private int pageSize = 10;
}
