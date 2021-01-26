package com.lancabbage.lancodeapi.utils.doc;

import com.lancabbage.lancodeapi.bean.dto.ApiInfoDto;
import com.lancabbage.lancodeapi.bean.dto.ApiParamDto;
import com.lancabbage.lancodeapi.bean.dto.ClassInfoDto;
import com.lancabbage.lancodeapi.bean.dto.MenuDto;
import com.lancabbage.lancodeapi.enums.ParamModeEnum;
import com.lancabbage.lancodeapi.enums.ParamTypeEnum;
import com.sun.javadoc.*;
import com.sun.tools.javadoc.AnnotatedTypeImpl;
import com.sun.tools.javadoc.AnnotationTypeDocImpl;
import com.sun.tools.javadoc.ClassDocImpl;
import tk.mybatis.mapper.util.StringUtil;

import java.util.*;

import static com.lancabbage.lancodeapi.utils.doc.NotesConfigUtils.s;

/**
 * @author: lanyanhua
 * @date: 2020/12/4 8:38 下午
 * @Description:
 */
public class ApiInfoUtils {

    private final AnnotationUtils annotationUtils;
    private final ClassInfoUtils classInfoUtils;
    private final List<ApiInfoDto> apiAll;

    public ApiInfoUtils() {
        this.annotationUtils = new AnnotationUtils();
        this.classInfoUtils = new ClassInfoUtils(annotationUtils);
        apiAll = new ArrayList<>();
    }

    public Collection<ClassInfoDto> getClassInfoList() {
        return classInfoUtils.getClassMap().values();
    }

    public List<ApiInfoDto> getApiAll() {
        return apiAll;
    }

    /**
     * 解析类
     *
     * @param sources 文件路径
     * @return 菜单API
     */
    public Map<String,List<MenuDto>> parsingClass(List<String> sources) {
        ClassDoc[] classes = DocletUtils.getClassDoc(sources);
        Map<String,List<MenuDto>> menuMap = new HashMap<>();
        //组装对象
//        List<MenuDto> menuList = new ArrayList<>();
        for (ClassDoc classDoc : classes) {
            System.out.println(classDoc);
            ClassDocImpl c = (ClassDocImpl) classDoc;
            AnnotationDesc[] annotations = c.annotations();
            AnnotationRes controller = annotationUtils.isController(annotations);
            //注解 判断是否是controller RestController 还有路径RequestMapping({"/jobGroup"})
            if (!controller.isController || classDoc instanceof AnnotationTypeDocImpl) {
                continue;
            }
            //组装菜单
            MenuDto m = new MenuDto();
            //类名
            m.setClassName(c.name());
            //tag描述
            annotationUtils.setParmTag(NotesConfigUtils.getClassTag());
            String tagDesc = annotationUtils.getTagDesc(classDoc.tags());
            annotationUtils.setParmTag(NotesConfigUtils.getClassAnnotation());
            String annotationDesc = annotationUtils.getAnnotationDesc(annotations);
            //获取注释
            m.setMenuName(s(annotationDesc, tagDesc, c.commentText(), c.name()));
            //api
            m.setApiInfos(parsingMethod(controller, c));

            String path = classDoc.position().file().getPath();
            //win linux获取的路径不同
            int src = path.indexOf("/src/") & path.indexOf("\\src\\");
            int n =path.lastIndexOf("/", src-1) & path.lastIndexOf("\\", src-1);
            String key = path.substring(n+ 1, src);
            List<MenuDto> menuDtoList = menuMap.computeIfAbsent(key, k -> new ArrayList<>());
            menuDtoList.add(m);
        }
        return menuMap;
    }

    /**
     * 解析方法
     *
     * @param cc controller类信息
     * @param c  类
     * @return API
     */
    private List<ApiInfoDto> parsingMethod(AnnotationRes cc, ClassDoc c) {
        List<ApiInfoDto> apiInfos = new ArrayList<>();
        //先读取父类的
        ClassDoc[] interfaces = c.interfaces();
        if (interfaces != null && interfaces.length > 0) {
            for (ClassDoc anInterface : interfaces) {
                apiInfos.addAll(parsingMethod(cc, anInterface));
            }
        }
        //
        MethodDoc[] methods = c.methods();
        for (MethodDoc method : methods) {
            //解析注解
            AnnotationDesc[] annotations = method.annotations();
            AnnotationRes isApi = annotationUtils.isApi(annotations);
            //不是API下一个
            if (isApi.apiType == -1) {
                continue;
            }
            ApiInfoDto apiInfo = new ApiInfoDto();
            //接口描述 方法名
            annotationUtils.setParmTag(NotesConfigUtils.getMethodTag());
            String tagDesc = annotationUtils.getTagDesc(method.tags());
            annotationUtils.setParmTag(NotesConfigUtils.getMethodAnnotation());
            String annotationDesc = annotationUtils.getAnnotationDesc(annotations);
            apiInfo.setName(s(annotationDesc, tagDesc, method.commentText(), method.name()));
            apiInfo.setMethod(method.name());
            if ("batchSaleSignSecondSubmit".equals(apiInfo.getMethod())) {
                System.out.println("debugger");
            }
            //接口类型 接口访问路径
            apiInfo.setType(isApi.apiType);
            apiInfo.setPath(cc.path + isApi.path);
            //参数信息
            List<ApiParamDto> apiParams = new ArrayList<>();
            apiInfo.setApiParams(apiParams);
            //返回参数
            apiParams.add(getOutParam(method));
            //入参
            apiParams.addAll(getInputParam(method));
            apiInfos.add(apiInfo);
            apiAll.add(apiInfo);
        }

        return apiInfos;
    }

    /**
     * 出参
     *
     * @param method method
     * @return 出参
     */
    private ApiParamDto getOutParam(MethodDoc method) {
        //返回值
        ApiParamDto retParam = new ApiParamDto();
        Type returnType = method.returnType();
        //返回值类名称
        String resClassName = returnType.typeName();
        retParam.setParamName(resClassName);
        retParam.setType(ParamTypeEnum.OUT_PARAM.getCode());
        //tag描述
        annotationUtils.setParmTag(NotesConfigUtils.getMethodReturnTag());
        retParam.setParamDescribe(annotationUtils.getTagDesc(method.tags()));
        //赋值数据类型
        setDataType(retParam, returnType);
        return retParam;
    }

    /**
     * 入参
     *
     * @param method method
     * @return 入参
     */
    private List<ApiParamDto> getInputParam(MethodDoc method) {
        //参数注释信息
        annotationUtils.setParmTag(NotesConfigUtils.getMethodParamTag());
        Map<String, String> paramMap = annotationUtils.getParamMap(method.tags());
        //参数
        Parameter[] parameters = method.parameters();
        List<ApiParamDto> apiParams = new ArrayList<>();
        if (parameters == null || parameters.length == 0) {
            return apiParams;
        }
        for (Parameter parameter : parameters) {
            String name = parameter.name();
            ApiParamDto apiParamDto = new ApiParamDto();
            apiParams.add(apiParamDto);
            apiParamDto.setType(ParamTypeEnum.INPUT_PARAM.getCode());
            //参数名称，描述
            apiParamDto.setParamName(name);
            apiParamDto.setParamDescribe(paramMap.get(name));
            //参数传输方式 1：form-data 2：post json格式 3：path {id}
            apiParamDto.setParamMode(ParamModeEnum.FORM_DATA.getCode());
            for (AnnotationDesc annotation : parameter.annotations()) {
                String name1 = annotation.annotationType().name();
                int paramMode = annotationUtils.getParamMode(name1);
                if (paramMode != -1) {
                    apiParamDto.setParamMode(paramMode);
                    String paramValue = annotationUtils.getParamValue(annotation, Collections.singletonList("value"));
                    if (!StringUtil.isEmpty(paramValue)) {
                        apiParamDto.setParamName(paramValue);
                    }
                }
            }
            //赋值数据类型
            setDataType(apiParamDto, parameter.type());
        }
        return apiParams;
    }

    private void setDataType(ApiParamDto paramDto, Type type) {
        if (type instanceof AnnotatedTypeImpl) {
            // ((AnnotatedTypeImpl)parameter.type()).annotations() 字段注解
            // 这个地方 类型是注解类型类 需要获取底层类型 花里胡哨，
            // 外边Parameter parameter就能获取到，不知道这框架为啥要怎么设计
            // 但是这里也有一个比较有意思的东西
            // AnnotatedType.annotations() 只取到了一个值 @javax.validation.Valid
            // parameter.annotations() 还能取到 @RequestBody 这类的注解
            type = ((AnnotatedTypeImpl) type).underlyingType();
        }
        String typeName = type.typeName();
        ClassInfoDto classInfo = classInfoUtils.getClassInfo(type);
        if (classInfo.getBaseType() != null) {
            paramDto.setDataType(typeName);
        } else {
            paramDto.setDataType(classInfo.getClassName());
            paramDto.setClassInfo(classInfo);
        }
    }


}
