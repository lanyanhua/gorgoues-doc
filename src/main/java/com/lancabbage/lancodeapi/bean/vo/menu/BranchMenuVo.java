package com.lancabbage.lancodeapi.bean.vo.menu;

import com.lancabbage.lancodeapi.bean.vo.api.ApiInfoVo;
import com.lancabbage.lancodeapi.bean.vo.classInfo.ClassInfoVo;

import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/6 8:59 下午
 * @Description:
 */
public class BranchMenuVo {

    /**
     * 菜单数据
     */
    private List<MenuVo> menuList;

    /**
     * API数据
     */
    private List<ApiInfoVo> apiInfoList;

    /**
     * classI数据
     */
    private List<ClassInfoVo> classInfoList;

    public List<MenuVo> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuVo> menuList) {
        this.menuList = menuList;
    }

    public List<ApiInfoVo> getApiInfoList() {
        return apiInfoList;
    }

    public void setApiInfoList(List<ApiInfoVo> apiInfoList) {
        this.apiInfoList = apiInfoList;
    }

    public List<ClassInfoVo> getClassInfoList() {
        return classInfoList;
    }

    public void setClassInfoList(List<ClassInfoVo> classInfoList) {
        this.classInfoList = classInfoList;
    }
}
