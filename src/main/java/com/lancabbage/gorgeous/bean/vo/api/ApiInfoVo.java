package com.lancabbage.gorgeous.bean.vo.api;

import com.lancabbage.gorgeous.bean.po.ApiParam;

import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/6 9:13 下午
 * @Description:
 */
public class ApiInfoVo {

    private Integer id;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 接口类型：0:all 1:post 2:get 3:delete 4:put
     */
    private Integer type;

    /**
     * 方法名
     */
    private String method;

    /**
     * 访问路径
     */
    private String path;

    /**
     * 参数
     */
    private List<ApiParam> apiParamList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<ApiParam> getApiParamList() {
        return apiParamList;
    }

    public void setApiParamList(List<ApiParam> apiParamList) {
        this.apiParamList = apiParamList;
    }
}
