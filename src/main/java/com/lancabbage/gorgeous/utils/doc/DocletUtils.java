package com.lancabbage.gorgeous.utils.doc;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: DocletUtils
 * @Description:TODO (读取class类信息)
 * @author: lanyanhua
 * @date: 2020/12/1 4:57 下午
 * @Copyright:
 */
public class DocletUtils extends Doclet {


    /**
     * 文档根节点
     */
    private static RootDoc root;

    /**
     * 获取classDoc
     *
     * @param sources1 class path
     * @return classDoc
     */
    public static ClassDoc[] getClassDoc(List<String> sources1) {
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("-doclet");
        list1.add(DocletUtils.class.getName());
        list1.addAll(sources1);
        com.sun.tools.javadoc.Main.execute(list1.toArray(new String[0]));
        //组装对象
        return DocletUtils.root.classes();
    }

    public static LanguageVersion languageVersion() {
        return LanguageVersion.JAVA_1_5;
    }

    /**
     * javadoc调用入口
     */
    public static boolean start(RootDoc root) {
        DocletUtils.root = root;
        return true;
    }
}
