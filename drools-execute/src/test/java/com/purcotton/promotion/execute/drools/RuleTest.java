package com.purcotton.promotion.execute.drools;


import com.purcotton.promotion.api.bean.Condition;
import com.purcotton.promotion.api.bean.KieContaineString;
import com.purcotton.promotion.api.bean.PromotionRuleContent;
import com.purcotton.promotion.api.bean.PromotionRuleParam;
import com.purcotton.promotion.core.service.PromotionRuleContainerService;
import com.purcotton.promotion.core.util.KieSessionHelper;
import com.purcotton.promotion.execute.BaseTest;


import com.purcotton.promotion.execute.drools.bean.RuleInfo;
import com.purcotton.promotion.core.util.KieContaineHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class RuleTest extends BaseTest {
    private final ConcurrentMap<String, KieContainer> kieContainerMap = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, byte[]> kieContainerStringMap = new ConcurrentHashMap<>();

    @Autowired
    private KieContaineHelper kieContaineHelper;

    @Autowired
    private KieSessionHelper kieSessionHelper;

 /*   @Autowired
    private RedisTemplate template;*/

    @Autowired
    private PromotionRuleContainerService promotionRuleContainerService;

    /**
     * 阶梯满减
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Test
    public void initTest(){
        String  sceneId = "22";
        initRule(sceneId);//生成动态规则，并放入drools中

        //fact对象
        Condition condition = new Condition();
        condition.setRuleValue(101);
        condition.setConditionType(1);
        executeRule(sceneId,condition);//执行规则
    }

    private void executeRule(String sceneId,Condition condition)  {
        //KieContainer kieContainer = (KieContainer)template.opsForValue().get(sceneId);
        KieSession kieSession = kieSessionHelper.getKieSessionBySceneId(sceneId);

        //插入Fact到工作内存
        kieSession.insert(condition);
        int count = kieSession.fireAllRules();
        kieSession.dispose();
    }


    private void initRule(String sceneId)  {
        String  result =  ruleTest(sceneId);
        log.info("集合："+result);
        kieContaineHelper.reload(sceneId);
    }

    private String splicingCharacterString(List<PromotionRuleParam> promotionRuleInfoList, String id){
        List<RuleInfo> list = new ArrayList<>();
        if(promotionRuleInfoList==null||promotionRuleInfoList.size()==0){
            log.info("规则条件为空");
            return null;
        }
        int i = 0;
        StringBuilder ttt = new StringBuilder();//最终字符串
        ttt.append("\npackage rules.scene_"+id+"\n")
                .append("import com.purcotton.promotion.api.bean.PromotionJTRuleInfo"+"\n"+
                        "import com.purcotton.promotion.api.bean.Condition"+"\n");
        for(PromotionRuleParam promotionRuleInfo:promotionRuleInfoList){
            List<Condition> cList  = promotionRuleInfo.getList();
            StringBuilder sss = new StringBuilder();
            RuleInfo ruleInfo = new RuleInfo();
            for(Condition c :cList){
                int conditionType = c.getConditionType();
                int ruleValue = c.getRuleValue();
                sss.append("  $condition:Condition(conditionType==" +conditionType+ ")").append(" and Condition(ruleValue>"+ruleValue+")\n");
            }
            long ruleid = 1000l+i;
            ttt.append("rule \"rule_"+ruleid+"\"\n" +
                    "when\n" +
                    sss.toString()+
                    "then\n" +
                    "   System.out.println("+promotionRuleInfo.getDiscountValue()+");\n" +
                    "end\n");
            i++;
            ruleInfo.setId(ruleid);
            ruleInfo.setContent(ttt.toString());
            ruleInfo.setSceneId(id);
        }
        PromotionRuleContent promotionRuleContent = new PromotionRuleContent();
        promotionRuleContent.setSceneId(id);
        promotionRuleContent.setContent(ttt.toString());
        promotionRuleContainerService.saveKiePromotionRuleContent(promotionRuleContent);
        return ttt.toString();
    }


    private String ruleTest(String  id) {
        //规则1
        PromotionRuleParam pto =  new PromotionRuleParam();
        List<Condition> list = new ArrayList();
        Condition cn1 = new Condition();
        cn1.setConditionType(1);//指定商品满
        cn1.setRuleValue(100);//满100
        list.add(cn1);
        pto.setDiscountValue(10);//优惠金额
        pto.setMaxValue(20);
        pto.setList(list);

        //规则2
        PromotionRuleParam pto2 =  new PromotionRuleParam();
        List<Condition> list2 = new ArrayList();
        Condition cn2 = new Condition();
        cn2.setConditionType(1);//指定商品满
        cn2.setRuleValue(200);//满100
        list2.add(cn2);
        pto2.setList(list2);
        pto2.setDiscountValue(15);//优惠金额
        pto2.setMaxValue(20);

        //规则3
        PromotionRuleParam pto3 =  new PromotionRuleParam();
        List<Condition> list3 = new ArrayList();
        Condition cn3 = new Condition();
        cn3.setConditionType(1);//指定商品满
        cn3.setRuleValue(300);//满100
        list3.add(cn3);
        pto3.setList(list3);
        pto3.setDiscountValue(20);//优惠金额
        pto3.setMaxValue(30);

        List<PromotionRuleParam> promotionRuleInfoList = new ArrayList<>();
        promotionRuleInfoList.add(pto);

        promotionRuleInfoList.add(pto2);
        promotionRuleInfoList.add(pto3);
        //ConcurrentHashMap<Long,List<RuleInfo>> hashMap = dealString(promotionRuleInfoList,id);
        String result = splicingCharacterString(promotionRuleInfoList,id);
        //log.info("Map集合为:"+ JSON.toJSONString(hashMap));
        return result;
    }

    private void reload(String sceneId, String   ruleInfos) {
        KieServices kieServices = KieServices.Factory.get();

        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();
        KieBaseModel kieBaseModel = kieModuleModel.newKieBaseModel(kieContaineHelper.buildKbaseName(sceneId));
        kieBaseModel.setDefault(true);
        kieBaseModel.addPackage(MessageFormat.format("rules.scene_{0}", String.valueOf(sceneId)));
        kieBaseModel.newKieSessionModel(kieContaineHelper.buildKsessionName(sceneId));

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        String fullPath = MessageFormat.format("src/main/resources/rules/scene_{0}/rule_test.drl", String.valueOf(sceneId));
        kieFileSystem.write(fullPath,  ruleInfos);
        kieFileSystem.writeKModuleXML(kieModuleModel.toXML());

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            System.out.println(results.getMessages());
            throw new IllegalStateException("rule error");
        }

        ByteArrayOutputStream byt=new ByteArrayOutputStream();
        //ObjectOutputStream obj=new ObjectOutputStream(byt);
        //obj.writeObject(kieContainer);

        byte[] bytes=byt.toByteArray();
        System.out.println("字符串："+bytes);

        KieContaineString kieContaineString = new KieContaineString();
        //kieContaineString.setKieContainerString(bytes.toString());
        kieContaineString.setId(sceneId+"");
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        kieContainerStringMap.putIfAbsent(kieContaineHelper.buildKcontainerName(sceneId), bytes);
        kieContainerMap.put(kieContaineHelper.buildKcontainerName(sceneId), kieContainer);
    }
}
