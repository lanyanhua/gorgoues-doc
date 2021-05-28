package com.lancabbage.gorgeous.service.impl;

import com.lancabbage.gorgeous.bean.po.NotesConfig;
import com.lancabbage.gorgeous.mapper.NotesConfigMapper;
import com.lancabbage.gorgeous.service.NotesConfigService;
import com.lancabbage.gorgeous.utils.doc.NotesConfigUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
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

    @Override
    public Integer saveNotesConfig(NotesConfig config) {
        if(config.getId() == null){
            config.setCreateTime(new Date());
            configMapper.insert(config);
        }else {
            configMapper.updateByPrimaryKeySelective(config);
        }
        NotesConfigUtils.refresh();
        return config.getId();
    }

    @Override
    public void deleteById(Integer id) {
        configMapper.deleteByPrimaryKey(id);
        NotesConfigUtils.refresh();
    }

    @Override
    public List<NotesConfig> listNotesConfigByType(String type) {
        Example example = new Example(NotesConfig.class);
        if(StringUtils.hasLength(type)) {
            example.createCriteria().andEqualTo("type",type);
        }
        return configMapper.selectByExample(example);
    }
}
