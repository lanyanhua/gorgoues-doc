package com.lancabbage.lancodeapi.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "project_branch")
public class ProjectBranch {
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
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

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

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}