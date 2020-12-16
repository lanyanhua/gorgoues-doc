package com.lancabbage.lancodeapi.utils.doc;

import com.lancabbage.lancodeapi.enums.ApiTypeEnum;
import com.lancabbage.lancodeapi.enums.ParamModeEnum;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Tag;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationUtils {
    /**
     * controller 注解
     */
    private static final List<String> controllerAnnotation = Arrays.asList("RestController", "Controller");

    /**
     * 参数描述取值方式
     */
    private List<String> parmTag;

    /**
     * 判断是否controller
     *
     * @param annotations 注解
     * @return 是否 path
     */
    public AnnotationRes isController(AnnotationDesc[] annotations) {
        AnnotationRes res = new AnnotationRes();
        if (annotations == null || annotations.length == 0) {
            return res;
        }
        for (AnnotationDesc annotation : annotations) {
            //注解名称
            String name = annotation.annotationType().name();
            if (controllerAnnotation.contains(name)) {
                res.isController = true;
                res.mode = "RestController".equals(name) ? 2 : 1;
            }
            //获取注解
            if (isMapping(name) != -1) {
                res.path = getMappingValue(annotation);
            }
        }

        return res;
    }

    /**
     * 判断是否controller
     *
     * @param annotations 注解
     * @return 是否 path
     */
    public AnnotationRes isApi(AnnotationDesc[] annotations) {
        AnnotationRes res = new AnnotationRes();
        if (annotations == null || annotations.length == 0) {
            return res;
        }
        //读取注解
        for (AnnotationDesc annotation : annotations) {
            String name = annotation.annotationType().name();
            int isMapping = isMapping(name);
            if (isMapping != -1) {
                res.apiType = isMapping;
                res.path = getMappingValue(annotation);
                res.mode = "ResponseBody".equals(name) ? 2 : 1;
            }

        }
        return res;
    }

    /**
     * 判断注解是否mapping
     *
     * @param name 注解名称
     * @return 是否
     */
    private int isMapping(String name) {
        for (ApiTypeEnum type : ApiTypeEnum.values()) {
            if (type.getType().equals(name)) {
                return type.getCode();
            }
        }
        return -1;
    }

    /**
     * 获取mapping 路径
     *
     * @param annotation 注释
     * @return 路径
     */
    private String getMappingValue(AnnotationDesc annotation) {
        AnnotationDesc.ElementValuePair[] elementValuePairs = annotation.elementValues();
        for (AnnotationDesc.ElementValuePair elementValuePair : elementValuePairs) {
            String name = elementValuePair.element().name();
            if ("value".equals(name)) {
                Object value = ((AnnotationValue[]) elementValuePair.value().value())[0].value();
                String s = value.toString();
                if (!s.startsWith("/")) {
                    return "/" + s;
                }
                if (s.endsWith("/")) {
                    return s.substring(0, s.length() - 1);
                }
                return s;
            }
            //路径 只保留前面的斜杠/ 没有斜杠加斜杠 后面有斜杠去掉
        }

        return "";
    }

    /**
     * 获取tag描述
     *
     * @param tags 方法注解上的 @param jobGroup    * @return
     * @return 描述
     */
    public String getTagDesc(Tag[] tags) {
        if (tags == null || tags.length == 0) {
            return null;
        }
        for (Tag tag : tags) {
            if (parmTag.contains(tag.name())) {
                return tag.text();
            }
        }
        return null;
    }

    /**
     * 参数注释信息
     *
     * @param tags 注释上的tag: @param @return
     * @return map
     */
    public Map<String, String> getParamMap(Tag[] tags) {
        Map<String, String> map = new HashMap<>();
        if (tags == null || tags.length == 0) {
            return map;
        }
        for (Tag tag : tags) {
            if (parmTag.contains(tag.name())) {
                String key = ((ParamTag) tag).parameterName();
                String value = ((ParamTag) tag).parameterComment();
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * 获取参数名称
     *
     * @param annotation 注释
     * @return 名称
     */
    public String getParamValue(AnnotationDesc annotation) {
        AnnotationDesc.ElementValuePair[] elementValuePairs = annotation.elementValues();
        for (AnnotationDesc.ElementValuePair elementValuePair : elementValuePairs) {
            String name = elementValuePair.element().name();
            if ("value".equals(name)) {
                Object value = elementValuePair.value().value();
                return value.toString();
            }
            //路径 只保留前面的斜杠/ 没有斜杠加斜杠 后面有斜杠去掉
        }
        return "";
    }

    /**
     * 判断注解是否mapping
     *
     * @param name 注解名称
     * @return 是否
     */
    public int getParamMode(String name) {
        for (ParamModeEnum type : ParamModeEnum.values()) {
            if (type.getType().equals(name)) {
                return type.getCode();
            }
        }
        return -1;
    }

    public void setParmTag(List<String> parmTag) {
        this.parmTag = parmTag;
    }
}
