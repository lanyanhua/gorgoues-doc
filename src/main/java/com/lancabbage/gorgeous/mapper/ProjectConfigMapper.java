package com.lancabbage.gorgeous.mapper;

import com.lancabbage.gorgeous.bean.po.ProjectConfig;
import com.lancabbage.gorgeous.mapper.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProjectConfigMapper extends BaseMapper<ProjectConfig> {

    /**
     * 查询项目配置信息
     * @param ids 项目ID
     * @return 上下文、端口
     */
    List<ProjectConfig> listByProjectIds(@Param("ids") List<Integer> ids);
}