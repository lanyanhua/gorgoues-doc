package com.lancabbage.lancodeapi.service;

import com.lancabbage.lancodeapi.bean.dto.ProjectBranchAddDto;
import com.lancabbage.lancodeapi.bean.dto.ProjectBranchDto;
import com.lancabbage.lancodeapi.bean.po.ProjectBranch;

import java.util.List;

/**
 * 项目分支service
 *
 * @author: lanyanhua
 * @date: 2020/12/5 5:31 下午
 * @Description:
 */
public interface ProjectBranchService {

    /**
     * 根据项目ID查询分支
     */
    List<ProjectBranch> listProjectBranchById(List<Integer> id);

    /**
     * 添加项目分支
     *
     * @param branchAddDto 分支名称，项目信息
     * @return ID
     */
    int addProjectBranch(ProjectBranchAddDto branchAddDto);

    /**
     * 拉git代码
     */
    void pullProjectBranch(ProjectBranchDto branch);
}
