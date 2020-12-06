package com.lancabbage.lancodeapi.service.impl;

import com.lancabbage.lancodeapi.bean.dto.MenuDto;
import com.lancabbage.lancodeapi.bean.dto.ProjectBranchAddDto;
import com.lancabbage.lancodeapi.bean.po.ProjectBranch;
import com.lancabbage.lancodeapi.mapper.ProjectBranchMapper;
import com.lancabbage.lancodeapi.service.GitService;
import com.lancabbage.lancodeapi.service.MenuService;
import com.lancabbage.lancodeapi.service.ProjectBranchService;
import com.lancabbage.lancodeapi.utils.doc.ApiInfoUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final MenuService menuService;

    public ProjectBranchServiceImpl(ProjectBranchMapper projectBranchMapper, GitService gitService, MenuService menuService) {
        this.projectBranchMapper = projectBranchMapper;
        this.gitService = gitService;
        this.menuService = menuService;
    }

    @Override
    public List<ProjectBranch> listProjectBranchById(List<Integer> id) {
        Example example = new Example(ProjectBranch.class);
        example.createCriteria().andIn("projectId",id);
        return projectBranchMapper.selectByExample(example);
    }

    @Transactional
    @Override
    public int addProjectBranch(ProjectBranchAddDto branchAddDto) {
        Integer projectId = branchAddDto.getProject().getId();
        //新增分支
        ProjectBranch branch = new ProjectBranch();
        branch.setName(branchAddDto.getName());
        branch.setProjectId(projectId);
        branch.setCreateTime(new Date());
        projectBranchMapper.insert(branch);
        //同步git代码
        List<String> javaFile = gitService.cloneCode(branchAddDto.getProject(),branch.getName());
        //解析api
        ApiInfoUtils classDocUtils = new ApiInfoUtils();
        List<MenuDto> menuDtoList = classDocUtils.parsingClass(javaFile);
        //保存
        menuService.saveMenuList(menuDtoList,projectId,branch.getId());
        return branch.getId();
    }
}
