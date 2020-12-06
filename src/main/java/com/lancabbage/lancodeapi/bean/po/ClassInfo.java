package com.lancabbage.lancodeapi.bean.po;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "class_info")
public class ClassInfo {
    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 类名
     */
    @Column(name = "class_name")
    private String className;

    /**
     * 类名
     */
    @Column(name = "class_describe")
    private String classDescribe;

    /**
     * 包名
     */
    @Column(name = "package_path")
    private String packagePath;

    /**
     * 类路径
     */
    @Column(name = "class_path")
    private String classPath;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 项目ID
     */
    @Column(name = "project_id")
    private Integer projectId;

    /**
     * 分支ID
     */
    @Column(name = "branch_id")
    private Integer branchId;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取类名
     *
     * @return class_name - 类名
     */
    public String getClassName() {
        return className;
    }

    /**
     * 设置类名
     *
     * @param className 类名
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 获取类名
     *
     * @return class_describe - 类名
     */
    public String getClassDescribe() {
        return classDescribe;
    }

    /**
     * 设置类名
     *
     * @param classDescribe 类名
     */
    public void setClassDescribe(String classDescribe) {
        this.classDescribe = classDescribe;
    }

    /**
     * 获取包名
     *
     * @return package_path - 包名
     */
    public String getPackagePath() {
        return packagePath;
    }

    /**
     * 设置包名
     *
     * @param packagePath 包名
     */
    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    /**
     * 获取类路径
     *
     * @return class_path - 类路径
     */
    public String getClassPath() {
        return classPath;
    }

    /**
     * 设置类路径
     *
     * @param classPath 类路径
     */
    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }
}