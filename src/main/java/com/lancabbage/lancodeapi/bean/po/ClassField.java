package com.lancabbage.lancodeapi.bean.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "class_field")
public class ClassField {
    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * classId
     */
    @Column(name = "class_id")
    private Integer classId;

    /**
     * 字段名称
     */
    @Column(name = "param_name")
    private String paramName;

    /**
     * 描述
     */
    @Column(name = "param_describe")
    private String paramDescribe;

    /**
     * 数据类型
     */
    private String type;

    /**
     * ID不为null 关联class表
     */
    @Column(name = "type_id")
    private Integer typeId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取classId
     *
     * @return class_id - classId
     */
    public Integer getClassId() {
        return classId;
    }

    /**
     * 设置classId
     *
     * @param classId classId
     */
    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    /**
     * 获取字段名称
     *
     * @return param_name - 字段名称
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * 设置字段名称
     *
     * @param paramName 字段名称
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * 获取描述
     *
     * @return param_describe - 描述
     */
    public String getParamDescribe() {
        return paramDescribe;
    }

    /**
     * 设置描述
     *
     * @param paramDescribe 描述
     */
    public void setParamDescribe(String paramDescribe) {
        this.paramDescribe = paramDescribe;
    }

    /**
     * 获取数据类型
     *
     * @return type - 数据类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置数据类型
     *
     * @param type 数据类型
     */
    public String setType(String type) {
        this.type = type;
        return type;
    }

    /**
     * 获取ID不为null 关联class表
     *
     * @return type_id - ID不为null 关联class表
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * 设置ID不为null 关联class表
     *
     * @param typeId ID不为null 关联class表
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}