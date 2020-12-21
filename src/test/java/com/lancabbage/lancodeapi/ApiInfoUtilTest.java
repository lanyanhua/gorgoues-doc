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
import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 1:47 下午
 * @Description:
 */
public class ApiInfoUtilTest {

    @Test
    public void test() throws IOException {
        List<NotesConfig> notesConfigList = new ArrayList<>();
        notesConfigList.add(NotesConfigUtils.notesConfig("classTag","@Description:"));
        notesConfigList.add(NotesConfigUtils.notesConfig("methodParamTag","@param"));
        notesConfigList.add(NotesConfigUtils.notesConfig("methodReturnTag","@return"));
        notesConfigList.add(NotesConfigUtils.notesConfig("classAnnotation","@Api(tags)"));
        notesConfigList.add(NotesConfigUtils.notesConfig("methodAnnotation","@ApiOperation(value)"));
        notesConfigList.add(NotesConfigUtils.notesConfig("fieldAnnotation","@ApiModelProperty(value)"));
        NotesConfigUtils.notesConfigList =notesConfigList;
//        String basePath = "/Users/lanyanhua/Desktop/gittest/gyl/master";
        String basePath = "/Users/lanyanhua/Desktop/gittest/qns/master";
//        String basePath = "/Users/lanyanhua/Desktop/gittest/lan-code-api/master";
        File file = new File(basePath);
        List<String> javaFile = GitUtils.getJavaFile(file);
        javaFile.add("/Users/lanyanhua/Desktop/gittest/public/main/java/com/jaagro/utils/BaseResponse.java");
        ApiInfoUtils classDocUtils = new ApiInfoUtils();
        List<MenuDto> menuDtoList = classDocUtils.parsingClass(javaFile);
        System.out.println(JSON.toJSONString(menuDtoList.get(0)));
    }
}
