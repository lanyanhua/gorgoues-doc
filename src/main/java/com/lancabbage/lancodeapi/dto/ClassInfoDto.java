package com.lancabbage.lancodeapi.dto;

import com.lancabbage.lancodeapi.entity.ClassInfo;

import java.util.List;

/**
 * @ClassName: ClassInfoDto
 * @Description:TODO ()
 * @author: lanyanhua
 * @date: 2020/12/3 10:02 上午
 * @Copyright:
 */
public class ClassInfoDto extends ClassInfo {

    private List<ClassFieldDto> fieldList;

    public List<ClassFieldDto> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<ClassFieldDto> fieldList) {
        this.fieldList = fieldList;
    }
}
