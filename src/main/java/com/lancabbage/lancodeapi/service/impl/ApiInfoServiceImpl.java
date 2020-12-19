package com.lancabbage.lancodeapi.service.impl;

import com.lancabbage.lancodeapi.bean.dto.ApiInfoDto;
import com.lancabbage.lancodeapi.bean.dto.ApiParamDto;
import com.lancabbage.lancodeapi.bean.dto.ClassInfoDto;
import com.lancabbage.lancodeapi.bean.po.ApiInfo;
import com.lancabbage.lancodeapi.bean.po.ApiParam;
import com.lancabbage.lancodeapi.bean.vo.api.ApiInfoVo;
import com.lancabbage.lancodeapi.map.ApiDtoToVo;
import com.lancabbage.lancodeapi.mapper.ApiInfoMapper;
import com.lancabbage.lancodeapi.mapper.ApiParamMapper;
import com.lancabbage.lancodeapi.service.ApiInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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

    @Override
    public void saveApiList(List<ApiInfoDto> apiInfos, Integer projectId, Integer branchId) {
        for (ApiInfoDto dto : apiInfos) {
            //新增菜单
            dto.setProjectId(projectId);
            dto.setBranchId(branchId);
            dto.setCreateTime(new Date());
            apiInfoMapper.insert(dto);
            //保存参数信息
            if (!CollectionUtils.isEmpty(dto.getApiParams())) {
                saveParam(dto);
            }
        }
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
    public void updateApi(ApiInfo apiInfo, ApiInfoDto api) {
        if (!apiInfo.getMethod().equals(api.getMethod())) {
            apiInfo.setMethod(api.getMethod());
            apiInfoMapper.updateByPrimaryKey(apiInfo);
        }
        if (!CollectionUtils.isEmpty(api.getApiParams())) {
            Example example = new Example(ApiParam.class);
            example.createCriteria().andEqualTo("apiId", apiInfo.getId());
            apiParamMapper.deleteByExample(example);
            api.setId(apiInfo.getId());
            //保存参数
            saveParam(api);
        }
    }

    private void saveParam(ApiInfoDto api) {
        List<ApiParamDto> paramDtoList = api.getApiParams().stream().peek(i -> {
            i.setApiId(api.getId());
            i.setCreateTime(new Date());
            //赋值class ID
            ClassInfoDto classInfo = i.getClassInfo();
            if (classInfo != null) {
                i.setClassId(classInfo.getId());
            }
        }).collect(Collectors.toList());
        apiParamMapper.insertList(apiDtoToVo.listApiParamDtoToPo(paramDtoList));
    }

}
