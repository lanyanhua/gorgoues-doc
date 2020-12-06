package com.lancabbage.lancodeapi;

import com.lancabbage.lancodeapi.bean.dto.*;
import com.lancabbage.lancodeapi.bean.po.ApiParam;
import com.lancabbage.lancodeapi.enums.ParamModeEnum;
import com.lancabbage.lancodeapi.enums.ParamTypeEnum;
import com.lancabbage.lancodeapi.utils.doc.ClassKey;
import com.lancabbage.lancodeapi.utils.doc.AnnotationRes;
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
//    public static String srcPath = "src/main/java";
//    public static String controllerPath = "com/lanyanhua/job/controller";

    public static String[] controller = {"RestController", "Controller"};
    //1:post 2:get 3:delete 4:put
    public static String[] mapping = {"RequestMapping", "PostMapping", "GetMapping", "DeleteMapping", "PutMapping"};

    public static String[] notSetField = {"void", "String", "Object", "List", "Set", "byte", "Byte", "short", "Short", "int", "Integer", "long", "Long",
            "double", "Double", "float", "Float", "char", "Char", "boolean", "Boolean", "HttpServletResponse"};
    /**
     * 参数描述取值方式
     */
    public static List<String> parmTag = Arrays.asList("@param");
    /**
     * class描述取值
     */
    public static List<String> classTag = Arrays.asList("@Description:");
    public static List<String> returnTag = Arrays.asList("@return");
    /**
     * class Map
     */
    public Map<ClassKey, ClassInfoDto> classMap = new HashMap<>();

//    public static List<String> tagList = Arrays.asList("@param","@Description:","@return");

    /**
     * 参数传输方式 1：form-data 2：post json格式 3：path {id}
     */
    public static String[] paramMode = {"RequestParam", "RequestBody", "PathVariable"};
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

    @Test
    public void test() {
        //范型
        ArrayList<String> sources = new ArrayList<>();
        sources.add("/Users/lanyanhua/Desktop/gittest/lan-job/master/src/main/java/com/lanyanhua/job/controller/EmailController.java");
        sources.add("/Users/lanyanhua/Desktop/gittest/lan-job/master/src/main/java/com/lanyanhua/job/entity/Result.java");

        ClassDoc[] classDoc = getClassDoc(sources);
        for (MethodDoc method : classDoc[0].methods()) {
            getMethod((ClassDocImpl) classDoc[0], "", method);
        }
    }


    /**
     * 获取classDoc
     *
     * @param sources1 class path
     * @return classDoc
     */
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
//        String basePath = localPath + "/" + project + "/" + branch;//+ "/" + srcPath + "/" + controllerPath;
        String basePath = "/Users/lanyanhua/Documents/workspace/lan-job/";
        File file = new File(basePath);
//        assert list != null;
        //获取路径下所有的java文件
        List<String> sources = getJavaFile(file);
        //读取class
        ClassDoc[] classes = getClassDoc(sources);
        //组装对象
        List<MenuDto> menuList = new ArrayList<>();

        for (ClassDoc classDoc : classes) {
            System.out.println(classDoc);
            ClassDocImpl c = (ClassDocImpl) classDoc;
            AnnotationRes controller = isController(c.annotations());
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
            List<ApiInfoDto> apiInfos = new ArrayList<>();
            m.setApiInfos(apiInfos);
            //api
            for (MethodDoc method : c.methods()) {
                ApiInfoDto apiInfoDto = getMethod(c, controller.path, method);
                if (apiInfoDto != null) {
                    apiInfos.add(apiInfoDto);
                }
            }
        }
        System.out.println(menuList);

    }

    private ApiInfoDto getMethod(ClassDocImpl c, String path, MethodDoc method) {
        AnnotationDesc[] mAnn = method.annotations();
        if (mAnn == null || mAnn.length == 0) {
            return null;
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
            return null;
        }
        //api信息
        ApiInfoDto apiInfo = new ApiInfoDto();
        //接口名称
        apiInfo.setName(s(method.commentText(), method.name()));
        apiInfo.setMethod(method.name());
        //接口类型
        apiInfo.setType(mappingType);
        apiInfo.setPath(path + mappingValue);
        //参数信息
        List<ApiParamDto> apiParams = new ArrayList<>();
        apiInfo.setApiParams(apiParams);
        //返回参数
        apiParams.add(getOutParam(c, method));
        //入参
        apiParams.addAll(getInputParam(c, method));
        return apiInfo;
    }

    /**
     * 入参
     *
     * @param c      class
     * @param method method
     * @return 入参
     */
    private List<ApiParamDto> getInputParam(ClassDocImpl c, MethodDoc method) {
        //参数注释信息
        Map<String, String> paramMap = getParamMap(method.tags());
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
                int paramMode = getParamMode(name1);
                if (paramMode != -1) {
                    apiParamDto.setParamMode(paramMode);
                    String paramValue = getParamValue(annotation);
                    if (StringUtils.isNotBlank(paramValue)) {
                        apiParamDto.setParamName(paramValue);
                    }
                }
            }
            apiParamDto.setClassInfo(getClassInfo(parameter.type()));
        }
        return apiParams;
    }

    /**
     * 出参
     *
     * @param c      class
     * @param method method
     * @return 出参
     */
    private ApiParamDto getOutParam(ClassDocImpl c, MethodDoc method) {
        //返回值
        ApiParamDto retParam = new ApiParamDto();
        Type returnType = method.returnType();
        //返回值类名称
        String resClassName = returnType.typeName();
        retParam.setParamName(resClassName);
        retParam.setType(ParamTypeEnum.OUT_PARAM.getCode());
        retParam.setParamDescribe(getReturnDesc(method.tags()));
        retParam.setClassInfo(getClassInfo(returnType));
        return retParam;
    }


    private Map<String, String> getParamMap(Tag[] tags) {
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
     * 获取返回值 描述
     *
     * @param tags 方法注解上的 @param jobGroup    * @return
     * @return 描述
     */
    public String getReturnDesc(Tag[] tags) {
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
     * 获取入参出餐 class
     *
     * @param type class类型
     * @return classInfo
     */
    private ClassInfoDto getClassInfo(Type type) {
        ClassInfoDto classInfoDto;
        String resClassName = type.typeName();
        //赋值返回Class数据
        ClassKey classKey = new ClassKey();
        classKey.setName(type.typeName());
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
        //是否需要赋值字段
        if (!isSetField(resClassName)) {
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
                ClassInfoDto classInfoDto1;
                // 类型变量
                if (type instanceof ParameterizedTypeImpl && (classInfoDto1 = paradigmMap.get(fType.typeName())) != null) {
                    //数据类型
                    fieldDto.setTypeClass(classInfoDto1);
                } else {
                    //数据类型
                    fieldDto.setTypeClass(getClassInfo(fType));
                }
            }
        }

        return classInfoDto;
    }


    /**
     * 判断是否controller
     *
     * @param annotations 注解
     * @return 是否 path
     */
    public AnnotationRes isController(AnnotationDesc[] annotations) {
        AnnotationRes isController = new AnnotationRes();
        if (annotations == null || annotations.length == 0) {
            return isController;
        }
        for (AnnotationDesc annotation : annotations) {
            //注解名称
            String name = annotation.annotationType().name();
            for (String s : controller) {
                if (s.equals(name)) {
                    isController.isController = true;
                    break;
                }
            }
            //获取注解
            if (isMapping(name) != -1) {
                isController.path = getMappingValue(annotation);
            }
        }

        return isController;
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
                return i + 1;
            }
        }
        return -1;
    }

    public boolean isSetField(String name) {
        for (String s : notSetField) {
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
                sources.addAll(getJavaFile(value));
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