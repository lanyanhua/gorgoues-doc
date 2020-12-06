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
import com.lancabbage.lancodeapi.service.ClassInfoService;
import org.springframework.stereotype.Service;
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
    private final ClassInfoService classInfoService;
    private final ApiDtoToVo apiDtoToVo;

    public ApiInfoServiceImpl(ApiInfoMapper apiInfoMapper, ApiParamMapper apiParamMapper
            , ClassInfoService classInfoService, ApiDtoToVo apiDtoToVo) {
        this.apiInfoMapper = apiInfoMapper;
        this.apiParamMapper = apiParamMapper;
        this.classInfoService = classInfoService;
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
            List<ApiParamDto> paramDtoList = dto.getApiParams().stream().peek(i -> {
                i.setApiId(dto.getId());
                i.setCreateTime(new Date());
                //保存class
                ClassInfoDto classInfo = i.getClassInfo();
                //ID不等于null set 等于null保存再set
                if (classInfo != null && i.setClassId(classInfo.getId()) == null) {
                    classInfo.setProjectId(projectId);
                    classInfo.setBranchId(branchId);
                    classInfoService.saveClass(classInfo);
                    i.setClassId(classInfo.getId());
                }
            }).collect(Collectors.toList());
            apiParamMapper.insertList(apiDtoToVo.listApiParamDtoToPo(paramDtoList));
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


}
