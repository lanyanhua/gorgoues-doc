package com.lancabbage.lancodeapi.service.impl;

import com.lancabbage.lancodeapi.bean.po.EnvInfo;
import com.lancabbage.lancodeapi.mapper.EnvInfoMapper;
import com.lancabbage.lancodeapi.service.EnvInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

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

    @Transactional
    @Override
    public int addEnv(EnvInfo envInfo) {
        envInfo.setCreateTime(new Date());
        envInfoMapper.insert(envInfo);
        return envInfo.getId();
    }

    @Transactional
    @Override
    public void saveEnv(EnvInfo envInfo) {
        envInfoMapper.updateByPrimaryKeySelective(envInfo);
    }

    @Override
    public List<EnvInfo> listEnvAll() {
        return envInfoMapper.selectAll();
    }

    @Transactional
    @Override
    public void deleteEnvById(Integer id) {
        Example example = new Example(EnvInfo.class);
        example.createCriteria().andNotEqualTo("id",id);
        int count = envInfoMapper.selectCountByExample(example);
        Assert.isTrue(count>1,"最少要有一条环境数据");
        envInfoMapper.deleteByPrimaryKey(id);
    }
}