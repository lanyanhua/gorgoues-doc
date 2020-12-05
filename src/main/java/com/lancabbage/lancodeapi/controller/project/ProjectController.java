package com.lancabbage.lancodeapi.controller.project;

import com.lancabbage.lancodeapi.bean.vo.project.ProjectAddVo;
import com.lancabbage.lancodeapi.bean.vo.project.ProjectVo;
import com.lancabbage.lancodeapi.bean.vo.base.BaseResponse;
import com.lancabbage.lancodeapi.service.ProjectBranchService;
import com.lancabbage.lancodeapi.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/4 10:34 下午
 * @Description:
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * 添加项目
     * @return ID
     */
    @PostMapping("/addProject")
    public BaseResponse<Integer> addProject(ProjectAddVo vo){
        int id = projectService.addProject(vo);
        return BaseResponse.successInstance(id);
    }

    /**
     * 查询所有项目信息
     * @return 项目信息
     */
    @GetMapping("/listProjectAll")
    public BaseResponse<List<ProjectVo>> listProjectAll() {
        List<ProjectVo> projectVos = projectService.listProjectAll();
        return BaseResponse.successInstance(projectVos);
    }
}
