package com.purcotton.promotion.core.drools;


import com.purcotton.promotion.api.bean.PromotionRuleContent;
import com.purcotton.promotion.core.service.PromotionRuleContainerService;
import com.purcotton.promotion.core.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
@Slf4j
public class MongodbTest extends BaseTest {

    @Autowired
    private PromotionRuleContainerService promotionRuleContainerService;


    @Test
    public void noLoopTest() {
        PromotionRuleContent promotionRuleContent = new PromotionRuleContent();
        promotionRuleContent.setSceneId("66");
        String content ="package rules.scene_22\n" +
                "import com.purcotton.promotion.common.bean.PromotionJTRuleInfo\n" +
                "import com.purcotton.promotion.common.bean.Condition\n" +
                "rule \"rule_1000\"\n" +
                "when\n" +
                "  $condition:Condition(conditionType==1) and Condition(ruleValue>100)\n" +
                "then\n" +
                "   System.out.println(10);\n" +
                "end\n" +
                "rule \"rule_1001\"\n" +
                "when\n" +
                "  $condition:Condition(conditionType==1) and Condition(ruleValue>200)\n" +
                "then\n" +
                "   System.out.println(15);\n" +
                "end\n" +
                "rule \"rule_1002\"\n" +
                "when\n" +
                "  $condition:Condition(conditionType==1) and Condition(ruleValue>300)\n" +
                "then\n" +
                "   System.out.println(20);\n" +
                "end";
        promotionRuleContent.setContent(content);
        promotionRuleContainerService.saveKiePromotionRuleContent(promotionRuleContent);

        PromotionRuleContent test = promotionRuleContainerService.findPromotionRuleContentById("66");
        log.info("结果为:"+test);
    }
}
