package com.lancabbage.lancodeapi.controller.git;

import com.lancabbage.lancodeapi.bean.po.GitInfo;
import com.lancabbage.lancodeapi.bean.vo.base.BaseResponse;
import com.lancabbage.lancodeapi.bean.vo.git.GitInfoSaveVo;
import com.lancabbage.lancodeapi.service.GitService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 3:36 下午
 * @Description: git管理
 */
@RestController
@RequestMapping("/git")
public class GitController {

    private final GitService gitService;

    public GitController(GitService gitService) {
        this.gitService = gitService;
    }

    /**
     * 获取git信息
     */
    @GetMapping("/getGitInfo")
    public BaseResponse<GitInfo> getGitInfo() {
        GitInfo gitInfo = gitService.getGitInfo();
        Assert.notNull(gitInfo,"无git配置信息");
        return BaseResponse.successInstance(gitInfo);
    }

    /**
     * 保存git信息
     */
    @PutMapping("/save")
    public BaseResponse<String> save(@RequestBody GitInfoSaveVo gitInfo) {
        gitService.save(gitInfo);
        return BaseResponse.successInstance("成功");
    }
}
