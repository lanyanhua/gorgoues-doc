package com.lancabbage.lancodeapi.mapper;

import com.lancabbage.lancodeapi.bean.po.ApiParam;
import com.lancabbage.lancodeapi.mapper.base.BaseMapper;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.provider.SpecialProvider;

import java.util.List;

@Mapper
@Repository
public interface ApiParamMapper extends BaseMapper<ApiParam> {


}