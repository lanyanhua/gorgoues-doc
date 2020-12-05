package com.lancabbage.lancodeapi.mapper;

import com.lancabbage.lancodeapi.bean.po.GitInfo;
import com.lancabbage.lancodeapi.mapper.base.BaseMapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GitInfoMapper extends BaseMapper<GitInfo> {
}