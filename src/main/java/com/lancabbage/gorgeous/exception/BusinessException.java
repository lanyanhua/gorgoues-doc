package com.lancabbage.gorgeous.exception;

/**
 * 业务异常
 *
 * @author yj
 * @date 2019/3/27 9:29
 */
public class BusinessException extends RuntimeException {
    public static final int FAIL = 500;
    private static final long serialVersionUID = 9045237586542073761L;
    private int code;
    private String message;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        this.code = FAIL;
        this.message = message;
    }

    public BusinessException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static int getFAIL() {
        return FAIL;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
