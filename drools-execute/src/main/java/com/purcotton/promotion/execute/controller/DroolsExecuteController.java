package com.purcotton.promotion.execute.controller;


import com.purcotton.promotion.api.bean.PromotionJTRuleInfo;
import com.purcotton.promotion.api.service.DroolsExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 规则执行
 */
@RestController
@RequestMapping("/execute")
public class DroolsExecuteController {

    @Autowired
    private DroolsExecuteService droolsExecuteService;

    /**
     * 执行规则
     * @param promotionJTRuleInfo
     * @return
     */
    @PostMapping("/droolsExecute")
    public PromotionJTRuleInfo droolsExecute(@RequestBody PromotionJTRuleInfo promotionJTRuleInfo) {
        return droolsExecuteService.droolsExecute(promotionJTRuleInfo);
    }
}
