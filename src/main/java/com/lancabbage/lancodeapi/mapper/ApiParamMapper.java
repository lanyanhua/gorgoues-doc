package com.lancabbage.lancodeapi.mapper;

import com.lancabbage.lancodeapi.bean.po.ApiParam;
import com.lancabbage.lancodeapi.mapper.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ApiParamMapper extends BaseMapper<ApiParam> {
}