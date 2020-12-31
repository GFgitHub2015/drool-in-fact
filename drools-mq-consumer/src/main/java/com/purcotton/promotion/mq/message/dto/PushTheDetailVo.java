package com.purcotton.promotion.mq.message.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PushTheDetailVo {


    private Long id;

    /**
     * 单据主表id
     */
    private Long headerId;

    /**
     * 单据明细id
     */
    private Long itemId;

    /**
     * 店铺编号
     */
    private String storeCode;

    /**
     * 管理区域编码
     */
    private String cityCode;

    /**
     * 商品编码 ,内部编码
     */
    private String commoNo;

    /**
     * 货品编码 ,内部编码
     */
    private String goodsNo;


    /**
     * 售价/现价
     */
    private BigDecimal sellPrice;

    /**
     * 商品标题
     */
    private String commoTitle;


    /**
     * 商品编码,业务编码
     */
    private String commoCode;

    /**
     * 货品编码,业务编码
     */
    private String goodsCode;

    /**
     * 推送类型，1 普通调价，2会员调价
     */
    private Integer type;

    /**
     * 1 上线，0 下线价格
     */
    private Integer pushFlag;


}
