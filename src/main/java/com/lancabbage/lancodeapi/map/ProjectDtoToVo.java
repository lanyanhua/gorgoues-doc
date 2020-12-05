package com.lancabbage.lancodeapi.map;

import com.lancabbage.lancodeapi.bean.po.Project;
import com.lancabbage.lancodeapi.bean.po.ProjectBranch;
import com.lancabbage.lancodeapi.bean.vo.project.ProjectAddVo;
import com.lancabbage.lancodeapi.bean.vo.project.ProjectBranchVo;
import com.lancabbage.lancodeapi.bean.vo.project.ProjectVo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 3:48 下午
 * @Description:
 */
@Mapper
public interface ProjectDtoToVo {

    List<ProjectVo> listProjectToVo(List<Project> projects);

    List<ProjectBranchVo> listProjectBranchToVo(List<ProjectBranch> collect);

    Project projectAddVoToPo(ProjectAddVo vo);
}
