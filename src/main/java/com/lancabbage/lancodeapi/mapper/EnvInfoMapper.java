package com.lancabbage.lancodeapi.mapper;

import com.lancabbage.lancodeapi.bean.po.EnvInfo;
import com.lancabbage.lancodeapi.mapper.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EnvInfoMapper extends BaseMapper<EnvInfo> {
}