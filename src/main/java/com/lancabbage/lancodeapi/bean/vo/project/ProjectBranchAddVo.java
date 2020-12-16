package com.lancabbage.lancodeapi.bean.vo.project;

import com.sun.istack.internal.NotNull;

import javax.persistence.Table;

@Table(name = "project_branch")
public class ProjectBranchAddVo {

    /**
     * 项目ID
     */
    @NotNull
    private Integer projectId;

    /**
     * 分支名称
     */
    @NotNull
    private String name;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * 获取分支名称
     *
     * @return name - 分支名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置分支名称
     *
     * @param name 分支名称
     */
    public void setName(String name) {
        this.name = name;
    }

}