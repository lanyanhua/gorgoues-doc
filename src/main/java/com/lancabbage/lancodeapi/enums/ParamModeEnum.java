package com.lancabbage.lancodeapi.enums;

public enum ParamModeEnum {

    /**
     * 参数传输方式 1：form-data 2：post json格式 3：path {id}
     */
    FORM_DATA(1, "FORM_DATA", "form-data"),
    POST(2, "POST", "post"),
    PATH(3, "PATH", "path");

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