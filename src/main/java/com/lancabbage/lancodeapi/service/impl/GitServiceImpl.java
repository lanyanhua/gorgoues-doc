package com.lancabbage.lancodeapi.service.impl;

import com.lancabbage.lancodeapi.bean.po.Project;
import com.lancabbage.lancodeapi.mapper.GitInfoMapper;
import com.lancabbage.lancodeapi.service.GitService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 11:57 下午
 * @Description:
 */
@Service
public class GitServiceImpl implements GitService {

    private GitInfoMapper gitInfoMapper;

    public GitServiceImpl(GitInfoMapper gitInfoMapper) {
        this.gitInfoMapper = gitInfoMapper;
    }

    @Override
    public List<String> cloneCode(Project project, String branch) {
        return null;
    }
}
