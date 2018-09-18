package io.jing.server.flux.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class FluxController {

    @RequestMapping("")
    public Flux<String> index(ServerRequest request){

        ServerResponse.ok().body(BodyInserters.fromObject(123));
        throw new RuntimeException("123");
//        return Flux.just("a123","b123","c123").delayElements(Duration.ofSeconds(1));
    }
}
