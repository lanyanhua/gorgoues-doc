package com.lancabbage.gorgeous.service.impl;

import com.lancabbage.gorgeous.bean.dto.ProjectBranchAddDto;
import com.lancabbage.gorgeous.bean.po.Project;
import com.lancabbage.gorgeous.bean.po.ProjectBranch;
import com.lancabbage.gorgeous.bean.vo.project.ProjectAddVo;
import com.lancabbage.gorgeous.bean.vo.project.ProjectVo;
import com.lancabbage.gorgeous.map.ProjectDtoToVo;
import com.lancabbage.gorgeous.mapper.ProjectMapper;
import com.lancabbage.gorgeous.service.ProjectBranchService;
import com.lancabbage.gorgeous.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 3:52 下午
 * @Description:
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectMapper projectMapper;
    private final ProjectBranchService projectBranchService;
    private final ProjectDtoToVo projectDtoToVo;

    public ProjectServiceImpl(ProjectMapper projectMapper, ProjectBranchService projectBranchService, ProjectDtoToVo projectDtoToVo) {
        this.projectMapper = projectMapper;
        this.projectBranchService = projectBranchService;
        this.projectDtoToVo = projectDtoToVo;
    }


    @Override
    public List<ProjectVo> listProjectAll() {
        List<Project> projects = projectMapper.selectAll();
        List<ProjectVo> projectVos = new ArrayList<>();
        if (!projects.isEmpty()) {
            //查询分支
            projectVos = projectDtoToVo.listProjectToVo(projects);
            List<ProjectBranch> projectBranches = projectBranchService.listProjectBranchById(projects.stream()
                    .map(Project::getId).collect(Collectors.toList())
            );
            for (ProjectVo projectVo : projectVos) {
                List<ProjectBranch> collect = projectBranches.stream()
                        .filter(i -> i.getProjectId().equals(projectVo.getId()))
                        .collect(Collectors.toList());
                projectVo.setBranchList(projectDtoToVo.listProjectBranchToVo(collect));
            }
        }
        return projectVos;
    }

    @Transactional
    @Override
    public int addProject(ProjectAddVo vo) {
        Example example = new Example(Project.class);
        example.createCriteria().andEqualTo("name", vo.getName());
        int count = projectMapper.selectCountByExample(example);
        Assert.isTrue(count == 0, "项目名已存在，请选择新增分支");
        Project project = projectDtoToVo.projectAddVoToPo(vo);
        project.setCreateTime(new Date());
        projectMapper.insert(project);
        ProjectBranchAddDto branchAddDto = new ProjectBranchAddDto();
        branchAddDto.setName(vo.getBranchName());
        branchAddDto.setProject(project);
        // git信息 项目名
        projectBranchService.addProjectBranch(branchAddDto);
        return project.getId();
    }

    @Override
    public Project getProjectById(Integer projectId) {
        return projectMapper.selectByPrimaryKey(projectId);
    }

    @Transactional
    @Override
    public void saveProject(Project p) {
        Project project = projectMapper.selectByPrimaryKey(p.getId());
        Assert.notNull(project,"项目不存在");
        project.setName(p.getName());
        project.setRemotePath(p.getRemotePath());
        project.setPort(p.getPort());
        project.setRemotePath(p.getRemotePath());
        projectMapper.updateByPrimaryKey(project);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        projectMapper.deleteByPrimaryKey(id);
        projectBranchService.deleteByProjectId(id);
    }

    @Override
    public List<String> listModelById(Integer id) {
        return projectMapper.listModelById(id);
    }
}