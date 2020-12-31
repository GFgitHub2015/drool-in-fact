package com.purcotton.promotion.mq.message.dto;

import lombok.Data;

@Data
public class SendMessageDto {

    //主题-必填
    private String topic;
    //消息-必填
    private String message;
    //消息key-选填
    private String key;
    //业务key-选填
    private String businessKey;
    //消息id
    private String messageId;
    //状态
    private Integer status;

}
