package com.purcotton.promotion.api.bean;

import lombok.Data;

@Data
public class PromotionJTRuleInfo {
    private String activityCode;//活动编码
    private String goodsId;//商品id
    private Integer ruleValue; //使用门槛金额/数量
    private  Integer conditionType;//规则类型：1：指定商品金额满 2：指定商品件数满 3：整单金额满 4：整单数量满
    private  Integer discountValue;//优惠金额
    private  Integer maxValue;//最高减多少钱

}
