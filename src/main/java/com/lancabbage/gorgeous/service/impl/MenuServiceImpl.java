package com.lancabbage.gorgeous.service.impl;

import com.lancabbage.gorgeous.bean.dto.ApiInfoDto;
import com.lancabbage.gorgeous.bean.dto.MenuDto;
import com.lancabbage.gorgeous.bean.po.ApiInfo;
import com.lancabbage.gorgeous.bean.po.Menu;
import com.lancabbage.gorgeous.map.MenuDtoToVo;
import com.lancabbage.gorgeous.mapper.MenuMapper;
import com.lancabbage.gorgeous.service.ApiInfoService;
import com.lancabbage.gorgeous.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    public void addMenuList(Map<String, List<MenuDto>> menuDtoList, Integer branchId) {
        menuDtoList.forEach((k, v) -> {
            String menuName = k;
            if (menuDtoList.size() == 1) {
                menuName = "默认";
            }
            addBranchMenu(menuName, v, branchId);
        });
    }


    @Transactional
    @Override
    public void saveMenuList(Map<String, List<MenuDto>> menuDtoList, Integer branchId) {
        menuDtoList.forEach((k, v) -> {
            String menuName = k;
            if (menuDtoList.size() == 1) {
                menuName = "默认";
            }
            Example example = new Example(Menu.class);
            example.createCriteria().andEqualTo("branchId", branchId)
                    .andEqualTo("menuName", menuName);
            Menu menu = menuMapper.selectOneByExample(example);
            if (menu == null) {
                //为空新增当前菜单
                addBranchMenu(menuName, v, branchId);
                return;
            }
            //原来的controller 菜单
            example = new Example(Menu.class);
            example.createCriteria().andEqualTo("parentId", menu.getId());
            List<Menu> menus = menuMapper.selectByExample(example);
            //删除原来的菜单
            menuMapper.deleteByExample(example);
            //加载当前分支下所有的API API关联的菜单
            List<ApiInfo> apiList = apiInfoService.listApiInfoByBranchId(branchId);
            List<Integer> aIds = apiList.stream().map(ApiInfo::getId).collect(Collectors.toList());
            List<Menu> apiMenus = listApiMenuByApiId(aIds);
            //修改
            for (MenuDto cMenu : v) {
                //controller级别重新新增 父ID
                cMenu.setParentId(menu.getId());
                cMenu.setCreateTime(new Date());
                menuMapper.insertSelective(cMenu);
                List<ApiInfoDto> apiInfos = cMenu.getApiInfos();
                if (apiInfos.isEmpty()) {
                    continue;
                }
                //修改
                List<ApiInfoDto> apiInfoAdds = new ArrayList<>();
                for (ApiInfoDto a : apiInfos) {
                    List<Menu> collect = apiMenus.stream()
                            .filter(i -> i.getApiId().equals(a.getId()))
                            .collect(Collectors.toList());
                    //不存在 就新增
                    if (collect.isEmpty()) {
                        apiInfoAdds.add(a);
                        continue;
                    }
                    //存在修改当前API信息 已经关联的菜单信息 关联菜单是默认菜单的修改
                    // 1.当前API 修改名称 2.同时判断是否是默认菜单修改为新菜单
                    collect.stream().peek(i -> {
                                //修改名称
                                i.setMenuName(a.getName());
                                //父菜单是默认菜单修改为新菜单
                                if (menus.stream().anyMatch(p -> p.getId().equals(i.getParentId()))) {
                                    i.setParentId(cMenu.getId());
                                }
                            })
                            .forEach(menuMapper::updateByPrimaryKeySelective);
                }
                //新增菜单
                saveApiMenu(cMenu, apiInfoAdds);
            }
        });
    }


    @Override
    public List<MenuDto> listMenuByBranchId(Integer branchId) {
        //查询菜单数据
        Example example = new Example(Menu.class);
        example.createCriteria().andEqualTo("branchId", branchId);
        List<Menu> menuList = menuMapper.selectByExample(example);
        List<MenuDto> menuVoList = menuDtoToVo.listMenuToDto(menuList);
        //查询子级菜单
        setChildrenMenu(menuVoList);
        return menuVoList;
    }

    @Override
    public List<Menu> listApiMenuByApiId(List<Integer> aIds) {
        Example example = new Example(Menu.class);
        example.createCriteria().andIn("apiId", aIds);
        return menuMapper.selectByExample(example);
    }

    @Override
    public void deleteByBranchId(List<Integer> id) {
        Example example = new Example(Menu.class);
        example.createCriteria().andIn("branchId", id);
        List<Menu> menuList = menuMapper.selectByExample(example);
        deleteChildrenMenu(menuList);
    }

    /**
     * 添加分支菜单
     * @param menuName 菜单名称
     * @param v 菜单
     * @param branchId 分支ID
     */
    private void addBranchMenu(String menuName, List<MenuDto> v, Integer branchId) {
        //新增默认菜单
        Menu menu = new Menu();
        menu.setBranchId(branchId);
        menu.setMenuName(menuName);
        //赋值ID
        menu.setCreateTime(new Date());
        menuMapper.insertSelective(menu);
        for (MenuDto cMenu : v) {
            //父ID
            cMenu.setParentId(menu.getId());
            cMenu.setCreateTime(new Date());
            menuMapper.insertSelective(cMenu);
            //先保存API信息
            List<ApiInfoDto> apiInfos = cMenu.getApiInfos();
            if (apiInfos.isEmpty()) {
                continue;
            }
            saveApiMenu(cMenu, apiInfos);
        }
    }

    /**
     * 保存API菜单
     *
     * @param cMenu    父ID
     * @param apiInfos API信息
     */
    private void saveApiMenu(MenuDto cMenu, List<ApiInfoDto> apiInfos) {
        if (apiInfos.isEmpty()) {
            return;
        }
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

    /**
     * 赋值子菜单 每次加载一级的子菜单，这样递归遍历的次数是菜单的深度 减少递归查库的次数
     */
    private void setChildrenMenu(List<MenuDto> menuList) {
        //查询子菜单
        List<Integer> idList = menuList.stream().map(MenuDto::getId).collect(Collectors.toList());
        Example example = new Example(Menu.class);
        example.createCriteria().andIn("parentId", idList);
        List<Menu> menuList1 = menuMapper.selectByExample(example);
        if (menuList1.isEmpty()) {
            return;
        }
        //赋值子菜单
        List<MenuDto> childrenList = new ArrayList<>();
        for (MenuDto menu : menuList) {
            List<Menu> children = menuList1.stream().filter(i -> menu.getId().equals(i.getParentId())).collect(Collectors.toList());
            if (children.isEmpty()) {
                menu.setChildrenMenu(new ArrayList<>(0));
                continue;
            }
            List<MenuDto> menuVos = menuDtoToVo.listMenuToDto(children);
            menu.setChildrenMenu(menuVos);
            childrenList.addAll(menuVos);
            //查询API
        }
        //赋值下一级子菜单
        setChildrenMenu(childrenList);
    }

    /**
     * 删除子菜单
     */
    private void deleteChildrenMenu(List<Menu> menuList) {
        List<Integer> idList = menuList.stream().map(Menu::getId).collect(Collectors.toList());
        //删除
        Example exampleD = new Example(Menu.class);
        exampleD.createCriteria().andIn("id",idList);
        menuMapper.deleteByExample(exampleD);
        //查询子菜单
        Example example = new Example(Menu.class);
        example.createCriteria().andIn("parentId", idList);
        List<Menu> menuList1 = menuMapper.selectByExample(example);
        if (menuList1.isEmpty()) {
            return;
        }
        //递归继续
        deleteChildrenMenu(menuList1);
    }
}
