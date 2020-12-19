package com.lancabbage.lancodeapi.map;

import com.lancabbage.lancodeapi.bean.dto.ClassFieldDto;
import com.lancabbage.lancodeapi.bean.dto.ClassInfoDto;
import com.lancabbage.lancodeapi.bean.po.ClassField;
import com.lancabbage.lancodeapi.bean.po.ClassInfo;
import com.lancabbage.lancodeapi.bean.vo.classInfo.ClassInfoVo;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 3:48 下午
 * @Description:
 */
@Mapper(componentModel = "spring")
public interface ClassDtoToVo {


    List<ClassField> listClassFieldDtoToPo(List<ClassFieldDto> fieldDtoList);

    List<ClassInfoVo> listClassFieldToVo(List<ClassInfo> classInfos);

    List<ClassInfo> listClassInfoDtoToPo(Collection<ClassInfoDto> classInfo);

}
