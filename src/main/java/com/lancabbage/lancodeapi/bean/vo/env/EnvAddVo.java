package com.lancabbage.lancodeapi.bean.vo.env;

import com.sun.istack.internal.NotNull;

/**
 * @author: lanyanhua
 * @date: 2020/12/7 1:09 下午
 * @Description:
 */
public class EnvAddVo {

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
}
