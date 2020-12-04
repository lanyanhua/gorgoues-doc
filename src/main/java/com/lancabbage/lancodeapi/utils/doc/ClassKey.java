package com.lancabbage.lancodeapi.utils.doc;

import java.util.Arrays;
import java.util.Objects;

public class ClassKey {

    /**
     * 类名
     */
    private String name;
    /**
     * 包地址
     */
    private String packagePath;
    /**
     * 路径
     */
    private String path;
    /**
     * 范型
     */
    private ClassKey[] paradigms;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassKey classKey = (ClassKey) o;
        return Objects.equals(name, classKey.name) &&
                Objects.equals(packagePath, classKey.packagePath) &&
                Arrays.equals(paradigms, classKey.paradigms);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, packagePath);
        result = 31 * result + Arrays.hashCode(paradigms);
        return result;
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

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public ClassKey[] getParadigms() {
        return paradigms;
    }

    public void setParadigms(ClassKey[] paradigms) {
        this.paradigms = paradigms;
    }
}