package com.lancabbage.gorgeous.service.impl;

import com.lancabbage.gorgeous.bean.dto.ClassInfoDto;
import com.lancabbage.gorgeous.bean.po.ClassField;
import com.lancabbage.gorgeous.bean.po.ClassInfo;
import com.lancabbage.gorgeous.bean.vo.classInfo.ClassInfoVo;
import com.lancabbage.gorgeous.map.ClassDtoToVo;
import com.lancabbage.gorgeous.mapper.ClassFieldMapper;
import com.lancabbage.gorgeous.mapper.ClassInfoMapper;
import com.lancabbage.gorgeous.service.ClassInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collection;
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
    public void addClass(Collection<ClassInfoDto> classInfo, Integer projectId, Integer branchId) {
        for (ClassInfoDto c : classInfo) {
            c.setProjectId(projectId);
            c.setBranchId(branchId);
            c.setCreateTime(new Date());
        }
        classInfoMapper.insertList(classDtoTovo.listClassInfoDtoToPo(classInfo));
        saveFieldDto(classInfo);
    }

    @Transactional
    @Override
    public void saveClass(Collection<ClassInfoDto> classInfo, Integer projectId, Integer branchId) {
        Example example = new Example(ClassInfo.class);
        example.createCriteria().andEqualTo("branchId", branchId);
        List<ClassInfo> classInfos = classInfoMapper.selectByExample(example);
        //删除当前所有类的字段
        List<ClassInfoDto> addList = new ArrayList<>();
        for (ClassInfoDto c : classInfo) {
            List<ClassInfo> collect = classInfos.stream()
                    .filter(i -> i.getPackagePath().equals(c.getPackagePath()))
                    .collect(Collectors.toList());
            if (collect.isEmpty()) {
                c.setProjectId(projectId);
                c.setBranchId(branchId);
                c.setCreateTime(new Date());
                addList.add(c);
                continue;
            }
            ClassInfo classInfo1 = collect.get(0);
            //赋值ID
            c.setId(classInfo1.getId());
            //修改
            classInfo1.setClassDescribe(c.getClassDescribe());
            classInfoMapper.updateByPrimaryKeySelective(classInfo1);
        }
        if (!addList.isEmpty()) {
            classInfoMapper.insertList(classDtoTovo.listClassInfoDtoToPo(addList));
        }
        example = new Example(ClassField.class);
        example.createCriteria().andIn("classId", classInfos.stream().map(ClassInfo::getId).collect(Collectors.toList()));
        classFieldMapper.deleteByExample(example);
        saveFieldDto(classInfo);
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

    @Override
    public void deleteByBranchId(List<Integer> id) {
        Example example = new Example(ClassInfo.class);
        example.createCriteria().andIn("branchId", id);
        classInfoMapper.deleteByExample(example);
    }

    /**
     * 保存字段
     *
     * @param classInfo class
     */
    private void saveFieldDto(Collection<ClassInfoDto> classInfo) {
        List<ClassField> fields = new ArrayList<>();
        for (ClassInfoDto c : classInfo) {
            if (c.getFieldList() == null) {
                continue;
            }
            fields.addAll(c.getFieldList().stream().map(i -> {
                ClassField field = classDtoTovo.classFieldDtoToPo(i);
                field.setClassId(c.getId());
                field.setCreateTime(new Date());
                //赋值字段类型
                ClassInfoDto typeClass = i.getTypeClass();
                if (typeClass != null) {
                    field.setTypeId(typeClass.getId());
                }
                return field;
            }).collect(Collectors.toList()));
        }
        classFieldMapper.insertList(fields);
    }
}
