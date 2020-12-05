package com.lancabbage.lancodeapi;

import com.alibaba.fastjson.JSON;
import com.lancabbage.lancodeapi.utils.doc.ApiInfoUtils;
import com.lancabbage.lancodeapi.utils.git.GitUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 1:47 下午
 * @Description:
 */
public class ApiInfoUtilTest {

    public void test() throws IOException {
        String basePath = "/Users/lanyanhua/Desktop/gittest/lan-job/master";
//        String basePath = "/Users/lanyanhua/Documents/workspace/lan-job/" + srcPath;
        File file = new File(basePath);
        List<String> javaFile = GitUtils.getJavaFile(file);
        ApiInfoUtils classDocUtils = new ApiInfoUtils();
        System.out.println(JSON.toJSONString(classDocUtils.parsingClass(javaFile).get(0)));
    }
}
