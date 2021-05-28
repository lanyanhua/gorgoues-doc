package com.lancabbage.gorgeous.bean.vo.project;

import com.lancabbage.gorgeous.bean.po.ProjectConfig;

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
     * 远程库路径
     */
    private String remotePath;
    /**
     * 分支
     */
    private List<ProjectBranchVo> branchList;
    /**
     * 项目配置
     */
    private List<ProjectConfig> projectConfigs;

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

    public List<ProjectConfig> getProjectConfigs() {
        return projectConfigs;
    }

    public void setProjectConfigs(List<ProjectConfig> projectConfigs) {
        this.projectConfigs = projectConfigs;
    }
}
