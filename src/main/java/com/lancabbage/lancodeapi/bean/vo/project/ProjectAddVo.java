package com.lancabbage.lancodeapi.bean.vo.project;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 5:26 下午
 * @Description:
 */
public class ProjectAddVo {

    /**
     * 项目名称
     */
    private String name;

    /**
     * 远程库路径
     */
    private String remotePath;

    /**
     * 分支名称
     */
    private String branchName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
