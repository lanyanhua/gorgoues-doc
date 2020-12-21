package com.lancabbage.lancodeapi.service.impl;

import com.lancabbage.lancodeapi.bean.po.NotesConfig;
import com.lancabbage.lancodeapi.mapper.NotesConfigMapper;
import com.lancabbage.lancodeapi.service.NotesConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/20 10:26 下午
 * @Description:
 */
@Service
public class NotesConfigServiceImpl implements NotesConfigService {

    private final NotesConfigMapper configMapper;

    public NotesConfigServiceImpl(NotesConfigMapper configMapper) {
        this.configMapper = configMapper;
    }

    @Override
    public List<NotesConfig> selectAll() {
        return configMapper.selectAll();
    }
}
