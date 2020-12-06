package com.lancabbage.lancodeapi.bean.vo.menu;

import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/6 6:31 下午
 * @Description:
 */
public class MenuVo {

    private Integer id;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * controller
     */
    private String className;

    /**
     * 当前菜单对应API ID
     */
    private Integer apiId;

    /**
     * 子菜单
     */
    private List<MenuVo> childrenMenu;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getApiId() {
        return apiId;
    }

    public void setApiId(Integer apiId) {
        this.apiId = apiId;
    }

    public List<MenuVo> getChildrenMenu() {
        return childrenMenu;
    }

    public void setChildrenMenu(List<MenuVo> childrenMenu) {
        this.childrenMenu = childrenMenu;
    }

}
