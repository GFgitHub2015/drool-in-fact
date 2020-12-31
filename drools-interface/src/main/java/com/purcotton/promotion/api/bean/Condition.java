package com.purcotton.promotion.api.bean;


import lombok.Data;

@Data
public class Condition {
    private  int conditionType;//规则类型：1：指定商品金额满 2：指定商品件数满 3：整单金额满 4：整单数量满
    private int ruleValue; //使用门槛金额/数量
}
