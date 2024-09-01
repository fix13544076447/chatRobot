package com.fix.ai.demo.chatrobot.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;

/**
 *  @author huangfei
 *
 * @copyright Copyright (c) 2024 fix Inc. All Rights Reserved.  @description
 *  @date 2024-08-24 16:14
 *  
 */
@RestController
public class EventStreamController {
    @GetMapping(value = "/event-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getEventStream() {
        return Flux.interval(Duration.ofSeconds(1)).map(sequence -> "Event" + sequence + "at" + LocalTime.now());

    }
}

