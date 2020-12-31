package com.purcotton.promotion.mq.message.constant;

public interface MessageConstant {

    String  MPS_PUSH_MPC_TOPIC ="mps_push_mpc_topic";

    String MPS_PUSH_MPC_COMMO_TOPIC="mps_push_mpc_commo_topic";

    String MPS_GIFT_ORDER_TOPIC="mps_gift_order_topic";

    String MPS_GIFT_PRESALE_TOPIC="mps_gift_presale_topic";

    //成功
    Integer MPS_PRICE_PUSH_STATUS_SUCCESS = 1;
    //异常
    Integer MPS_PRICE_PUSH_STATUS_ERROR = 3;

    //成功
    Integer MPS_PRICE_PULL_STATUS_SUCCESS = 4;
    //异常
    Integer MPS_PRICE_PULL_STATUS_ERROR = 6;

}
