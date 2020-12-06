package com.lancabbage.lancodeapi.bean.po;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "git_info")
public class GitInfo {
    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * git用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 本地仓库地址
     */
    @Column(name = "repository_path")
    private String repositoryPath;

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
     * 获取git用户名
     *
     * @return username - git用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置git用户名
     *
     * @param username git用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取本地仓库地址
     *
     * @return repository_path - 本地仓库地址
     */
    public String getRepositoryPath() {
        return repositoryPath;
    }

    /**
     * 设置本地仓库地址
     *
     * @param repositoryPath 本地仓库地址
     */
    public void setRepositoryPath(String repositoryPath) {
        this.repositoryPath = repositoryPath;
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