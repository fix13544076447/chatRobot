package com.fix.ai.demo.chatrobot.controller;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import io.reactivex.Flowable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;

/**
 *  @author huangfei
 *
 * @copyright Copyright (c) 2024  fix Inc. All Rights Reserved.
 *  @description
 *  @date 2024-08-24 16:27
 *  
 */
@RestController
@CrossOrigin
public class EventController {
    @Value("${api.key}")
    private String apiKey;

    @GetMapping(value = "/events/streamAsk", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> streamAsk(@RequestParam("q") String q) throws NoApiKeyException, InputRequiredException {
        Generation generation = new Generation();
        // 创建用户消息对象
        Message message = Message.builder().role(Role.USER.getValue()).content(q).build();
        // 创建QwenParam对象，设置参数
        QwenParam qwenParam = QwenParam.builder().model(Generation.Models.QWEN_PLUS).messages(Arrays.asList(message))
                .resultFormat(QwenParam.ResultFormat.MESSAGE).topP(0.8).enableSearch(true).apiKey(apiKey)
                .incrementalOutput(true).build();
        // 调用生成接口，获取Flowable对象
        Flowable<GenerationResult> flowable = generation.streamCall(qwenParam);
        return Flux.from(flowable).delayElements(Duration.ofMillis(1000)).map(u -> {
            String outPut = u.getOutput().getChoices().get(0).getMessage().getContent();
            return ServerSentEvent.<String>builder().data(outPut).build();
        }).concatWith(Flux.just(ServerSentEvent.<String>builder().comment("").build())).doOnError(e -> {
            if (e instanceof NoApiKeyException) {

            } else if (e instanceof InputRequiredException) {

            } else if (e instanceof ApiException) {

            } else {

            }
        });
    }
}
