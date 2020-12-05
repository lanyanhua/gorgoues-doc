package com.lancabbage.lancodeapi.enums;

public enum ResponseStatusCode {
    OPERATION_SUCCESS(200, "操作成功"),
    QUERY_DATA_ERROR(400, "参数错误"),
    UNAUTHORIZED_ERROR(401, "未登录"),
    FORBIDDEN_ERROR(403, "权限不足"),
    SERVER_ERROR(500, "服务器错误"),
    ID_VALUE_NULL(502, "主键值空指针异常"),
    ID_VALUE_ERROR(503, "主键值传输异常"),
    QUERY_DATA_EMPTY(504, "没有查询到数据"),
    MICRO_SERVICE_ERROR(40001, "微服务不可用");

    private int code;
    private String msg;

    private ResponseStatusCode(int statusCode, String statusMsg) {
        this.code = statusCode;
        this.msg = statusMsg;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String toString() {
        return "ResponseStatusCode{code=" + this.code + ", msg='" + this.msg + '\'' + '}';
    }
}
