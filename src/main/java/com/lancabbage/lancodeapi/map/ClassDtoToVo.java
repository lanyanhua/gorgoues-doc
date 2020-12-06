package com.lancabbage.lancodeapi.map;

import com.lancabbage.lancodeapi.bean.dto.ApiInfoDto;
import com.lancabbage.lancodeapi.bean.dto.ApiParamDto;
import com.lancabbage.lancodeapi.bean.dto.ClassFieldDto;
import com.lancabbage.lancodeapi.bean.po.ApiInfo;
import com.lancabbage.lancodeapi.bean.po.ApiParam;
import com.lancabbage.lancodeapi.bean.po.ClassField;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 3:48 下午
 * @Description:
 */
@Mapper(componentModel = "spring")
public interface ClassDtoToVo {


    List<ClassField> listClassFieldDtoToPo(List<ClassFieldDto> fieldDtoList);
}
