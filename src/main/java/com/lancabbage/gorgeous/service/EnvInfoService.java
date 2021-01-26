package com.lancabbage.gorgeous.service;

import com.lancabbage.gorgeous.bean.po.EnvInfo;

import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/7 1:07 下午
 * @Description: 环境service
 */
public interface EnvInfoService {

    /**
     * 保存环境信息
     */
    int saveEnv(EnvInfo envInfo);

    /**
     * 获取所有环境信息
     *
     * @return 环境列表
     */
    List<EnvInfo> listEnvAll();

    /**
     * 删除环境信息
     *
     * @param id ID
     */
    void deleteEnvById(Integer id);
}
