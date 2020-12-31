package com.purcotton.promotion.core.dao;


import com.purcotton.promotion.api.bean.KieContaineString;
import org.springframework.data.mongodb.repository.MongoRepository;

//评论的持久层接口
public interface KieContainerRepository extends MongoRepository<KieContaineString,String> {
     //Page<Comment> findByParentid(String parentId, Pageable pageable);
}

