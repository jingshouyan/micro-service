package io.jing.server.fitness.bean;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class LessonQ {

    private String date = "";
    private String name = "";
    private String location = "";
    private String type = "";

    private int page = 1;
    private int pageSize =10;
}
