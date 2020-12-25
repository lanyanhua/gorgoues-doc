package com.lancabbage.lancodeapi.utils.doc;

import com.lancabbage.lancodeapi.bean.dto.ClassFieldDto;
import com.lancabbage.lancodeapi.bean.dto.ClassInfoDto;
import com.lancabbage.lancodeapi.bean.po.ClassInfo;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.Type;
import com.sun.javadoc.TypeVariable;
import com.sun.tools.javadoc.ParameterizedTypeImpl;

import java.util.*;
import java.util.stream.Collectors;

import static com.lancabbage.lancodeapi.utils.doc.NotesConfigUtils.s;

/**
 * @author: lanyanhua
 * @date: 2020/12/4 8:38 下午
 * @Description:
 */
public class ClassInfoUtils {

    public static List<String> baseDataType = Arrays.asList("void", "String", "Object", "byte", "Byte", "short", "Short", "int", "Integer", "long", "Long",
            "double", "Double", "float", "Float", "char", "Char", "boolean", "Boolean", "Date", "MultipartFile", "BigDecimal");
    public static List<String> notSetField = Arrays.asList("HttpServletResponse", "HttpServletRequest", "LinkedHashMap", "HashMap", "URL");
    public static List<String> arrayType = Arrays.asList("List", "Set", "array");
    /**
     * class Map
     */
    public Map<ClassKey, ClassInfoDto> classMap;
    private final AnnotationUtils annotationUtils;


    public ClassInfoUtils(AnnotationUtils annotationUtils) {
        this.annotationUtils = annotationUtils;
        classMap = new HashMap<>();
    }

    public Map<ClassKey, ClassInfoDto> getClassMap() {
        return classMap;
    }

    public ClassInfoDto getClassInfo(Type type) {
        return getClassInfo(type, new HashMap<>());
    }

    /**
     * 获取入参出餐 class
     *
     * @param type        class类型
     * @param paradigmMap 范型值
     * @return classInfo
     */
    public ClassInfoDto getClassInfo(Type type, Map<String, ClassInfoDto> paradigmMap) {
        String packagePath = type.toString();
        ClassInfoDto classInfoDto;
        boolean isArr = packagePath.endsWith("[]");
//        if (isArr) {
//            System.out.println("debugger");
//        }
        String className = isArr ? packagePath.substring(packagePath.lastIndexOf(".") + 1) : type.typeName();
        //基本数据类型直接返回
        if (baseDataType.contains(className) && !isArr) {
            return new ClassInfoDto(className);
        }
        //赋值返回Class数据
        ClassKey classKey = new ClassKey();
        classKey.setName(className);
        classKey.setPackagePath(packagePath);
        //从classMap中获取class
        classInfoDto = classMap.get(classKey);
        if (classInfoDto != null) {
            return classInfoDto;
        }
        classInfoDto = new ClassInfoDto();
        //当前类
        ClassDoc doc = type.asClassDoc();
        //父类
        Type superclass = null;
        //范型
        if (type instanceof ParameterizedTypeImpl) {
            //范型 c.type.getTypeArguments()
            ParameterizedTypeImpl parameterizedType = ((ParameterizedTypeImpl) type);
            superclass = parameterizedType.superclassType();
            //类型参数
            Type[] types = parameterizedType.typeArguments();
            //类型变量
            TypeVariable[] tv = doc.typeParameters();
            List<ClassInfoDto> paradigmList = new ArrayList<>(tv.length);
            classInfoDto.setParadigmList(paradigmList);
            //范型包地址
            boolean isPutPac= false;
            for (int i = 0; i < tv.length; i++) {
                ClassInfoDto v ;
                if (types != null && types.length > 0 ) {
                    //  java.util.List<T> T存在类型 修改包名
                    if( (v = paradigmMap.get(types[i].typeName()))!=null){
                        isPutPac = true;
                    }else {
                        v = getClassInfo(types[i]);
                    }
                } else {
                    v = getClassInfo(tv[i].asClassDoc());
                }
                //获取范型classKey
                paradigmList.add(v);
                paradigmMap.put(tv[i].typeName(), v);
            }
            //数组修改类名
            if (arrayType.contains(className)) {
                classKey.setName(paradigmList.get(0).getClassName() + "[]");
            }
            if(isPutPac) {
                packagePath = packagePath.substring(0, packagePath.indexOf("<")) +"<"+
                        paradigmList.stream().map(ClassInfo::getPackagePath).collect(Collectors.joining(","))
                        +">";
                classKey.setPackagePath(packagePath);
            }
        }

        //先申明对象放入classMap中 解决循环依赖问题
        classMap.put(classKey, classInfoDto);
        classInfoDto.setClassName(classKey.getName());
        //class路径不为null，价值class信息
        classInfoDto.setClassPath(classKey.getPackagePath());
        classInfoDto.setPackagePath(classKey.getPackagePath());
        //基本数据类型会为空
        if (doc == null) {
            return classInfoDto;
        }
        if(superclass == null){
            superclass = doc.superclass();
        }
        //描述
        classInfoDto.setClassDescribe(doc.commentText());
        //不用赋值字段的赋值范型，用赋值字段的范型当字段数据类型用

        List<ClassFieldDto> fieldDtoList = new ArrayList<>();
        classInfoDto.setFieldList(fieldDtoList);
        //数组赋值类型
        if (arrayType.contains(className) || isArr) {
            //[] 表示是数组
            ClassFieldDto fieldDto = new ClassFieldDto();
            fieldDtoList.add(fieldDto);
            fieldDto.setParamName("value");
            fieldDto.setParamDescribe("");
            ClassInfoDto c;
            if (isArr) {
                c = getClassInfo(type.getElementType());
            } else {
                c = paradigmMap.values().isEmpty() ? new ClassInfoDto("Object") : paradigmMap.values().iterator().next();
            }
            fieldDto.setTypeClass(c);
            fieldDto.setType(s(c.getClassName(), c.getBaseType()));
        }
        //是否需要赋值字段
        else if (!notSetField.contains(className)) {
            //字段
            FieldDoc[] fields = doc.fields(false);
            for (FieldDoc field : fields) {
                ClassFieldDto fieldDto = new ClassFieldDto();
                fieldDtoList.add(fieldDto);
                fieldDto.setParamName(field.name());
                //字段注释
                annotationUtils.setParmTag(NotesConfigUtils.getFieldTag());
                String tagDesc = annotationUtils.getTagDesc(field.tags());
                annotationUtils.setParmTag(NotesConfigUtils.getFieldAnnotation());
                String annotationDesc = annotationUtils.getAnnotationDesc(field.annotations());
                fieldDto.setParamDescribe(s(annotationDesc, tagDesc, field.commentText()));
                Type fType = field.type();
                String name = fType.typeName();

                //数据类型
                ClassInfoDto dataType;
                if ((dataType = paradigmMap.get(name)) == null) {
                    //当前字段是范型字段 并且引用了当前范型 赋值范型
                    Map<String, ClassInfoDto> paradigmMap1 = new HashMap<>();
                    if (fType instanceof ParameterizedTypeImpl) {
                        Type[] types = fType.asParameterizedType().typeArguments();
                        for (Type type1 : types) {
                            if (paradigmMap.containsKey(type1.typeName())) {
                                paradigmMap1 = paradigmMap;
                                break;
                            }
                        }
                    }
                    //paradigmMap get 为null 就调用getClassInfo获取
                    dataType = getClassInfo(fType, paradigmMap1);
                }
                //为基本数据类型
                if (fieldDto.setType(dataType.getBaseType()) == null) {
                    //为引用数据类型
                    fieldDto.setTypeClass(dataType);
                    fieldDto.setType(dataType.getClassName());
                }
            }
            //父类字段
            if (superclass != null && !superclass.toString().contains("java.lang.")) {
                //获取父类字段
                ClassInfoDto classInfo = getClassInfo(superclass);
                if (classInfo.getFieldList() != null) {
                    fieldDtoList.addAll(classInfo.getFieldList());
                }
            }
        }
        return classInfoDto;
    }

}
