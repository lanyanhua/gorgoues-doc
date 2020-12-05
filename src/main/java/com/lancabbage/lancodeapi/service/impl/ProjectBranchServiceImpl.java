package com.lancabbage.lancodeapi.service.impl;

import com.lancabbage.lancodeapi.bean.dto.ProjectBranchAddDto;
import com.lancabbage.lancodeapi.bean.po.ProjectBranch;
import com.lancabbage.lancodeapi.mapper.ProjectBranchMapper;
import com.lancabbage.lancodeapi.service.GitService;
import com.lancabbage.lancodeapi.service.ProjectBranchService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 5:38 下午
 * @Description:
 */
@Service
public class ProjectBranchServiceImpl implements ProjectBranchService {
    private final ProjectBranchMapper projectBranchMapper;
    private final GitService gitService;

    public ProjectBranchServiceImpl(ProjectBranchMapper projectBranchMapper, GitService gitService) {
        this.projectBranchMapper = projectBranchMapper;
        this.gitService = gitService;
    }

    @Override
    public List<ProjectBranch> listProjectBranchById(List<Integer> id) {
        Example example = new Example(ProjectBranch.class);
        example.createCriteria().andIn("projectId",id);
        return projectBranchMapper.selectByExample(example);
    }

    @Override
    public int addProjectBranch(ProjectBranchAddDto branchAddDto) {
        //新增分支
        ProjectBranch branch = new ProjectBranch();
        branch.setName(branchAddDto.getName());
        branch.setProjectId(branchAddDto.getProject().getId());
        branch.setCreateTime(new Date());
        projectBranchMapper.insert(branch);
        //同步git代码
        List<String> javaFile = gitService.cloneCode(branchAddDto.getProject(),branch.getName());
        return branch.getId();
    }
}
