package com.purcotton.promotion.core.util;

import com.alibaba.fastjson.JSON;
import com.purcotton.promotion.api.bean.KieContaineString;
import com.purcotton.promotion.api.bean.PromotionRuleContent;
import com.purcotton.promotion.common.exception.ActivityExceptionEnum;
import com.purcotton.promotion.common.exception.BusinessException;
import com.purcotton.promotion.core.service.KieContainerService;
import com.purcotton.promotion.core.service.PromotionRuleContainerService;
import com.purcotton.tenant.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.MessageFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * KieContainer工具类
 *
 * @author loujinhe
 * @date 2020/12/15 22:29
 */
@Slf4j
@Component
public class KieContaineHelper {
    @Autowired
    private KieContainerService kieContainerService;

    /*@Autowired
    private RedisTemplate template;*/

    @Autowired
    private PromotionRuleContainerService promotionRuleContainerService;

    private final ConcurrentMap<String, KieContainer> kieContainerMap = new ConcurrentHashMap<>();

    /**
     * 发布规则文件，把KieContainer保存到本地Map中.
     * @param sceneId 活动编码
     */
    public void reload(String sceneId) {
        if(StringUtils.isEmpty(sceneId)){
            throw new BusinessException(ActivityExceptionEnum.PARAM_IS_NULL.getCode(),"入参不能为空");
        }
        KieServices kieServices = KieServices.Factory.get();
        //获取规则
        PromotionRuleContent promotionRuleContent = promotionRuleContainerService.findPromotionRuleContentById(sceneId);
        if(null == promotionRuleContent ||StringUtils.isEmpty(promotionRuleContent.getContent())){
            throw new BusinessException(ActivityExceptionEnum.PARAM_IS_NULL.getCode(),"规则不能为空");
        }
        String ruleInfo = promotionRuleContent.getContent();//规则字符串

        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();
        KieBaseModel kieBaseModel = kieModuleModel.newKieBaseModel(buildKbaseName(sceneId));
        kieBaseModel.setDefault(true);
        kieBaseModel.addPackage(MessageFormat.format("rules.scene_{0}", String.valueOf(sceneId)));
        kieBaseModel.newKieSessionModel(buildKsessionName(sceneId));

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        String fullPath = MessageFormat.format("src/main/resources/rules/scene_{0}/rule_test.drl", String.valueOf(sceneId));
        kieFileSystem.write(fullPath,  ruleInfo);
        kieFileSystem.writeKModuleXML(kieModuleModel.toXML());

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            log.info(JSON.toJSONString(results.getMessages()));
            throw new IllegalStateException("rule error");
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        /*template.setValueSerializer(new StringRedisSerializer());
        template.opsForValue().set(sceneId, kieContainer);*/
        kieContainerMap.put(buildKcontainerName(sceneId), kieContainer);
        log.info("加载到drools内存完毕");
    }


    public KieContainer getKieContainerBySceneId(String sceneId) {
        return kieContainerMap.get(buildKcontainerName(sceneId));
    }

    public String buildKbaseName(String sceneId) {
        return "kbase_" + sceneId;
    }

    public String buildKsessionName(String sceneId) {
        return "ksession_" + sceneId;
    }

    public String buildKcontainerName(String sceneId) {
        return "kcontainer_" + sceneId;
    }

}
