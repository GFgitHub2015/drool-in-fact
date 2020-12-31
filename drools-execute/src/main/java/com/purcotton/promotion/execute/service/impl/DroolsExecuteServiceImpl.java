package com.purcotton.promotion.execute.service.impl;

import com.alibaba.fastjson.JSON;

import com.purcotton.promotion.api.bean.KieContaineString;
import com.purcotton.promotion.api.bean.PromotionJTRuleInfo;
import com.purcotton.promotion.api.service.DroolsExecuteService;
import com.purcotton.promotion.common.exception.ActivityExceptionEnum;
import com.purcotton.promotion.common.exception.BusinessException;
import com.purcotton.promotion.core.service.KieContainerService;
import com.purcotton.promotion.core.util.KieContaineHelper;
import com.purcotton.promotion.core.util.KieSessionHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DroolsExecuteServiceImpl implements DroolsExecuteService {

    @Autowired
    private KieContaineHelper kieContaineHelper;

    @Autowired
    private KieSessionHelper kieSessionHelper;

    /**
     * 执行规则
     * @param promotionJTRuleInfo 规则执行入参
     * @return
     */
    @Override
    public PromotionJTRuleInfo droolsExecute(PromotionJTRuleInfo promotionJTRuleInfo) {
        checkParams(promotionJTRuleInfo);//参数校验
        String activityCode = promotionJTRuleInfo.getActivityCode();//活动编码

        KieContainer kieContainer = kieContaineHelper.getKieContainerBySceneId(activityCode);
        if(null == kieContainer){//为空
            kieContaineHelper.reload(activityCode);
        }
        KieSession kieSession = kieSessionHelper.getKieSessionBySceneId(activityCode);//获取KieSession
        //插入Fact到工作内存
        kieSession.insert(promotionJTRuleInfo);
        kieSession.fireAllRules();//执行规则
        kieSession.dispose();//关闭kieSession*/
        return promotionJTRuleInfo;
    }

    /**
     * 参数校验
     * @param promotionJTRuleInfo
     */
    private void checkParams(PromotionJTRuleInfo promotionJTRuleInfo){
        if(null == promotionJTRuleInfo){
            log.info("入参为空");
            throw new BusinessException(ActivityExceptionEnum.PARAM_IS_NULL.getCode(),"满减执行规则入参不能为空");
        }
        String activityCode = promotionJTRuleInfo.getActivityCode();
        if(StringUtils.isEmpty(activityCode)){
            throw new BusinessException(ActivityExceptionEnum.PARAM_IS_NULL.getCode(),"满减执行规则活动编码不能为空");
        }

        Integer type = promotionJTRuleInfo.getConditionType();
        if(null == type){
            throw new BusinessException(ActivityExceptionEnum.PARAM_IS_NULL.getCode(),"满减执行规则活动类型不能为空");
        }

        Integer ruleValue = promotionJTRuleInfo.getRuleValue();
        if(null == ruleValue){
            throw new BusinessException(ActivityExceptionEnum.PARAM_IS_NULL.getCode(),"满减执行规则使用门槛金额/数量不能为空");
        }
    }
}
