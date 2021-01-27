package com.lancabbage.gorgeous.controller.git;

import com.lancabbage.gorgeous.bean.vo.base.BaseResponse;
import com.lancabbage.gorgeous.config.GitInfoConfig;
import com.lancabbage.gorgeous.service.GitService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.security.provider.MD5;

import java.io.File;
import java.io.IOException;

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
    public BaseResponse<GitInfoConfig> getGitInfo() {
        GitInfoConfig gitInfo = gitService.getGitInfo();
        GitInfoConfig gc = new GitInfoConfig();
        gc.setUsername(gitInfo.getUsername());
        gc.setRepositoryPath(gitInfo.getRepositoryPath());
        return BaseResponse.successInstance(gc);
    }

    /**
     * 上传类
     *
     * @param bean 文件
     * @param path 包地址
     */
    @PostMapping("/uploadBean")
    public BaseResponse<String> uploadBean(@RequestParam MultipartFile[] bean, @RequestParam String path) throws IOException {
        String publicPath = gitService.getPublicPath() + "/" + path + "/";
        File file = new File(publicPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        for (MultipartFile f : bean) {
            String fileName = f.getOriginalFilename();
            f.transferTo(new File(publicPath + fileName));
        }
        return BaseResponse.successInstance("成功");
    }

}
