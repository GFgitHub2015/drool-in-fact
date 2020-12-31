package com.purcotton.promotion.mq.drools;

import cn.hutool.json.JSONUtil;
import com.purcotton.promotion.mq.BaseTest;
import com.purcotton.promotion.mq.message.dto.TestObject;
import com.purcotton.rocketmq.sdk.core.Producer;
import com.purcotton.rocketmq.sdk.support.Message;
import com.purcotton.rocketmq.sdk.support.OnExceptionContext;
import com.purcotton.rocketmq.sdk.support.SendCallback;
import com.purcotton.rocketmq.sdk.support.SendResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;

@Slf4j
@SpringBootTest(classes = MQTest.class)
@RunWith(SpringRunner.class)
public class MQTest extends BaseTest {
    @Autowired
    private Producer producer;


    @Test
    public void mqCusumerTest() {
        TestObject testObject = new TestObject();
        testObject.setAge(10);
        testObject.setName("玉皇大帝");
        Message message = new Message();
        message.setTopic("t_test");
        message.setTag("TagA");
        message.setBody(JSONUtil.toJsonStr(testObject).getBytes(StandardCharsets.UTF_8));

        // 异步发送消息
        producer.sendAsync(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info(sendResult.getMessageId());
            }

            /**
             * 发生异常需要业务方处理
             *  @param context 异常上下文
             */
            @Override
            public void onException(OnExceptionContext context) {
                log.info(context.getException().getMessage());
            }
        });
    }


}
