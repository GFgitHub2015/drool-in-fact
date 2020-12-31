package com.purcotton.promotion.mq.message.consumer;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.purcotton.cloud.boot.autoconfigure.mq.annotation.MQListener;
import com.purcotton.cloud.boot.autoconfigure.mq.core.RocketMQListener;
import com.purcotton.promotion.api.bean.PromotionRuleContent;
import com.purcotton.promotion.common.exception.ActivityExceptionEnum;
import com.purcotton.promotion.common.exception.BusinessException;
import com.purcotton.promotion.core.service.PromotionRuleContainerService;
import com.purcotton.promotion.core.util.KieContaineHelper;
import com.purcotton.promotion.mq.message.dto.TestObject;
import com.purcotton.rocketmq.sdk.support.Message;
import com.purcotton.tenant.common.util.JsonUtils;
import com.purcotton.tenant.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 测试消费者
 * 注意：必须使用 @Component 等注解将该类注册到 Spring 容器中
 *
 * @author 2310
 * @date 2020/12/23
 * @since 1.0.0
 **/
@Slf4j
@Component
@MQListener(topic = "t_test", groupId = "GID_test")
public class TestConsumer implements RocketMQListener<String> {
    @Autowired
    private KieContaineHelper kieContaineHelper;

    @Autowired
    private PromotionRuleContainerService promotionRuleContainerService;
    /**
     * 只要不抛异常就表示消费成功
     */
    @Override
    public void onMessage(String activityCode) {
        //根据活动编码从缓存中拿到规则字符串
        PromotionRuleContent promotionRuleContent = promotionRuleContainerService.findPromotionRuleContentById(activityCode);
        checkParam(promotionRuleContent);//参数校验
        log.info("消费端消费:{}",promotionRuleContent.getContent());

        //将规则放入drools内存中
        kieContaineHelper.reload(activityCode);
    }

    private void checkParam(PromotionRuleContent promotionRuleContent){
        if(null == promotionRuleContent){
            throw new BusinessException(ActivityExceptionEnum.PARAM_IS_NULL.getCode(),"规则对象不能为空");
        }
        if(StringUtils.isEmpty(promotionRuleContent.getContent())){
            throw new BusinessException(ActivityExceptionEnum.PARAM_IS_NULL.getCode(),"规则内容不能为空");
        }
    }
}