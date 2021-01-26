package com.lancabbage.gorgeous.map;

import com.lancabbage.gorgeous.bean.dto.ClassFieldDto;
import com.lancabbage.gorgeous.bean.dto.ClassInfoDto;
import com.lancabbage.gorgeous.bean.po.ClassField;
import com.lancabbage.gorgeous.bean.po.ClassInfo;
import com.lancabbage.gorgeous.bean.vo.classInfo.ClassInfoVo;
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

    ClassField classFieldDtoToPo(ClassFieldDto i);
}
