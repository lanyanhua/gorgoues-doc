package com.lancabbage.lancodeapi.bean.dto;

import com.lancabbage.lancodeapi.bean.po.Project;
import com.lancabbage.lancodeapi.bean.po.ProjectBranch;

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
