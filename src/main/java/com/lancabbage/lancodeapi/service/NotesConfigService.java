package com.lancabbage.lancodeapi.service;

import com.lancabbage.lancodeapi.bean.po.NotesConfig;

import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/20 10:22 下午
 * @Description:
 */
public interface NotesConfigService {
    /**
     * 获取所有配置
     */
    List<NotesConfig> selectAll();

}
