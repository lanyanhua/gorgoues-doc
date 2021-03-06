package com.lancabbage.gorgeous;

import com.alibaba.fastjson.JSON;
import com.lancabbage.gorgeous.bean.dto.MenuDto;
import com.lancabbage.gorgeous.utils.doc.ApiInfoUtils;
import com.lancabbage.gorgeous.utils.doc.NotesConfigUtils;
import com.lancabbage.gorgeous.utils.git.GitUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
class LanCodeApiApplicationTests {


    @Test
    void contextLoads() {
        List<String> classTag = NotesConfigUtils.getClassTag();
        System.out.println(classTag);
    }


    @Test
    public void test() throws IOException {
        //配置读库
        String basePath = "/Users/lanyanhua/Desktop/gittest/qns/master";
//        String basePath = "/Users/lanyanhua/Desktop/gittest/lan-code-api/master";
        File file = new File(basePath);
        List<String> javaFile = GitUtils.getJavaFile(file);
        ApiInfoUtils classDocUtils = new ApiInfoUtils();
        Map<String, List<MenuDto>> menuDtoList = classDocUtils.parsingClass(javaFile);
        System.out.println(JSON.toJSONString(menuDtoList));
    }

}
