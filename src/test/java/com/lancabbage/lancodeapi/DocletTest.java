package com.lancabbage.lancodeapi;

import com.lancabbage.lancodeapi.dto.ApiInfoDto;
import com.lancabbage.lancodeapi.dto.ApiParamDto;
import com.lancabbage.lancodeapi.dto.ClassInfoDto;
import com.lancabbage.lancodeapi.dto.MenuDto;
import com.lancabbage.lancodeapi.entity.ApiParam;
import com.lancabbage.lancodeapi.enums.ParamModeEnum;
import com.lancabbage.lancodeapi.enums.ParamTypeEnum;
import com.sun.javadoc.*;
import com.sun.tools.javadoc.ClassDocImpl;
import com.sun.tools.javadoc.ParameterizedTypeImpl;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 类说明：打印类及其字段、方法的注释<br>
 * 使用javadoc实现<br>
 * 需要在工程中加载jdk中的包$JAVA_HOME/lib/tools.jar
 */
public class DocletTest extends Doclet {
    // 因为比较依赖注释的格式 所以 注释格式要 可以配置这样

    public static String localPath = "/Users/lanyanhua/Desktop/gittest";//下载已有仓库到本地路径
    public static String branch = "master";
    public static String project = "lan-job";
    public static String srcPath = "src/main/java";
    public static String controllerPath = "com/lanyanhua/job/controller";

    public static String[] controller = {"RestController", "Controller"};
    //1:post 2:get 3:delete 4:put
    public static String[] mapping = {"RequestMapping", "PostMapping", "GetMapping", "DeleteMapping", "PutMapping"};

    public static String[] notClassType = {"void", "String", "Object", "List","Set", "byte", "Byte", "short", "Short","int", "Integer", "long", "Long",
            "double", "Double", "float", "Float", "char", "Char",  "boolean", "Boolean"};
    /**
     * 参数描述取值方式
     */
    public static List<String> parmTag = Arrays.asList("@param");
    /**
     * class描述取值
     */
    public static List<String> classTag = Arrays.asList("@Description:");
    public static List<String> returnTag = Arrays.asList("@return");

//    public static List<String> tagList = Arrays.asList("@param","@Description:","@return");

    /**
     * 参数传输方式 1：form-data 2：post json格式 3：path {id}
     */
    public static String[] paramMode = { "RequestParam","RequestBody", "PathVariable"};
//@RequestParam(value = "id",required = false)
//@PathVariable(value = "id")

    /**
     * 测试
     */
    @Test
    public void main() {

        //java源文件的路径
        ArrayList<String> sources = new ArrayList<>();
        sources.add("/Users/lanyanhua/Documents/workspace/lan-code-api/src/test/java/com/lancabbage/lancodeapi/gitTest.java");

        //读取class
        ClassDoc[] classDoc = getClassDoc(sources);

    }


    private ClassDoc[] getClassDoc(List<String> sources1) {
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("-doclet");
        list1.add(DocletTest.class.getName());
        list1.addAll(sources1);
        com.sun.tools.javadoc.Main.execute(list1.toArray(new String[0]));
        //组装对象
        return DocletTest.root.classes();
    }

    @Test
    public void file() throws IOException {
        ///Users/lanyanhua/Desktop/gittest/lan-job/master/src/main/java/com/lanyanhua/job/controller
        //controller 路径
        String basePath = localPath + "/" + project + "/" + branch + "/" + srcPath + "/" + controllerPath;
        File file = new File(basePath);
//        assert list != null;
        //获取路径下所有的java文件
        List<String> sources = getJavaFile(file);
        //读取class
        ClassDoc[] classes = getClassDoc(sources);
        //组装对象
        List<MenuDto> menuList = new ArrayList<>();
        //classMap
        Map<ClassKey, ClassInfoDto> classMap = new HashMap<>();
        for (ClassDoc classDoc : classes) {
            System.out.println(classDoc);
            ClassDocImpl c = (ClassDocImpl) classDoc;

            //注解 判断是否是controller RestController 还有路径RequestMapping({"/jobGroup"})
            AnnotationDesc[] annotations = c.annotations();
            if (annotations == null || annotations.length == 0) {
                continue;
            }
            String path = "";
            boolean isController = false;
            for (AnnotationDesc annotation : annotations) {
                //注解名称
                String name = annotation.annotationType().name();
                if (isController(name)) {
                    isController = true;
                }
                //获取注解
                if (isMapping(name) != -1) {
                    path = getMappingValue(annotation);
                }
            }
            if (!isController) {
                continue;
            }
            //组装菜单
            MenuDto m = new MenuDto();
            menuList.add(m);
            //类名
            m.setClassName(c.name());
            //获取注释
            m.setMenuName(s(c.commentText(), c.name()));
            List<ApiInfoDto> apiInfos = new ArrayList<>();
            m.setApiInfos(apiInfos);
            //api
            for (MethodDoc method : c.methods()) {
                AnnotationDesc[] mAnn = method.annotations();
                if (mAnn == null || mAnn.length == 0) {
                    continue;
                }
                int mappingType = -1;
                String mappingValue = "";
                //读取注解
                for (AnnotationDesc annotation : mAnn) {
                    String name = annotation.annotationType().name();
                    int isMapping = isMapping(name);
                    if (isMapping != -1) {
                        mappingType = isMapping;
                        mappingValue = getMappingValue(annotation);
                    }
                }
                if (mappingType == -1) {
                    continue;
                }
                //api信息
                ApiInfoDto apiInfo = new ApiInfoDto();
                apiInfos.add(apiInfo);
                //接口名称
                apiInfo.setName(s(method.commentText(), method.name()));
                apiInfo.setMethod(method.name());
                //接口类型
                apiInfo.setType(mappingType);
                apiInfo.setPath(path + mappingValue);
                //参数信息
                List<ApiParam> apiParams = new ArrayList<>();
                apiInfo.setApiParams(apiParams);

                //方法注解上的 @param jobGroup    * @return
                Tag[] tags = method.tags();
                //返回值 没有类的路径
                ApiParamDto retParam = new ApiParamDto();
                apiParams.add(retParam);
                Type returnType = method.returnType();
                //从类名称
                String resClassName = returnType.typeName();
                retParam.setParamName(resClassName);
                retParam.setType(ParamTypeEnum.OUT_PARAM.getCode());
                retParam.setParamDescribe(getReturnDesc(tags));
                //赋值返回Class数据
                if(!notClassType(resClassName)){
                    ClassKey classKey = getClassInfo(c, resClassName);
                    ClassInfoDto classInfoDto = getClassInfoDto(classMap, classKey);
                    retParam.setClassInfo(classInfoDto);
                }else{
                    ClassInfoDto classInfoDto = new ClassInfoDto();
                    classInfoDto.setClassName(resClassName);
//                    ((ParameterizedTypeImpl) returnType).typeArguments(); //范型
                    retParam.setClassInfo(classInfoDto);
                }
                //入参
                Map<String, String> paramMap = getParamMap(tags);
                Parameter[] parameters = method.parameters();
                if(parameters == null || parameters.length == 0){
                    continue;
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
                        int paramMode = getParamMode(name1);
                        if(paramMode!= -1){
                            apiParamDto.setParamMode(paramMode);
                            String paramValue = getParamValue(annotation);
                            if(StringUtils.isNotBlank(paramValue)){
                                apiParamDto.setParamName(paramValue);
                            }
                        }
                    }
                    resClassName = parameter.typeName();
                    //赋值返回Class数据
                    if(!notClassType(resClassName)){
                        ClassKey classKey = getClassInfo(c, resClassName);
                        ClassInfoDto classInfoDto = getClassInfoDto(classMap, classKey);
                        apiParamDto.setClassInfo(classInfoDto);
                    }else{
                        ClassInfoDto classInfoDto = new ClassInfoDto();
                        classInfoDto.setClassName(resClassName);
                        apiParamDto.setClassInfo(classInfoDto);
                    }
                }
            }
        }

        System.out.println(menuList);
    }


    private ClassInfoDto getClassInfoDto(Map<ClassKey, ClassInfoDto> classMap,ClassKey classKey) {
        ClassInfoDto dto = classMap.get(classKey);
        if(dto == null){
            dto = new ClassInfoDto();
            dto.setClassName(classKey.getName());
            dto.setClassPath(classKey.getPath());
            classMap.put(classKey,dto);
        }
        return dto;
    }


    private Map<String, String> getParamMap(Tag[] tags) {
        Map<String, String> map = new HashMap<>();
        if (tags == null || tags.length == 0) {
            return map;
        }
        for (Tag tag : tags) {
            if(parmTag.contains(tag.name())) {
                String key = ((ParamTag) tag).parameterName();
                String value = ((ParamTag) tag).parameterComment();
                map.put(key, value);
            }
        }
        return map;
    }
    public String getReturnDesc(Tag[] tags){
        if (tags == null || tags.length == 0) {
            return null;
        }
        for (Tag tag : tags) {
            if(parmTag.contains(tag.name())) {
                return tag.text();
            }
        }
        return null;
    }

    public ClassKey getClassInfo(ClassDocImpl c, String className) {
        //所有导入 入参出参没有的就根据导包来获取
        ClassDoc[] classDocs = c.importedClasses();
        PackageDoc[] packageDocs = c.importedPackages();
        ClassKey info = new ClassKey();
        info.setName(className);
        for (ClassDoc classDoc : classDocs) {
            String name = classDoc.name();
            if(className.equals(name)){
                String packages = classDoc.toString().replace(".","/")+".java";
                String path = localPath + "/" + project + "/" + branch + "/" + srcPath +"/"+packages;
                File file = new File(path);
                if(file.exists()){
                    info.setPath(path);
                    return info;
                }

            }
        }
        for (PackageDoc packageDoc : packageDocs) {
            String packages = packageDoc.name().replace(".","/")+"/"+className+".java";
            String path = localPath + "/" + project + "/" + branch + "/" + srcPath +"/"+packages;
            File file = new File(path);
            if(file.exists()){
                info.setPath(path);
                return info;
            }
        }
        return info;
    }

    /**
     * 判断是否controller
     *
     * @param name 注解名称
     * @return 是否
     */
    public boolean isController(String name) {
        for (String s : controller) {
            if (s.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断注解是否mapping
     *
     * @param name 注解名称
     * @return 是否
     */
    public int isMapping(String name) {
        for (int i = 0; i < mapping.length; i++) {
            if (mapping[i].equals(name)) {
                return i;
            }
        }
        return -1;
    }
    /**
     * 判断注解是否mapping
     *
     * @param name 注解名称
     * @return 是否
     */
    public int getParamMode(String name) {
        for (int i = 0; i < paramMode.length; i++) {
            if (paramMode[i].equals(name)) {
                return i+1;
            }
        }
        return -1;
    }

    public boolean notClassType(String name) {
        for (String s : notClassType) {
            if (s.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取mapping 路径
     *
     * @param annotation 注释
     * @return 路径
     */
    public String getMappingValue(AnnotationDesc annotation) {
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
     * 获取文件路径
     *
     * @param file 文件夹
     */
    public List<String> getJavaFile(File file) throws IOException {
        List<String> sources = new ArrayList<>();

        File[] list = file.listFiles();
        if (list == null || list.length == 0) {
            return sources;
        }
        for (File value : list) {
            if (value.isDirectory()) {
                sources.addAll(getJavaFile(file));
                continue;
            }
            String path = value.getCanonicalPath();
            if (path.endsWith(".java")) {
                sources.add(path);
            }
        }
        return sources;
    }

    public String s(String s1, String s2) {
        return StringUtils.isBlank(s1) ? s2 : s1;
    }

    public static class ClassKey {

        /**
         * 类名
         */
        private String name;
        /**
         * 路径
         */
        private String path;


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ClassKey classInfo = (ClassKey) o;
            return Objects.equals(name, classInfo.name) &&
                    Objects.equals(path, classInfo.path);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, path);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }


    /**
     * 文档根节点
     */
    private static RootDoc root;
    public static LanguageVersion languageVersion() {
        return LanguageVersion.JAVA_1_5;
    }
    /**
     * javadoc调用入口
     */
    public static boolean start(RootDoc root) {
        DocletTest.root = root;
        return true;
    }
}