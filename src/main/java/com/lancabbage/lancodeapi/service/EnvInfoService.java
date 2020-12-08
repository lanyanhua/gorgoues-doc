package com.lancabbage.lancodeapi.service;

import com.lancabbage.lancodeapi.bean.po.EnvInfo;

import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/7 1:07 下午
 * @Description: 环境service
 */
public interface EnvInfoService {
    /**
     * 添加环境
     * @return ID
     */
    int addEnv(EnvInfo envInfo);

    /**
     * 保存环境信息
     */
    void saveEnv(EnvInfo envInfo);

    /**
     * 获取所有环境信息
     * @return 环境列表
     */
    List<EnvInfo> listEnvAll();

    /**
     * 删除环境信息
     * @param id ID
     */
    void deleteEnvById(Integer id);
}
