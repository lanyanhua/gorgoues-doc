package com.lancabbage.lancodeapi.service;

import com.lancabbage.lancodeapi.bean.dto.MenuDto;

import java.util.List;

/**
 * 菜单service
 * @author: lanyanhua
 * @date: 2020/12/6 12:36 上午
 * @Description:
 */
public interface MenuService {
    /**
     * 保存菜单
     * @param menuDtoList 菜单
     * @param projectId 项目ID
     * @param branchId 分支ID
     */
    void saveMenuList(List<MenuDto> menuDtoList, Integer projectId, Integer branchId);
}
