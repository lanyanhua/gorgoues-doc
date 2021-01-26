package com.lancabbage.gorgeous.service;

import com.lancabbage.gorgeous.bean.po.NotesConfig;

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

    /**
     * 保存注释配置
     */
    Integer saveNotesConfig(NotesConfig config);

    /**
     * 删除配置
     *
     * @param id ID
     */
    void deleteById(Integer id);

    /**
     * 根据类型查询配置
     * @param type 类型
     */
    List<NotesConfig> listNotesConfigByType(String type);
}
