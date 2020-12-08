package com.lancabbage.lancodeapi.service.impl;

import com.lancabbage.lancodeapi.bean.dto.ClassFieldDto;
import com.lancabbage.lancodeapi.bean.dto.ClassInfoDto;
import com.lancabbage.lancodeapi.bean.po.ClassField;
import com.lancabbage.lancodeapi.bean.po.ClassInfo;
import com.lancabbage.lancodeapi.bean.vo.classInfo.ClassInfoVo;
import com.lancabbage.lancodeapi.map.ClassDtoToVo;
import com.lancabbage.lancodeapi.mapper.ClassFieldMapper;
import com.lancabbage.lancodeapi.mapper.ClassInfoMapper;
import com.lancabbage.lancodeapi.service.ClassInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
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
        if(CollectionUtils.isEmpty(classInfo.getFieldList())){
            return;
        }
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

    @Override
    public List<ClassInfoVo> listClassByBranchId(Integer branchId) {
        Example example = new Example(ClassInfo.class);
        example.createCriteria().andEqualTo("branchId", branchId);
        List<ClassInfo> classInfos = classInfoMapper.selectByExample(example);
        if (classInfos.isEmpty()) {
            return new ArrayList<>(0);
        }
        //查询字段
        example = new Example(ClassField.class);
        example.createCriteria().andIn("classId"
                , classInfos.stream().map(ClassInfo::getId).collect(Collectors.toList()));
        List<ClassField> fields = classFieldMapper.selectByExample(example);
        //赋值
        List<ClassInfoVo> classInfoVoList = classDtoTovo.listClassFieldToVo(classInfos);
        for (ClassInfoVo c : classInfoVoList) {
            c.setClassFieldList(fields.stream()
                    .filter(i -> i.getClassId().equals(c.getId()))
                    .collect(Collectors.toList())
            );
        }
        return classInfoVoList;
    }
}
