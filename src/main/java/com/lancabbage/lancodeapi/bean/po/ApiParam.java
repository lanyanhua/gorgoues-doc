package com.lancabbage.lancodeapi.bean.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "api_param")
public class ApiParam {
    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 参数类型：1：入参 2：出参
     */
    private Integer type;

    /**
     * 参数对应的 class
     */
    @Column(name = "class_id")
    private Integer classId;

    /**
     * 传参方式，0：form-data 1：post json格式 2：path {id}
     */
    @Column(name = "param_mode")
    private Integer paramMode;

    /**
     * 参数名称
     */
    @Column(name = "param_name")
    private String paramName;

    /**
     * 参数描述
     */
    @Column(name = "param_describe")
    private String paramDescribe;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * api_info_id
     */
    @Column(name = "api_id")
    private Integer apiId;

    /**
     * 基本数据类型
     */
    @Column(name = "data_type")
    private String dataType;

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
     * 获取参数类型：1：入参 2：出参
     *
     * @return type - 参数类型：1：入参 2：出参
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置参数类型：1：入参 2：出参
     *
     * @param type 参数类型：1：入参 2：出参
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取参数对应的 class
     *
     * @return class_id - 参数对应的 class
     */
    public Integer getClassId() {
        return classId;
    }

    /**
     * 设置参数对应的 class
     *
     * @param classId 参数对应的 class
     */
    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    /**
     * 获取传参方式，0：form-data 1：post json格式 2：path {id}
     *
     * @return param_mode - 传参方式，0：form-data 1：post json格式 2：path {id}
     */
    public Integer getParamMode() {
        return paramMode;
    }

    /**
     * 设置传参方式，0：form-data 1：post json格式 2：path {id}
     *
     * @param paramMode 传参方式，0：form-data 1：post json格式 2：path {id}
     */
    public void setParamMode(Integer paramMode) {
        this.paramMode = paramMode;
    }

    /**
     * 获取参数名称
     *
     * @return param_name - 参数名称
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * 设置参数名称
     *
     * @param paramName 参数名称
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * 获取参数描述
     *
     * @return param_describe - 参数描述
     */
    public String getParamDescribe() {
        return paramDescribe;
    }

    /**
     * 设置参数描述
     *
     * @param paramDescribe 参数描述
     */
    public void setParamDescribe(String paramDescribe) {
        this.paramDescribe = paramDescribe;
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

    /**
     * 获取api_info_id
     *
     * @return api_id - api_info_id
     */
    public Integer getApiId() {
        return apiId;
    }

    /**
     * 设置api_info_id
     *
     * @param apiId api_info_id
     */
    public void setApiId(Integer apiId) {
        this.apiId = apiId;
    }

    /**
     * 获取基本数据类型
     *
     * @return data_type - 基本数据类型
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * 设置基本数据类型
     *
     * @param dataType 基本数据类型
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}