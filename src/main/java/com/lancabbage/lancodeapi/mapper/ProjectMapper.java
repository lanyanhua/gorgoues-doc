package com.lancabbage.lancodeapi.mapper;

import com.lancabbage.lancodeapi.bean.po.Project;
import com.lancabbage.lancodeapi.mapper.base.BaseMapper;
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