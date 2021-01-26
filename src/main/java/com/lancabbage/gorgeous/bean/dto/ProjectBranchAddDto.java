package com.lancabbage.gorgeous.bean.dto;

import com.lancabbage.gorgeous.bean.po.Project;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 6:45 下午
 * @Description:
 */
public class ProjectBranchAddDto {

    /**
     * ID
     */
    private Integer id;

    /**
     * 分支名称
     */
    private String name;

    /**
     * 项目信息
     */
    private Project project;

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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
