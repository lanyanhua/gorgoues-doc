package com.lancabbage.lancodeapi.mapper;

import com.lancabbage.lancodeapi.bean.po.NotesConfig;
import com.lancabbage.lancodeapi.mapper.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface NotesConfigMapper extends BaseMapper<NotesConfig> {
}