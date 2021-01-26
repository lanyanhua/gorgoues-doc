package com.lancabbage.gorgeous.map;

import com.lancabbage.gorgeous.bean.po.Project;
import com.lancabbage.gorgeous.bean.po.ProjectBranch;
import com.lancabbage.gorgeous.bean.vo.project.ProjectAddVo;
import com.lancabbage.gorgeous.bean.vo.project.ProjectBranchVo;
import com.lancabbage.gorgeous.bean.vo.project.ProjectVo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 3:48 下午
 * @Description:
 */
@Mapper(componentModel = "spring")
public interface ProjectDtoToVo {

    List<ProjectVo> listProjectToVo(List<Project> projects);

    List<ProjectBranchVo> listProjectBranchToVo(List<ProjectBranch> collect);

    Project projectAddVoToPo(ProjectAddVo vo);
}
