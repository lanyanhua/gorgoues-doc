package com.lancabbage.lancodeapi.bean.vo.project;

import com.sun.istack.internal.NotNull;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 5:26 下午
 * @Description:
 */
public class ProjectAddVo {

    /**
     * 项目名称
     */
    @NotNull
    private String name;

    /**
     * 上下文路径
     */
    @NotNull
    private String contextPath;
    /**
     * 远程库路径
     */
    @NotNull
    private String remotePath;

    /**
     * 分支名称
     */
    @NotNull
    private String branchName;

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

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
