package com.lancabbage.lancodeapi.utils.doc;

import com.lancabbage.lancodeapi.bean.dto.*;
import com.lancabbage.lancodeapi.bean.po.ApiParam;
import com.lancabbage.lancodeapi.enums.ParamModeEnum;
import com.lancabbage.lancodeapi.enums.ParamTypeEnum;
import com.sun.javadoc.*;
import com.sun.tools.javadoc.ClassDocImpl;
import tk.mybatis.mapper.util.StringUtil;

import java.util.*;

/**
 * @author: lanyanhua
 * @date: 2020/12/4 8:38 下午
 * @Description:
 */
public class ApiInfoUtils {

    private final AnnotationUtils annotationUtils;
    private final ClassInfoUtils classInfoUtils;

    public ApiInfoUtils() {
        List<String> parmTag = Collections.singletonList("@param");
        this.annotationUtils = new AnnotationUtils(parmTag);
        this.classInfoUtils = new ClassInfoUtils();
    }

    /**
     * 解析类
     *
     * @param sources 文件路径
     * @return 菜单API
     */
    public List<MenuDto> parsingClass(List<String> sources) {
        ClassDoc[] classes = DocletUtils.getClassDoc(sources);

        //组装对象
        List<MenuDto> menuList = new ArrayList<>();
        for (ClassDoc classDoc : classes) {
            System.out.println(classDoc);
            ClassDocImpl c = (ClassDocImpl) classDoc;
            AnnotationRes controller = annotationUtils.isController(c.annotations());
            //注解 判断是否是controller RestController 还有路径RequestMapping({"/jobGroup"})
            if (!controller.isController) {
                continue;
            }
            //组装菜单
            MenuDto m = new MenuDto();
            menuList.add(m);
            //类名
            m.setClassName(c.name());
            //获取注释
            m.setMenuName(s(c.commentText(), c.name()));
            //api
            m.setApiInfos(parsingMethod(controller, c.methods()));

        }
        return menuList;
    }

    /**
     * 解析方法
     *
     * @param cc      controller类信息
     * @param methods 方法
     * @return API
     */
    private List<ApiInfoDto> parsingMethod(AnnotationRes cc, MethodDoc[] methods) {
        List<ApiInfoDto> apiInfos = new ArrayList<>();
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
            apiInfo.setName(s(method.commentText(), method.name()));
            apiInfo.setMethod(method.name());
            //接口类型 接口访问路径
            apiInfo.setType(isApi.apiType);
            apiInfo.setPath(cc.path + isApi.path);
            //参数信息
            List<ApiParam> apiParams = new ArrayList<>();
            apiInfo.setApiParams(apiParams);
            //返回参数
            apiParams.add(getOutParam(method));
            //入参
            apiParams.addAll(getInputParam(method));
            apiInfos.add(apiInfo);
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
        retParam.setParamDescribe(annotationUtils.getReturnDesc(method.tags()));
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
                    String paramValue = annotationUtils.getParamValue(annotation);
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

    private void setDataType(ApiParamDto paramDto, Type type){
        String typeName = type.typeName();
        ClassInfoDto classInfo = classInfoUtils.getClassInfo(type);
        if (classInfo.getBaseType() !=null) {
            paramDto.setBaseType(typeName);
        }else {
            paramDto.setClassInfo(classInfo);
        }
    }


    private String s(String s1, String s2) {
        return StringUtil.isEmpty(s1) ? s2 : s1;
    }

}
