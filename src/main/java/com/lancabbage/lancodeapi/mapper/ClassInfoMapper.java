package com.lancabbage.lancodeapi.mapper;

import com.lancabbage.lancodeapi.bean.po.ClassInfo;
import com.lancabbage.lancodeapi.mapper.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ClassInfoMapper extends BaseMapper<ClassInfo> {
    /**
     * 查询当前最大ID
     */
    int selectId();
}