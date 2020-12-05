package com.lancabbage.lancodeapi.bean.po;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
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
     * 菜单名称
     */
    @Column(name = "menu_name")
    private String menuName;
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