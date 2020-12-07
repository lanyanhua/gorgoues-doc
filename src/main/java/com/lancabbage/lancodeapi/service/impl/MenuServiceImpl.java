package com.lancabbage.lancodeapi.service.impl;

import com.lancabbage.lancodeapi.bean.dto.ApiInfoDto;
import com.lancabbage.lancodeapi.bean.dto.MenuDto;
import com.lancabbage.lancodeapi.bean.po.Menu;
import com.lancabbage.lancodeapi.bean.vo.menu.MenuVo;
import com.lancabbage.lancodeapi.map.MenuDtoToVo;
import com.lancabbage.lancodeapi.mapper.MenuMapper;
import com.lancabbage.lancodeapi.service.ApiInfoService;
import com.lancabbage.lancodeapi.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
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
            if(apiInfos.isEmpty()){
                continue;
            }
            apiInfoService.saveApiList(apiInfos, projectId, branchId);
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

    @Override
    public List<MenuVo> listMenuByBranchId(Integer branchId) {
        //查询菜单数据
        Example example = new Example(Menu.class);
        example.createCriteria().andEqualTo("branchId", branchId);
        List<Menu> menuList = menuMapper.selectByExample(example);
        List<MenuVo> menuVoList = menuDtoToVo.listMenuToVo(menuList);

        //查询子级菜单
        setChildrenMenu(menuVoList);
        return menuVoList;
    }

    /**
     * 赋值子菜单
     */
    private void setChildrenMenu(List<MenuVo> menuList) {
        //查询子菜单
        List<Integer> idList = menuList.stream().map(MenuVo::getId).collect(Collectors.toList());
        Example example = new Example(Menu.class);
        example.createCriteria().andIn("parentId", idList);
        List<Menu> menuList1 = menuMapper.selectByExample(example);
        if (menuList1.isEmpty()) {
            return;
        }
        //赋值子菜单
        List<MenuVo> childrenList = new ArrayList<>();
        for (MenuVo menu : menuList) {
            List<Menu> children = menuList1.stream().filter(i -> menu.getId().equals(i.getParentId())).collect(Collectors.toList());
            if (children.isEmpty()) {
                menu.setChildrenMenu(new ArrayList<>(0));
                continue;
            }
            List<MenuVo> menuVos = menuDtoToVo.listMenuToVo(children);
            menu.setChildrenMenu(menuVos);
            childrenList.addAll(menuVos);
            //查询API
        }
        //赋值下一级子菜单
        setChildrenMenu(childrenList);
    }
}
