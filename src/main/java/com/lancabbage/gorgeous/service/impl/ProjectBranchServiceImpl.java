package com.lancabbage.gorgeous.service.impl;

import com.lancabbage.gorgeous.bean.dto.*;
import com.lancabbage.gorgeous.bean.po.ProjectBranch;
import com.lancabbage.gorgeous.mapper.ProjectBranchMapper;
import com.lancabbage.gorgeous.service.*;
import com.lancabbage.gorgeous.utils.doc.ApiInfoUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

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
    private final ApiInfoService apiInfoService;
    private final ClassInfoService classInfoService;

    public ProjectBranchServiceImpl(ProjectBranchMapper projectBranchMapper, GitService gitService, MenuService menuService
            , ApiInfoService apiInfoService, ClassInfoService classInfoService) {
        this.projectBranchMapper = projectBranchMapper;
        this.gitService = gitService;
        this.menuService = menuService;
        this.apiInfoService = apiInfoService;
        this.classInfoService = classInfoService;
    }

    @Override
    public List<ProjectBranch> listProjectBranchById(List<Integer> id) {
        Example example = new Example(ProjectBranch.class);
        example.createCriteria().andIn("projectId", id);
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
        List<String> javaFile = gitService.cloneCode(branchAddDto.getProject(), branch.getName());
        //解析api
        ApiInfoUtils classDocUtils = new ApiInfoUtils();
        Map<String, List<MenuDto>> menuDtoList = classDocUtils.parsingClass(javaFile);
        //class 信息单独处理
        Collection<ClassInfoDto> classInfoList = classDocUtils.getClassInfoList();
        classInfoService.addClass(classInfoList, projectId, branch.getId());
        //保存API信息
        List<ApiInfoDto> apiAll = classDocUtils.getApiAll();
        apiInfoService.addApiList(apiAll, projectId, branch.getId());
        //保存
        menuService.addMenuList(menuDtoList, branch);
        return branch.getId();
    }

    @Transactional
    @Override
    public void pullProjectBranch(ProjectBranchDto dto) {
        ProjectBranch b = projectBranchMapper.selectByPrimaryKey(dto.getId());
        Assert.notNull(b, "分支ID不存在");
        //同步git代码
        List<String> javaFile = gitService.cloneCode(dto.getProject(), b.getName());
        //重新读取 java文件
        ApiInfoUtils classDocUtils = new ApiInfoUtils();
        Map<String, List<MenuDto>> menuDtoList = classDocUtils.parsingClass(javaFile);
        //class 信息单独处理
        Collection<ClassInfoDto> classInfoList = classDocUtils.getClassInfoList();
        classInfoService.saveClass(classInfoList, b.getProjectId(), dto.getId());
        //保存API信息
        List<ApiInfoDto> apiAll = classDocUtils.getApiAll();
        apiInfoService.saveApiList(apiAll, b.getProjectId(), dto.getId());
        //保存菜单
        menuService.saveMenuList(menuDtoList, b);
    }

    @Transactional
    @Override
    public void deleteBranchById(Integer id) {
        projectBranchMapper.deleteByPrimaryKey(id);
        menuService.deleteByBranchId(Collections.singletonList(id));
        classInfoService.deleteByBranchId(Collections.singletonList(id));
        apiInfoService.deleteByBranchId(Collections.singletonList(id));
    }

    @Transactional
    @Override
    public void deleteByProjectId(Integer id) {
        Example example = new Example(ProjectBranch.class);
        example.createCriteria().andEqualTo("projectId", id);
        List<ProjectBranch> projectBranches = projectBranchMapper.selectByExample(example);
        if (projectBranches.isEmpty()) {
            return;
        }
        projectBranchMapper.deleteByExample(example);
        List<Integer> idList = projectBranches.stream().map(ProjectBranch::getId).collect(Collectors.toList());
        menuService.deleteByBranchId(idList);
        classInfoService.deleteByBranchId(idList);
        apiInfoService.deleteByBranchId(idList);
    }

}
