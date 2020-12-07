package com.lancabbage.lancodeapi.bean.vo.env;

import com.sun.istack.internal.NotNull;

/**
 * @author: lanyanhua
 * @date: 2020/12/7 1:09 下午
 * @Description:
 */
public class EnvSaveVo {

    @NotNull
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
    @NotNull
    private String header;

}
