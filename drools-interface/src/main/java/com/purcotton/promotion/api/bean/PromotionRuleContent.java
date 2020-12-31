package com.purcotton.promotion.api.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 规则内容
 */
@Document(collection="promotionRuleContent")//可以省略，如果省略，则默认使用类名小写映射集合
@Data
public class PromotionRuleContent {
    /**
     * 场景id，一个场景对应多个规则，一个场景对应一个业务场景，一个场景对应一个kmodule
     */
    @Id
    private String sceneId;

    /**
     * 规则内容，既drl文件内容
     */
    private String content;

}
