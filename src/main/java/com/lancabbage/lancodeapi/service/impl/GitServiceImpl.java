package com.lancabbage.lancodeapi.service.impl;

import com.lancabbage.lancodeapi.bean.po.GitInfo;
import com.lancabbage.lancodeapi.bean.po.Project;
import com.lancabbage.lancodeapi.bean.vo.git.GitInfoSaveVo;
import com.lancabbage.lancodeapi.exception.BusinessException;
import com.lancabbage.lancodeapi.map.GitInfoDtoToVo;
import com.lancabbage.lancodeapi.mapper.GitInfoMapper;
import com.lancabbage.lancodeapi.service.GitService;
import com.lancabbage.lancodeapi.utils.git.GitUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 11:57 下午
 * @Description:
 */
@Service
public class GitServiceImpl implements GitService {

    private final GitInfoMapper gitInfoMapper;
    private final GitInfoDtoToVo gitInfoDtoToVo;

    public GitServiceImpl(GitInfoMapper gitInfoMapper, GitInfoDtoToVo gitInfoDtoToVo) {
        this.gitInfoMapper = gitInfoMapper;
        this.gitInfoDtoToVo = gitInfoDtoToVo;
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
            //拉代码
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

    @Override
    public GitInfo getGitInfo() {
        List<GitInfo> gitInfo = gitInfoMapper.selectAll();
        if (gitInfo.isEmpty()) {
            return null;
        }
        return gitInfo.get(0);
    }

    @Override
    public void save(GitInfoSaveVo vo) {
        List<GitInfo> gitInfoList = gitInfoMapper.selectAll();
        GitInfo gitInfo = gitInfoDtoToVo.gitInfoSaveVoToPo(vo);
        if (gitInfoList.isEmpty()) {
            gitInfo.setCreateTime(new Date());
            gitInfoMapper.insert(gitInfo);
        } else {
            gitInfo.setId(gitInfoList.get(0).getId());
            gitInfoMapper.updateByPrimaryKeySelective(gitInfo);
        }
    }

    public String getPublicPath() {
        GitInfo gitInfo = gitInfoMapper.selectAll().get(0);
        GitUtils gitUtils = GitUtils.getInstance(gitInfo.getRepositoryPath());
        return gitUtils.getPublicPath();
    }

}
