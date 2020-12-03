package com.lancabbage.lancodeapi.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "env_info")
public class EnvInfo {
    /**
     * ID
     */
    @Id
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 域名
     */
    private String domain;

    /**
     * header参数，多个逗号隔开
     */
    private String header;

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
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取域名
     *
     * @return domain - 域名
     */
    public String getDomain() {
        return domain;
    }

    /**
     * 设置域名
     *
     * @param domain 域名
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * 获取header参数，多个逗号隔开
     *
     * @return header - header参数，多个逗号隔开
     */
    public String getHeader() {
        return header;
    }

    /**
     * 设置header参数，多个逗号隔开
     *
     * @param header header参数，多个逗号隔开
     */
    public void setHeader(String header) {
        this.header = header;
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