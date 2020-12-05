package com.lancabbage.lancodeapi.enums;

import java.util.Arrays;

public enum ParamModeEnum {

    /**
     * 参数传输方式 0：form-data 1：post json格式 2：path {id}
     */
    FORM_DATA(0, "RequestParam", "form-data"),
    POST(1, "RequestBody", "post"),
    PATH(2, "PathVariable", "path");

    private final int code;
    private final String type;
    private final String desc;

    ParamModeEnum(int code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (ParamModeEnum type : ParamModeEnum.values()) {
            if (type.getCode() == code) {
                return type.getDesc();
            }
        }
        return null;
    }

    public static String getTypeByCode(int code) {
        for (ParamModeEnum type : ParamModeEnum.values()) {
            if (type.getCode() == code) {
                return type.getType();
            }
        }
        return null;
    }

    public static Integer getCode(String desc) {
        for (ParamModeEnum type : ParamModeEnum.values()) {
            if (type.getDesc().equalsIgnoreCase(desc)) {
                return type.getCode();
            }
        }
        return null;
    }

    public static String[] typeALl() {
        return (String[]) Arrays.stream(ApiTypeEnum.values()).map(ApiTypeEnum::getType).toArray();
    }
    public static ParamModeEnum toEnum(int code) {
        for (ParamModeEnum type : ParamModeEnum.values()) {
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