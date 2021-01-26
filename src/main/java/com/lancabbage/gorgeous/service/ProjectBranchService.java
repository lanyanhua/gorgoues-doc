package com.lancabbage.gorgeous.service;

import com.lancabbage.gorgeous.bean.dto.ProjectBranchAddDto;
import com.lancabbage.gorgeous.bean.dto.ProjectBranchDto;
import com.lancabbage.gorgeous.bean.po.ProjectBranch;

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

    /**
     * 根据ID删除分支
     */
    void deleteBranchById(Integer id);

    /**
     *  删除分支
     * @param id 项目ID
     */
    void deleteByProjectId(Integer id);

}
