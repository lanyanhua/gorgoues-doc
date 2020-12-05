package com.lancabbage.lancodeapi.bean.po;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Project {
    /**
     * ID
     */
    @Id
    private Integer id;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 远程库路径
     */
    @Column(name = "remote_path")
    private String remotePath;

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
     * 获取项目名称
     *
     * @return name - 项目名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置项目名称
     *
     * @param name 项目名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取远程库路径
     *
     * @return remote_path - 远程库路径
     */
    public String getRemotePath() {
        return remotePath;
    }

    /**
     * 设置远程库路径
     *
     * @param remotePath 远程库路径
     */
    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
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