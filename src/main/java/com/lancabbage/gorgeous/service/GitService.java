package com.lancabbage.gorgeous.service;

import com.lancabbage.gorgeous.bean.po.GitInfo;
import com.lancabbage.gorgeous.bean.po.Project;
import com.lancabbage.gorgeous.bean.vo.git.GitInfoSaveVo;

import java.util.List;

/**
 * git service
 *
 * @author: lanyanhua
 * @date: 2020/12/5 11:43 下午
 * @Description:
 */
public interface GitService {

    /**
     * 克隆代码
     *
     * @param project 项目名，远程地址
     * @param branch  分支名称
     * @return Java文件
     */
    List<String> cloneCode(Project project, String branch);

    /**
     * 获取git配置信息
     */
    GitInfo getGitInfo();

    /**
     * 保存git信息
     */
    void save(GitInfoSaveVo gitInfo);

    /**
     * 获取公共类路径
     */
    String getPublicPath();
}
