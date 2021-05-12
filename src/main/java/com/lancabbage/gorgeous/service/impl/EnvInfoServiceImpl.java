package com.lancabbage.gorgeous.service.impl;

import com.lancabbage.gorgeous.bean.po.EnvInfo;
import com.lancabbage.gorgeous.mapper.EnvInfoMapper;
import com.lancabbage.gorgeous.service.EnvInfoService;
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
    public int saveEnv(EnvInfo envInfo) {
        if (envInfo.getId() == null) {
            envInfo.setCreateTime(new Date());
            envInfoMapper.insert(envInfo);
            return envInfo.getId();
        }
        envInfo.setName(null);
        envInfoMapper.updateByPrimaryKeySelective(envInfo);
        return envInfo.getId();
    }

    @Override
    public List<EnvInfo> listEnvAll() {
        return envInfoMapper.selectAll();
    }

    @Transactional
    @Override
    public void deleteEnvById(Integer id) {
        Example example = new Example(EnvInfo.class);
        example.createCriteria().andNotEqualTo("id", id);
        int count = envInfoMapper.selectCountByExample(example);
        Assert.isTrue(count > 0, "最少要有一条环境数据");
        envInfoMapper.deleteByPrimaryKey(id);
    }
}
