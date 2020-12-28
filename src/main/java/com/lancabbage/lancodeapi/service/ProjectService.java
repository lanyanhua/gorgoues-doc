package com.lancabbage.lancodeapi.service;

import com.lancabbage.lancodeapi.bean.po.Project;
import com.lancabbage.lancodeapi.bean.vo.project.ProjectAddVo;
import com.lancabbage.lancodeapi.bean.vo.project.ProjectVo;

import java.util.List;

/**
 * 项目信息service
 *
 * @author: lanyanhua
 * @date: 2020/12/4 10:34 下午
 * @Description:
 */
public interface ProjectService {

    /**
     * 查询所有项目信息
     *
     * @return 项目信息
     */
    List<ProjectVo> listProjectAll();

    /**
     * 添加项目
     *
     * @param vo 名称，git地址
     * @return id
     */
    int addProject(ProjectAddVo vo);

    /**
     * 查询项目信息
     *
     * @param projectId 项目ID
     */
    Project getProjectById(Integer projectId);

    /**
     * 保存项目
     */
    void saveProject(Project vo);

    /**
     * 删除项目
     * @param id ID
     */
    void deleteById(Integer id);

    /**
     * 查询项目模块名称
     */
    List<String> listModelById(Integer id);
}
