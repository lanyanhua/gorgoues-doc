package com.lancabbage.gorgeous.bean.vo.base;

import com.lancabbage.gorgeous.enums.ResponseStatusCode;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {
    private int statusCode;
    private String statusMsg;
    private T data;

    public BaseResponse() {
        this.statusCode = ResponseStatusCode.OPERATION_SUCCESS.getCode();
        this.statusMsg = ResponseStatusCode.OPERATION_SUCCESS.getMsg();
    }

    public static <T> BaseResponse<T> successInstance(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setStatusCode(ResponseStatusCode.OPERATION_SUCCESS);
        return response.setData(data);
    }

    public static <T> BaseResponse<T> successInstanceMsg(String msg) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setStatusCode(ResponseStatusCode.OPERATION_SUCCESS);
        response.setStatusMsg(msg);
        return response;
    }

    public static <T> BaseResponse<T> errorInstance(String errorMsg) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setStatusCode(ResponseStatusCode.SERVER_ERROR);
        response.setStatusMsg(errorMsg);
        return response;
    }

    public static <T> BaseResponse<T> errorInstance(int status, String errorMsg) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setStatusCode(status);
        response.setStatusMsg(errorMsg);
        return response;
    }

    public String getStatusMsg() {
        return this.statusMsg;
    }

    public BaseResponse<T> setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public BaseResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public BaseResponse<T> setStatusCode(ResponseStatusCode statusCode) {
        this.setStatusCode(statusCode.getCode());
        this.setStatusMsg(statusCode.getMsg());
        return this;
    }

    public String toString() {
        return "BaseResponse{statusCode=" + this.statusCode + ", statusMsg='" + this.statusMsg + '\'' + ", data=" + this.data + '}';
    }

}