package com.fix.ai.demo.chatrobot.controller;
import com.alibaba.dashscope.aigc.conversation.Conversation;
import com.alibaba.dashscope.aigc.conversation.ConversationParam;
import com.alibaba.dashscope.aigc.conversation.ConversationResult;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.Constants;
import com.alibaba.dashscope.utils.JsonUtils;
/**
 *  @author fix
 *
 * @copyright Copyright (c) 2024  fix Inc. All Rights Reserved.
 *  @description
 *  @date 2024-08-24 17:06
 *  
 */


public class DashscopeUtils {

    public static String quickStart(String question) throws ApiException, NoApiKeyException, InputRequiredException {
        Conversation conversation = new Conversation();
        // String prompt = "你好";
        ConversationParam param = ConversationParam
                .builder()
                .model(Conversation.Models.QWEN_TURBO)
                .prompt(question)
                .build();
        ConversationResult result = conversation.call(param);
        System.out.println(JsonUtils.toJson(result));
        return result.getOutput().getText();
    }

    public static void main(String[] args) throws InputRequiredException {
        Constants.apiKey="sk-a10b1f8fe59b4d70b7dbdb389ff9ac0f";
        try {
            String question = "你好";
            quickStart(question);
        } catch (ApiException | NoApiKeyException e) {
            System.out.println(e.getMessage());
        }
        System.exit(0);
    }

}
