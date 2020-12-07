package com.lancabbage.lancodeapi.service.impl;

import com.lancabbage.lancodeapi.bean.po.EnvInfo;
import com.lancabbage.lancodeapi.mapper.EnvInfoMapper;
import com.lancabbage.lancodeapi.service.EnvInfoService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/7 1:28 下午
 * @Description:
 */
@Service
public class EnvInfoServiceImpl implements EnvInfoService {

    private final EnvInfoMapper envInfoMapper;

    public EnvInfoServiceImpl(EnvInfoMapper envInfoMapper) {
        this.envInfoMapper = envInfoMapper;
    }

    @Override
    public int addEnv(EnvInfo envInfo) {
        envInfo.setCreateTime(new Date());
        envInfoMapper.insert(envInfo);
        return envInfo.getId();
    }

    @Override
    public void saveEnv(EnvInfo envInfo) {
        envInfoMapper.updateByPrimaryKeySelective(envInfo);
    }

    @Override
    public List<EnvInfo> listEnvAll() {
        return envInfoMapper.selectAll();
    }
}
