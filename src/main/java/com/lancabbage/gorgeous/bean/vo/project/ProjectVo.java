package com.lancabbage.gorgeous.bean.vo.project;

import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 3:41 下午
 * @Description:
 */
public class ProjectVo {
    /**
     * ID
     */
    private Integer id;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 上下文路径
     */
    private String contextPath;
    /**
     * 远程库路径
     */
    private String remotePath;

    /**
     * 端口
     */
    private Integer port;
    /**
     * 分支
     */
    private List<ProjectBranchVo> branchList;

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

    public List<ProjectBranchVo> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<ProjectBranchVo> branchList) {
        this.branchList = branchList;
    }

}