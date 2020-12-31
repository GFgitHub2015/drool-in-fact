package com.purcotton.promotion.core.service;

import com.purcotton.promotion.api.bean.KieContaineString;
import com.purcotton.promotion.core.dao.KieContainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;


@Component
public class KieContainerService {
    @Autowired
    private KieContainerRepository kieContainerRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存KieContaine
     * @param kieContaineString
     */
    public void saveKieContaine(KieContaineString kieContaineString){
        mongoTemplate.save(kieContaineString);
        //kieContainerRepository.save(kieContaineString);
    }

    /**
     * 根据id查询KieContaine
     * @param id
     * @return
     */
    public KieContaineString findKieContaineById(String id){
        Query query = new Query(Criteria.where("_id").is(id));
        KieContaineString c =  mongoTemplate.findById(id,KieContaineString.class);
        return c;
    }


}
