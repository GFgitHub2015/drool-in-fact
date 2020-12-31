package com.purcotton.promotion.core.dao;


import com.purcotton.promotion.api.bean.PromotionRuleContent;
import org.springframework.data.mongodb.repository.MongoRepository;

//评论的持久层接口
public interface PromotionRuleContentRepository extends MongoRepository<PromotionRuleContent,String> {
     //Page<Comment> findByParentid(String parentId, Pageable pageable);
}

