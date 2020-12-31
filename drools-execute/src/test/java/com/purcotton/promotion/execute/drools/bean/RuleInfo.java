package com.purcotton.promotion.execute.drools.bean;

import lombok.Data;
import lombok.ToString;

/**
 * 规则信息
 *
 * @author loujinhe
 * @date 2019/4/14 00:13
 */
@Data
@ToString
public class RuleInfo {

    /**
     * 规则id，全局唯一
     */
    private Long id;

    /**
     * 场景id，一个场景对应多个规则，一个场景对应一个业务场景，一个场景对应一个kmodule
     */
    private String sceneId;

    /**
     * 规则内容，既drl文件内容
     */
    private String content;

}
