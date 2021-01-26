package com.lancabbage.gorgeous.enums;

public enum ParamTypeEnum {

    /**
     * 状态 1：入参 2：出参
     */
    INPUT_PARAM(1, "INPUT_PARAM", "入参"),
    OUT_PARAM(2, "OUT_PARAM", "出参");

    private final int code;
    private final String type;
    private final String desc;

    ParamTypeEnum(int code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (ParamTypeEnum type : ParamTypeEnum.values()) {
            if (type.getCode() == code) {
                return type.getDesc();
            }
        }
        return null;
    }

    public static String getTypeByCode(int code) {
        for (ParamTypeEnum type : ParamTypeEnum.values()) {
            if (type.getCode() == code) {
                return type.getType();
            }
        }
        return null;
    }

    public static Integer getCode(String desc) {
        for (ParamTypeEnum type : ParamTypeEnum.values()) {
            if (type.getDesc().equalsIgnoreCase(desc)) {
                return type.getCode();
            }
        }
        return null;
    }

    public static ParamTypeEnum toEnum(int code) {
        for (ParamTypeEnum type : ParamTypeEnum.values()) {
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