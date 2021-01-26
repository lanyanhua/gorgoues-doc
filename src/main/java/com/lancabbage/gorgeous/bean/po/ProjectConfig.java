package com.lancabbage.gorgeous.bean.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "project_config")
public class ProjectConfig {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 项目ID
     */
    @Column(name = "project_id")
    private Integer projectId;

    /**
     * 名称
     */
    private String name;

    /**
     * 上下文路径
     */
    @Column(name = "context_path")
    private String contextPath;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
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
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取上下文路径
     *
     * @return context_path - 上下文路径
     */
    public String getContextPath() {
        return contextPath;
    }

    /**
     * 设置上下文路径
     *
     * @param contextPath 上下文路径
     */
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    /**
     * 获取端口
     *
     * @return port - 端口
     */
    public Integer getPort() {
        return port;
    }

    /**
     * 设置端口
     *
     * @param port 端口
     */
    public void setPort(Integer port) {
        this.port = port;
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