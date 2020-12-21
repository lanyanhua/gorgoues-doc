package com.lancabbage.lancodeapi.bean.vo.env;

import com.sun.istack.internal.NotNull;

/**
 * @author: lanyanhua
 * @date: 2020/12/7 1:09 下午
 * @Description:
 */
public class EnvSaveVo {

    private Integer id;

    /**
     * 名称
     */
    @NotNull
    private String name;

    /**
     * 域名
     */
    @NotNull
    private String domain;

    /**
     * header参数，多个逗号隔开
     */
    private String header;

    /**
     * 是否使用项目端口：1：是 0：否
     */
    private Boolean isPort;

    /**
     * 是否使用项目上下文路径
     */
    private Boolean isContextPath;


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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Boolean getPort() {
        return isPort;
    }

    public void setPort(Boolean port) {
        isPort = port;
    }

    public Boolean getContextPath() {
        return isContextPath;
    }

    public void setContextPath(Boolean contextPath) {
        isContextPath = contextPath;
    }
}
