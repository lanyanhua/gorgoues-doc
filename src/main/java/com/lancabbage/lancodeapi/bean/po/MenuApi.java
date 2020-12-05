package com.lancabbage.lancodeapi.bean.po;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "menu_api")
public class MenuApi {
    /**
     * ID
     */
    @Id
    private Integer id;

    /**
     * 菜单ID
     */
    @Column(name = "menu_id")
    private Integer menuId;

    /**
     * API ID
     */
    @Column(name = "api_id")
    private Integer apiId;

    /**
     * 优先级大于API名称
     */
    @Column(name = "api_name")
    private Integer apiName;

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
     * 获取API ID
     *
     * @return api_id - API ID
     */
    public Integer getApiId() {
        return apiId;
    }

    /**
     * 设置API ID
     *
     * @param apiId API ID
     */
    public void setApiId(Integer apiId) {
        this.apiId = apiId;
    }

    /**
     * 获取优先级大于API名称
     *
     * @return api_name - 优先级大于API名称
     */
    public Integer getApiName() {
        return apiName;
    }

    /**
     * 设置优先级大于API名称
     *
     * @param apiName 优先级大于API名称
     */
    public void setApiName(Integer apiName) {
        this.apiName = apiName;
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