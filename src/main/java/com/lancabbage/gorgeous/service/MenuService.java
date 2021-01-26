package com.lancabbage.gorgeous.service;

import com.lancabbage.gorgeous.bean.dto.MenuDto;
import com.lancabbage.gorgeous.bean.po.Menu;

import java.util.List;
import java.util.Map;

/**
 * 菜单service
 *
 * @author: lanyanhua
 * @date: 2020/12/6 12:36 上午
 * @Description:
 */
public interface MenuService {
    /**
     * 新增菜单
     *  @param menuDtoList 菜单
     * @param branchId    分支ID
     */
    void addMenuList(Map<String, List<MenuDto>> menuDtoList, Integer branchId);

    /**
     * 保存菜单信息
     *  @param menuDtoList 菜单
     * @param branchId    分支ID
     */
    void saveMenuList(Map<String, List<MenuDto>> menuDtoList, Integer branchId);

    /**
     * 查询菜单
     *
     * @param branchId 分支ID
     * @return 菜单列表 API信息
     */
    List<MenuDto> listMenuByBranchId(Integer branchId);

    /**
     * 查询API关联的菜单
     *
     * @param aIds API ID
     */
    List<Menu> listApiMenuByApiId(List<Integer> aIds);


    /**
     * 删除菜单
     * @param id 分支ID
     */
    void deleteByBranchId(List<Integer> id);

}
