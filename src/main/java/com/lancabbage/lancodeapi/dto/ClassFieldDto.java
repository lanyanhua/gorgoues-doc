package com.lancabbage.lancodeapi.dto;

import com.lancabbage.lancodeapi.entity.ClassField;

import java.util.List;

/**
 * @ClassName: ClassFieldDto
 * @Description:TODO ()
 * @author: lanyanhua
 * @date: 2020/12/3 10:03 上午
 * @Copyright:
 */
public class ClassFieldDto  extends ClassField {

    private ClassInfoDto classInfo;

    public ClassInfoDto getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfoDto classInfo) {
        this.classInfo = classInfo;
    }
}
