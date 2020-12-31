package com.purcotton.promotion.api.bean;

import lombok.Data;

@Data
public class PromotionMQMessageInfo {
    private String activityCode;//活动编码
    private String content ;//规则文件内容

}
