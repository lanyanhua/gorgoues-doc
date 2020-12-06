package com.lancabbage.lancodeapi.service;

import com.lancabbage.lancodeapi.bean.dto.ApiInfoDto;

import java.util.List;

/**
 * API service
 * @author: lanyanhua
 * @date: 2020/12/6 4:07 下午
 * @Description:
 */
public interface ApiInfoService {
    /**
     * 保存API信息
     * @param apiInfos API info
     * @param projectId 项目ID
     * @param branchId 分支ID
     */
    void saveApiList(List<ApiInfoDto> apiInfos, Integer projectId, Integer branchId);
}
