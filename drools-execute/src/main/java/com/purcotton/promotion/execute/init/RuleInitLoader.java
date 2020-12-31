package com.purcotton.promotion.execute.init;

import cn.hutool.core.collection.CollectionUtil;
import com.purcotton.promotion.api.bean.PromotionRuleContent;
import com.purcotton.promotion.core.service.PromotionRuleContainerService;
import com.purcotton.promotion.core.util.KieContaineHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
@Slf4j
@Component
public class RuleInitLoader implements ApplicationRunner {
    @Autowired
    private PromotionRuleContainerService promotionRuleContainerService;

    @Autowired
    private KieContaineHelper kieContaineHelper;
    /**
     * 加载所有规则
     */
    private void reloadRuleAll() {
        List<PromotionRuleContent> ruleList =  promotionRuleContainerService.findPromotionRuleContentAll();
        if(CollectionUtil.isEmpty(ruleList)){
            log.info("加载所有规则-规则为空");
        }
        for (PromotionRuleContent promotionRuleContent : ruleList) {
            String sceneId = promotionRuleContent.getSceneId();
            kieContaineHelper.reload(sceneId);//加载规则到drools内存，并将kieContainer放入本地Map中
        }
        log.info("加载所有规则成功");
    }

    /**
     * 服务启动就加载所以规则
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        reloadRuleAll();
    }
}
