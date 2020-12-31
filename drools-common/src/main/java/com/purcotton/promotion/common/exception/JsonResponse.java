package com.purcotton.promotion.common.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 业务异常类json
 *
 * @author xuhua
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResponse {
    private int code;
    private int level;
    private String message;
    private Object data;

    public JsonResponse(Exception exception) {
        if (exception instanceof BusinessException) {
            BusinessException businessException = (BusinessException) exception;
            this.code = businessException.getCode();
            this.message = businessException.getMessage();
            this.level = businessException.getLevel();
        } else {
            this.code = 500;
            this.message = "传入数据错误";
            this.level = 5;
        }
    }

    public JsonResponse(Object data) {
        this.code = 200;
        this.message = "success";
        this.data = data;
    }
}
