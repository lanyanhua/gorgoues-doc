package com.lancabbage.lancodeapi.enums;

import java.util.Arrays;

public enum ApiTypeEnum {

    /**
     * 0:all 1:post 2:get 3:delete 4:put
     *  {"RequestMapping", "PostMapping", "GetMapping", "DeleteMapping", "PutMapping"}
     */
    ALL(0, "RequestMapping", "ALL"),
    POST(1, "PostMapping", "POST"),
    GET(2, "GetMapping", "GET"),
    DELETE(3, "DeleteMapping", "DELETE"),
    PUT(4, "PutMapping", "PUT");

    private final int code;
    private final String type;
    private final String desc;

    ApiTypeEnum(int code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (ApiTypeEnum type : ApiTypeEnum.values()) {
            if (type.getCode() == code) {
                return type.getDesc();
            }
        }
        return null;
    }

    public static String getTypeByCode(int code) {
        for (ApiTypeEnum type : ApiTypeEnum.values()) {
            if (type.getCode() == code) {
                return type.getType();
            }
        }
        return null;
    }

    public static Integer getCode(String desc) {
        for (ApiTypeEnum type : ApiTypeEnum.values()) {
            if (type.getDesc().equalsIgnoreCase(desc)) {
                return type.getCode();
            }
        }
        return null;
    }

    public static ApiTypeEnum toEnum(int code) {
        for (ApiTypeEnum type : ApiTypeEnum.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }
    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}