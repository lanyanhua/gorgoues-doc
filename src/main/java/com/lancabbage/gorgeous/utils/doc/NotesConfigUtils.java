package com.lancabbage.gorgeous.utils.doc;

import com.lancabbage.gorgeous.bean.po.NotesConfig;
import com.lancabbage.gorgeous.service.NotesConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: lanyanhua
 * @date: 2020/12/20 10:16 下午
 * @Description:
 */
@Component
public class NotesConfigUtils {
    public static final String classTag = "classTag";
    public static final String methodTag = "methodTag";
    public static final String methodParamTag = "methodParamTag";
    public static final String methodReturnTag = "methodReturnTag";
    public static final String fieldTag = "fieldTag";
    public static final String classAnnotation = "classAnnotation";
    public static final String methodAnnotation = "methodAnnotation";
    public static final String fieldAnnotation = "fieldAnnotation";

    public static final String baseDataType = "baseDataType";
    public static final String arrayType = "arrayType";

    public static List<NotesConfig> notesConfigList;
    public static NotesConfigService service;

    @Autowired
    public void initNotesConfigService(NotesConfigService service) {
        if (NotesConfigUtils.service == null) {
            NotesConfigUtils.service = service;
            //修改刷新位置到启动配置中
//            refresh();
        }
    }
    public static void refresh(){
        notesConfigList = service.selectAll();
    }

    public static List<String> getClassTag() {
        return getTag(classTag);
    }

    public static List<String> getMethodTag() {
        return getTag(methodTag);
    }

    public static List<String> getMethodParamTag() {
        return getTag(methodParamTag);
    }

    public static List<String> getMethodReturnTag() {
        return getTag(methodReturnTag);
    }

    public static List<String> getFieldTag() {
        return getTag(fieldTag);
    }

    public static List<String> getClassAnnotation() {
        return getTag(classAnnotation);
    }

    public static List<String> getMethodAnnotation() {
        return getTag(methodAnnotation);
    }

    public static List<String> getFieldAnnotation() {
        return getTag(fieldAnnotation);
    }

    public static List<String> getBaseDataType() {
        return getTag(baseDataType);
    }

    public static List<String> getArrayType() {
        return getTag(arrayType);
    }

    public static List<String> getTag(String tag) {
        return notesConfigList.stream()
                .filter(i -> i.getType().equals(tag))
                .map(NotesConfig::getNotes)
                .collect(Collectors.toList());
    }

    public static String s(String... s) {
        if (s == null) {
            return "";
        }
        for (String s1 : s) {
            if (StringUtils.hasLength(s1)) {
                return s1;
            }
        }
        return "";
    }

    public static NotesConfig notesConfig(String type,String notes){
        NotesConfig config = new NotesConfig();
        config.setType(type);
        config.setNotes(notes);
        return config;
    }
}
