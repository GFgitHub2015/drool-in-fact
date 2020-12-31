package com.purcotton.promotion.api.bean;

import lombok.Data;

import java.util.List;

@Data
public class PromotionRuleParam {
    private String activityCode;//活动编码
    private List<Condition> list ;//规则条件
    private  int discountValue;//优惠金额
    private  int maxValue;//最高减多少钱

}
