package com.lancabbage.lancodeapi.controller.project;

import com.lancabbage.lancodeapi.bean.po.Project;
import com.lancabbage.lancodeapi.bean.vo.base.BaseResponse;
import com.lancabbage.lancodeapi.bean.vo.project.ProjectAddVo;
import com.lancabbage.lancodeapi.bean.vo.project.ProjectVo;
import com.lancabbage.lancodeapi.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/4 10:34 下午
 * @Description: 项目管理
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
     *
     * @return ID
     */
    @PostMapping("/addProject")
    public BaseResponse<Integer> addProject(@RequestBody @Valid ProjectAddVo vo) {
        int id = projectService.addProject(vo);
        return BaseResponse.successInstance(id);
    }

    /**
     * 保存项目
     *
     * @return ID
     */
    @PostMapping("/saveProject")
    public BaseResponse<String> saveProject(@RequestBody Project p) {
        projectService.saveProject(p);
        return BaseResponse.successInstance("成功");
    }

    /**
     * 查询所有项目信息
     *
     * @return 项目信息
     */
    @GetMapping("/listProjectAll")
    public BaseResponse<List<ProjectVo>> listProjectAll() {
        List<ProjectVo> projectVos = projectService.listProjectAll();
        return BaseResponse.successInstance(projectVos);
    }

}
