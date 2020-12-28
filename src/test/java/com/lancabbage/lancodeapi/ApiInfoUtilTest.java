package com.lancabbage.lancodeapi;

import com.alibaba.fastjson.JSON;
import com.lancabbage.lancodeapi.bean.dto.MenuDto;
import com.lancabbage.lancodeapi.bean.po.NotesConfig;
import com.lancabbage.lancodeapi.utils.doc.ApiInfoUtils;
import com.lancabbage.lancodeapi.utils.doc.NotesConfigUtils;
import com.lancabbage.lancodeapi.utils.git.GitUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 1:47 下午
 * @Description:
 */
public class ApiInfoUtilTest {

    @Test
    public void test() throws IOException {
        //注释不读库
        List<NotesConfig> notesConfigList = new ArrayList<>();
        notesConfigList.add(NotesConfigUtils.notesConfig("classTag", "@Description:"));
        notesConfigList.add(NotesConfigUtils.notesConfig("methodParamTag", "@param"));
        notesConfigList.add(NotesConfigUtils.notesConfig("methodReturnTag", "@return"));
        notesConfigList.add(NotesConfigUtils.notesConfig("classAnnotation", "@Api(tags)"));
        notesConfigList.add(NotesConfigUtils.notesConfig("methodAnnotation", "@ApiOperation(value)"));
        notesConfigList.add(NotesConfigUtils.notesConfig("fieldAnnotation", "@ApiModelProperty(value)"));
        notesConfigList.add(NotesConfigUtils.notesConfig("arrayType", "List"));
        notesConfigList.add(NotesConfigUtils.notesConfig("arrayType", "Set"));
        notesConfigList.addAll(Stream.of("void", "String", "Object", "byte", "Byte", "short", "Short",
                "int", "Integer", "long", "Long", "double", "Double", "float", "Float", "char", "Char", "boolean", "Boolean",
                "Date", "MultipartFile", "BigDecimal", "URL", "HttpServletResponse", "HttpServletRequest",
                "LinkedHashMap", "HashMap", "Map")
                .map(i->NotesConfigUtils.notesConfig("baseDataType",i)).collect(Collectors.toList()));
        NotesConfigUtils.notesConfigList = notesConfigList;
//        String basePath = "/Users/lanyanhua/Desktop/gittest/gyl/master";
//        String basePath = "/Users/lanyanhua/Desktop/gittest/qns/2.4.3";
//        String basePath = "/Users/lanyanhua/Desktop/gittest/lan-code-api/master";
//        String basePath = "/Users/lanyanhua/Desktop/gittest/jianan-station/station1.0";
        String basePath = "/Users/lanyanhua/Documents/workspace/lan-code-api";
        String publicPath = "/Users/lanyanhua/Desktop/gittest/public";
        File file = new File(basePath);
        File publicFile = new File(publicPath);
        List<String> javaFile = GitUtils.getJavaFile(file);
         javaFile.addAll( GitUtils.getJavaFile(publicFile));
//        javaFile.add("/Users/lanyanhua/Desktop/gittest/public/main/java/com/jaagro/utils/BaseResponse.java");
        ApiInfoUtils classDocUtils = new ApiInfoUtils();
        Map<String, List<MenuDto>> menuDtoList = classDocUtils.parsingClass(javaFile);
        System.out.println(JSON.toJSONString(menuDtoList));
    }
}
