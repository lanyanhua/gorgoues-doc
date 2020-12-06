package com.lancabbage.lancodeapi.bean.vo.classInfo;

import com.lancabbage.lancodeapi.bean.po.ClassField;

import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/6 9:22 下午
 * @Description:
 */
public class ClassInfoVo {

    private Integer id;

    /**
     * 类名
     */
    private String className;

    /**
     * 描述
     */
    private String classDescribe;

    /**
     * 字段
     */
    private List<ClassField> classFieldList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassDescribe() {
        return classDescribe;
    }

    public void setClassDescribe(String classDescribe) {
        this.classDescribe = classDescribe;
    }

    public List<ClassField> getClassFieldList() {
        return classFieldList;
    }

    public void setClassFieldList(List<ClassField> classFieldList) {
        this.classFieldList = classFieldList;
    }
}
