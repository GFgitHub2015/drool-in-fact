package com.purcotton.promotion.mq.message.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PushTheDetailCacheVo
{

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 单据主id(关联t_sell_maintain_header)
     */
    private Long headerId;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 店铺编码
     */
    private String storeCode;

    /**
     * 商品编码 业务编码
     */
    private String commoNo;

    /**
     * 货品编码 业务编码
     */
    private String goodsNo;

    /**
     *  牌价
     **/
    private BigDecimal standardPrice;

    /**
     * 售价/现价
     */
    private BigDecimal sellPrice;

    /**
     * 生效时间
     */
    private Date effectTime;

    /**
     * 结束时间
     */
    private Date finishTime;

    /**
     * 审核通过时间
     */
    private Date headerTime;

    /**
     * 调价单类型(0-普通调价单，1-会员调价单)
     */
    private Integer adjustType;

    /**
     * 缓存状态(0—未缓存，1—已缓存，-1—作废)
     */
    private Integer cacheStatus;


    /**
     * 1 上线，0 下线价格
     */
    private Integer pushFlag;


}
