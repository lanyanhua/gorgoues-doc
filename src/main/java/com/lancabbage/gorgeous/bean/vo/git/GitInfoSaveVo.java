package com.lancabbage.gorgeous.bean.vo.git;

import javax.validation.constraints.NotNull;


import java.io.Serializable;

/**
 * @author: lanyanhua
 * @date: 2020/12/7 12:46 下午
 * @Description:
 */
public class GitInfoSaveVo implements Serializable {
    /**
     * git用户名
     */
    @NotNull
    private String username;

    /**
     * 密码
     */
    @NotNull
    private String password;

    /**
     * 本地仓库地址
     */
    @NotNull
    private String repositoryPath;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepositoryPath() {
        return repositoryPath;
    }

    public void setRepositoryPath(String repositoryPath) {
        this.repositoryPath = repositoryPath;
    }
}
