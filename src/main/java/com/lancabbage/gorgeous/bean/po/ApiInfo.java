package com.lancabbage.gorgeous.bean.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "api_info")
public class ApiInfo {
    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
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
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 访问路径
     */
    private String path;

    /**
     * 项目ID
     */
    @Column(name = "project_id")
    private Integer projectId;

    /**
     * 分支ID
     */
    @Column(name = "branch_id")
    private Integer branchId;

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
     * 获取接口名称
     *
     * @return name - 接口名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置接口名称
     *
     * @param name 接口名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取接口类型：0:all 1:post 2:get 3:delete 4:put
     *
     * @return type - 接口类型：0:all 1:post 2:get 3:delete 4:put
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置接口类型：0:all 1:post 2:get 3:delete 4:put
     *
     * @param type 接口类型：0:all 1:post 2:get 3:delete 4:put
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取方法名
     *
     * @return method - 方法名
     */
    public String getMethod() {
        return method;
    }

    /**
     * 设置方法名
     *
     * @param method 方法名
     */
    public void setMethod(String method) {
        this.method = method;
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
     * 获取访问路径
     *
     * @return path - 访问路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置访问路径
     *
     * @param path 访问路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取项目ID
     *
     * @return project_id - 项目ID
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * 设置项目ID
     *
     * @param projectId 项目ID
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * 获取分支ID
     *
     * @return branch_id - 分支ID
     */
    public Integer getBranchId() {
        return branchId;
    }

    /**
     * 设置分支ID
     *
     * @param branchId 分支ID
     */
    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }
}