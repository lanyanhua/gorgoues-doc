package com.lancabbage.gorgeous.bean.dto;

import com.lancabbage.gorgeous.bean.po.ClassInfo;

import java.util.List;

/**
 * @ClassName: ClassInfoDto
 * @Description:TODO ()
 * @author: lanyanhua
 * @date: 2020/12/3 10:02 上午
 * @Copyright:
 */
public class ClassInfoDto extends ClassInfo {

    /**
     * 字段
     */
    private List<ClassFieldDto> fieldList;
    /**
     * 范型
     */
    private List<ClassInfoDto> paradigmList;
    /**
     * 基本数据类型
     */
    private String baseType;

    public ClassInfoDto() {
    }

    public ClassInfoDto(String baseType) {
        this.baseType = baseType;
        setClassName(baseType);
    }

    public List<ClassFieldDto> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<ClassFieldDto> fieldList) {
        this.fieldList = fieldList;
    }

    public List<ClassInfoDto> getParadigmList() {
        return paradigmList;
    }

    public void setParadigmList(List<ClassInfoDto> paradigmList) {
        this.paradigmList = paradigmList;
    }

    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }
}
