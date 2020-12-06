package com.lancabbage.lancodeapi.service.impl;

import com.lancabbage.lancodeapi.bean.po.GitInfo;
import com.lancabbage.lancodeapi.bean.po.Project;
import com.lancabbage.lancodeapi.exception.BusinessException;
import com.lancabbage.lancodeapi.mapper.GitInfoMapper;
import com.lancabbage.lancodeapi.service.GitService;
import com.lancabbage.lancodeapi.utils.git.GitUtils;
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

    private final GitInfoMapper gitInfoMapper;

    public GitServiceImpl(GitInfoMapper gitInfoMapper) {
        this.gitInfoMapper = gitInfoMapper;
    }

    @Override
    public List<String> cloneCode(Project project, String branch) {
        GitInfo gitInfo = gitInfoMapper.selectAll().get(0);
        GitUtils gitUtils = GitUtils.getInstance(project.getRemotePath(), gitInfo.getRepositoryPath()
                , project.getName(), branch, gitInfo.getUsername(), gitInfo.getPassword());
        try {
            String basePath = gitUtils.getPath();
            //读取java
            File file = new File(basePath);
            //啦代码
            if (file.exists()) {
                gitUtils.pull();
            } else {
                gitUtils.cloneCode();
            }
            return GitUtils.getJavaFile(file);
        } catch (Exception e) {
            throw new BusinessException("git 拉去代码失败");
        }
    }
}
