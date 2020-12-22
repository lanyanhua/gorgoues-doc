package com.lancabbage.lancodeapi.controller;

import com.lancabbage.lancodeapi.bean.po.EnvInfo;
import com.lancabbage.lancodeapi.bean.po.GitInfo;
import com.lancabbage.lancodeapi.bean.vo.project.ProjectVo;
import com.lancabbage.lancodeapi.service.EnvInfoService;
import com.lancabbage.lancodeapi.service.GitService;
import com.lancabbage.lancodeapi.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/4 10:34 下午
 * @Description: 路由
 */
@Controller
public class RouteController {

    private final GitService gitService;
    private final ProjectService projectService;
    private final EnvInfoService envInfoService;

    public RouteController(GitService gitService, ProjectService projectService, EnvInfoService envInfoService) {
        this.gitService = gitService;
        this.projectService = projectService;
        this.envInfoService = envInfoService;
    }

    @GetMapping("/docs")
    public String doc() {
        GitInfo gitInfo = gitService.getGitInfo();
        if (gitInfo == null) {
            return "steps/steps";
        }
        List<ProjectVo> projectVos = projectService.listProjectAll();
        if (projectVos.isEmpty()) {
            return "steps/steps";
        }
        List<EnvInfo> envInfos = envInfoService.listEnvAll();
        if (envInfos.isEmpty()) {
            return "steps/steps";
        }
        return "template";
    }

    @GetMapping("/template")
    public String template() {
        return "template";
    }

    @GetMapping("/steps")
    public String steps() {
        return "steps/steps";
    }

    @GetMapping("/manage")
    public String manage() {
        return "manage";
    }


}
