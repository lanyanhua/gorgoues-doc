package com.lancabbage.lancodeapi.service.impl;

import com.lancabbage.lancodeapi.bean.dto.ClassFieldDto;
import com.lancabbage.lancodeapi.bean.dto.ClassInfoDto;
import com.lancabbage.lancodeapi.map.ClassDtoToVo;
import com.lancabbage.lancodeapi.mapper.ClassFieldMapper;
import com.lancabbage.lancodeapi.mapper.ClassInfoMapper;
import com.lancabbage.lancodeapi.service.ClassInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: lanyanhua
 * @date: 2020/12/6 5:08 下午
 * @Description:
 */
@Service
public class ClassInfoServiceImpl implements ClassInfoService {

    private final ClassInfoMapper classInfoMapper;
    private final ClassFieldMapper classFieldMapper;
    private final ClassDtoToVo classDtoTovo;


    public ClassInfoServiceImpl(ClassInfoMapper classInfoMapper, ClassFieldMapper classFieldMapper, ClassDtoToVo classDtoTovo) {
        this.classInfoMapper = classInfoMapper;
        this.classFieldMapper = classFieldMapper;
        this.classDtoTovo = classDtoTovo;
    }

    @Transactional
    @Override
    public void saveClass(ClassInfoDto classInfo) {
        classInfo.setCreateTime(new Date());
        classInfoMapper.insert(classInfo);
        //保存字段
        List<ClassFieldDto> fieldDtoList = classInfo.getFieldList().stream().peek(i -> {
            i.setClassId(classInfo.getId());
            i.setCreateTime(new Date());
            //赋值字段类型
            ClassInfoDto typeClass = i.getTypeClass();
            if (typeClass != null && i.setTypeId(typeClass.getId()) == null) {
                saveClass(typeClass);
                i.setTypeId(typeClass.getId());
            }
        }).collect(Collectors.toList());
        classFieldMapper.insertList(classDtoTovo.listClassFieldDtoToPo(fieldDtoList));
    }
}
