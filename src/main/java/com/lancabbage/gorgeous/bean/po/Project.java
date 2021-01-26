package com.lancabbage.gorgeous.bean.po;

import java.util.Date;
import javax.persistence.*;

public class Project {
    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 远程库路径
     */
    @Column(name = "remote_path")
    private String remotePath;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

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
     * 获取项目名称
     *
     * @return name - 项目名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置项目名称
     *
     * @param name 项目名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取远程库路径
     *
     * @return remote_path - 远程库路径
     */
    public String getRemotePath() {
        return remotePath;
    }

    /**
     * 设置远程库路径
     *
     * @param remotePath 远程库路径
     */
    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
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
}