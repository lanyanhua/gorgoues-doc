package com.lancabbage.gorgeous.service.impl;

import com.lancabbage.gorgeous.bean.dto.ProjectBranchAddDto;
import com.lancabbage.gorgeous.bean.po.Project;
import com.lancabbage.gorgeous.bean.po.ProjectBranch;
import com.lancabbage.gorgeous.bean.po.ProjectConfig;
import com.lancabbage.gorgeous.bean.vo.project.ProjectAddVo;
import com.lancabbage.gorgeous.bean.vo.project.ProjectSaveVo;
import com.lancabbage.gorgeous.bean.vo.project.ProjectVo;
import com.lancabbage.gorgeous.map.ProjectDtoToVo;
import com.lancabbage.gorgeous.mapper.ProjectConfigMapper;
import com.lancabbage.gorgeous.mapper.ProjectMapper;
import com.lancabbage.gorgeous.service.ProjectBranchService;
import com.lancabbage.gorgeous.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collections;
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
    private final ProjectConfigMapper projectConfigMapper;
    private final ProjectBranchService projectBranchService;
    private final ProjectDtoToVo projectDtoToVo;

    public ProjectServiceImpl(ProjectMapper projectMapper, ProjectConfigMapper projectConfigMapper, ProjectBranchService projectBranchService
            , ProjectDtoToVo projectDtoToVo) {
        this.projectMapper = projectMapper;
        this.projectConfigMapper = projectConfigMapper;
        this.projectBranchService = projectBranchService;
        this.projectDtoToVo = projectDtoToVo;
    }


    @Override
    public List<ProjectVo> listProjectAll() {
        List<Project> projects = projectMapper.selectAll();
        List<ProjectVo> projectVos = new ArrayList<>();
        if (projects.isEmpty()) {
            return projectVos;
        }
        //查询分支
        projectVos = projectDtoToVo.listProjectToVo(projects);
        List<Integer> ids = projects.stream()
                .map(Project::getId).collect(Collectors.toList());
        List<ProjectBranch> projectBranches = projectBranchService.listProjectBranchById(ids);
        //查询项目配置
        List<ProjectConfig> pcs = projectConfigMapper.listByProjectIds(ids);
        for (ProjectVo projectVo : projectVos) {
            List<ProjectBranch> collect = projectBranches.stream()
                    .filter(i -> i.getProjectId().equals(projectVo.getId()))
                    .collect(Collectors.toList());
            projectVo.setBranchList(projectDtoToVo.listProjectBranchToVo(collect));
            List<ProjectConfig> pc = pcs.stream()
                    .filter(i -> i.getProjectId().equals(projectVo.getId()))
                    .collect(Collectors.toList());
            projectVo.setProjectConfigs(pc);
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
        //项目默认配置
        ProjectConfig pc = new ProjectConfig();
        pc.setProjectId(project.getId());
        pc.setMenuName(vo.getBranchName());
        pc.setName("默认");
        pc.setCreateTime(new Date());
        projectConfigMapper.insert(pc);
        //分支
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
    public void saveProject(ProjectSaveVo p) {
        Project project = projectMapper.selectByPrimaryKey(p.getId());
        Assert.notNull(project, "项目不存在");
        project.setName(p.getName());
        project.setRemotePath(p.getRemotePath());
        projectMapper.updateByPrimaryKey(project);
        //保存配置信息
        if (CollectionUtils.isEmpty(p.getProjectConfigs())) {
            return;
        }
        //修改
        p.getProjectConfigs().stream()
                .filter(i -> i.getId() != null)
                .forEach(projectConfigMapper::updateByPrimaryKeySelective);
        //新增
        List<ProjectConfig> collect = p.getProjectConfigs().stream()
                .filter(i -> i.getId() == null)
                .peek(i -> {
                    i.setCreateTime(new Date());
                    i.setProjectId(p.getId());
                })
                .collect(Collectors.toList());
        if(collect.isEmpty()){
            return;
        }
        projectConfigMapper.insertList(collect);
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

    @Override
    public long count() {
        return projectMapper.selectCount(null);
    }

    @Override
    public List<ProjectConfig> listProjectConfigById(Integer id) {
        return projectConfigMapper.listByProjectIds(Collections.singletonList(id));
    }
}
