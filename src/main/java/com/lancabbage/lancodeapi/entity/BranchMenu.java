package com.lancabbage.lancodeapi.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "branch_menu")
public class BranchMenu {
    /**
     * ID
     */
    @Id
    private Integer id;

    /**
     * 分支ID
     */
    @Column(name = "branch_id")
    private Integer branchId;

    /**
     * 菜单ID
     */
    @Column(name = "menu_id")
    private Integer menuId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

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

    /**
     * 获取菜单ID
     *
     * @return menu_id - 菜单ID
     */
    public Integer getMenuId() {
        return menuId;
    }

    /**
     * 设置菜单ID
     *
     * @param menuId 菜单ID
     */
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
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
}