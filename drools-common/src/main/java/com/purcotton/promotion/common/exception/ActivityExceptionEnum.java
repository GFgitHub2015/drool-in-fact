package com.purcotton.promotion.common.exception;

/**
 * 促销活动异常
 */
public enum ActivityExceptionEnum
{
    PARAM_IS_NULL(77000, "参数为空"),
    USER_NOT_EXIST(77100,"用户不存在");
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误消息
     */
    private String message;
    
    ActivityExceptionEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
   
    public Integer getCode()
    {
        return code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

}
