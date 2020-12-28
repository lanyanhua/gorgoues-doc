package com.lancabbage.lancodeapi.map;

import com.lancabbage.lancodeapi.bean.dto.ApiInfoDto;
import com.lancabbage.lancodeapi.bean.dto.ApiParamDto;
import com.lancabbage.lancodeapi.bean.po.ApiInfo;
import com.lancabbage.lancodeapi.bean.po.ApiParam;
import com.lancabbage.lancodeapi.bean.vo.api.ApiInfoVo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 3:48 下午
 * @Description:
 */
@Mapper(componentModel = "spring")
public interface ApiDtoToVo {
 

    List<ApiParam> listApiParamDtoToPo(List<ApiParamDto> paramDtoList);

    List<ApiInfoVo> listApiInfoToVo(List<ApiInfo> apiInfos);

    List<ApiInfoDto> listApiInfoToDto(List<ApiInfo> apiInfos);

    List<ApiInfo> listApiInfoDtoToPo(List<ApiInfoDto> apiInfos);
}
