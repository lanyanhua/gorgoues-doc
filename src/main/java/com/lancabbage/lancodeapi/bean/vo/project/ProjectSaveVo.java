package com.lancabbage.lancodeapi.bean.vo.project;


import javax.validation.constraints.NotNull;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 5:26 下午
 * @Description:
 */
public class ProjectSaveVo {

    /**
     * ID
     */
    @NotNull
    private Integer id;
    /**
     * 项目名称
     */
    @NotNull
    private String name;

    /**
     * 远程库路径
     */
    @NotNull
    private String remotePath;

    /**
     * 上下文路径
     */
    private String contextPath;

    /**
     * 端口
     */
    private Integer port;


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

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }
}
