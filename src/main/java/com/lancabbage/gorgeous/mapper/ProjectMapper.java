package com.lancabbage.gorgeous.mapper;

import com.lancabbage.gorgeous.bean.po.Project;
import com.lancabbage.gorgeous.mapper.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProjectMapper extends BaseMapper<Project> {


    /**
     * 查询项目模块名称
     * @param id ID
     * @return 模块名称
     */
    List<String> listModelById(Integer id);

}