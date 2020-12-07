package com.lancabbage.lancodeapi.controller.project;

import com.lancabbage.lancodeapi.bean.dto.ProjectBranchAddDto;
import com.lancabbage.lancodeapi.bean.po.Project;
import com.lancabbage.lancodeapi.bean.vo.base.BaseResponse;
import com.lancabbage.lancodeapi.bean.vo.project.ProjectBranchAddVo;
import com.lancabbage.lancodeapi.service.ProjectBranchService;
import com.lancabbage.lancodeapi.service.ProjectService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public BaseResponse<String> addProjectBranch(ProjectBranchAddVo vo) {
        ProjectBranchAddDto dto = new ProjectBranchAddDto();
        dto.setName(vo.getName());
        Project project = projectService.getProjectById(vo.getProjectId());
        Assert.notNull(project,"项目ID不存在");
        dto.setProject(project);
        // git信息 项目名
        branchService.addProjectBranch(dto);
        return BaseResponse.successInstance("成功");
    }
}
