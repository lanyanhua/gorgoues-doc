package com.lancabbage.gorgeous.bean.vo.project;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "project_branch")
public class ProjectBranchVo {
    /**
     * ID
     */
    @Id
    private Integer id;

    /**
     * 分支名称
     */
    private String name;

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