package com.lancabbage.gorgeous.bean.dto;

import com.lancabbage.gorgeous.bean.po.Menu;

import java.util.List;

/**
 * @ClassName: MenuDto
 * @Description:TODO ()
 * @author: lanyanhua
 * @date: 2020/12/2 6:47 下午
 * @Copyright:
 */
public class MenuDto extends Menu {


    private List<MenuDto> childrenMenu;
    private List<ApiInfoDto> apiInfos;

    public List<MenuDto> getChildrenMenu() {
        return childrenMenu;
    }

    public void setChildrenMenu(List<MenuDto> childrenMenu) {
        this.childrenMenu = childrenMenu;
    }

    public List<ApiInfoDto> getApiInfos() {
        return apiInfos;
    }

    public void setApiInfos(List<ApiInfoDto> apiInfos) {
        this.apiInfos = apiInfos;
    }
}
