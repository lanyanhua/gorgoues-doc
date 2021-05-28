package com.lancabbage.gorgeous.bean.vo.project;


import com.lancabbage.gorgeous.bean.po.ProjectConfig;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 5:26 下午
 * @Description:
 */
public class ProjectSaveVo implements Serializable {

    /**
     * ID
     */
    @NotNull
    private Integer id;
    /**
     * 项目名称
     */
    @NotNull
    private String name;

    /**
     * 远程库路径
     */
    @NotNull
    private String remotePath;

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

    public List<ProjectConfig> getProjectConfigs() {
        return projectConfigs;
    }

    public void setProjectConfigs(List<ProjectConfig> projectConfigs) {
        this.projectConfigs = projectConfigs;
    }
}
