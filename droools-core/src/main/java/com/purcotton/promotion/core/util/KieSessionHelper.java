package com.purcotton.promotion.core.util;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * KieSession助手类
 *
 * @author loujinhe
 * @date 2019/4/15 22:29
 */
@Component
public class KieSessionHelper {

    @Autowired
    private KieContaineHelper kieContaineHelper;

    /**
     * 获取KieSession
     *
     * @param sceneId 场景ID
     * @return KieSession
     */
    public KieSession getKieSessionBySceneId(String sceneId) {
        //后面从redis中根据活动编码取值你，然后拿到kieSession
        return kieContaineHelper.getKieContainerBySceneId(sceneId).getKieBase().newKieSession();
    }
}
