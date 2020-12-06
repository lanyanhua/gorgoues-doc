package com.lancabbage.lancodeapi.service.impl;

import com.lancabbage.lancodeapi.bean.dto.ApiInfoDto;
import com.lancabbage.lancodeapi.bean.dto.ApiParamDto;
import com.lancabbage.lancodeapi.bean.dto.ClassInfoDto;
import com.lancabbage.lancodeapi.map.ApiDtoToVo;
import com.lancabbage.lancodeapi.mapper.ApiInfoMapper;
import com.lancabbage.lancodeapi.mapper.ApiParamMapper;
import com.lancabbage.lancodeapi.service.ApiInfoService;
import com.lancabbage.lancodeapi.service.ClassInfoService;
import org.springframework.stereotype.Service;

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
//                ClassInfoDto classInfo = i.getClassInfo();
//                //ID不等于null set 等于null保存再set
//                if (classInfo != null && i.setClassId(classInfo.getId()) == null) {
//                    classInfoService.saveClass(classInfo);
//                    i.setClassId(classInfo.getId());
//                }

            }).collect(Collectors.toList());
            apiParamMapper.insertList( apiDtoToVo.listApiParamDtoToPo(paramDtoList));
        }
    }




}
