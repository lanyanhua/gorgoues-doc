package com.lancabbage.gorgeous.map;

import com.lancabbage.gorgeous.bean.dto.ApiInfoDto;
import com.lancabbage.gorgeous.bean.dto.ApiParamDto;
import com.lancabbage.gorgeous.bean.po.ApiInfo;
import com.lancabbage.gorgeous.bean.po.ApiParam;
import com.lancabbage.gorgeous.bean.vo.api.ApiInfoVo;
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
