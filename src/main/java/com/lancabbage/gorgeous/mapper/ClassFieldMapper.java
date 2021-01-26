package com.lancabbage.gorgeous.mapper;

import com.lancabbage.gorgeous.bean.po.ClassField;
import com.lancabbage.gorgeous.mapper.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ClassFieldMapper extends BaseMapper<ClassField> {
}