package com.lancabbage.gorgeous.service.impl;

import com.lancabbage.gorgeous.bean.po.Project;
import com.lancabbage.gorgeous.config.GitInfoConfig;
import com.lancabbage.gorgeous.exception.BusinessException;
import com.lancabbage.gorgeous.service.GitService;
import com.lancabbage.gorgeous.utils.git.GitUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 11:57 下午
 * @Description:
 */
@Service
public class GitServiceImpl implements GitService {

    private final GitInfoConfig gitInfoConfig;

    public GitServiceImpl(GitInfoConfig gitInfoConfig) {
        this.gitInfoConfig = gitInfoConfig;
    }

    @Override
    public List<String> cloneCode(Project project, String branch) {
        GitUtils gitUtils = GitUtils.getInstance(project.getRemotePath(), project.getName(), branch, gitInfoConfig);
        try {
            String basePath = gitUtils.getPath();
            //读取java
            File file = new File(basePath);
            //拉代码
            if (file.exists()) {
                gitUtils.pull();
            } else {
                gitUtils.cloneCode();
            }
            return gitUtils.getJavaFile();
        } catch (Exception e) {
            throw new BusinessException("git 拉去代码失败");
        }
    }

    @Override
    public GitInfoConfig getGitInfo() {
        return gitInfoConfig;
    }


    public String getPublicPath() {
        GitUtils gitUtils = GitUtils.getInstance(gitInfoConfig.getRepositoryPath());
        return gitUtils.getPublicPath();
    }

}
