package com.lancabbage.lancodeapi.service.impl;

import com.lancabbage.lancodeapi.bean.dto.ApiInfoDto;
import com.lancabbage.lancodeapi.bean.dto.MenuDto;
import com.lancabbage.lancodeapi.bean.po.Menu;
import com.lancabbage.lancodeapi.map.MenuDtoToVo;
import com.lancabbage.lancodeapi.mapper.MenuMapper;
import com.lancabbage.lancodeapi.service.ApiInfoService;
import com.lancabbage.lancodeapi.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: lanyanhua
 * @date: 2020/12/6 12:40 上午
 * @Description:
 */
@Service
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;
    private final ApiInfoService apiInfoService;
    private final MenuDtoToVo menuDtoToVo;

    public MenuServiceImpl(MenuMapper menuMapper, ApiInfoService apiInfoService, MenuDtoToVo menuDtoToVo) {
        this.menuMapper = menuMapper;
        this.apiInfoService = apiInfoService;
        this.menuDtoToVo = menuDtoToVo;
    }

    @Transactional
    @Override
    public void saveMenuList(List<MenuDto> menuDtoList, Integer projectId, Integer branchId) {
        //新增默认菜单
        Menu menu = new Menu();
        menu.setBranchId(branchId);
        menu.setMenuName("默认");
        menu.setCreateTime(new Date());
        menuMapper.insertSelective(menu);
        for (MenuDto cMenu : menuDtoList) {
            //父ID
            cMenu.setParentId(menu.getId());
            cMenu.setCreateTime(new Date());
            menuMapper.insertSelective(cMenu);
            //先保存API信息
            List<ApiInfoDto> apiInfos = cMenu.getApiInfos();
            apiInfoService.saveApiList(apiInfos,projectId,branchId);
            List<Menu> menuList = apiInfos.stream().map(i -> {
                Menu m = new Menu();
                m.setParentId(cMenu.getId());
                m.setCreateTime(new Date());
                m.setApiId(i.getId());
                m.setMenuName(i.getName());
                return m;
            }).collect(Collectors.toList());
            menuMapper.insertList(menuList);
        }
    }
}
