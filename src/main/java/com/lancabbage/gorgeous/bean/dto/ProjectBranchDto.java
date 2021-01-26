package com.lancabbage.gorgeous.bean.dto;

import com.lancabbage.gorgeous.bean.po.Project;
import com.lancabbage.gorgeous.bean.po.ProjectBranch;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 6:45 下午
 * @Description:
 */
public class ProjectBranchDto extends ProjectBranch {

    /**
     * 项目信息
     */
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
