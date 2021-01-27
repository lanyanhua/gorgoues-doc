package com.lancabbage.gorgeous.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: lanyanhua
 * @date: 2021/1/27 9:47 上午
 * @Description: git信息
 */
@Component
@ConfigurationProperties(prefix ="git-info")
public class GitInfoConfig {

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 仓库地址
     */
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

    @Override
    public String toString() {
        return "GitInfoConfig{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", repositoryPath='" + repositoryPath + '\'' +
                '}';
    }
}
