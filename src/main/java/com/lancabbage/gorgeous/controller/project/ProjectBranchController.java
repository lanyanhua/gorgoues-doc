package com.lancabbage.gorgeous.controller.project;

import com.lancabbage.gorgeous.bean.dto.ProjectBranchAddDto;
import com.lancabbage.gorgeous.bean.dto.ProjectBranchDto;
import com.lancabbage.gorgeous.bean.po.Project;
import com.lancabbage.gorgeous.bean.vo.base.BaseResponse;
import com.lancabbage.gorgeous.bean.vo.project.ProjectBranchAddVo;
import com.lancabbage.gorgeous.service.ProjectBranchService;
import com.lancabbage.gorgeous.service.ProjectService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 6:35 下午
 * @Description: 项目分支
 */
@RestController
@RequestMapping("/branch")
public class ProjectBranchController {

    private final ProjectBranchService branchService;
    private final ProjectService projectService;

    public ProjectBranchController(ProjectBranchService branchService, ProjectService projectService) {
        this.branchService = branchService;
        this.projectService = projectService;
    }

    /**
     * 添加项目分支
     */
    @PostMapping("/addProjectBranch")
    public BaseResponse<Integer> addProjectBranch(@RequestBody @Valid ProjectBranchAddVo vo) {
        ProjectBranchAddDto dto = new ProjectBranchAddDto();
        dto.setName(vo.getName());
        Project project = projectService.getProjectById(vo.getProjectId());
        Assert.notNull(project, "项目ID不存在");
        dto.setProject(project);
        // git信息 项目名
        int id = branchService.addProjectBranch(dto);
        return BaseResponse.successInstance(id);
    }

    /**
     * 拉git代码
     *
     * @param projectId 项目ID
     * @param branchId  分支ID
     */
    @PostMapping("/pullProjectBranch")
    public BaseResponse<String> pullProjectBranch(@RequestParam Integer projectId, @RequestParam Integer branchId) {
        Project project = projectService.getProjectById(projectId);
        Assert.notNull(project, "项目ID不存在");
        ProjectBranchDto branch = new ProjectBranchDto();
        branch.setProject(project);
        branch.setId(branchId);
        // git信息 项目名
        branchService.pullProjectBranch(branch);
        return BaseResponse.successInstance("成功");
    }

    /**
     * 删除分支
     * @param id 分支ID
     */
    @DeleteMapping("/deleteBranchById")
    public BaseResponse<String> deleteBranchById(@RequestParam Integer id){
        branchService.deleteBranchById(id);
        return BaseResponse.successInstance("成功");
    }

}
