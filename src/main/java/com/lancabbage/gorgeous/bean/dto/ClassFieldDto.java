package com.lancabbage.gorgeous.bean.dto;

import com.lancabbage.gorgeous.bean.po.ClassField;

/**
 * @ClassName: ClassFieldDto
 * @Description:TODO ()
 * @author: lanyanhua
 * @date: 2020/12/3 10:03 上午
 * @Copyright:
 */
public class ClassFieldDto extends ClassField {

    private ClassInfoDto typeClass;

    public ClassInfoDto getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(ClassInfoDto typeClass) {
        this.typeClass = typeClass;
    }
}
