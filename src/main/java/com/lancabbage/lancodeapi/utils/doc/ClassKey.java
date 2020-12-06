package com.lancabbage.lancodeapi.utils.doc;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassKey classKey = (ClassKey) o;
        return name.equals(classKey.name) &&
                packagePath.equals(classKey.packagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, packagePath);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

}