package com.lancabbage.lancodeapi.controller.project;

import com.lancabbage.lancodeapi.bean.dto.ProjectBranchAddDto;
import com.lancabbage.lancodeapi.bean.dto.ProjectBranchDto;
import com.lancabbage.lancodeapi.bean.po.Project;
import com.lancabbage.lancodeapi.bean.vo.base.BaseResponse;
import com.lancabbage.lancodeapi.bean.vo.project.ProjectBranchAddVo;
import com.lancabbage.lancodeapi.service.ProjectBranchService;
import com.lancabbage.lancodeapi.service.ProjectService;
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
}
