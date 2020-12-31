package com.purcotton.promotion.api.bean;

import lombok.Data;
import org.kie.api.runtime.KieContainer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.io.Serializable;

@Document(collection="kieContaineString")//可以省略，如果省略，则默认使用类名小写映射集合
@Data
public class KieContaineString implements Serializable {
    @Id
    private  String id;
    //private KieContainer kieContainer;
    //private byte[] kieContainerString;
    private String kieContainerString;

}
