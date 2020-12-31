package com.purcotton.promotion.core.service;

import com.purcotton.promotion.api.bean.PromotionRuleContent;
import com.purcotton.promotion.core.dao.PromotionRuleContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PromotionRuleContainerService {
    @Autowired
    private PromotionRuleContentRepository promotionRuleContentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存规则内容
     * @param promotionRuleContent
     */
    public void saveKiePromotionRuleContent(PromotionRuleContent promotionRuleContent){
        promotionRuleContentRepository.save(promotionRuleContent);
    }

    /**
     * 根据活动编码查询规则内容
     * @param activityCode
     * @return
     */
    public PromotionRuleContent findPromotionRuleContentById(String activityCode){
       // Query query = new Query(Criteria.where("_id").is(id));
        //PromotionRuleContent result =  mongoTemplate.findById(activityCode,PromotionRuleContent.class);
        Optional<PromotionRuleContent> optional =  promotionRuleContentRepository.findById(activityCode);
        PromotionRuleContent result = optional.get();
        return result;
    }

    /**
     * 根据活动编码查询规则内容
     * @return
     */
    public List<PromotionRuleContent> findPromotionRuleContentAll(){
        List<PromotionRuleContent> result =  promotionRuleContentRepository.findAll();
        return result;
    }
}
