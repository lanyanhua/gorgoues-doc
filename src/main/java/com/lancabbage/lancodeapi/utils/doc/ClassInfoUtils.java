package com.lancabbage.lancodeapi.utils.doc;

import com.lancabbage.lancodeapi.bean.dto.ClassFieldDto;
import com.lancabbage.lancodeapi.bean.dto.ClassInfoDto;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.Type;
import com.sun.tools.javadoc.ClassDocImpl;
import com.sun.tools.javadoc.ParameterizedTypeImpl;

import java.util.*;

/**
 * @author: lanyanhua
 * @date: 2020/12/4 8:38 下午
 * @Description:
 */
public class ClassInfoUtils {

    public static List<String> baseDataType = Arrays.asList("void", "String", "Object", "byte", "Byte", "short", "Short", "int", "Integer", "long", "Long",
            "double", "Double", "float", "Float", "char", "Char", "boolean", "Boolean", "Date", "MultipartFile");
    public static List<String> notSetField = Arrays.asList("HttpServletResponse", "HttpServletRequest");
    public static List<String> arrayType = Arrays.asList("List", "Set", "array");
    /**
     * class Map
     */
    public Map<ClassKey, ClassInfoDto> classMap;


    public ClassInfoUtils() {
        classMap = new HashMap<>();
    }

    public Map<ClassKey, ClassInfoDto> getClassMap() {
        return classMap;
    }

    /**
     * 获取入参出餐 class
     *
     * @param type class类型
     * @return classInfo
     */
    public ClassInfoDto getClassInfo(Type type) {
        String className = type.typeName();
        ClassInfoDto classInfoDto;
        //基本数据类型直接返回
        if (baseDataType.contains(className)) {
            classInfoDto = new ClassInfoDto();
            classInfoDto.setBaseType(className);
            return classInfoDto;
        }
        if (arrayType.contains(className)) {
            type.typeName();
        }
        //赋值返回Class数据
        ClassKey classKey = new ClassKey();
        classKey.setName(className);
        classKey.setPackagePath(type.toString());
        //从classMap中获取class
        classInfoDto = classMap.get(classKey);
        if (classInfoDto != null) {
            return classInfoDto;
        }
        classInfoDto = new ClassInfoDto();
        //先申明对象放入classMap中 解决循环依赖问题
        classMap.put(classKey, classInfoDto);
        classInfoDto.setClassName(classKey.getName());
        //class路径不为null，价值class信息
        classInfoDto.setClassPath(classKey.getPackagePath());
        classInfoDto.setPackagePath(classKey.getPackagePath());
        //doc
        ClassDocImpl doc = (ClassDocImpl) type.asClassDoc();
        if (doc == null) {
            return classInfoDto;
        }
        //描述
        classInfoDto.setClassDescribe(doc.commentText());
        //不用赋值字段的赋值范型，用赋值字段的范型当字段数据类型用
        Map<String, ClassInfoDto> paradigmMap = new HashMap<>();
        if (type instanceof ParameterizedTypeImpl) {
            //范型 c.type.getTypeArguments()
            Type[] types = ((ParameterizedTypeImpl) type).typeArguments();
            if (types != null && types.length != 0) {
                List<ClassInfoDto> paradigmList = new ArrayList<>();
                classInfoDto.setParadigmList(paradigmList);
                //范型T
                String s = doc.toString();
                String[] key = s.substring(s.indexOf("<") + 1, s.lastIndexOf(">")).split(",");
                //先申明范型对象
                for (int i = 0; i < types.length; i++) {
                    ClassInfoDto v = getClassInfo(types[i]);
                    //获取范型classKey
                    paradigmList.add(v);
                    paradigmMap.put(key[i], v);
                }
            }
        }
        if (arrayType.contains(className)) {
            List<ClassFieldDto> fieldDtoList = new ArrayList<>();
            classInfoDto.setFieldList(fieldDtoList);
            ClassFieldDto fieldDto = new ClassFieldDto();
            fieldDtoList.add(fieldDto);
            fieldDto.setParamName("value");
            fieldDto.setParamDescribe("");
            fieldDto.setParamDescribe("");
            ClassInfoDto c = paradigmMap.values().isEmpty() ? null : paradigmMap.values().iterator().next();
            fieldDto.setTypeClass(c);
            fieldDto.setType(c == null ? "Object" : c.getClassName());
        }
        //是否需要赋值字段
        else if (!notSetField.contains(className)) {
            //字段
            List<ClassFieldDto> fieldDtoList = new ArrayList<>();
            classInfoDto.setFieldList(fieldDtoList);
            FieldDoc[] fields = doc.fields(false);
            for (FieldDoc field : fields) {
                ClassFieldDto fieldDto = new ClassFieldDto();
                fieldDtoList.add(fieldDto);
                fieldDto.setParamName(field.name());
                fieldDto.setParamDescribe(field.commentText());
                Type fType = field.type();
                String name = fType.typeName();

                //数据类型
                ClassInfoDto dataType;
                if ((dataType = paradigmMap.get(name)) == null) {
                    //paradigmMap get 为null 就调用getClassInfo获取
                    dataType = getClassInfo(fType);
                }
                //为基本数据类型
                if (fieldDto.setType(dataType.getBaseType()) == null) {
                    //为引用数据类型
                    fieldDto.setTypeClass(dataType);
                    fieldDto.setType(dataType.getClassName());
                }
            }
        }

        return classInfoDto;
    }

}
