package com.lancabbage.gorgeous.service.impl;

import com.lancabbage.gorgeous.bean.dto.ApiInfoDto;
import com.lancabbage.gorgeous.bean.dto.ClassInfoDto;
import com.lancabbage.gorgeous.bean.po.ApiInfo;
import com.lancabbage.gorgeous.bean.po.ApiParam;
import com.lancabbage.gorgeous.bean.vo.api.ApiInfoVo;
import com.lancabbage.gorgeous.map.ApiDtoToVo;
import com.lancabbage.gorgeous.mapper.ApiInfoMapper;
import com.lancabbage.gorgeous.mapper.ApiParamMapper;
import com.lancabbage.gorgeous.service.ApiInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: lanyanhua
 * @date: 2020/12/6 4:10 下午
 * @Description:
 */
@Service
public class ApiInfoServiceImpl implements ApiInfoService {


    private final ApiInfoMapper apiInfoMapper;
    private final ApiParamMapper apiParamMapper;
    private final ApiDtoToVo apiDtoToVo;

    public ApiInfoServiceImpl(ApiInfoMapper apiInfoMapper, ApiParamMapper apiParamMapper
            , ApiDtoToVo apiDtoToVo) {
        this.apiInfoMapper = apiInfoMapper;
        this.apiParamMapper = apiParamMapper;
        this.apiDtoToVo = apiDtoToVo;
    }


    @Transactional
    @Override
    public synchronized void addApiList(List<ApiInfoDto> apiInfos, Integer projectId, Integer branchId) {

        for (ApiInfoDto api : apiInfos) {
            api.setProjectId(projectId);
            api.setBranchId(branchId);
            api.setCreateTime(new Date());
        }
        apiInfoMapper.insertList(apiDtoToVo.listApiInfoDtoToPo(apiInfos));
        //保存参数信息
        saveParam(apiInfos);
    }

    @Transactional
    @Override
    public synchronized void saveApiList(List<ApiInfoDto> apiInfos, Integer projectId, Integer branchId) {
        Example example = new Example(ApiInfo.class);
        example.createCriteria().andEqualTo("branchId", branchId);
        List<ApiInfo> apiInfoList = apiInfoMapper.selectByExample(example);
        if (!apiInfoList.isEmpty()) {
            example = new Example(ApiParam.class);
            example.createCriteria().andIn("apiId", apiInfoList.stream().map(ApiInfo::getId).collect(Collectors.toList()));
            apiParamMapper.deleteByExample(example);
        }
        List<ApiInfoDto> apiInfoAdds = new ArrayList<>();

        for (ApiInfoDto api : apiInfos) {
            List<ApiInfo> collect;
            //不存在新增
            if (apiInfoList.isEmpty() || (collect = apiInfoList.stream()
                    .filter(i -> i.getPath().equals(api.getPath())
                            && i.getType().equals(api.getType()))
                    .collect(Collectors.toList())).isEmpty()) {
                api.setProjectId(projectId);
                api.setBranchId(branchId);
                api.setCreateTime(new Date());
                apiInfoAdds.add(api);
                continue;
            }
            ApiInfo apiInfo = collect.get(0);
            api.setId(apiInfo.getId());
            apiInfo.setMethod(api.getMethod());
            apiInfo.setName(api.getName());
            apiInfoMapper.updateByPrimaryKey(apiInfo);
        }
        if(!apiInfoAdds.isEmpty()) {
            apiInfoMapper.insertList(apiDtoToVo.listApiInfoDtoToPo(apiInfoAdds));
        }
        //保存参数信息
        saveParam(apiInfos);
    }

    @Override
    public List<ApiInfoVo> listApiByBranchId(Integer branchId) {
        Example example = new Example(ApiInfo.class);
        example.createCriteria().andEqualTo("branchId", branchId);
        List<ApiInfo> apiInfos = apiInfoMapper.selectByExample(example);
        if (apiInfos.isEmpty()) {
            return new ArrayList<>();
        }
        //查询参数
        List<ApiInfoVo> apiInfoList = apiDtoToVo.listApiInfoToVo(apiInfos);
        example = new Example(ApiParam.class);
        example.createCriteria().andIn("apiId"
                , apiInfos.stream().map(ApiInfo::getId).collect(Collectors.toList()));
        List<ApiParam> apiParams = apiParamMapper.selectByExample(example);
        //赋值参数
        for (ApiInfoVo apiInfo : apiInfoList) {
            apiInfo.setApiParamList(apiParams.stream()
                    .filter(i -> i.getApiId().equals(apiInfo.getId()))
                    .collect(Collectors.toList())
            );
        }
        return apiInfoList;
    }

    @Override
    public List<ApiInfo> listApiInfoByBranchId(Integer branchId) {
        Example example = new Example(ApiInfo.class);
        example.createCriteria().andEqualTo("branchId", branchId);
        return apiInfoMapper.selectByExample(example);
    }

    @Override
    public void deleteByBranchId(List<Integer> id) {
        Example example = new Example(ApiInfo.class);
        example.createCriteria().andIn("branchId", id);
        apiInfoMapper.deleteByExample(example);
    }

    private void saveParam(List<ApiInfoDto> api) {
        List<ApiParam> paramDtoList = new ArrayList<>();
        for (ApiInfoDto a : api) {
            if (a.getApiParams() == null) {
                continue;
            }
            paramDtoList.addAll(a.getApiParams().stream().peek(i -> {
                i.setApiId(a.getId());
                i.setCreateTime(new Date());
                //赋值class ID
                ClassInfoDto classInfo = i.getClassInfo();
                if (classInfo != null) {
                    i.setClassId(classInfo.getId());
                }
            }).collect(Collectors.toList()));
        }
        apiParamMapper.insertList(paramDtoList);
    }

}
