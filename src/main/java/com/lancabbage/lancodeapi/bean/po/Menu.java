package com.lancabbage.lancodeapi.bean.po;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

public class Menu {
    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 菜单名称
     */
    @Column(name = "menu_name")
    private String menuName;

    /**
     * controller
     */
    @Column(name = "class_name")
    private String className;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 父菜单ID
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 当前菜单对应API ID
     */
    @Column(name = "api_id")
    private Integer apiId;

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
     * 获取菜单名称
     *
     * @return menu_name - 菜单名称
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * 设置菜单名称
     *
     * @param menuName 菜单名称
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * 获取controller
     *
     * @return class_name - controller
     */
    public String getClassName() {
        return className;
    }

    /**
     * 设置controller
     *
     * @param className controller
     */
    public void setClassName(String className) {
        this.className = className;
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

    /**
     * 获取父菜单ID
     *
     * @return parent_id - 父菜单ID
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父菜单ID
     *
     * @param parentId 父菜单ID
     */
    public Menu setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    public Integer getApiId() {
        return apiId;
    }

    public void setApiId(Integer apiId) {
        this.apiId = apiId;
    }

    /**
     * 获取分支ID
     *
     * @return branch_id - 分支ID
     */
    public Integer getBranchId() {
        return branchId;
    }

    /**
     * 设置分支ID
     *
     * @param branchId 分支ID
     */
    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }
}
