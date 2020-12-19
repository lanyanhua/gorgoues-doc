package com.lancabbage.lancodeapi.service;

import com.lancabbage.lancodeapi.bean.dto.ClassInfoDto;
import com.lancabbage.lancodeapi.bean.vo.classInfo.ClassInfoVo;

import java.util.Collection;
import java.util.List;

/**
 * 类service
 *
 * @author: lanyanhua
 * @date: 2020/12/6 4:55 下午
 * @Description:
 */
public interface ClassInfoService {
    /**
     * 保存class信息
     */
    void addClass(Collection<ClassInfoDto> classInfo,Integer projectId, Integer branchId);
    /**
     * 保存class信息
     */
    void saveClass(Collection<ClassInfoDto> classInfo, Integer projectId, Integer branchId);

    /**
     * 查询class 信息
     *
     * @param branchId 分支ID
     * @return class
     */
    List<ClassInfoVo> listClassByBranchId(Integer branchId);
}
