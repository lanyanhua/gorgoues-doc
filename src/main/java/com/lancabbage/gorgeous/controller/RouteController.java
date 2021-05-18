package com.lancabbage.gorgeous.controller;

import com.lancabbage.gorgeous.bean.po.EnvInfo;
import com.lancabbage.gorgeous.bean.vo.project.ProjectVo;
import com.lancabbage.gorgeous.config.GitInfoConfig;
import com.lancabbage.gorgeous.service.EnvInfoService;
import com.lancabbage.gorgeous.service.GitService;
import com.lancabbage.gorgeous.service.ProjectService;
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

    @GetMapping("/")
    public String main() {
        return "redirect:/docs";
    }

    @GetMapping("/docs")
    public String doc() {
        GitInfoConfig gitInfo = gitService.getGitInfo();
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
