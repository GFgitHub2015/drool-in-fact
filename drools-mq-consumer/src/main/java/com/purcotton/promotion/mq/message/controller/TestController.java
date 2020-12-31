package com.purcotton.promotion.mq.message.controller;

import cn.hutool.json.JSONUtil;
import com.purcotton.promotion.mq.message.dto.TestObject;
import com.purcotton.rocketmq.sdk.core.Producer;
import com.purcotton.rocketmq.sdk.support.Message;
import com.purcotton.rocketmq.sdk.support.OnExceptionContext;
import com.purcotton.rocketmq.sdk.support.SendCallback;
import com.purcotton.rocketmq.sdk.support.SendResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/test")
public class TestController {

    /**
     * 自动注入生产者
     */
    private final Producer producer;

    /**
     * 测试同步发送
     * 只要不抛异常就表示成功
     *
     * @param testObject 发送的数据
     * @return msgID
     */
    @PostMapping("/send")
    public String testSendMessage(@RequestBody TestObject testObject) {
        Message message = new Message();
        message.setTopic("test");
        message.setTag("TagA");
        message.setBody(JSONUtil.toJsonStr(testObject).getBytes(StandardCharsets.UTF_8));
        // 同步发送消息，只要不抛异常就表示成功
        SendResult result = producer.send(message);
        return result.getMessageId();
    }

    /**
     * 测试异步发送
     *
     * @param testObject 发送的数据
     */
    @PostMapping("/sendAsync")
    public void testSendAsyncMessage(@RequestBody TestObject testObject) {
        Message message = new Message();
        message.setTopic("t_test");
        message.setTag("TagA");
        message.setBody(JSONUtil.toJsonStr(testObject).getBytes(StandardCharsets.UTF_8));
        //message.setBody("22".getBytes(StandardCharsets.UTF_8));

        // 异步发送消息
        producer.sendAsync(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info(sendResult.getMessageId());
            }

            /**
             * 发生异常需要业务方处理
             * @param context 异常上下文
             */
            @Override
            public void onException(OnExceptionContext context) {
                log.info(context.getException().getMessage());
            }
        });
    }

    /**
     * 测试单向发送
     * 单向发送速度最快 服务器不应答，无法保证消息是否成功到达服务器
     *
     * @param testObject 发送的数据
     */
    @PostMapping("/sendOneWay")
    public void testSendOneWay(@RequestBody TestObject testObject) {
        Message message = new Message();
        message.setTopic("test");
        message.setTag("TagA");
        message.setBody(JSONUtil.toJsonStr(testObject).getBytes(StandardCharsets.UTF_8));
        producer.sendOneway(message);

    }

    /**
     * 规则引擎测试异步发送
     *
     * @param activityCode 发送的数据
     */
    @PostMapping("/sendDroolAsync")
    public void testSendAsyncMessageDrools(String activityCode) {
        Message message = new Message();
        message.setTopic("t_test");
        message.setTag("TagA");
        //message.setBody(JSONUtil.toJsonStr(testObject).getBytes(StandardCharsets.UTF_8));
        message.setBody(activityCode.getBytes(StandardCharsets.UTF_8));

        // 异步发送消息
        producer.sendAsync(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info(sendResult.getMessageId());
            }

            /**
             * 发生异常需要业务方处理
             * @param context 异常上下文
             */
            @Override
            public void onException(OnExceptionContext context) {
                log.info(context.getException().getMessage());
            }
        });
    }

}